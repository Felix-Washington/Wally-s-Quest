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

import javax.swing.JFrame;
import javax.swing.JPanel;

import wally.WallyQuest;

@SuppressWarnings("serial")
public class WallyQuest extends Canvas {
	private BufferStrategy strategy;
	private boolean gameRunning = true;
	
	private int area = 0;
	private int pessoa = -1;
	private int pessoaArea = 0;
	private int[] achados = new int[]{0,0,0,-2,-2,0,0,-2,-2,0,0,-2,0,0,0};	
	private int achadosNum = 0;
	private int tempoFinal = 200;
	private int victory = 0;
	private String[] achadosNome = new String[15];
	private String resposta = "";
	private String respostaFinal = "";
	
	//These arrays are all written in order so Felix's (nome[0]) favourite colour is Azul (cor[0])
	private String[] nome = new String[] {"felix", "roberto", "lisa", "Francisco", "Geraldo", "elisa", "catarina", "Elias", "Evaldo", "katia", "angelo", "Mario", "brian", "sara", "chico"};	

	private String[] cor = new String[] { "Azul", "Laranja", "Laranja", "Vermelho", "Azul", "Vermelho", "Preto", "Laranja", "Preto", "Azul", "Vermelho", "Laranja", "Preto", "Vermelho", "Azul"};
	
	//The code below became obsolete when we were able to show the actual caracters
	//private String[] cabelo = new String[] {"Careca", "Preto", "Loiro", "Ruivo", "Careca", "Loiro", "Preto", "Careca", "Preto", "Ruivo", "Castanho", "Loiro", "Castanho", "Ruivo", "Careca"};

	private String[] hq = new String[] {"Dare Devil", "Preacher", "Punisher", "Sandman", "Spider-Man", "Avengers", "Spider-Man", "SandMan", "Conan", "Punisher", "Preacher", "Spider-Man", "Batman", "Avengers", "Sandman"};
			
	private String[] jogo = new String[] {"God of War", "Mortal Kombat", "Spyro", "Tibia", "God of War", "Spyro", "Pou", "Tibia", "Mortal Kombat", "The Sims", "God of War", "Rachet and Clank", "World of Warcraft", "Silent Hill", "Rachet and Clank"};
			
	private String[] comida = new String[] {"Macarrão", "Pizza", "Chips", "Taco", "Feijão", "Sushi", "Macarrão", "Banana", "Chips", "Sushi", "Pizza", "Feijão", "Taco", "Sushi", "Banana"};
	
	private Image map;
	private Image timeCheck;
	private Image lista;
	private Image listaAchados;
	private Image norte;
	private Image centro;
	private Image oeste;
	private Image leste;
	private Image sul;
	private Image suemdonsa;
	private Image dialogo;
	private Image suemdonsaWin;
	private Image gameOver;
	

	private Image Felix;
	private Image Roberto;
	private Image Lisa;
	private Image Francisco;
	private Image Geraldo;
	private Image Elisa;
	private Image Catarina;
	private Image Elias;
	private Image Evaldo;
	private Image Katia;
	private Image Angelo;
	private Image Mario;
	private Image Brian;
	private Image Sara;
	private Image Chico;
	
	
	private boolean gameTerminate = false;
	
	private boolean clicouTempo = false;
	private boolean clicouLista = false;
	private boolean clicouAchados = false;
	private boolean clicouSuemdonsa = false;
	
	private boolean clicouNorte = false;
	private boolean clicouCentro = false;
	private boolean clicouOeste = false;
	private boolean clicouLeste = false;
	private boolean clicouSul = false;
	
	private boolean falando = false;
	private boolean correct = false;
	private boolean wrong = false;
	
	private boolean clicouCor = false;
	private boolean clicouHQ = false;
	private boolean clicouJogo = false;
	private boolean clicouComida = false;
	private boolean adivinhar = false;
	
	
	
