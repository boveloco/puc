package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import java.io.File;
import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Window;

public class LitCube implements Scene {
			
	private Keyboard keys = Keyboard.getInstance();
    
    private Mesh mesh;
    private float angleX = 0.0f;
    private float angleY = 0.5f;
    private Camera camera = new Camera();
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        try {
			mesh = MeshFactory.loadTerrain(new File(Files.HEIGHTMAP_IMAGE_PATH), 0.4f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        camera.getPosition().y = 100.0f;
        camera.getPosition().x = 100.0f;
    }

    @Override
    public void update(float secs) {
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }

        if (keys.isDown(GLFW_KEY_A)) {
            angleY += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_D)) {
            angleY -= Math.toRadians(180) * secs;
        }
        
        if (keys.isDown(GLFW_KEY_W)) {
            angleX += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_S)) {
            angleX -= Math.toRadians(180) * secs;
        }
    }

@Override
public void draw() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    
    Shader shader = mesh.getShader();
    shader.bind()
        .setUniform("uProjection", camera.getProjectionMatrix())
        .setUniform("uView", camera.getViewMatrix())
        .setUniform("uCameraPosition", camera.getPosition())
    	.setUniform("uAmbientLight", new Vector3f(0.1f, 0.1f, 0.1f))
    	.setUniform("uDiffuseLight", new Vector3f(1.0f, 1.0f, 0.0f))
    	.setUniform("uSpecularLight", new Vector3f(1.0f, 1.0f, 1.0f))
    	.setUniform("uAmbientMaterial", new Vector3f(1.0f, 1.0f, 1.0f))
    	.setUniform("uDiffuseMaterial", new Vector3f(1.0f, 0.0f, 0.0f))
    	.setUniform("uSpecularMaterial", new Vector3f(1.0f, 1.0f, 1.0f))
    	.setUniform("uSpecularPower", 128.0f)
    	.setUniform("uLightDir", new Vector3f(1.0f, -1.0f,  1.0f));
    shader.unbind();

    mesh.setUniform("uWorld", new Matrix4f().rotateY(angleY).rotateX(angleX).scale(0.1f));
    mesh.draw();
}

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        try {
			new Window(new LitCube(), "Cube with lights", 800, 600).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
