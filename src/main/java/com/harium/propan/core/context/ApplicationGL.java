package com.harium.propan.core.context;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.propan.commons.context.PropanApplication;
import com.harium.propan.core.graphics.AWTGraphics3D;
import com.harium.propan.core.graphics.Graphics3D;
import com.jogamp.opengl.GL2;

public abstract class ApplicationGL extends Application implements PropanApplication {
		
	protected double zoom = 1;
	
	protected ApplicationGL returnApplication3D = this;
	
	public ApplicationGL(int w, int h) {
		super(w,h);
		init();
	}
	
	public ApplicationGL(int x, int y, int width, int height) {
		super(x,y,width,height);
		init();
	}
	
	private void init() {
		nextApplication = this;		
		clearBeforeDraw = false;
	}
	
	public void preDisplay(Graphics3D g) {
		
	}
		
	public abstract void display(Graphics3D g);

	public void dispose(Graphics3D g) {
		// TODO Auto-generated method stub
	}
	
	public ApplicationGL getReturnApplication3D() {
		return returnApplication3D;
	}

	protected Vector3 transformPosition(AWTGraphics3D g, Vector3 point) {
				
		float[] m = g.getModelView();
		
		float x = m[0]*point.x+m[4]*point.y+m[8]*point.z;//+m[12]*0;
		
		float y = m[1]*point.x+m[5]*point.y+m[9]*point.z;//+m[13]*0;
		
		float z = m[2]*point.x+m[6]*point.y+m[10]*point.z;//+m[14]*0;
		
		//double w = m[3]*point.getX()+m[7]*point.getY()+m[11]*point.getZ()+m[15]*0;
		
		return new Vector3(x, y, z);
	}
	
	protected int[] getViewPort(AWTGraphics3D g) {
		return g.getViewPort();
	}

	protected float[] getModelView(AWTGraphics3D g) {
		return g.getModelView();
	}
	
	protected float[] getProjection(AWTGraphics3D g) {
		return g.getProjection();
	}
	
	protected Matrix4 getProjectionMatrix(AWTGraphics3D g) {
		GL2 gl = g.getGL2();
		
		float[] projection = new float[16];
		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection, 0);

		return new Matrix4(projection);
	}
	
	@Override
	public void load() { }

	@Override
	public void draw(Graphics g) { }

	//LibGDX methods
	public void debug(String tag, String message) {
		System.out.println(tag+" "+message);
	}
	
	public void debug(String tag, String message, Exception exception) {
		System.out.println(tag+" "+message+" "+exception);
	}
	
	public void log(String tag, String message) {
		System.out.println(tag+" "+message);
	}
	
	public void log(String tag, String message, Exception exception) {
		System.out.println(tag+" "+message+" "+exception);
	}
	
	public void error(String tag, String message) {
		System.err.println(tag+" "+message);
	}
	
	public void error(String tag, String message, Throwable exception) {
		System.err.println(tag+" "+message+" "+exception);
	}

}