	//List showing the girls and some of their characteristics
	private String[] listagarotas = new String[]{ "Garotas:",
			"",
			"Catarina",
			"Cor preferida: Preto | Cabelo: Preto | HQ preferida: Spider-Man",
			"Jogo Preferido: Pou | Comida preferida: Macarrão",
			"",
			"Elisa",
			"Cor preferida: Vermelho | Cabelo: Loura | HQ preferida: Avengers",
			"Jogo Preferido: Spyro | Comida preferida: Sushi",
			"",
			"Katia",
			"Cor preferida: Azul | Cabelo: Ruiva | HQ preferida: Punisher",
			"Jogo Preferido: The Sims | Comida preferida: Sushi",
			"",
			"Lisa",
			"Cor preferida: Laranja | Cabelo: Loura | HQ preferida: Punisher",
			"Jogo Preferido: Spyro | Comida preferida: Chips",
			"",
			"Sara",
			"Cor preferida: Vermelho | Cabelo: Ruiva | HQ preferida: Avengers",
			"Jogo Preferido: Silent Hill | Comida preferida: Sushi"};
	
	//List showing the boys and some of their characteristics
    //(split into 2 lists to be more manageable both for the player and the programmers)
	private String[] listagarotos = new String[] { "Garotos:",
			"",
			"Angelo",
			"Cor preferida: Vermelho | Cabelo: Castanho | HQ preferida: Preacher",
			"Jogo Preferido: God of War | Comida preferida: Pizza",
			"",
			"Brian",
			"Cor preferida: Preto | Cabelo: Castanha | HQ preferida: Batman",
			"Jogo Preferido: World of Warcraft | Comida preferida: Taco",
			"",
			"Chico",
			"Cor preferida: Azul | Cabelo: Careca | HQ preferida: Conan",
			"Jogo Preferido: Rachet and Clank | Comida preferida: Banana",
			"",
			"Felix",
			"Cor preferida: Azul | Cabelo: Careca | HQ preferida: Dare Devil",
			"Jogo Preferido: God of War | Comida preferida: Macarrão",
			"",
			"Roberto",
			"Cor preferida: Laranja | Cabelo: Preto | HQ preferida: Preacher",
			"Jogo Preferido: Mortal Kombat | Comida preferida: Pizza"};
	
	
	public WallyQuest() {
		JFrame container = new JFrame("Wally's Quest");

		JPanel panel = (JPanel) container.getContentPane();
		//Aqui é definido o tamanho da janela.
		panel.setPreferredSize(new Dimension(1000, 780));
		panel.setLayout(null);

		//Aqui deve ser definido o mesmo tamanho que foi utilizado anteriormente.
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
		//Aqui dentro deve ser inicializado todos os valores necessários
		//Loading all required images
		map = Toolkit.getDefaultToolkit().getImage("map.png");
		timeCheck = Toolkit.getDefaultToolkit().getImage("timecheck.png");
		lista = Toolkit.getDefaultToolkit().getImage("listab.png");
		listaAchados = Toolkit.getDefaultToolkit().getImage("listaachados.png");
		norte = Toolkit.getDefaultToolkit().getImage("area.png");
		centro = Toolkit.getDefaultToolkit().getImage("area.png");
		oeste = Toolkit.getDefaultToolkit().getImage("area.png");
		leste = Toolkit.getDefaultToolkit().getImage("area.png");
		sul = Toolkit.getDefaultToolkit().getImage("area.png");
		suemdonsa = Toolkit.getDefaultToolkit().getImage("suemdonsa.png");
		dialogo = Toolkit.getDefaultToolkit().getImage("dialogo.png");
		suemdonsaWin = Toolkit.getDefaultToolkit().getImage("suemdonsa WIN.png");
		gameOver = Toolkit.getDefaultToolkit().getImage("gameover.png");
		
		Felix = Toolkit.getDefaultToolkit().getImage("Felix NEW.png");
		Roberto = Toolkit.getDefaultToolkit().getImage("Roberto NEW.png");
		Lisa = Toolkit.getDefaultToolkit().getImage("Lisa NEW.png");
		Francisco = Toolkit.getDefaultToolkit().getImage("Francisco NEW.png");
		Geraldo = Toolkit.getDefaultToolkit().getImage("Geraldo NEW.png");
		Elisa = Toolkit.getDefaultToolkit().getImage("Elisa NEW.png");
		Catarina = Toolkit.getDefaultToolkit().getImage("Catarina NEW.png");
		Elias = Toolkit.getDefaultToolkit().getImage("Elias NEW.png");
		Evaldo = Toolkit.getDefaultToolkit().getImage("Evaldo NEW.png");
		Katia = Toolkit.getDefaultToolkit().getImage("Katia NEW.png");
		Angelo = Toolkit.getDefaultToolkit().getImage("Angelo NEW.png");
		Mario = Toolkit.getDefaultToolkit().getImage("Mario NEW.png");
		Brian = Toolkit.getDefaultToolkit().getImage("Brian NEW.png");
		Sara = Toolkit.getDefaultToolkit().getImage("Sara NEW.png");
		Chico = Toolkit.getDefaultToolkit().getImage("Chico NEW.png");
	}
	
