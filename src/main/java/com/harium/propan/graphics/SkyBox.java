package com.harium.propan.graphics;

import com.jogamp.opengl.GL2;

import com.harium.etyl.linear.Point3D;
import com.harium.propan.core.graphics.GLDrawable;
import com.harium.propan.core.graphics.AWTGraphics3D;
import com.harium.propan.loader.TextureLoader;

import com.jogamp.opengl.util.texture.Texture;

public class SkyBox extends Point3D implements GLDrawable {
	
	private Texture texture;
	private double size = 500;
	
	public SkyBox(String path) {
		texture = TextureLoader.getInstance().loadTexture(path, false);		
	}
	
	public SkyBox(String path, double size) {
		this(path);
		this.size = size;
	}

	@Override
	public void draw(AWTGraphics3D g) {
		GL2 gl = g.getGL2();
		
		texture.enable(gl);
		texture.bind(gl);
		gl.glColor3f(1, 1, 1);
		gl.glBegin(GL2.GL_QUADS);
		//Draw skybox based on center 
		drawUpperFace(gl, x, y, z, size);
		drawFrontFace(gl, x, y, z, size);
		drawRightFace(gl, x, y, z, size);
		drawLeftFace(gl, x, y, z, size);
		drawLowerFace(gl, x, y, z, size);
		drawBackFace(gl, x, y, z, size);
		gl.glEnd();

		texture.disable(gl);
	}
	
	private void drawUpperFace(GL2 gl, double cx, double cy, double cz, double tileSize) {
		gl.glTexCoord2d((double)1/4, 0);
		gl.glVertex3d(cx+tileSize/2, cy+tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d((double)1/2, 0);
		gl.glVertex3d(cx-tileSize/2, cy+tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d((double)1/2, (double)1/3);
		gl.glVertex3d(cx-tileSize/2, cy+tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d((double)1/4, (double)1/3);
		gl.glVertex3d(cx+tileSize/2, cy+tileSize/2, cz+tileSize/2);
	}
	
	private void drawFrontFace(GL2 gl, double cx, double cy, double cz, double tileSize) {
		gl.glTexCoord2d(0.25, (double)1/3);
		gl.glVertex3d(cx+tileSize/2, cy+tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d(0.5, (double)1/3);
		gl.glVertex3d(cx-tileSize/2, cy+tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d(0.5, (double)2/3);
		gl.glVertex3d(cx-tileSize/2, cy-tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d(0.25, (double)2/3);
		gl.glVertex3d(cx+tileSize/2, cy-tileSize/2, cz+tileSize/2);
	}

	private void drawRightFace(GL2 gl, double cx, double cy, double cz, double tileSize) {
		gl.glTexCoord2d(0.5, (double)1/3);
		gl.glVertex3d(cx-tileSize/2, cy+tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d(0.75, (double)1/3);
		gl.glVertex3d(cx-tileSize/2, cy+tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d(0.75, (double)2/3);
		gl.glVertex3d(cx-tileSize/2, cy-tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d(0.5, (double)2/3);
		gl.glVertex3d(cx-tileSize/2, cy-tileSize/2, cz+tileSize/2);
	}
	
	private void drawLeftFace(GL2 gl, double cx, double cy, double cz, double tileSize) {
		gl.glTexCoord2d(0, (double)1/3);
		gl.glVertex3d(cx+tileSize/2, cy+tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d(0.25, (double)1/3);
		gl.glVertex3d(cx+tileSize/2, cy+tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d(0.25, 0.66);
		gl.glVertex3d(cx+tileSize/2, cy-tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d(0.0, 0.66);
		gl.glVertex3d(cx+tileSize/2, cy-tileSize/2, cz-tileSize/2);
	}
	
	private void drawLowerFace(GL2 gl, double cx, double cy, double cz, double tileSize) {
		gl.glTexCoord2d((double)1/4, (double)2/3);
		gl.glVertex3d(cx+tileSize/2, cy-tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d((double)1/2, (double)2/3);
		gl.glVertex3d(cx-tileSize/2, cy-tileSize/2, cz+tileSize/2);

		gl.glTexCoord2d((double)1/2, (double)1);
		gl.glVertex3d(cx-tileSize/2, cy-tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d((double)1/4, (double)1);
		gl.glVertex3d(cx+tileSize/2, cy-tileSize/2, cz-tileSize/2);
	}
	
	private void drawBackFace(GL2 gl, double cx, double cy, double cz, double tileSize) {
		gl.glTexCoord2d(0.75, (double)1/3);
		gl.glVertex3d(cx-tileSize/2, cy+tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d(1, (double)1/3);
		gl.glVertex3d(cx+tileSize/2, cy+tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d(1, (double)2/3);
		gl.glVertex3d(cx+tileSize/2, cy-tileSize/2, cz-tileSize/2);

		gl.glTexCoord2d(0.75, (double)2/3);
		gl.glVertex3d(cx-tileSize/2, cy-tileSize/2, cz-tileSize/2);
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
		
}
