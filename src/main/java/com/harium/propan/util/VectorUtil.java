package com.harium.propan.util;

import com.badlogic.gdx.math.Vector3;

import com.harium.etyl.linear.Point3D;

public class VectorUtil {

	public static Vector3 pointToVector(Point3D p) {
		return new Vector3((float)p.getX(), (float)p.getY(), (float)p.getZ());
	}
	
}
