package com.harium.propan.core.context;

import com.harium.etyl.commons.context.load.LoadApplication;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.commons.event.PointerEvent;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.propan.core.graphics.Graphics3D;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class DefaultLoadApplicationGL extends ApplicationGL implements LoadApplication {

	public DefaultLoadApplicationGL(int x, int y, int w, int h) {
		super(x,y,w,h);
				
		load();
	}

	protected String loadingInfo = "Loading...";
	
	protected String percent = "0%";

	protected int fill = 0;

	private int bxLimite = 400;
	private int byLimite = 30;

	private float bx = (w/2)-bxLimite/2;

	private float by = h/3;

	public void load(){

		by = h-100;
		
		loading = 100;
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(Color.BLUE);
		g.fillRect(x,y,w,h);

		g.setColor(Color.BLACK);
		g.drawRect(bx,by,bxLimite,byLimite);
		g.setColor(Color.WHITE);
		g.drawRect(bx-1,by-1,bxLimite+2,byLimite+2);

		g.setColor(Color.WHITE);
		g.drawStringX(loadingInfo, 100);
			
		//Fill bar
		g.fillRect(bx+2, by+2, fill*4, byLimite-3);

		g.setColor(Color.WHITE);
		
		g.drawStringX(percent, h-85);

	}

	public void setText(String phrase, int load){
		
		this.loadingInfo = phrase;
		
		this.percent = Integer.toString(load)+"%";

		this.fill = load;
	}

	@Override
	public void updateMouse(PointerEvent event) {

	}

	@Override
	public void updateKeyboard(KeyEvent event) {
		
	}

	@Override
	public void init(Graphics3D g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void display(Graphics3D g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reshape(Graphics3D g, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onChangeLoad(float load) {
		this.loading = load;
	}

	@Override
	public void onChangeText(String loadingInfo) {
		this.loadingInfo = loadingInfo;
	}

}