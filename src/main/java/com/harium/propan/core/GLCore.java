package com.harium.propan.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.harium.etyl.commons.context.Session;
import com.harium.etyl.core.EtylFrame;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.harium.propan.core.loader.AnimationLoader;
import com.harium.propan.core.loader.MeshLoader;
import com.harium.etyl.core.BaseCore;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.event.GUIEvent;
import com.harium.etyl.loader.FontLoader;
import com.harium.etyl.loader.Loader;
import com.harium.etyl.loader.image.ImageLoader;
import com.harium.etyl.util.io.IOHelper;
import com.harium.propan.core.context.ApplicationGL;
import com.harium.propan.core.context.DefaultLoadApplicationGL;
import com.harium.propan.glg2d.GLG2DPanel;
import com.harium.propan.glg2d.GLGraphics2D;
import com.harium.propan.core.graphics.AWTGraphics3D;
import com.harium.propan.loader.TextureLoader;

import com.jogamp.opengl.util.FPSAnimator;


public class GLCore extends BaseCore implements GLEventListener, Runnable {

	public static final String COMPONENT = "COMPONENT";

	private static final int UPDATE_DELAY = 20;

	private static final int REFRESH_FPS = 60; // Display refresh frames per second

	private GLGraphics2D glGraphics;

	private AWTGraphics3D graphic;

	private String path;

	private GLG2DPanel canvas = new GLG2DPanel();

	private FPSAnimator animator;  // Used to drive display()

	//TODO Most Important
	private WindowGL activeWindowGL;

	private ExecutorService loadExecutor;

	private JFrame component;

	private int oldW = 0;
	private int oldH = 0;

	private ApplicationGL anotherApplication3D;

	private boolean changeApp = false;

	private Set<Loader> loaders = new HashSet<Loader>();
	
	private static final Font DEFAULT_FONT = new Font("ARIAL", Font.PLAIN, 14);
	private static final Color DEFAULT_COLOR = Color.BLACK;

	public GLCore(int w, int h) {
		super(w, h);

		activeWindowGL = new WindowGL(0, 0, w, h);

		glGraphics = new GLGraphics2D();

		graphic = new AWTGraphics3D(w,h);

		canvas.addMouseMotionListener(getMouse());
		canvas.addMouseWheelListener(getMouse());
		canvas.addMouseListener(getMouse());

		canvas.getCanvas().addGLEventListener(this);		

		animator = new FPSAnimator(REFRESH_FPS, true);
		animator.add(canvas.getCanvas());
				
		initLoaders();
	}
	
	public void initLoaders() {
		loaders.add(ImageLoader.getInstance());
		loaders.add(FontLoader.getInstance());		
		loaders.add(TextureLoader.getInstance());
		loaders.add(MeshLoader.getInstance());
		loaders.add(AnimationLoader.getInstance());
	}

	public void setComponent(JFrame frame) {
		this.component = frame;
		activeWindowGL.setLocation(frame.getX(), frame.getY());
	}

	public String getPath() {
		return path;
	}

	public void setPath(String url) {

		String s = IOHelper.fixPath(url);

		this.path = s;
	}
	
	public void initDefault() {
		for(Loader loader:loaders) {
			loader.setUrl(path);
			loader.initLoader();
		}
	}

	public void start() {
		animator.start();
	}

	public void stop() {
		animator.stop();
	}

	//TODO Core must implement GLEventListener
	@Override
	public void init(GLAutoDrawable drawable) {
		resetGraphics(drawable);

		//TODO verify
		activeWindowGL.getLoadApplication3D().init(graphic);
	}

	private void resetGraphics(GLAutoDrawable drawable) {
		glGraphics.setCanvas(drawable);
		
		graphic.setGraphics(glGraphics);
		graphic.setDrawable(drawable);
		
		initGraphics(glGraphics);
	}

	private void initGraphics(GLGraphics2D graphics) {
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		graphic.setColor(DEFAULT_COLOR);
		graphic.setFont(DEFAULT_FONT);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		graphic.setDrawable(drawable);
		activeWindowGL.getApplication3D().dispose(graphic);
	}

	@Override
	public void display(final GLAutoDrawable drawable) {
		graphic.setDrawable(drawable);
		
		if(changeApp) {
			changeApplication(drawable);
		} else {
			updateSuperEvents(superEvent);
			drawActiveWindow(drawable);
		}
	}

	private void updateSuperEvents(GUIEvent event) {

		if(event == GUIEvent.ENABLE_FULL_SCREEN) {

			this.oldW = component.getWidth();
			this.oldH = component.getHeight();

			component.setExtendedState(JFrame.MAXIMIZED_BOTH);

		} else if(event == GUIEvent.DISABLE_FULL_SCREEN) {

			component.setExtendedState(JFrame.NORMAL);
			component.setSize(oldW, oldH);			
		}

	}

	private void changeApplication(GLAutoDrawable drawable) {

		graphic.setDrawable(drawable);
		DefaultLoadApplicationGL load3D = activeWindowGL.getLoadApplication3D();

		load3D.init(graphic);
		load3D.load();

		anotherApplication3D.init(graphic);
		anotherApplication3D.load();

		loadExecutor = Executors.newSingleThreadExecutor();

		loadExecutor.execute(new Runnable() {

			@Override
			public void run() {

				activeWindowGL.setApplication3D(anotherApplication3D);

				//desktop.reload(anotherApplication3D);

			}
		});

		loadExecutor.shutdown();

		changeApp = false;

	}

	private void drawActiveWindow(GLAutoDrawable drawable) {
		
		//Pre Drawing
		resetGraphics(drawable);
		preDisplay(graphic);

		//Display 3D
		reshape(drawable, canvas.getX(), canvas.getY(), canvas.getWidth(), canvas.getHeight());
		activeWindowGL.getApplication3D().display(graphic);

		//Post Drawing
		//Avoid 2D drawings being mixed with 3D Environment
		graphic.getGL2().glClear(GL.GL_DEPTH_BUFFER_BIT);
		resetGraphics(drawable);
		draw(graphic);
	}

	private void preDisplay(AWTGraphics3D g3d) {
		activeWindowGL.getApplication3D().preDisplay(g3d);
	}

	@Override
	public void changeApplication() {

		ApplicationGL nextApplication = (ApplicationGL)activeWindowGL.getApplication3D().getNextApplication();
		
		setMainApplication3D(nextApplication);
	}

	public void setMainApplication3D(ApplicationGL application3D) {
		anotherApplication3D = application3D;
		anotherApplication3D.setSession(session);

		replaceWindow(activeWindowGL);

		reload();
	}

	protected Session buildSession() {
		Session session = new Session();
		session.put(COMPONENT, component);
		return session;
	}

	private void reload() {
		//activeWindowGL.reload(anotherApplication3D);
		activeWindowGL.setApplication(anotherApplication3D);

		changeApp = true;
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		graphic.setDrawable(drawable);
		activeWindowGL.getApplication3D().reshape(graphic, x, y, width, height);
	}

	public void run() {
		update(System.currentTimeMillis());
	}

	public JComponent getPanel() {
		return canvas;
	}
	
	public void hideDefaultCursor() {
		
		int[] pixels = new int[16 * 16];
		
		Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				Toolkit.getDefaultToolkit().createImage( new MemoryImageSource(16, 16, pixels, 0, 16))
				, new Point(0, 0), "invisibleCursor");
		component.setCursor( transparentCursor );
	}

	@Override
	public void initMonitors(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToCenter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEngine(EtylFrame engine) {

	}

	@Override
	public void startEngine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startCore(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double delta) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSuperEvent(GUIEvent event) {

	}

}
