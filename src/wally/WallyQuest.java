package wally;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WallyQuest extends Canvas {
	private BufferStrategy strategy;
	
	private int area = 0;
	private int person_area = -1;
	private int time_left = 200;
	private int selected_student =  -1;
	//0 - Nothing | 1 - Colour | 2 - Hqs | 3 - Game | 4 - Food | 5 - Guess
	private int dialog_option = 0;
	//0 - Running | 1 - Victory | 2 - Game Over | 3 - Close
	private int game_state = 0;

	private String[] names = new String[] {"angelo", "brian", "catarina", "chico", "elias", "elisa", "evaldo", "felix", "francisco", "geraldo", "katia", "lisa", "mario", "roberto", "sara"};
	private String[] colours = new String[] { "Azul", "Laranja", "Vermelho", "Preto", "Marrom", "Cinza", "Rosa", "Verde"};
	private String[] hair = new String[] {"Careca", "Preto", "Loiro", "Ruivo", "Castanho"};
	private String[] hqs = new String[] {"Dare Devil", "Preacher", "Punisher", "Avengers", "Spider-Man", "SandMan", "Conan", "Batman"};
	private String[] game = new String[] {"God of War", "Mortal Kombat", "Spyro", "Tibia", "Pou", "The Sims", "Rachet and Clank", "World of Warcraft", "Silent Hill"};
	private String[] food = new String[] {"Macarrão", "Pizza", "Chips", "Taco", "Feijão", "Sushi", "Banana"};
	private String resposta = "";
	private String respostaFinal = "";
		
	private Image image_state;
	private Image timeCheck;
	private Image lista;
	private Image listaAchados;
	private Image dialogo;
	
	private Random random;

	private List <Student> students;
	private List <Integer> found;
	
	//0 - Time | 1 - List | 2 - Found | 3 - School
	private Boolean[] hud_control = new Boolean[] {false, false, false, false};	
	private boolean talking = false;
	private boolean adivinhar = false;
	private boolean text_control = false;
	
	public WallyQuest() {
		JFrame container = new JFrame("Wally's Quest");

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(1000, 780));
		panel.setLayout(null);

		setBounds(0, 0, 1000, 780);
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		addMouseListener(new mouseInputHandler());
		addKeyListener(new keyInputHandler());
		
		init();
		
		container.setVisible(true);

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

	private void init() {
		//Loading all required images
		image_state = loadImage("map");
		timeCheck = loadImage("timecheck");
		lista = loadImage("listab");
		listaAchados = loadImage("listaachados");
		dialogo = loadImage("dialogo");

		students = new ArrayList<Student>();
		found = new ArrayList<Integer>();
		random = new Random();
		
		createStudents();
	}
	
	public void createStudents() {
		Image sprite = null;
		Integer sex = null;
		String name = null;
		String color = null;
		String hair = null;
		String hqs = null;
		String game = null;
		String food = null;
		Boolean isStudent = null;
		
		List <Student> students_aux = new ArrayList<>(students.size());;
				
		for (int i = 0; i < names.length; i++) {
			name = this.names[i];
			color = this.colours[random.nextInt(7)];
			hair = this.hair[random.nextInt(4)];
			hqs = this.hqs[random.nextInt(7)];
			game = this.game[random.nextInt(8)];
			food = this.food[random.nextInt(6)];
			sex = this.checkSex(name);
			sprite = loadImage(name);
			isStudent = random.nextBoolean();
			
			Student new_student = new Student(i, sprite, sex, name, color, hair, hqs, game, food, isStudent);
			students.add(new_student);
			students_aux.add(null);

		}

		int control = 0;
		//Change students list position
		while (control < 15) {
			int random_aux = random.nextInt(15);
			Student new_student = students.get(random_aux);
			
			for (int i = 0; i < students_aux.size(); i++) {
				if (students_aux.get(i) == null ) {
					students_aux.set(i, new_student);
					students_aux.get(i).setId(i);
					control++;
					break;
				} else if (new_student.getName() == students_aux.get(i).getName()) {
					break;
				}
			}			
		}
		
		students = students_aux;
		
	}

	
	public Integer checkSex(String name) {
		String[] girls_names = new String[] {"catarina", "elisa", "katia", "lisa", "sara"};

		for (int i = 0; i < girls_names.length; i++) {
			if (name == girls_names[i]) {
				return 1;
			}
		}
		return 0;
	}
	
	public Image loadImage(String name) {
		Image image = Toolkit.getDefaultToolkit().getImage(name + ".png");
		return image;
	}
	
	public void gameLoop() {
		while(game_state < 3) {
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			//Draw main map
			g.drawImage(image_state, 0, 0, null);	

			if (game_state == 1) {
				image_state = loadImage("suemdonsaWin");
			} else if (game_state == 2) {
				image_state = loadImage("gameOver");
			} else if (area == 0) {
				image_state = loadImage("map");
			} else if (area > 0) {
				image_state = loadImage("area");
				
				students.get(person_area).setX(175);
				students.get(person_area-1).setX(416);
				students.get(person_area-2).setX(754);
				
				students.get(person_area).setY(587);
				students.get(person_area-1).setY(360);
				students.get(person_area-2).setY(562);
				
				g.drawImage(students.get(person_area).getSprite(), 175, 587, null);
				g.drawImage(students.get(person_area-1).getSprite(), 416, 360, null);
				g.drawImage(students.get(person_area-2).getSprite(), 754, 562, null);
				
				//Drawing the characters inside the dialog screen
				if (talking) {
					g.drawImage(dialogo, 300, 234, null);
					g.drawImage(students.get(selected_student).getSprite(), 500, 350, null);
				}
				//Writing out the answers to each question
				g.setFont(new Font("Arial", Font.BOLD, 18));
				if (dialog_option == 1) {
					g.drawString(students.get(selected_student).getColor(), 632, 310);
				} else if (dialog_option == 2) {
					g.drawString(students.get(selected_student).getHq(), 632, 310);
				} else if (dialog_option == 3) {
					g.drawString(students.get(selected_student).getGame(), 632, 310);
				} else if (dialog_option == 4) {
					g.drawString(students.get(selected_student).getFood(), 632, 310);
				} else if (adivinhar) {
					String check_guess = guess();
					
					switch (check_guess) {
					case "not_student":
						int text_x = 640;
						g.setFont(new Font ("Arial", Font.BOLD, 12));
						g.drawString("Prazer em te conhecer,", text_x, 280);
						g.drawString("eu sou " +students.get(selected_student).getName()+ ".", text_x, 300);
						g.drawString("Desculpe mas eu", text_x, 320);
						g.drawString("não estudo", text_x, 340);
						g.drawString("no Suemdonsa.", text_x, 360);
						break;
					case "found_student":
						g.setFont(new Font ("Arial", Font.BOLD, 17));
						g.drawString("Voce já encontrou", 633, 305);
						g.drawString("esta pessoa!", 635, 320);
						break;
					case "default":
						g.setFont(new Font ("Arial", Font.BOLD, 20));
						g.drawString("Digite o nome", 635, 290);
						g.drawString("e aperte enter", 635, 315);
						g.drawString(resposta, 657, 360);
						break;
					case "correct":
						g.setFont(new Font ("Arial", Font.BOLD, 35));						
						g.drawString("Voce encontrou:", 630, 305);
						g.drawString(students.get(selected_student).getName() + "!", 635, 340);
						found.add(selected_student);
						break;
					case "incorrect":
						g.setFont(new Font ("Arial", Font.BOLD, 30));
						g.drawString("Incorreto!", 635, 300);
						break;
					}
				}
			} 
			
			//Drawing time check
			 if (hud_control[0]) {
			 g.drawImage(timeCheck, 0, 280, null);
			 g.setFont(new Font("Arial", Font.BOLD, 22));
			 	if (time_left >= 100) {
			 		g.drawString(String.valueOf(time_left), 96, 380);
			 	} else {
			 		g.drawString(String.valueOf(time_left), 101, 380);
			 	}
			 }
			 
			//Drawing the List
			if (hud_control[1]) {
				int j = 50;
				List <String> girls_tips = new ArrayList<>();
				List <String> boys_tips = new ArrayList<>();
				List <String> students_tips = new ArrayList<>();

				//Separate girls from boys
				for (int i = 0; i < students.size(); i++) {
					if (students.get(i).getSex() == 1) {
						girls_tips.add(students.get(i).getName());
						girls_tips.add("Cor: " + students.get(i).getColor() + " | Cabelo: " + students.get(i).getHair() + " | HQ: " + students.get(i).getHq() + " | Jogo: " + students.get(i).getGame() + " | Comida: " + students.get(i).getFood());
					} else {
						boys_tips.add(students.get(i).getName());
						boys_tips.add("Cor: " + students.get(i).getColor() + " | Cabelo: " + students.get(i).getHair() + " | HQ: " + students.get(i).getHq()  + " | Jogo: " + students.get(i).getGame() + " | Comida: " + students.get(i).getFood());
					}
					students_tips.add(students.get(i).getName());
					students_tips.add("Cor: " + students.get(i).getColor() + " | Cabelo: " + students.get(i).getHair() + " | HQ: " + students.get(i).getHq() + " | Jogo: " + students.get(i).getGame() + " | Comida: " + students.get(i).getFood());
				} 
				
				int control = 0;
				boolean all_girls_in = false;
				while (control <= students.size()) {
					if (control < students.size() && !all_girls_in) {
						
					}
				}
					
				g.drawImage(lista, 230, 15, null);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawString("Garotas", 235, j);				

				g.setFont(new Font("Arial", Font.BOLD, 11));
				for (int i = 0; i < girls_tips.size(); i++) {
					g.drawString(girls_tips.get(i), 235, j + 14);
					j += 14;
				}
				j += 28;
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawString("Garotos", 235, j);
				g.setFont(new Font("Arial", Font.BOLD, 11));
				for (int i = 0; i < boys_tips.size(); i++) {
					g.drawString(boys_tips.get(i), 235, j + 14);
					j += 14;
				}
			}
				
			//Drawing the List of found students
			if (hud_control[2]) {
				int j = 410;
				g.drawImage(listaAchados, 740, 335, null);
				g.setFont(new Font("Arial", Font.BOLD, 32));
				g.drawString("Alunos encontrados:", 745, 375);
				
				for (int i = 1; i < found.size(); i++) {
					g.setFont(new Font("Arial", Font.BOLD, 34));
					g.drawString(students.get(found.get(i)).getName(), 755, j);
					j += 28;
				}
			}
			
			 //Drawing the school (suemdonsa) screen
			if (hud_control[3]){
				g.setFont(new Font ("Arial", Font.BOLD, 70));
				g.drawString(String.valueOf(10), 890, 365);
			} 
			
			if(time_left < 1) {
				game_state = 2;
			}
			
			g.dispose();
			strategy.show();
			
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	public String guess() {
		for (int i = 0; i < found.size(); i++) {
			if (found.get(i) == selected_student) {
				return "found_student";
			}
		}
		System.out.println("response: " + respostaFinal + " |student: " + students.get(selected_student).getName());
		if (students.get(selected_student).getIsStudent() == false) {
			return "not_student";
		} else if (respostaFinal.equals(students.get(selected_student).getName())) {
			return "correct";
		} else if (!respostaFinal.equals(students.get(selected_student).getName())) {
			return "incorrect";
		}
		return "default";
	}
	
	public void resetState(String screen) {
		switch (screen) {
		case "out_of_bounds":
			talking = false;
			adivinhar = false;
			selected_student = -1;
			dialog_option = 0;
			break;
		
		case "school_reset":
			hud_control[0] = false;
			hud_control[1] = false;
			hud_control[2] = false;
			talking = false;
			selected_student = -1;
			area = 0;
			image_state = loadImage("suemdonsa");
			break;
		}
	}
	
	public static void main(String[] args) {
		WallyQuest ig = new WallyQuest();
		ig.gameLoop();
	}
	
	public class mouseInputHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);

			if (!hud_control[3]) {
				//Time
				if (e.getX () >= 337 && e.getX () <= 382 && e.getY () >= 707 && e.getY () <= 754) {
					hud_control[0] = !hud_control[0];
				} else if (e.getX () >= 152 && e.getX () <= 177 && e.getY () >= 290 && e.getY () <= 313 && hud_control[0]) {
					//Checking if you clicked on the "X" on the watch to close it
					hud_control[0] = false;
				}
				
				//List
				if (e.getX () >= 436 && e.getX () <= 471 && e.getY () >= 718 && e.getY () <= 762) {
					hud_control[1] = !hud_control[1];	
				} else if (e.getX () >= 675 && e.getX () <= 707 && e.getY () >= 18 && e.getY () <= 47 && hud_control[1]){
					//Checking if you clicked on the "X" in the list to close it
					hud_control[1] = false;
				}
				
				//List of Found Students
				if (e.getX () >= 512 && e.getX () <= 544 && e.getY () >= 712 && e.getY () <= 762) {
					hud_control[2] = !hud_control[2];	
				} else if (e.getX () >= 967 && e.getX () <= 984 && e.getY () >= 335 && e.getY () <= 352 && hud_control[2]) {
					//Checking if you clicked on the "X" in the Found students page to close it
					hud_control[2] = false;	
				}		
			}
			//School
			if (e.getX () >= 602 && e.getX () <= 639 && e.getY () >= 695 && e.getY () <= 755) {
				resetState("resetState");
				hud_control[3] = !hud_control[3];				
			} 
			
			//Check to see if you clicked the "EXIT" button on the game over screen
			if (e.getX () >= 355 && e.getX () <= 639 && e.getY () >= 575 && e.getY () <= 741 && game_state == 2){
				System.exit(0);
			}
			
			
			if (area > 0) {
				//Back button
				if (e.getX () >= 847 && e.getX () <= 1000 && e.getY () >= 0 && e.getY () <= 60) {
					if (hud_control[3]) {
						hud_control[3] = false;
					} else {
						area = 0;
					}
				}
				
				//People in the map
				if (!talking) {
					for (int i = person_area; i >= person_area - 2; i--) {
						if (e.getX () >= students.get(i).getX() && e.getX () <= students.get(i).getX() + students.get(i).getSprite().getWidth(null)
								&& e.getY () >= students.get(i).getY() && e.getY () <= students.get(i).getY() + students.get(i).getSprite().getHeight(null)) {
							selected_student = person_area - (person_area - i);
							break;
						}
					}
					//Decreases time
					if (selected_student != -1) {
						time_left -= 3;
						talking = true;
					}
					
				} else {
					this.questionButtonClick(e);
					
					//Check if clicked out of student's screen bounds
					if (e.getX() <= 300 || e.getX() >= 300 + dialogo.getWidth(null) || e.getY() <= 234 || e.getY() >= 234 + dialogo.getHeight(null)) {
						resetState("out_of_bounds");
					}
				}
				
				System.out.println("x: " + e.getX() +" Y:"+ e.getY());
				if (e.getX() >= 627 && e.getX() <= 782 && e.getY() >= 253 && e.getY() <= 372 && !text_control) {
					text_control = false;
				}
				
			} else {			
				this.areaClick(e);
			}			
		}

		//Question Buttons
		public void questionButtonClick(MouseEvent e) {
			dialog_option = 0;

			if (e.getX () >= 602 && e.getX () <= 687 && e.getY () >= 387 && e.getY () <= 437) {
				dialog_option = 1;
			//Clicking this button will ask this person what their favourite Comic book is, this will waste some time
			} else if (e.getX () >= 692 && e.getX () <= 785 && e.getY () >= 390 && e.getY () <= 437) {
				dialog_option = 2;
			//Clicking this button will ask this person what their favourite video game is, this will waste some time
			} else if (e.getX () >= 597 && e.getX () <= 692 && e.getY () >= 449 && e.getY () <= 494) {
				dialog_option = 3;
			//Clicking this button will ask this person what their favourite food is, this will waste some time
			} else if (e.getX () >= 692 && e.getX () <= 782 && e.getY () >= 450 && e.getY () <= 497) {
				dialog_option = 4;
			//Clicking this button will allow you to guess who you're talking to, this will not waste time
			} else if (e.getX () >= 600 && e.getX () <= 777 && e.getY () >= 509 && e.getY () <= 547) {
				//resposta = "";
				adivinhar = true;
			} else {
				time_left += 1;
			}
			time_left -= 1;
			
		}
		
		//Areas
		public void areaClick(MouseEvent e) {
			if (e.getX () >= 372 && e.getX () <= 414 && e.getY () >= 0 && e.getY () <= 142) {
				//North
				area = 1;
			} else if (e.getX () >= 372 && e.getX () <= 444 && e.getY () >= 220 && e.getY () <= 312) {
				//Center
				area = 2;
			} else if (e.getX () >= 232 && e.getX () <= 332 && e.getY () >= 325 && e.getY () <= 407) {
				//West
				area = 3;
			} else if (e.getX () >= 527 && e.getX () <= 565 && e.getY () >= 350 && e.getY () <= 405) {
				//East
				area = 4;
			} else if (e.getX () >= 380 && e.getX () <= 452 && e.getY () >= 565 && e.getY () <= 624) {
				//South
				area = 5;
			} 
			
			if (area > 0) {
				time_left -= 10;
				person_area = ((area - 1) * 3 ) + 2;
			}
			
		}
			
	}	
				
	private class keyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			
			if (!talking) {
				if (!hud_control[3]) {
					if (e.getKeyChar() == KeyEvent.VK_1) {
						hud_control[0] = !hud_control[0];
					} else if (e.getKeyChar() == KeyEvent.VK_2) {
						hud_control[1] = !hud_control[1];
					} else if (e.getKeyChar() == KeyEvent.VK_3) {
						hud_control[2] = !hud_control[2];
					} 
				}
					
				if (e.getKeyChar() == KeyEvent.VK_4) {
					resetState("school_reset");
					hud_control[3] = !hud_control[3];
				}
				
			} 
			
			if (adivinhar) {
				resposta += e.getKeyChar();
				
				if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
					if (resposta == "") {
						resposta = "";
					} else {
						resposta.substring(1, resposta.length() - 1);
					}
				} else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					respostaFinal = resposta;
					resposta = "";
				}
			} else if (talking) {
				if (e.getKeyChar() == KeyEvent.VK_1) {
					dialog_option = 1;
				} else if (e.getKeyChar() == KeyEvent.VK_2) {
					dialog_option = 2;
				} else if (e.getKeyChar() == KeyEvent.VK_3) {
					dialog_option = 3;
				} else if (e.getKeyChar() == KeyEvent.VK_4) {
					dialog_option = 4;
				} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					resetState("out_of_bounds");
				}
			} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
				if (area > 0 && !hud_control[0] && !hud_control[1] && !hud_control[2]) {						
					area = 0;
				}
				hud_control = new Boolean[] {false, false, false, false};	
			} 
			
		}	

	}
}