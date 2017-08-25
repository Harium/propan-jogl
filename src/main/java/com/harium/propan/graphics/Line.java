package com.harium.propan.graphics;

import com.badlogic.gdx.math.Vector3;

import com.harium.etyl.linear.Point3D;
import com.harium.propan.util.VectorUtil;

public class Line {

  protected Vector3 origin;
  
  protected Vector3 t;
  
  public Line(float x, float xt, float y, float yt, float z, float zt) {
    super();
    origin = new Vector3(x,y,z);
    t = new Vector3(xt,yt,zt);
  }
  
  public Line(Point3D p, Point3D q) {
    super();
    
    this.origin = VectorUtil.pointToVector(p);    
    this.t = VectorUtil.pointToVector(q).sub(origin);
  }

  public Vector3 getOrigin() {
    return origin;
  }

  public void setOrigin(Vector3 origin) {
    this.origin = origin;
  }

  public Vector3 getT() {
    return t;
  }

  public void setT(Vector3 t) {
    this.t = t;
  }  
  
}
