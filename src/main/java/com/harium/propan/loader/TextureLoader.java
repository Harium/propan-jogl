package com.harium.propan.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;

import com.harium.etyl.loader.LoaderImpl;
import com.harium.etyl.loader.image.ImageLoader;

import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class TextureLoader extends LoaderImpl {

	private static TextureLoader instance = null;

	private static GLContext context;

	private TextureLoader() {
		super();

		folder = "assets/images/";
	}

	public static TextureLoader getInstance() {

		if(instance == null) {
			context = GLContext.getCurrent();
			instance = new TextureLoader();
		}

		return instance;
	}
	
	private void setupContext() {
		if(context == null) {
			context = GLContext.getCurrent();
		}
		
		if(context!=null) {
			GLContext.getCurrent().makeCurrent();	
		}
	}
	
	public BufferedImage loadBuffer(String fullPath, String textureName, boolean flipVertical) {
		File file = new File(fullPath+textureName);

		try {
			//return TextureIO.newTexture(file, false);
			BufferedImage image = ImageIO.read(file);
			if(image != null && flipVertical) {
				ImageUtil.flipImageVertically(image);
			}
			return image;

		} catch (GLException e) {
			System.err.println("Error creating texture from: "+file);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File not found: "+file);
			e.printStackTrace();
		}

		return null;
	}

	public Texture loadTexture(String fullPath, String textureName, boolean flipVertical) {
		BufferedImage buffer = loadBuffer(fullPath, textureName, flipVertical);
		return loadTexture(buffer);
	}

	public Texture loadTexture(String fullPath, String textureName) {
		return loadTexture(fullPath, textureName, true);
	}

	public Texture loadTexture(String textureName) {
		return loadTexture(getPath()+folder, textureName);
	}
	
	public Texture loadTexture(String textureName, boolean flipVertical) {
		return loadTexture(getPath()+folder, textureName, flipVertical);
	}

	public Texture loadTexture(BufferedImage buffer) {
		setupContext();
		
		return AWTTextureIO.newTexture(GLProfile.getGL2GL3(), buffer, false);
	}
	
	//TextureData methods
	public TextureData loadTextureData(String fullPath, String textureName, boolean flipVertical) {
		BufferedImage buffer = loadBuffer(fullPath, textureName, flipVertical);
		return loadTextureData(buffer);
	}

	public TextureData loadTextureData(String fullPath, String textureName) {
		return loadTextureData(fullPath, textureName, true);
	}

	public TextureData loadTextureData(String textureName) {
		return loadTextureData(getPath()+folder, textureName);
	}
	
	public TextureData loadTextureData(String textureName, boolean flipVertical) {
		return loadTextureData(getPath()+folder, textureName, flipVertical);
	}
	
	public TextureData loadTextureData(BufferedImage buffer) {
		setupContext();
		
		return AWTTextureIO.newTextureData(GLProfile.getGL2GL3(), buffer, false);
	}

	public Texture loadTexture(TextureData textureData) {
		return TextureIO.newTexture(textureData);
	}

	public Texture loadTextureFromFile(String path) {
		return loadTextureFromFile(path, false);
	}
	
	public Texture loadTextureFromFile(String path, boolean absolute) {
		BufferedImage image = ImageLoader.getInstance().getImage(path, absolute);
		return loadTexture(image);
	}

}