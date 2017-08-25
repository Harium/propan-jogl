package com.harium.propan.core.graphics;

import com.harium.propan.graphics.Billboard;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.harium.etyl.awt.AWTGraphics;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.linear.Point3D;
import com.harium.propan.linear.*;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLBase;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import org.jogamp.glg2d.GLGraphics2D;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class AWTGraphics3D extends AWTGraphics implements Graphics3D {

	private GL2 gl;
	private GLU glu;

	private GLAutoDrawable drawable;

	public static int DEFAULT_RESOLUTION = 16;

	public AWTGraphics3D(int width, int height) {
		super(width, height);

		glu = new GLU(); // GL Utilities
	}

	public void setGraphics(Graphics2D graphics) {
		this.screen = graphics;
		this.screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void setGraphics(GLGraphics2D graphics) {
		this.screen = graphics;
		this.screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public GLAutoDrawable getDrawable() {
		return drawable;
	}

	public void setDrawable(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = getGL2();
	}

	public int[] getViewPort() {
		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

		return viewport;
	}

	@Override
	public void glMatrixMode(int mode) {
        gl.glMatrixMode(mode);
	}

	@Override
	public void glMultMatrixf(float[] m, int m_offset) {
        gl.glMultMatrixf(m, m_offset);
	}

	@Override
	public void glMultMatrixf(FloatBuffer m) {
        gl.glMultMatrixf(m);
	}

	@Override
	public void glOrthof(float left, float right, float bottom, float top, float zNear, float zFar) {
        gl.glOrtho(left, right, bottom, top, zNear, zFar);
	}

	@Override
	public void glPopMatrix() {
        gl.glPopMatrix();
	}

	@Override
	public void glPushMatrix() {
        gl.glPushMatrix();
	}

	@Override
	public void glRotatef(float angle, float x, float y, float z) {
        gl.glRotatef(angle, x, y, z);
	}

	@Override
	public void glScalef(float x, float y, float z) {
        gl.glScalef(x, y, z);
	}

	@Override
	public void glTranslatef(float x, float y, float z) {
        gl.glTranslatef(x, y, z);
	}

	@Override
	public void glClear(int mask) {
        gl.glClear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
        gl.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearDepth(float depth) {
		gl.glClearDepth(depth);
	}

	@Override
	public void glEnable(int cap) {
		gl.glEnable(cap);
	}

	@Override
	public void glDepthFunc(int func) {
		gl.glDepthFunc(func);
	}

	@Override
	public void glHint(int target, int mode) {
		gl.glHint(target, mode);
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		gl.glViewport(x, y, width, height);
	}

	@Override
	public void glFlush() {
		gl.glFlush();
	}

	public void drawLine(Point3D a, Point3D b) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(a.getX(), a.getY(), a.getZ());
		gl.glVertex3d(b.getX(), b.getY(), b.getZ());
		gl.glEnd();
	}

	public void drawLine(Point3D a, Vector3 b) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(a.getX(), a.getY(), a.getZ());
		gl.glVertex3f(b.x, b.y, b.z);
		gl.glEnd();
	}

	public void drawLine(Vector3 a, Vector3 b) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(a.x, a.y, a.z);
		gl.glVertex3f(b.x, b.y, b.z);
		gl.glEnd();
	}

	public void drawSphere(Vector3 point, double radius) {
		drawSphere(radius, point.x, point.y, point.z);
	}

	public void drawSphere(AimPoint point, double radius) {
		GLUquadric sphere = glu.gluNewQuadric();

		glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
		glu.gluQuadricTexture(sphere, true);
		glu.gluQuadricNormals(sphere, GLU.GLU_SMOOTH);

		// draw a sphere
		gl.glPushMatrix();
		gl.glTranslated(point.x, point.y, point.z);
		gl.glRotated(point.getAngleY(), 0, 1, 0);
		gl.glRotated(point.getAngleX(), 1, 0, 0);
		glu.gluSphere(sphere, radius, 32, 32);
		gl.glPopMatrix();
	}

	@Override
	public void glFrustumf(float left, float right, float bottom, float top, float zNear, float zFar) {
        gl.glFrustumf(left, right, bottom, top, zNear, zFar);
	}

	@Override
	public void glGetFloatv(int pname, float[] params, int params_offset) {
        gl.glGetFloatv(pname, params, params_offset);
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params) {
        gl.glGetFloatv(pname, params);
	}

	@Override
	public void glGetIntegerv(int pname, int[] params, int params_offset) {
		gl.glGetIntegerv(pname, params, params_offset);
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		gl.glGetIntegerv(pname, params);
	}

	@Override
	public void glBegin(int mode) {
		gl.glBegin(mode);
	}

	@Override
	public void glEnd() {
		gl.glEnd();
	}

	@Override
	public void glColor3d(double red, double green, double blue) {
		gl.glColor3d(red, green, blue);
	}

	@Override
	public void glLoadIdentity() {
		gl.glLoadIdentity();
	}

	@Override
	public void glLoadMatrixf(float[] m, int m_offset) {
		gl.glLoadMatrixf(m, m_offset);
	}

	@Override
	public void glLoadMatrixf(FloatBuffer m) {
		gl.glLoadMatrixf(m);
	}

	public void drawTile(double x, double y, double tileSize, Texture texture) {
		texture.enable(gl);
		texture.bind(gl);

		gl.glBegin(GL2.GL_QUADS);

		//(0,0)
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(x*tileSize, 0, y*tileSize);

		//(1,0)
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(x*tileSize+tileSize, 0, y*tileSize);

		//(1,1)
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(x*tileSize+tileSize, 0, y*tileSize+tileSize);

		//(0,1)
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(x*tileSize, 0, y*tileSize+tileSize);

		gl.glEnd();

		texture.disable(gl);
	}

	public float[] getModelView() {
		float modelView[] = new float[16];
		gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelView, 0);

		return modelView;
	}

	public float[] getProjection() {
		float projection[] = new float[16];
		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection, 0);

		return projection;
	}

	public Vector3 get3DPointerFromMouse(float mx, float my) {
		return get3DPointerFromMouse(mx, my, 0);
	}

	public void get3DPointFrom2D(float mx, float my, Vector3 out) {
		get3DPointFrom2D(mx, my, 0, out);
	}

	public float[] get2DPositionFromPoint(float px, float py, float pz) {

		float[] position = new float[3];

		int[] viewport = getViewPort();
		float[] modelview = getModelView();
		float[] projection = getProjection();

		glu.gluProject(px, py, pz, modelview, 0, projection, 0, viewport, 0, position, 0);

		return position;
	}

	public void get3DPointFrom2D(float mx, float my, float zPlane, Vector3 out) {

		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		IntBuffer viewport = BufferUtils.createIntBuffer(16);

		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection);
		gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelview);
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport);

		FloatBuffer positionNear = BufferUtils.createFloatBuffer(3);
		FloatBuffer positionFar = BufferUtils.createFloatBuffer(3);

		glu.gluUnProject(mx, viewport.get(3) - my, 0f, modelview, projection, viewport, positionNear);
		glu.gluUnProject(mx, viewport.get(3) - my, 1f, modelview, projection, viewport, positionFar);

		Vector3 v1 = new Vector3(positionNear.get(0), positionNear.get(1), positionNear.get(2));
		Vector3 v2 = new Vector3(positionFar.get(0), positionFar.get(1), positionFar.get(2));

		float t = (v1.y - zPlane) / (v1.y - v2.y);

		// so here are the desired (x, y) coordinates
		float fX = v1.x + (v2.x - v1.x) * t;
		float fZ = v1.z + (v2.z - v1.z) * t;

		out.x = fX;
		out.y = zPlane;
		out.z = fZ;
	}

	public Vector3 get3DPointerFromMouse(float mx, float my, float zPlane) {
		Vector3 out = new Vector3();
		get3DPointFrom2D(mx, my, zPlane, out);

		return out;
	}

	public Ray getCameraRay(int mx, int my, Ray rayOut) {
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		IntBuffer viewport = BufferUtils.createIntBuffer(16);

		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection);
		gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelview);
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport);

		FloatBuffer positionNear = BufferUtils.createFloatBuffer(3);
		FloatBuffer positionFar = BufferUtils.createFloatBuffer(3);

		glu.gluUnProject(mx, viewport.get(3) - my, 0f, modelview, projection, viewport, positionNear);
		glu.gluUnProject(mx, viewport.get(3) - my, 1f, modelview, projection, viewport, positionFar);

		rayOut.origin.set(positionNear.get(0), positionNear.get(1), positionNear.get(2));
		rayOut.direction.set(positionFar.get(0)-rayOut.origin.x, positionFar.get(1)-rayOut.origin.y, positionFar.get(2)-rayOut.origin.z);

		return rayOut;
	}

	public Ray getCameraRay(int mx, int my) {
		return getCameraRay(mx, my, new Ray());
	}

	public void updateCamera(Camera camera) {
		drawable.getGL().getGL2().glMatrixMode(GL2.GL_MODELVIEW);
		drawable.getGL().getGL2().glLoadIdentity();

		glu.gluLookAt( camera.position.x, camera.position.y, camera.position.z, camera.getTarget().x, camera.getTarget().y, camera.getTarget().z, 0, 1, 0 );
	}

	public void aimCamera(Vector3 cameraPoint, double angleX, double angleY, double angleZ) {
		gl.glMatrixMode(GL2.GL_MODELVIEW);

		gl.glLoadIdentity();

		gl.glRotated(360-angleX, 1, 0, 0);
		gl.glRotated(360-angleY, 0, 1, 0);
		gl.glRotated(360-angleZ, 0, 0, 1);

		gl.glTranslated(-cameraPoint.x, -cameraPoint.y, -cameraPoint.z);
	}

	public void aimCamera(AimPoint aim) {
		gl.glMatrixMode(GL2.GL_MODELVIEW);

		gl.glLoadIdentity();

		gl.glRotated(360-aim.getAngleX(), 1, 0, 0);
		gl.glRotated(360-aim.getAngleY(), 0, 1, 0);
		gl.glRotated(360-aim.getAngleZ(), 0, 0, 1);

		gl.glTranslated(-aim.x, -aim.y, -aim.z);
	}

	public GLBase getGL() {
		return drawable.getGL();
	}

	public GL2 getGL2() {
		return getGL().getGL2();
	}

	public GLBase getGL3() {
		return getGL().getGL3();
	}

	public GLU getGLU() {
		return glu;
	}

	public void drawPoint(Point3D point, Color color) {
		drawSphere(0.01, point.getX(), point.getY(), point.getZ(), 16, color);
	}

	public void drawPoint(Vector3 point, Color color) {
		drawSphere(0.01, point.x, point.y, point.z, 16, color);
	}

	public void drawSphere(double radius, double x,
			double y, double z, int resolution, Color color) {

		final int slices = resolution;
		final int stacks = resolution;

		gl.glPushMatrix();

		glSetColor(color);

		gl.glTranslated(x, y, z);

		GLUquadric sphere = generateQuadric(glu);

		glu.gluSphere(sphere, radius, slices, stacks);
		glu.gluDeleteQuadric(sphere);

		gl.glPopMatrix();
	}

	public void drawSphere(double radius, double x,
			double y, double z, int resolution) {

		final int slices = resolution;
		final int stacks = resolution;

		gl.glPushMatrix();

		// Draw sphere (possible styles: FILL, LINE, POINT).
		//gl.glColor3f(0.3f, 0.5f, 1f);

		gl.glTranslated(x, y, z);

		GLUquadric sphere = generateQuadric(glu);

		glu.gluSphere(sphere, radius, slices, stacks);
		glu.gluDeleteQuadric(sphere);

		gl.glPopMatrix();
	}

	private GLUquadric generateQuadric(GLU glu) {
		GLUquadric quadric = glu.gluNewQuadric();

		// Draw sphere (possible styles: FILL, LINE, POINT)
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
		glu.gluQuadricNormals(quadric, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(quadric, GLU.GLU_OUTSIDE);

		return quadric;
	}

	public void drawSphere(double radius) {
		drawSphere(radius, 0, 0, 0, DEFAULT_RESOLUTION);
	}

	public void drawSphere(double radius, double x,
			double y, double z) {

		drawSphere(radius, x, y, z, 16);
	}

	public void drawSphere(double radius, int sections, int divisions) {
		drawSphere(radius, 0, 0, 0, sections);
	}

	public void drawWireCube(double size) {
		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_LINE );
		drawCube(size);
		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_FILL );
	}

	public void drawCube(double size) {
		drawCube(size, 0, 0, 0);
	}

	public void drawCube(double size, double x, double y, double z) {
		gl.glPushMatrix();

		gl.glTranslated(x, y+size/2, z);

		gl.glPushMatrix();
		drawSquare(size);        // front face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(180,0,1,0);
		drawSquare(size);        // back face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,0,1,0);
		drawSquare(size);       // left face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,0,1,0);
		drawSquare(size);       // right face is magenta
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,1,0,0); // top face
		drawSquare(size);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,1,0,0); // bottom face
		drawSquare(size);
		gl.glPopMatrix();

		gl.glPopMatrix();
	}

	public void drawBoundingBox(BoundingBox box) {
		gl.glBegin(GL2.GL_LINES);
		drawBoundingLines(box.min, box.max);
		drawBoundingLines(box.max, box.min);
		gl.glEnd();
	}

	public void drawBoundingBox(BoundingBox box, Matrix4 transform) {
		gl.glPushMatrix();
		gl.glMultMatrixf(transform.val, 0);
		drawBoundingBox(box);
		gl.glPopMatrix();
	}

	private void drawBoundingLines(Vector3 minPoint, Vector3 maxPoint) {
		gl.glVertex3d(minPoint.x, minPoint.y, minPoint.z);
		gl.glVertex3d(maxPoint.x, minPoint.y, minPoint.z);

		gl.glVertex3d(minPoint.x, minPoint.y, minPoint.z);
		gl.glVertex3d(minPoint.x, maxPoint.y, minPoint.z);

		gl.glVertex3d(minPoint.x, minPoint.y, minPoint.z);
		gl.glVertex3d(minPoint.x, minPoint.y, maxPoint.z);

		gl.glVertex3d(maxPoint.x, minPoint.y, maxPoint.z);
		gl.glVertex3d(minPoint.x, minPoint.y, maxPoint.z);

		gl.glVertex3d(maxPoint.x, minPoint.y, maxPoint.z);
		gl.glVertex3d(maxPoint.x, minPoint.y, minPoint.z);

		gl.glVertex3d(maxPoint.x, minPoint.y, minPoint.z);
		gl.glVertex3d(maxPoint.x, maxPoint.y, minPoint.z);
	}

	public void drawBoundingBox(BoundingBox3D box) {

		Point3D minPoint = box.getMinPoint();
		Point3D maxPoint = box.getMaxPoint();

		gl.glBegin(GL2.GL_LINES);
		drawBoundingLines(gl, minPoint, maxPoint);
		drawBoundingLines(gl, maxPoint, minPoint);

		gl.glEnd();
	}

	private void drawBoundingLines(GL2 gl, Point3D minPoint, Point3D maxPoint) {
		gl.glVertex3d(minPoint.getX(), minPoint.getY(), minPoint.getZ());
		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), minPoint.getZ());

		gl.glVertex3d(minPoint.getX(), minPoint.getY(), minPoint.getZ());
		gl.glVertex3d(minPoint.getX(), maxPoint.getY(), minPoint.getZ());

		gl.glVertex3d(minPoint.getX(), minPoint.getY(), minPoint.getZ());
		gl.glVertex3d(minPoint.getX(), minPoint.getY(), maxPoint.getZ());

		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), maxPoint.getZ());
		gl.glVertex3d(minPoint.getX(), minPoint.getY(), maxPoint.getZ());

		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), maxPoint.getZ());
		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), minPoint.getZ());

		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), minPoint.getZ());
		gl.glVertex3d(maxPoint.getX(), maxPoint.getY(), minPoint.getZ());
	}

	public void drawCube() {

		GL2 gl = getGL2();

		gl.glPushMatrix();

		gl.glColor3f(0.3f, 0.5f, 1f);

		gl.glTranslated(0, 0.5, 0);

		gl.glPushMatrix();
		drawSquare(1);        // front face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(180,0,1,0);
		drawSquare(1);        // back face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,0,1,0);
		drawSquare(1);       // left face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,0,1,0);
		drawSquare(1);       // right face is magenta
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,1,0,0); // top face
		drawSquare(1);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,1,0,0); // bottom face
		drawSquare(1);
		gl.glPopMatrix();

		gl.glPopMatrix();
	}

	public void drawPyramid(double size) {

		GL2 gl = getGL2();

		double halfSize = size/2;

		gl.glPushMatrix();

		gl.glBegin(GL.GL_TRIANGLES);        // Drawing Using Triangles
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Front)
		gl.glVertex3d(-halfSize, -halfSize, halfSize);  // Left Of Triangle (Front)
		gl.glVertex3d(halfSize, -halfSize, halfSize);   // Right Of Triangle (Front)
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Right)
		gl.glVertex3d(halfSize, -halfSize, halfSize);   // Left Of Triangle (Right)
		gl.glVertex3d(halfSize, -halfSize, -halfSize);  // Right Of Triangle (Right)
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Back)
		gl.glVertex3d(halfSize, -halfSize, -halfSize);  // Left Of Triangle (Back)
		gl.glVertex3d(-halfSize, -halfSize, -halfSize); // Right Of Triangle (Back)
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Left)
		gl.glVertex3d(-halfSize, -halfSize, -halfSize); // Left Of Triangle (Left)
		gl.glVertex3d(-halfSize, -halfSize, halfSize);  // Right Of Triangle (Left)
		gl.glEnd();                         // Finished Drawing The Triangle

		gl.glPopMatrix();
	}

	public void drawColoredPyramid(double size) {
		double halfSize = size/2;

		gl.glPushMatrix();

		gl.glBegin(GL.GL_TRIANGLES);        // Drawing Using Triangles
		gl.glColor3d(halfSize, 0.0f, 0.0f);     // Red
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Front)
		gl.glColor3d(0.0f, halfSize, 0.0f);     // Green
		gl.glVertex3d(-halfSize, -halfSize, halfSize);  // Left Of Triangle (Front)
		gl.glColor3d(0.0f, 0.0f, halfSize);     // Blue
		gl.glVertex3d(halfSize, -halfSize, halfSize);   // Right Of Triangle (Front)
		gl.glColor3d(halfSize, 0.0f, 0.0f);     // Red
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Right)
		gl.glColor3d(0.0f, 0.0f, halfSize);     // Blue
		gl.glVertex3d(halfSize, -halfSize, halfSize);   // Left Of Triangle (Right)
		gl.glColor3d(0.0f, halfSize, 0.0f);     // Green
		gl.glVertex3d(halfSize, -halfSize, -halfSize);  // Right Of Triangle (Right)
		gl.glColor3d(halfSize, 0.0f, 0.0f);     // Red
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Back)
		gl.glColor3d(0.0f, halfSize, 0.0f);     // Green
		gl.glVertex3d(halfSize, -halfSize, -halfSize);  // Left Of Triangle (Back)
		gl.glColor3d(0.0f, 0.0f, halfSize);     // Blue
		gl.glVertex3d(-halfSize, -halfSize, -halfSize); // Right Of Triangle (Back)
		gl.glColor3d(halfSize, 0.0f, 0.0f);     // Red
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Left)
		gl.glColor3d(0.0f, 0.0f, halfSize);     // Blue
		gl.glVertex3d(-halfSize, -halfSize, -halfSize); // Left Of Triangle (Left)
		gl.glColor3d(0.0f, halfSize, 0.0f);     // Green
		gl.glVertex3d(-halfSize, -halfSize, halfSize);  // Right Of Triangle (Left)
		gl.glEnd();                         // Finished Drawing The Triangle

		gl.glPopMatrix();
	}

	public void drawSquare(double size) {
		double halfSize = size/2;

		gl.glTranslated(0,0, halfSize);

		gl.glBegin(GL.GL_TRIANGLE_FAN);
		gl.glVertex2d(-halfSize,-halfSize);    // Draw the square (before the
		gl.glVertex2d(halfSize,-halfSize);     //   the translation is applied)
		gl.glVertex2d(halfSize,halfSize);      //   on the xy-plane, with its
		gl.glVertex2d(-halfSize,halfSize);     //   at (0,0,0).
		gl.glEnd();
	}

	public void drawGrid(float x, float y, float z, float size, int rows, int columns) {

		//Axis Width
		gl.glLineWidth(1f);
		gl.glTranslatef(-x/2, -y/2, -z/2);

		//Draw Rows
		gl.glBegin(GL.GL_LINES);

		int a = 0, b = 0;

		for (int i = 0;i < rows+1; i++) {
			gl.glVertex3d(a+size*i, 0.0, b+size*columns);
			gl.glVertex3d(a+size*i, 0, b);
		}

		for (int j = 0;j < columns+1; j++) {
			gl.glVertex3d(a+size*rows, 0.0, b+size*j);
			gl.glVertex3d(a, 0, b+size*j);
		}

		gl.glEnd();
		gl.glTranslatef(x/2, y/2, z/2);
	}

	public void drawGrid(float size, int rows, int columns) {
		drawGrid(rows*size, 0, columns/size, size, rows, columns);
	}

	/*
	 * Draw camera model
	 */
	public void drawCamera(Camera camera) {
		Color color = camera.getColor();

		gl.glLineWidth(0.5f);
		glSetColor(color);

		//Draw origin point
		drawPoint(camera.position, color);

		//Draw target point
		drawPoint(camera.getTarget(), color);

		//Draw target line
		drawLine(camera.position, camera.getTarget());

		//Draw Camera as 3D Model
		gl.glPushMatrix();

		gl.glTranslated(camera.position.x, camera.position.y, camera.position.z);

		gl.glRotated(90, 0, 1, 0);
		gl.glRotated(camera.angleXY()+30, 1, 0, 0);
		gl.glRotated(camera.angleXZ()+180, 0, 0, 1);

		ColoredPoint3D ra = new ColoredPoint3D(-0.1, 0.2, 0.1);
		ColoredPoint3D rb = new ColoredPoint3D(0.1, 0.2, 0.1);
		ColoredPoint3D rc = new ColoredPoint3D(-0.1, 0.2, -0.1);
		ColoredPoint3D rd = new ColoredPoint3D(0.1, 0.2, -0.1);

		drawPoint(ra, color);
		drawPoint(rb, color);
		drawPoint(rc, color);
		drawPoint(rd, color);

		drawLine(ra, rb);
		drawLine(ra, rc);
		drawLine(rd, rb);
		drawLine(rd, rc);

		ColoredPoint3D origin = new ColoredPoint3D();

		drawLine(origin, ra);
		drawLine(origin, rb);
		drawLine(origin, rc);
		drawLine(origin, rd);

		gl.glTranslated(-camera.position.x, -camera.position.y, -camera.position.z);

		gl.glPopMatrix();
	}

	public void glSetColor(Color color) {
		float red = ((float)color.getRed()/255);
		float green = ((float)color.getGreen()/255);
		float blue = ((float)color.getBlue()/255);

		getGL2().glColor3f(red, green, blue);
	}

	/**
	 * Found at http://www.gamedev.net/topic/359467-draw-cylinder-with-triangle-strips/?view=findpost&p=3360321
	 * @param radius
	 * @param height
	 */
	public void drawCylinder(double radius, double height) {

		double numSteps = DEFAULT_RESOLUTION;

		double hl = height * 0.5f;
		double a = 0.0;
		double step = 2*Math.PI / numSteps;

		gl.glBegin(GL2.GL_TRIANGLE_STRIP);
		for (int i = 0; i <= numSteps; ++i) {
		    double x = Math.cos(a) * radius;
		    double y = Math.sin(a) * radius;
		    gl.glVertex3d(x,-hl, y);
		    gl.glVertex3d(x, hl, y);

		    a += step;
		}
		gl.glEnd();
	}

	public void drawCylinder(double baseRadius, double topRadius, double height) {
		drawCylinder(baseRadius, topRadius, height, 0, 0, 0);
	}

	public void drawCylinder(double baseRadius, double topRadius, double height, double x, double y, double z) {

		final int slices = DEFAULT_RESOLUTION;
		final int stacks = DEFAULT_RESOLUTION;

		gl.glPushMatrix();

		// Draw sphere (possible styles: FILL, LINE, POINT).
		//gl.glColor3f(0.3f, 0.5f, 1f);

		gl.glTranslated(x, y, z);

		GLUquadric cylinder = generateQuadric(glu);

		glu.gluCylinder(cylinder, baseRadius, topRadius, height, slices, stacks);
		glu.gluDeleteQuadric(cylinder);

		gl.glPopMatrix();
	}

	public void drawFrustrum(Frustrum frustrum) {

		gl.glPushMatrix();

		setColor(Color.RED);

		gl.glBegin(GL2.GL_LINES);

		//Draw Near Plane
		vertex(gl, frustrum.nBottomLeft);
		vertex(gl, frustrum.nBottomRight);

		vertex(gl, frustrum.nBottomLeft);
		vertex(gl, frustrum.nTopRight);

		vertex(gl, frustrum.nTopLeft);
		vertex(gl, frustrum.nTopRight);

		vertex(gl, frustrum.nTopLeft);
		vertex(gl, frustrum.nBottomRight);

		//Draw Far Plane
		vertex(gl, frustrum.fBottomLeft);
		vertex(gl, frustrum.fBottomRight);

		vertex(gl, frustrum.fBottomLeft);
		vertex(gl, frustrum.fTopRight);

		vertex(gl, frustrum.fTopLeft);
		vertex(gl, frustrum.fTopRight);

		vertex(gl, frustrum.fTopLeft);
		vertex(gl, frustrum.fBottomRight);

		//Draw Sides
		vertex(gl, frustrum.nBottomLeft);
		vertex(gl, frustrum.fBottomLeft);

		vertex(gl, frustrum.nBottomRight);
		vertex(gl, frustrum.fBottomRight);

		vertex(gl, frustrum.nTopLeft);
		vertex(gl, frustrum.fTopLeft);

		vertex(gl, frustrum.nTopRight);
		vertex(gl, frustrum.fTopRight);

		gl.glEnd();

		gl.glPopMatrix();
	}

	public void drawBillboard(Billboard billboard) {

		Vector3 p00 = new Vector3();
		billboard.getP00(p00);

		Vector3 p10 = new Vector3();
		billboard.getP10(p10);

		Vector3 p11 = new Vector3();
		billboard.getP11(p11);

		Vector3 p01 = new Vector3();
		billboard.getP01(p01);

		gl.glBegin(GL2.GL_TRIANGLE_STRIP);

		gl.glTexCoord2d(0, 0);
		vertex(p00);

		gl.glTexCoord2d(1, 0);
		vertex(p10);

		gl.glTexCoord2d(0, 1);
		vertex(p01);

		gl.glTexCoord2d(1, 1);
		vertex(p11);

		gl.glEnd();
	}

	private void vertex(GL2 gl, Vector3 vertex) {
		gl.glVertex3f(vertex.x, vertex.y, vertex.z);
	}

	public boolean supportsExtension(String string) {
		return false;
	}

	public float getDeltaTime() {
		return 0;
	}


	public float[] colorAsArray(Color color) {
		float r = (float)color.getRed()/255;
		float g = (float)color.getGreen()/255;
		float b = (float)color.getBlue()/255;

		float[] array = new float[]{r,g,b};
		return array;
	}

	public void vertex(Vector3 vector3) {
		gl.glVertex3f(vector3.x, vector3.y, vector3.z);
	}

	public void setColorRGB(Vector3 color) {
		gl.glColor3f(color.x/0xff, color.y/0xff, color.z/0xff);
	}

}
