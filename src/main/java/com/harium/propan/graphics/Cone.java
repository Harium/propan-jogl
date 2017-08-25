package com.harium.propan.graphics;

import com.harium.propan.core.graphics.AWTGraphics3D;
import com.jogamp.opengl.GL2;

import com.harium.propan.linear.BaseCone;

import com.badlogic.gdx.math.Vector3;

public class Cone extends BaseCone {

	public Cone(int sides) {
		super(sides);
	}
	
	public Cone(int sides, float height) {
		super(sides, height);
	}
	
	public Cone(int sides, float height, float radius) {
		super(sides, height, radius);
	}

	public void draw(AWTGraphics3D g) {
		GL2 gl = g.getGL2();
		
		gl.glMultMatrixf(transform.val, 0);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex3f(position.x, position.y + height/2, position.z);
		for (Vector3 point : lowerPoints) {
			g.vertex(point);
		}
		gl.glEnd();
		
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex3f(position.x, position.y - height/2, position.z);
		for (Vector3 point : lowerPoints) {
			g.vertex(point);
		}
		gl.glEnd();
	}
		
}