	public void gameLoop() {
		while(gameRunning) {
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			//Draw main map
			g.drawImage(map, 0, 0, null);	
			
			if(tempoFinal < 1) {
				gameTerminate = true;
			}
			
			
			//Draw areas
			
				if (clicouNorte) {
					g.drawImage(norte, 0, 0, null);
					area = 1;
				}
				if (clicouCentro) {
					g.drawImage(centro, 0, 0, null);
					area = 2;
				}
				if (clicouOeste) {
					g.drawImage(oeste, 0, 0, null);
					area = 3;
				}
				if (clicouLeste) {
					g.drawImage(leste, 0, 0, null);
					area = 4;
				}
				if (clicouSul) {
					g.drawImage(sul, 0, 0, null);
					area = 5;
				}
				
				//Drawing the school (suemdonsa) screen
				if (clicouSuemdonsa && victory != 1){
					falando = false;
					area = 6;
					g.drawImage(suemdonsa, 0, 0, null);
					g.setFont(new Font ("Arial", Font.BOLD, 70));
					g.drawString(String.valueOf(10 - achadosNum), 890, 365);
				} else if (clicouSuemdonsa && victory == 1) {
					clicouLista = false;
					clicouAchados = false;
					clicouTempo = false;
					falando = false;
					area = 6;
					g.drawImage(suemdonsaWin, 0, 0, null);
				}
			

			switch (area) {
			case 1 :
				g.drawImage(Felix, 175, 587, null);
				g.drawImage(Roberto, 416, 360, null);
				g.drawImage(Lisa, 754, 562, null);
				pessoa = 0;
				break;
			case 2 :
				g.drawImage(Francisco, 175, 587, null);
				g.drawImage(Geraldo, 416, 360, null);
				g.drawImage(Elisa, 754, 562, null);
				pessoa = 3;
				break;
			case 3 :
				g.drawImage(Catarina, 175, 587, null);
				g.drawImage(Elias, 416, 360, null);
				g.drawImage(Evaldo, 754, 562, null);
				pessoa = 6;
				break;
			case 4 :
				g.drawImage(Katia, 175, 587, null);
				g.drawImage(Angelo, 416, 360, null);
				g.drawImage(Mario, 754, 562, null);
				pessoa = 9;
				break;
			case 5 :
				g.drawImage(Brian, 175, 587, null);
				g.drawImage(Sara, 416, 360, null);
				g.drawImage(Chico, 754, 562, null);
				pessoa = 12;
				break;
			};
			
			/*Each question will take the int "pessoa" as it's number so if the player wants to talk to the third
			 * person (in area 1 (Norte)) the variable will be subtracted by 1 and the program will gather all it's answers
			 * from this int, Example: player enters "3"
			 * int "pessoa" is now equal to the number 3
			 * (in area 1) this variable is reduced by 1,
			 * pessoa now equal 2 so cor[pessoa] will lead to element
			 * number 2 in the array "cor[]"
			 */
			switch (pessoaArea) {
			case 1 :
				pessoa += 0;
				break;
			case 2 :
				pessoa += 1;
				break;
			case 3 :
				pessoa += 2;
				break;
			}
			
						
			//Draw dialog screen
			if (falando) {
				g.drawImage(dialogo, 300, 234, null);
			}
			
			//Drawing the characters inside the dialog screen
			if (area != 0 && falando) {
				switch (pessoa) {
				case -1 :
					break;
				case 0 :
					g.drawImage(Felix, 500, 350, null);
					break;
				case 1 :
					g.drawImage(Roberto, 500, 350, null);
					break;
				case 2 :
					g.drawImage(Lisa, 500, 350, null);
					break;
				case 3 :
					g.drawImage(Francisco, 500, 350, null);
					break;
				case 4 :
					g.drawImage(Geraldo, 500, 350, null);
					break;
				case 5 :
					g.drawImage(Elisa, 500, 350, null);
					break;
				case 6 :
					g.drawImage(Catarina, 500, 350, null);
					break;
				case 7 :
					g.drawImage(Elias, 500, 350, null);
					break;
				case 8 :
					g.drawImage(Evaldo, 500, 350, null);
					break;
				case 9 :
					g.drawImage(Katia, 500, 350, null);
					break;
				case 10 :
					g.drawImage(Angelo, 500, 325, null);
					break;
				case 11 :
					g.drawImage(Mario, 500, 350, null);
					break;
				case 12 :
					g.drawImage(Brian, 480, 350, null);
					break;
				case 13 :
					g.drawImage(Sara, 500, 350, null);
					break;
				case 14 :
					g.drawImage(Chico, 500, 350, null);
					break;
				};
			}
			

			//Writing out the answers to each question
			g.setFont(new Font("Arial", Font.BOLD, 18));

			if (clicouCor){
				g.drawString(cor[pessoa], 632, 310);
			} else if (clicouHQ) {
				g.drawString(hq[pessoa], 632, 310);
			} else if (clicouJogo) {
				g.drawString(jogo[pessoa], 632, 310);
			} else if (clicouComida) {
				g.drawString(comida[pessoa], 632, 310);
			
			//This is the player trying to guess who they're talking to (Adivinhar)
			} else if (adivinhar && falando) {
				//if The person does not go to your school, they'll say so only
				//when you ask to guess their name and take them to the lecture,
				//this serves only to waste your time and make the game more difficult

				if (achados[pessoa] == -2){
					g.setFont(new Font ("Arial", Font.BOLD, 12));
					g.drawString("Prazer em te conhecer,", 640, 280);
					g.drawString("eu sou " +nome[pessoa] + ".", 640, 300);
					g.drawString("Desculpe mas eu", 640, 320);
					g.drawString("não estudo", 640, 340);
					g.drawString("no Suemdonsa.", 640, 360);
					
				} else if (achados[pessoa] == 1) {
					g.setFont(new Font ("Arial", Font.BOLD, 17));
					correct = false;
					wrong = false;
					g.drawString("Voce já encontrou", 633, 305);
					g.drawString("esta pessoa!", 635, 320);
				} else if (achados[pessoa] == 0){
					wrong = false;
					g.setFont(new Font ("Arial", Font.BOLD, 20));
					g.drawString("Digite o nome", 635, 290);
					g.drawString("e aperte enter", 635, 315);
					g.drawString(resposta, 657, 360);
				}
				
			}
			//Checking to see if you guessed correctly
			if (correct && falando){
				achados[pessoa] = 1;
				g.drawString("Voce encontrou:", 630, 305);
				g.setFont(new Font ("Arial", Font.BOLD, 35));
				g.drawString(nome[pessoa] + "!", 635, 340);
				achadosNome[pessoa] = nome[pessoa];
			} else if (wrong && falando) {
				g.setFont(new Font ("Arial", Font.BOLD, 30));
				g.drawString("Incorreto!", 635, 300);
				adivinhar = false;
			}
			
			
			//Drawing the List
			if (clicouLista) {
				int j = 50;
				g.drawImage(lista, 230, 15, null);
				
				g.setFont(new Font("Arial", Font.BOLD, 14));
				
				for (int i = 0; i < listagarotas.length; i++) {
					g.drawString(listagarotas[i], 235, j);
					j += 14;
					
				}
				j+= 28;
				for (int i = 0; i < listagarotas.length; i++) {
					
					g.drawString(listagarotos[i], 235, j);
					j += 14;
				}
			}
			
			
			//Drawing the List of found students
			if (clicouAchados) {
				int j = 410;
				g.drawImage(listaAchados, 740, 335, null);
				
				g.setFont(new Font("Arial", Font.BOLD, 32));
				
				g.drawString("Achados:", 745, 375);
				for (int i = 0; i < achadosNome.length; i++) {
					if (achados[i] == 1) {
					g.setFont(new Font("Arial", Font.BOLD, 34));
					g.drawString(achadosNome[i], 755, j);
					j += 28;
					}
				}
			}
			
			//Drawing time check
			 if (clicouTempo) {
			 g.drawImage(timeCheck, 0, 280, null);
			 g.setFont(new Font("Arial", Font.BOLD, 22));
			 	if (tempoFinal >= 100) {
			 		g.drawString(String.valueOf(tempoFinal), 96, 380);
			 	} else {
			 		g.drawString(String.valueOf(tempoFinal), 101, 380);
			 	}
			 }
			 
			
			
			if (gameTerminate){
				g.drawImage(gameOver, 0, 0, null);
			}
			
			g.dispose();
			strategy.show();
			
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	
		

	}
	


	public static void main(String[] args) {
		WallyQuest ig = new WallyQuest();
		ig.gameLoop();
	}
	
	public class mouseInputHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			//This is used for programming to find coordinates 
			System.out.println("X: " + e.getX() + " - Y: " + e.getY());
			
			//Time
			//Checking to see if you've click on the time icon
			if (e.getX () >= 337 && e.getX () <= 382 && e.getY () >= 707 && e.getY () <= 754) {
				clicouTempo = true;
				System.out.println("Tempo restante: " + tempoFinal);
			}
			//Checking if you clicked on the "X" on the watch to close it
			if (e.getX () >= 152 && e.getX () <= 177 && e.getY () >= 290 && e.getY () <= 313 && clicouTempo) {
				clicouTempo = false;
			}
			
			
			//List
			//Checking if you clicked on the list icon	
			if (e.getX () >= 436 && e.getX () <= 471 && e.getY () >= 718 && e.getY () <= 762) {
				clicouLista = true;	
			}
			//Checking if you clicked on the "X" in the list to close it
			if (e.getX () >= 675 && e.getX () <= 707 && e.getY () >= 18 && e.getY () <= 47 && clicouLista){
				clicouLista = false;
			}
			
			
			//List of Found Students
			//Checking if you clicked on the Found students icon
			if (e.getX () >= 512 && e.getX () <= 544 && e.getY () >= 712 && e.getY () <= 762) {
				clicouAchados = true;	
			}
			//Checking if you clicked on the "X" in the Found students page to close it
			if (e.getX () >= 967 && e.getX () <= 984 && e.getY () >= 335 && e.getY () <= 352 && clicouAchados) {
				clicouAchados = false;	
			}		

			//Check to see if you clicked the "EXIT" button on the game over screen
			if (e.getX () >= 355 && e.getX () <= 639 && e.getY () >= 575 && e.getY () <= 741 && gameTerminate){
				System.exit(0);
			}
			
			//Returning to the school (Suemdonsa)
			if (e.getX () >= 602 && e.getX () <= 639 && e.getY () >= 695 && e.getY () <= 755) {
				falando = false;
				correct = false;
				wrong = false;
				clicouSuemdonsa = true;				
				//checking to see if you have found all the students
				achadosNum = 0;
				for (int i = 0; i < achados.length; i++) {
					if (achados[i] == 1) {
						achadosNum += 1;
					}
				}
				if (achadosNum == 10){
					victory = 1;
				}
			}
			
			//Back button (Voltar)
			if (e.getX () >= 847 && e.getX () <= 1000 &&
				e.getY () >= 0 && e.getY () <= 60 && area != 0) {
				System.out.println("Voltar");
				if (falando == false) {
					if (clicouNorte) {
						clicouNorte = false;
						area = 0;
					} else if (clicouCentro) {
						clicouCentro = false;
						area = 0;
					} else if (clicouSuemdonsa) {
						clicouSuemdonsa = false;
						area = 0;
					} else if (clicouOeste) {
						clicouOeste = false;
						area = 0;
					} else if (clicouLeste) {
						clicouLeste = false;
						area = 0;
					} else if (clicouSul) {
						clicouSul = false;
						area = 0;
					}
				} else {
					correct = false;
					wrong = false;
					falando = false;
					clicouCor = false;
					clicouHQ = false;
					clicouJogo = false;
					clicouComida = false;
					adivinhar = false;
					//resetting area default for "pessoa"
					switch (area) {
					case 1 :
						pessoa = 0;
						break;
					case 2 :
						pessoa = 3;
						break;
					case 3 :
						pessoa = 6;
						break;
					case 4 :
						pessoa = 9;
						break;
					case 5 :
						pessoa = 12;
						break;
					}
					
				}
			}
			

			//Areas
			//Area Norte
			if (e.getX () >= 372 && e.getX () <= 414 && e.getY () >= 0 && e.getY () <= 142 && area == 0) {
				tempoFinal -= 10;
				clicouNorte = true;
			}
			
			//Area Leste
			if (e.getX () >= 527 && e.getX () <= 565 && e.getY () >= 350 && e.getY () <= 405 && area == 0) {
				tempoFinal -= 10;
				clicouLeste = true;
			}

			//Area Oeste				
			if (e.getX () >= 232 && e.getX () <= 332 && e.getY () >= 325 && e.getY () <= 407 && area == 0) {
				tempoFinal -= 10;
				clicouOeste = true;
			}
			
			//Area Sul
			if (e.getX () >= 380 && e.getX () <= 452 && e.getY () >= 565 && e.getY () <= 624 && area == 0) {
				tempoFinal -= 10;
				clicouSul = true;
			}
			
			//Area Centro
			if (e.getX () >= 372 && e.getX () <= 444 && e.getY () >= 220 && e.getY () <= 312 && area == 0) {
				tempoFinal -= 10;
				clicouCentro = true;
			}

			
				//People
				//Person on the left
				if (e.getX () >= 175 && e.getX () <= 225 && e.getY () >= 587 && e.getY () <= 777 && area != 0 && area != 5 && falando == false) {
					System.out.println(pessoa);
					wrong = false;
					tempoFinal -= 3;
					pessoaArea = 1;
					falando = true;
					System.out.println("pessoa 1");
				//Special condition for the person on the left in area 5 (Brian) who is larger than any other character
				} else if (e.getX () >= 175 && e.getX () <= 281 && e.getY () >= 587 && e.getY () <= 777 && area == 5 && falando == false) {
					System.out.println(pessoa);
					wrong = false;
					tempoFinal -= 3;
					pessoaArea = 1;
					falando = true;
					System.out.println("pessoa 1 (Brian)");
				}
				//Person in the middle
				else if (e.getX () >= 415 && e.getX () <= 470 && e.getY () >= 360 && e.getY () <= 562 && area != 0 && falando == false) {
					System.out.println(pessoa);
					wrong = false;
					tempoFinal -= 3;
					pessoaArea = 2;
					falando = true;
					System.out.println("pessoa 2");
				//Person on the right
				} else if (e.getX () >= 754 && e.getX () <= 817 && e.getY () >= 562 && e.getY () <= 742 && area != 0 && falando == false) {
					System.out.println(pessoa);
					tempoFinal -= 3;
					pessoaArea = 3;
					falando = true;
					System.out.println("pessoa 3");
				}
				
				

				
				//Question Buttons
				//Clicking this button will ask this person what their favourite colour is, this will waste some time
				if (e.getX () >= 602 && e.getX () <= 687 && e.getY () >= 387 && e.getY () <= 437 && falando) {
					tempoFinal -= 1;
					correct = false;
					wrong = false;
					clicouHQ = false;
					clicouJogo = false;
					clicouComida = false;
					adivinhar = false;
					
					clicouCor = true;
				//Clicking this button will ask this person what their favourite Comic book is, this will waste some time
				} else if (e.getX () >= 692 && e.getX () <= 785 && e.getY () >= 390 && e.getY () <= 437 && falando) {
					tempoFinal -= 1;
					correct = false;
					wrong = false;
					clicouCor = false;
					clicouJogo = false;
					clicouComida = false;
					adivinhar = false;
					
					clicouHQ = true;
				//Clicking this button will ask this person what their favourite video game is, this will waste some time
				} else if (e.getX () >= 597 && e.getX () <= 692 && e.getY () >= 449 && e.getY () <= 494 && falando) {
					tempoFinal -= 1;
					correct = false;
					wrong = false;
					clicouCor = false;
					clicouHQ = false;
					clicouComida = false;
					adivinhar = false;
					
					clicouJogo = true;
				//Clicking this button will ask this person what their favourite food is, this will waste some time
				} else if (e.getX () >= 692 && e.getX () <= 782 && e.getY () >= 450 && e.getY () <= 497 && falando) {
					tempoFinal -= 1;
					correct = false;
					wrong = false;
					clicouCor = false;
					clicouHQ = false;
					clicouJogo = false;
					adivinhar = false;
					
					clicouComida = true;
				//Clicking this button will allow you to guess who you're talking to, this will not waste time
				} else if (e.getX () >= 600 && e.getX () <= 777 && e.getY () >= 509 && e.getY () <= 547 && falando) {
					correct = false;
					wrong = false;
					clicouCor = false;
					clicouHQ = false;
					clicouJogo = false;
					clicouComida = false;
					
					resposta = "";
					adivinhar = true;
					
				}
				
		}
				
	}	
				

		
			private class keyInputHandler extends KeyAdapter {
				public void keyPressed(KeyEvent e) {
						if (adivinhar && achados[pessoa] != 1) {
							if (e.getKeyChar() != KeyEvent.VK_ENTER) {
								resposta += e.getKeyChar();
								System.out.println(e.getKeyChar());
								} else {
									System.out.println(resposta);
									adivinhar = false;
									respostaFinal = resposta;
								}
						
						//checking if you guessed correctly
						if (respostaFinal.equals(nome[pessoa]) && achados[pessoa] != -2 && achados[pessoa] != 1){
							adivinhar = false;
							correct = true;
						} else if (!respostaFinal.equals(nome[pessoa]) && achados[pessoa] != -2 && achados[pessoa] != 1) {
							wrong = true;
						}
				}
						
					}				
				@Override
				public void keyReleased(KeyEvent e) {
					
				}
			}
		}