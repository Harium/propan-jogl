package com.harium.propan.core.context;

import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.context.Context;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.propan.commons.context.PropanApplication;

public abstract class ApplicationWrapper extends ApplicationGL implements PropanApplication {
	
	protected Application application;
	
	public ApplicationWrapper(Application application) {
		super(application.getX(), application.getY(), application.getW(), application.getH());
		this.application = application;
		init();
	}
	
	private void init() {
		nextApplication = this;
		clearBeforeDraw = true;
	}
	
	@Override
	public void draw(Graphics g) {
		application.draw(g);
	}
	
	@Override
	public Context getNextApplication() {
		return application.getNextApplication();
	}
}
