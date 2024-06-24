package wally;

import java.awt.Image;

public class Student {
	private Image sprite;
	private Integer x;
	private Integer y;
	private Integer sex;
	private Integer id;

	private String name;
	private String color;
	private String hair;
	private String hq;
	private String game;
	private String food;
	private Boolean isStudent;
	
	
	public Student(Integer id, Image sprite, Integer sex, String name, String color, String hair, String hq, String game, String food, Boolean isStudent) {
		this.id = id;
		this.sprite = sprite;
		this.x = 0;
		this.y = 0;
		this.sex = sex;
		
		this.name = name;
		this.color = color;
		this.hair = hair;
		this.hq = hq;
		this.game = game;
		this.food = food;
		this.isStudent = isStudent;
	}


	public Image getSprite() {
		return sprite;
	}


	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}


	public Integer getX() {
		return x;
	}


	public void setX(Integer x) {
		this.x = x;
	}


	public Integer getY() {
		return y;
	}


	public void setY(Integer y) {
		this.y = y;
	}


	public Integer getSex() {
		return sex;
	}


	public void setSex(Integer sex) {
		this.sex = sex;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getHair() {
		return hair;
	}


	public void setHair(String hair) {
		this.hair = hair;
	}


	public String getHq() {
		return hq;
	}


	public void setHq(String hq) {
		this.hq = hq;
	}


	public String getGame() {
		return game;
	}


	public void setGame(String game) {
		this.game = game;
	}


	public String getFood() {
		return food;
	}


	public void setFood(String food) {
		this.food = food;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Boolean getIsStudent() {
		return isStudent;
	}


	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}
	
	
	
}


