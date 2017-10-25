package com.harium.propan.util;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.harium.propan.graphics.Line;
import com.harium.propan.graphics.Plane;
import com.harium.propan.math.Intersector;

public class CollisionUtils {

    public static boolean intersectRayWithTriangle(Vector3 r1, Vector3 r2,
                                                   Vector3 t1, Vector3 t2, Vector3 t3) {
        Ray ray = new Ray(r1, r2);

        return Intersector.intersectRayTriangle(ray, t1, t2, t3, null);
    }

    public static Vector3 intersectLinePlane(Line l, Plane p) {

        double t = p.getA() * l.getT().x + p.getB() * l.getT().y + p.getC() * l.getT().z;
        double i = p.getA() * l.getOrigin().x + p.getB() * l.getOrigin().y + p.getC() * l.getOrigin().z;

        t = (p.getD() - i) / t;

        double px = l.getOrigin().x + l.getT().x * t;
        double py = l.getOrigin().y + l.getT().y * t;
        double pz = l.getOrigin().z + l.getT().z * t;

        Vector3 point = new Vector3((float) px, (float) py, (float) pz);

        return point;
    }

}
