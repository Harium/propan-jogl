package com.harium.propan.graphics;

import com.harium.propan.core.graphics.AWTGraphics3D;
import com.jogamp.opengl.GL2;

import com.harium.propan.linear.BaseCylinder;

import com.badlogic.gdx.math.Vector3;

public class Cylinder extends BaseCylinder {

	public Cylinder(int sides) {
		super(sides);
	}
	
	public Cylinder(int sides, float height) {
		super(sides, height);
	}
	
	public Cylinder(int sides, float height, float radius) {
		super(sides, height, radius);
	}

	public void draw(AWTGraphics3D g) {
		GL2 gl = g.getGL2();
		gl.glPushMatrix();
		gl.glMultMatrixf(transform.val, 0);
		
		//Body
		gl.glBegin(GL2.GL_TRIANGLE_STRIP);
		for (int i = 0; i < sides; i++) {
			g.vertex(upperPoints.get(i));
			g.vertex(lowerPoints.get(i));
		}
		gl.glEnd();
		
		//Top Cap
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex3f(position.x, position.y + height/2, position.z);
		for (Vector3 point : upperPoints) {
			g.vertex(point);
		}
		gl.glEnd();
		
		//Bottom Cap
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex3f(position.x, position.y - height/2, position.z);
		for (Vector3 point : lowerPoints) {
			g.vertex(point);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}
	
}
