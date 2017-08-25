package com.harium.propan.graphics;

import com.badlogic.gdx.math.Vector3;

import com.harium.etyl.linear.Point3D;
import com.harium.propan.util.VectorUtil;

/**
 * A plane can be defined as ax + by + cz = d
 */
public class Plane {
  double a = 0;
  double b = 0;
  double c = 0;
  double d = 0;
  
  public Plane(double d) {
    super();
    this.d = d; 
  }
  
  public Plane(double a, double b, double c) {
    super();
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public Plane(double a, double b, double c, double d) {
    super();
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }
  
  /**
   * Based on https://www.youtube.com/watch?v=0qYJfKG-3l8
   * @param p arbitrary point
   * @param q arbitrary point
   * @param r arbitrary point
   */
  public Plane(Point3D p, Point3D q, Point3D r) {
    super();
    
    Vector3 vp = VectorUtil.pointToVector(p);
    Vector3 pq = VectorUtil.pointToVector(q).sub(vp);
    Vector3 pr = VectorUtil.pointToVector(r).sub(vp);
    
    a = pq.y*pr.z-pq.z*pr.y;
    b = pq.x*pr.z-pq.z*pr.x;
    c = pq.x*pr.z-pq.y*pr.x;
    d = -(a*p.getX()+b*p.getY()+c*p.getZ());    
  }

  public double getA() {
    return a;
  }

  public void setA(double a) {
    this.a = a;
  }

  public double getB() {
    return b;
  }

  public void setB(double b) {
    this.b = b;
  }

  public double getC() {
    return c;
  }

  public void setC(double c) {
    this.c = c;
  }

  public double getD() {
    return d;
  }

  public void setD(double d) {
    this.d = d;
  }
  
}
