package com.harium.propan.util;

import com.badlogic.gdx.math.Vector3;

public class DirectionUtil {

	/**
	 * Angles in degree
	 * @param yaw
	 * @return directional vector
	 */
	public static Vector3 direction(double yaw) {
		double radians = Math.toRadians(yaw);
		return new Vector3((float)Math.cos(radians), 0, (float)Math.sin(radians));
	}
	
	/**
	 * Angles in degree
	 * @param yaw
	 * @param pitch
	 * @return directional vector
	 */
	public static Vector3 direction(double yaw, double pitch) {
		double radYaw = Math.toRadians(yaw);
		double radPitch = Math.toRadians(pitch);

		float x = (float)(Math.sin(radPitch) * Math.cos(radYaw));
		float y = (float)(Math.sin(radPitch) * Math.sin(radYaw));
		float z = (float)(Math.cos(radPitch));

		return new Vector3(x,y,z);
	}

	/**
	 * Angles in degree
	 * @param yaw
	 * @param pitch
	 * @param length
	 * @return directional vector
	 */
	public static Vector3 direction(double yaw, double pitch, double length) {
		double radYaw = Math.toRadians(yaw);
		double radPitch = Math.toRadians(pitch);

		float x = (float)(length * Math.sin(radPitch) * Math.cos(radYaw));
		float y = (float)(length * Math.sin(radPitch) * Math.sin(radYaw));
		float z = (float)(length * Math.cos(radPitch));

		return new Vector3(x,y,z);
	}

	/**
	 * Calculates the direction vector between 2 points
	 * @param origin
	 * @param destination
	 * @return directional vector
	 */
	public static Vector3 direction(Vector3 origin, Vector3 destination) {
		Vector3 out = new Vector3();
		direction(origin, destination, out);
		return out;
	}
	
	/**
	 * Calculates the direction vector between 2 points
	 * @param origin
	 * @param destination
	 * @return directional vector
	 */
	public static void direction(Vector3 origin, Vector3 destination, Vector3 out) {
		out.set(origin).sub(destination).nor();
	}
	
	/**
	 * Calculates the direction vector between 2 points
	 * @param origin
	 * @param destination
	 * @return directional vector
	 */
	public static Vector3 directionOrthogonal(Vector3 origin, Vector3 destination) {
		Vector3 out = new Vector3();
		directionOrthogonal(origin, destination, out);
		return out;
	}
	
	/**
	 * Calculate the orthogonal vector
	 * @param origin
	 * @param destination
	 * @param out
	 */
	public static void directionOrthogonal(Vector3 origin, Vector3 destination, Vector3 out) {
		out.set(origin).crs(destination).nor();
	}
	
	/**
	 * Calculates the angle between 2 points
	 * @param origin
	 * @param destination
	 * @return angle
	 */
	public static float angle(Vector3 origin, Vector3 destination) {
		float dot = origin.dot(destination);
		float len = origin.len()*destination.len();
		float cos = dot/len;
		return (float)Math.acos(cos);
	}
	
	/**
	 * Rotate a point around an anchor point based on axis and angle in degrees 
	 * @param point
	 * @param anchor
	 * @param axis
	 * @param angleInDegrees
	 */
	public static void rotateAround(Vector3 point, Vector3 anchor, float angleInDegrees) {
		Vector3 axis = directionOrthogonal(point, anchor);
		rotateAround(point, anchor, axis, angleInDegrees);
	}
	
	/**
	 * Rotate a point around an anchor point based on axis and angle in degrees 
	 * @param point
	 * @param anchor
	 * @param axis
	 * @param angleInDegrees
	 */
	public static void rotateAround(Vector3 point, Vector3 anchor, Vector3 axis, float angleInDegrees) {
		point.sub(anchor);
		point.rotate(axis, angleInDegrees);
		point.add(anchor);
	}

}
