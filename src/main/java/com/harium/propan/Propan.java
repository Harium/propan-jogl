package com.harium.propan;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import com.harium.etyl.awt.core.AWTCore;
import com.harium.etyl.commons.module.Module;
import com.harium.etyl.core.animation.Animation;
import com.harium.etyl.i18n.LanguageModule;
import com.harium.etyl.loader.image.ImageLoader;
import com.harium.etyl.ui.UI;
import com.harium.etyl.util.PathHelper;
import com.harium.propan.core.GLCore;
import com.harium.propan.core.context.ApplicationGL;

public abstract class Propan {

	private static final long serialVersionUID = -6060864375960874373L;

	private static final int UPDATE_DELAY = 10;    // Delay Between control updates
	private static final int TIME_UPDATE_INTERVAL = 80;    // Display refresh frames per second 

	private ScheduledFuture<?> future;
	
	private ScheduledExecutorService executor;

	protected int w = 640;
	protected int h = 480;

	protected GLCore glCore;
	
	protected JFrame frame;
	
	protected String icon = "";
	protected String title = "Propan - Window";
	
	private boolean setupCalled = false;
	
	// Constructor
	public Propan(int w, int h) {
		super();
		
		this.w = w;
		this.h = h;
		
		glCore = new GLCore(w, h);
		addModules();
		glCore.initMonitors(w, h);
		
		frame = createFrame(w, h);
		glCore.setComponent(frame);

		initialSetup("");
		
		setMainApplication(startApplication());
	}
	
	protected void addModule(Module module) {
		glCore.addModule(module);
	}

	private void addModules() {
		addModule(Animation.getInstance());
		addModule(UI.getInstance());
		addModule(LanguageModule.getInstance());
	}
	
	protected void setPath(String path) {
		glCore.setPath(path);
	}
	
	protected JFrame createFrame(int w, int h) {
				
		JFrame frame = new JFrame();
				
		frame.setSize(w, h);
		frame.setUndecorated(false);
		frame.setTitle(title);
		frame.addWindowListener(buildWindowAdapter());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener((KeyListener) glCore.getKeyboard());
		
		glCore.setComponent(frame);
		
		//frame.setLocation(p);
		glCore.moveToCenter();
		
		return frame;
	}

	private WindowAdapter buildWindowAdapter() {
		return new WindowAdapter() {
			@Override 
			public void windowClosing(WindowEvent e) {
				// Use a dedicate thread to run the stop() to ensure that the
				// animator stops before program exits.
				new Thread() {
					@Override 
					public void run() {
						glCore.stop(); // stop the animator loop
						System.exit(0);
					}
					
				}.start();
			}
			
		};
	}
	
	public void hideCursor() {
		AWTCore.hideDefaultCursor(frame);
	}
	
	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	protected void init() {
		frame.requestFocus();
		frame.setVisible(true);
		//startThread();
		//TODO Update Methods
		executor = Executors.newSingleThreadScheduledExecutor();
		
		future = executor.scheduleAtFixedRate(glCore, UPDATE_DELAY, UPDATE_DELAY, TimeUnit.MILLISECONDS);
		
		frame.add(glCore.getPanel());
		
		AWTCore.hideDefaultCursor(frame);
		updateIcon();
		
		glCore.start();
	}
	
	private void updateIcon() {
		if(!icon.isEmpty()) {
			frame.setIconImage(ImageLoader.getInstance().getImage(icon));
		}
	}

	protected void centerCursor() {
		try {
			Robot robot = new Robot();
			Rectangle bounds =  frame.getBounds();			
			robot.mouseMove(bounds.x+bounds.width/2, bounds.y+bounds.height/2);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void initialSetup(String path) {
		String directory = PathHelper.currentFileDirectory()+path;
		glCore.setPath(directory);
		glCore.initDefault();
		
		setupCalled = true;
	}
	
	public abstract ApplicationGL startApplication();
	
	private void setMainApplication(ApplicationGL applicationGL) {
		glCore.setMainApplication3D(applicationGL);
	}
	
	protected void setIcon(String icon) {
		this.icon = icon;
	}
	
}
