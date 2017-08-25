package com.harium.propan.graphics;

import com.badlogic.gdx.math.Vector3;

public class Billboard {
	
	public float width = 1, height = 1;
	public Vector3 center;
	protected Vector3 axis;
	protected float angle;
	
	public Billboard(Vector3 center) {
		this.center = center;
	}
	
	public Billboard(float width, float height, Vector3 center) {
		this.width = width;
		this.height = height;
		this.center = center;
	}
		
	public void turnTo(Vector3 target, Vector3 up) {
		Vector3 sub = new Vector3(target).sub(center);
		
		axis = new Vector3(up).crs(sub);
		axis.nor();
		
		float dot = up.dot(target);
		float len = up.len()*target.len();
		float cos = dot/len;
		
		angle = (float)Math.acos(cos);
	}

	public void getP00(Vector3 out) {
		out.set(center.x-width/2, center.y, center.z-height/2).rotateRad(axis, angle);
	}

	public void getP10(Vector3 out) {
		out.set(center.x+width/2, center.y, center.z-height/2).rotateRad(axis, angle);
	}

	public void getP11(Vector3 out) {
		out.set(center.x+width/2, center.y, center.z+height/2).rotateRad(axis, angle);
	}

	public void getP01(Vector3 out) {
		out.set(center.x-width/2, center.y, center.z+height/2).rotateRad(axis, angle);
	}
	
}
