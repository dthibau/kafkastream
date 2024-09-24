package org.formation.model;

public class Coursier {

	private final String id;
	private Position position;
	
	public Coursier(String id, Position currentPosition) {
		super();
		this.id = id;
		this.position = currentPosition;
	}
	
	public void move() {
		position.move(Math.random()-0.5, Math.random()-0.5);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Coursier [id=" + id + ", position=" + position + "]";
	}
	
	
}
