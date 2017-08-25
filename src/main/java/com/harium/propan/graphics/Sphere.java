package com.harium.propan.graphics;

import com.harium.propan.linear.Shape;
import com.harium.propan.core.graphics.AWTGraphics3D;

public class Sphere extends Shape {

	private double radius = 1;

	public Sphere(double radius) {
		super();
		this.radius = radius;
	}

	public void draw(AWTGraphics3D g) {
		g.drawSphere(radius, position.x, position.y, position.z, AWTGraphics3D.DEFAULT_RESOLUTION, color);	  
	}

}
