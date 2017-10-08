package com.harium.propan.graphics;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.harium.propan.core.graphics.AWTGraphics3D;
import com.harium.propan.core.graphics.GLDrawable;
import com.harium.propan.core.graphics.Graphics3D;
import com.harium.propan.core.loader.MeshLoader;
import com.harium.propan.core.model.Face;
import com.harium.propan.core.model.Group;
import com.harium.propan.core.model.Model;
import com.harium.propan.linear.Shape;
import com.harium.propan.material.Material;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jogamp.opengl.GL.*;

/**
 * @author yuripourre
 * @license LGPLv3
 */

public class ModelInstance extends Shape implements GLDrawable {

    private Model model;

    private Map<Group, Material> materials = new HashMap<Group, Material>();

    private boolean drawTexture = true;

    public ModelInstance() {
        this(0, 0, 0);
    }

    public ModelInstance(float x, float y, float z) {
        super();
        this.transform.setToTranslation(x, y, z);
    }

    public ModelInstance(Model model) {
        this(0, 0, 0);

        this.model = model;
        loadMaterials();
    }

    public ModelInstance(String path) {
        this(0, 0, 0);

        loadVBO(path);
        loadMaterials();
    }

    protected void loadVBO(String path) {
        model = MeshLoader.getInstance().loadModel(path);
    }

    protected void loadMaterials() {
        //For each group in VBO
        for (Group group : model.getGroups()) {
            //If has a material
            if (group.getMaterial() != Group.NULL_MATERIAL) {
                materials.put(group, new Material(group.getMaterial()));
            } else {
                materials.put(group, new Material());
            }
        }
    }

    public boolean isDrawTexture() {
        return drawTexture;
    }

    public void setDrawTexture(boolean drawTexture) {
        this.drawTexture = drawTexture;
    }

    public List<Vector3> getVertexes() {
        return model.getVertices();
    }

    public void wireframeRender(GL2 gl) {

        gl.glPushMatrix();
        // Turn on wireframe mode
        gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_LINE);

        setupColor(gl);
        applyTransform(gl);

        drawTexture = false;

        // Draw Model
        for (Group group : model.getGroups()) {

            for (Face face : group.getFaces()) {
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                drawFace(gl, face);
                gl.glEnd();
            }
        }

