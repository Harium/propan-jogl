package com.harium.propan.graphics;

import com.badlogic.gdx.math.Vector3;

public class CustomBillboard extends Billboard {

	Vector3 p00, p10, p11, p01;
	
	private static final Vector3 UP = new Vector3(0, 1, 0);
	
	public CustomBillboard(Vector3 center) {
		super(center);
	}
	
	public CustomBillboard(float width, float height, Vector3 center) {
		super(width, height, center);
	}

	public void turnTo(Vector3 target) {
		turnTo(target, UP);
	}
	
	@Override
	public void turnTo(Vector3 target, Vector3 up) {
		super.turnTo(target, up);
		
		p00 = new Vector3(center.x-width/2, center.y, center.z-height/2);
		p10 = new Vector3(center.x+width/2, center.y, center.z-height/2);
		p11 = new Vector3(center.x+width/2, center.y, center.z+height/2);
		p01 = new Vector3(center.x-width/2, center.y, center.z+height/2);
		
		p00.rotateRad(axis, angle);
		p10.rotateRad(axis, angle);
		p11.rotateRad(axis, angle);
		p01.rotateRad(axis, angle);
	}

	public Vector3 getP00() {
		return p00;
	}

	public Vector3 getP10() {
		return p10;
	}

	public Vector3 getP11() {
		return p11;
	}

	public Vector3 getP01() {
		return p01;
	}
	
}
