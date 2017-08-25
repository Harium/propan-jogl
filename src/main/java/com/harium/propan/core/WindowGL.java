package com.harium.propan.core;

import com.harium.etyl.awt.AWTWindow;
import com.harium.etyl.commons.context.Context;
import com.harium.propan.core.context.ApplicationGL;
import com.harium.propan.core.context.DefaultLoadApplicationGL;

public class WindowGL extends AWTWindow {
	
	private ApplicationGL application3D;
	
	private DefaultLoadApplicationGL load3D;
	
	public WindowGL(int x, int y, int w, int h) {
		super(x,y,w,h);
		
		load3D = new DefaultLoadApplicationGL(x, y, w, h);
		application3D = load3D;
	}
	
	public ApplicationGL getApplication3D() {
		return application3D;
	}
	
	@Override
	public void setApplication(Context application) {
		setApplication3D((ApplicationGL)application3D);
	}
	
	public void setApplication3D(ApplicationGL application3D) {
		this.application = application3D;
		this.application3D = application3D;
	}
	
	public DefaultLoadApplicationGL getLoadApplication3D() {
		return load3D;
	}
	
	public Context getContext() {
		return this.application3D;
	}
	
}