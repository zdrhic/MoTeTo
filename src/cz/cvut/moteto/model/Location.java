package cz.cvut.moteto.model;

import java.io.Serializable;

public class Location implements Serializable {
	private double x, y;
	private String name;

	public Location(String name, double x, double y) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
