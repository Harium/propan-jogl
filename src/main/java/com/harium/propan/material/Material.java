package com.harium.propan.material;

import com.harium.propan.core.material.OBJMaterial;
import com.harium.propan.loader.TextureLoader;

import com.jogamp.opengl.util.texture.Texture;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class Material {
		
	//Ambient Color
	private Texture textureKa = null;
	
	//Diffuse Color
	private Texture textureKd = null;
	
	//Specular Color
	private Texture textureKs = null;
	
	//Specular Highlight
	private Texture textureNs = null;
	
	private Texture textureD = null;	
	
	public Material() {
		super();
	}
	
	public Material(OBJMaterial material) {
		super();
				
		if(!material.getMapD().isEmpty()) {
			setTextureD(TextureLoader.getInstance().loadTexture("", material.getMapD()));
		}
		
		if(!material.getMapKd().isEmpty()) {
			setTextureKd(TextureLoader.getInstance().loadTexture("", material.getMapKd()));	
		}
		
		if(!material.getMapKa().isEmpty()) {
			setTextureKa(TextureLoader.getInstance().loadTexture("", material.getMapKa()));	
		}
	}
	
	public Texture getTextureKa() {
		return textureKa;
	}

	public void setTextureKa(Texture textureKa) {
		this.textureKa = textureKa;
	}

	public Texture getTextureKd() {
		return textureKd;
	}

	public void setTextureKd(Texture textureKd) {
		this.textureKd = textureKd;
	}

	public Texture getTextureKs() {

		return textureKs;
	}

	public void setTextureKs(Texture textureKs) {
		this.textureKs = textureKs;
	}

	public Texture getTextureNs() {
		return textureNs;
	}

	public void setTexture_Ns(Texture textureNs) {
		this.textureNs = textureNs;
	}

	public Texture getTextureD() {

		return textureD;
	}

	public void setTextureD(Texture textureD) {
		this.textureD = textureD;
	}
		
}