        // Turn off wireframe mode
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPopMatrix();
    }

    public void diffuseRender(GL2 gl) {
        gl.glPushMatrix();

        // Turn on fill mode
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        applyTransform(gl);

        drawTexture = false;

        // Draw Model
        for (Group group : model.getGroups()) {
            Vector3 color = group.getMaterial().getKd();
            if (color == null) {
                setupColor(gl);
            } else {
                setupColor(gl, color);
            }

            for (Face face : group.getFaces()) {
                gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                drawFace(gl, face);
                gl.glEnd();
            }
        }

        // Turn off wireframe mode
        gl.glPopMatrix();
    }

    public void texturedRender(GL2 gl, Set<Face> set) {
        gl.glPushMatrix();

        setupTextureAttribs(gl);
        applyTransform(gl);
        setupColor(gl);

        Texture texture = null;

        for (Group group : model.getGroups()) {

            texture = setupGroup(gl, texture, group);
            drawSetFaces(gl, group, set);

            disableTexture(gl, texture);
        }

        gl.glPopMatrix();
    }

    public void texturedRender(GL2 gl) {
        applyTransform(gl);

        simpleDraw(gl);
    }

    public void simpleDraw(GL2 gl) {
        Texture texture = null;

        for (Group group : model.getGroups()) {

            texture = setupGroup(gl, texture, group);

            drawFaces(gl, group);

            disableTexture(gl, texture);
        }
    }

    private void setupTextureAttribs(GL2 gl) {
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);
    }

    private void resetTextureAttribs(GL2 gl) {
        gl.glDisable(GL.GL_CULL_FACE);
    }

    private void drawFaces(GL2 gl, Group group) {

        for (Face face : group.getFaces()) {

            /*int vertices = face.vertexIndex.length;
            setupIndexes(vertices);*/

            gl.glBegin(GL2.GL_TRIANGLE_FAN);
            drawTexturedFace(gl, face);
            gl.glEnd();
        }
    }

    private void drawSetFaces(GL2 gl, Group group, Set<Face> set) {

        for (Face face : group.getFaces()) {
            if (!set.contains(face)) {
                continue;
            }
           
            /*int vertices = face.vertexIndex.length;
            setupIndexes(vertices);*/

            gl.glBegin(GL2.GL_TRIANGLE_FAN);
            drawTexturedFace(gl, face);
            gl.glEnd();
        }

    }

    private Texture setupTexture(GL2 gl, Texture texture, Group group) {
        if (group.getMaterial() != null) {
            texture = loadTexture(group);
        }

        if (texture != null && drawTexture) {
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            // Use linear filter for texture if image is smaller than the original texture
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);

            texture.enable(gl);
            texture.bind(gl);
        } else {
            setupColor(gl);
        }

        return texture;
    }

    private void setupColor(GL2 gl) {
        gl.glColor3d((double) color.getRed() / 255, (double) color.getGreen() / 255, (double) color.getBlue() / 255);
    }

    private void setupColor(GL2 gl, Vector3 color) {
        gl.glColor3f(color.x, color.y, color.z);
    }

    private void disableTexture(GL2 gl, Texture texture) {
        //TODO make it better
        if (texture != null) {
            if (drawTexture) {
                texture.disable(gl);
            }
            texture = null;
        }
    }

    private Texture loadTexture(Group group) {
        Texture texture;

        String texturePath = group.getMaterial().getMapD();
        texture = materials.get(group).getTextureD();

        if (texture == null) {
            texturePath = group.getMaterial().getMapKd();
            texture = materials.get(group).getTextureKd();
        }

        if (texture != null) {
            drawTexture = true;
        } else {
            if (!texturePath.isEmpty()) {
                System.err.println("texture not found: " + texturePath);
            }
        }

        return texture;
    }

    public void applyTransform(GL2 gl) {
        gl.glMultMatrixf(transform.val, 0);
    }

    private void drawFace(GL2 gl, Face face) {
        for (int i = 0; i < face.vertexIndex.length; i++) {
            int index = face.vertexIndex[i];
            Vector3 vertex = model.getVertices().get(index);
            gl.glVertex3f(vertex.x, vertex.y, vertex.z);
        }
    }

    private void drawTexturedFace(GL2 gl, Face face) {

        for (int i = 0; i < face.vertexIndex.length; i++) {
            int vertexIndex = face.vertexIndex[i];

            //Set normals if any
            if (!model.getNormals().isEmpty() && face.normalIndex != null) {
                Vector3 normal = model.getNormals().get(face.normalIndex[i]);
                gl.glNormal3f(normal.x, normal.y, normal.x);
            }

            if (drawTexture) {
                int textureIndex = face.textureIndex[i];
                if (model.getTextures().size() > textureIndex) {
                    Vector2 texture = model.getTextures().get(textureIndex);
                    gl.glTexCoord2f(texture.x, texture.y);
                }
            }

            Vector3 vertex = model.getVertices().get(vertexIndex);
            gl.glVertex3f(vertex.x, vertex.y, vertex.z);
        }
    }

    @Override
    public void draw(Graphics3D g) {
        AWTGraphics3D graphics = (AWTGraphics3D) g;
        GL2 gl = graphics.getGL2();

        gl.glPushMatrix();
        setupTextureAttribs(gl);
        texturedRender(gl);
        resetTextureAttribs(gl);
        gl.glPopMatrix();
    }

    public void draw(GL2 gl, Set<Face> faces) {
        gl.glEnable(GL.GL_DEPTH_TEST);
        texturedRender(gl, faces);
        gl.glDisable(GL.GL_DEPTH_TEST);
    }

    public void drawAsWireFrame(GL2 gl) {
        gl.glEnable(GL.GL_DEPTH_TEST);
        wireframeRender(gl);
        gl.glDisable(GL.GL_DEPTH_TEST);
    }

    private Texture setupGroup(GL2 gl, Texture texture, Group group) {
        //Setup texture
        texture = setupTexture(gl, texture, group);

        //Setup diffuse color
        if (group.getMaterial().getKd() == null) {
            setupColor(gl);
        } else {
            setupColor(gl, group.getMaterial().getKd());
        }
        return texture;
    }

    public Model getModel() {
        return model;
    }

}