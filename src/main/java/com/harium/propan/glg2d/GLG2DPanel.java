package com.harium.propan.glg2d;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;


public class GLG2DPanel extends org.jogamp.glg2d.GLG2DPanel {

	private static final long serialVersionUID = 370275184911204974L;

	public GLG2DPanel() {
		super(defaultCapabilities());
	}
	
	private static GLCapabilities defaultCapabilities() {
		GLProfile profile = GLProfile.getDefault();
		return new GLCapabilities(profile);
	}
	
	private static GLCapabilities GL2Capabilities() {
		GLCapabilities caps = new GLCapabilities(GLProfile.getGL2ES1());
	    caps.setRedBits(8);
	    caps.setGreenBits(8);
	    caps.setBlueBits(8);
	    caps.setAlphaBits(8);
	    caps.setDoubleBuffered(true);
	    caps.setHardwareAccelerated(true);
	    caps.setNumSamples(4);
	    caps.setBackgroundOpaque(false);
	    caps.setSampleBuffers(true);
	    return caps;
	}

	public GLAutoDrawable getCanvas() {
		return canvas;
	}
	
	@Override
	protected GLAutoDrawable createGLComponent(GLCapabilitiesImmutable capabilities, GLContext shareWith) {
		GLJPanel canvas = new GLJPanel(capabilities);
		canvas.setEnabled(false);
		return canvas;
	}
	
}
