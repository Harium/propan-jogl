package com.harium.propan.glg2d;

import com.jogamp.opengl.GLAutoDrawable;

import org.jogamp.glg2d.G2DDrawingHelper;


public class GLGraphics2D extends org.jogamp.glg2d.GLGraphics2D {

	public void setCanvas(GLAutoDrawable glDrawable) {
		this.glContext = glDrawable.getContext();
		
		for (G2DDrawingHelper helper : helpers) {
			helper.setG2D(this);
		}
	}
	
}
