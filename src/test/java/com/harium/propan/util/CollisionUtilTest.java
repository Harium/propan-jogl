package com.harium.propan.util;

import com.badlogic.gdx.math.Vector3;
import com.harium.propan.graphics.Line;
import com.harium.propan.graphics.Plane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CollisionUtilTest {

    private Vector3 s1, s2, s3;

    @Before
    public void setUp() {
        s1 = new Vector3(-1.0f, 1.0f, 0.0f);
        s2 = new Vector3(1.0f, 1.0f, 0.0f);
        s3 = new Vector3(-1.0f, -1.0f, 0.0f);
    }

    @Test
    public void testCollisionRayWithPlane() {
        Vector3 r1 = new Vector3(0.0f, 0.0f, -1.0f);
        Vector3 r2 = new Vector3(0.0f, 0.0f, 1.0f);

        Assert.assertTrue(CollisionUtils.intersectRayWithTriangle(r1, r2, s1, s2, s3));
    }

    public void testNonCollisionRayWithPlane() {
        Vector3 r1 = new Vector3(1.5f, 1.5f, -1.0f);
        Vector3 r2 = new Vector3(1.5f, 1.5f, 1.0f);

        Assert.assertFalse(CollisionUtils.intersectRayWithTriangle(r1, r2, s1, s2, s3));
    }

    /**
     * Tests based on:
     * http://ocw.mit.edu/courses/mathematics/18-02sc-multivariable-calculus-fall-2010/1.-vectors-and-matrices/part-c-parametric-equations-for-curves/session-16-intersection-of-a-line-and-a-plane/MIT18_02SC_we_9_comb.pdf
     */
    @Test
    public void testOnePointOfIntersection() {

        Plane p = new Plane(2, 1, -4, 4);
        Line l = new Line(0, 1, 2, 3, 0, 1);

        Vector3 point = CollisionUtils.intersectLinePlane(l, p);

        Assert.assertEquals(2, point.x, 0);
        Assert.assertEquals(8, point.y, 0);
        Assert.assertEquals(2, point.z, 0);
    }

    /**
     * There is no point of intersection (line and plane are parallel)
     */
    @Test
    public void testNoPointOfIntersection() {

        Plane p = new Plane(2, 1, -4, 4);
        Line l = new Line(1, 1, 4, 2, 0, 1);

        Vector3 point = CollisionUtils.intersectLinePlane(l, p);

        Assert.assertTrue(Double.isInfinite(point.x));
        Assert.assertTrue(Double.isInfinite(point.y));
        Assert.assertTrue(Double.isInfinite(point.z));
    }

    /**
     * All the line is in the plane
     */
    @Test
    public void testAllPointsOfIntersection() {

        Plane p = new Plane(2, 1, -4, 4);
        Line l = new Line(0, 1, 4, 2, 0, 1);

        Vector3 point = CollisionUtils.intersectLinePlane(l, p);

        Assert.assertTrue(Double.isNaN(point.x));
        Assert.assertTrue(Double.isNaN(point.y));
        Assert.assertTrue(Double.isNaN(point.z));
    }

}
