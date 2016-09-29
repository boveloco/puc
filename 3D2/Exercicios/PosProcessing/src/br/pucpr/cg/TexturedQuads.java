package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import br.pucpr.mage.*;
import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.PhongMaterial;
import br.pucpr.mage.postfx.PostFXMaterial;

public class TexturedQuads implements Scene {
    private static final String PATH = "../img/opengl/";
    
    private Keyboard keys = Keyboard.getInstance();
    private boolean normals = false;
    
    //Dados da cena
    private Camera camera = new Camera();
    private DirectionalLight light;

    //Dados da malha
    private Mesh mesh;
    private PhongMaterial material; 
    
    //Dados do Canvas
    private Mesh canvas;
    private FrameBuffer fb;
    private PostFXMaterial postFX;
    
    private float angleX = 0.0f;
    private float angleY = 0.0f;
    
    private float percentage = 0.0f;
    private float time = 0.0f;
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glPolygonMode(GL_FRONT_FACE, GL_LINE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        //camera.getPosition().z = 0.9f;
        light = new DirectionalLight(
                new Vector3f( 1.0f, -1.0f, -1.0f),    //direction
                new Vector3f( 0.02f,  0.02f,  0.02f), //ambient
                new Vector3f( 1.0f,  1.0f,  1.0f),    //diffuse
                new Vector3f( 1.0f,  1.0f,  1.0f));   //specular

        mesh = MeshFactory.createSquare();
        material = new PhongMaterial(
                new Vector3f(1.0f, 1.0f, 1.0f), //ambient
                new Vector3f(0.7f, 0.7f, 0.7f), //diffuse
                new Vector3f(0.5f, 0.5f, 0.5f), //specular
                2.0f);                          //specular power
        material.setTexture(new Texture("./img/opengl/textures/stone_t.png"));
        
       canvas = MeshFactory.createCanvas();
       fb = FrameBuffer.forCurrentViewport();
       postFX = PostFXMaterial.defaultPostFX("fxKernel", fb);
       
       Matrix3f kernel = new Matrix3f( 1f, 2f, 1f,
    		   						   0f, 0f, 0f,
    		   						   -1f, -2f, -1f);

       /*canvas.setUniform("uKernel", kernel);
       canvas.setUniform("uPercentage", percentage);*/
    }

    @Override
    public void update(float secs) {
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }
        
        if (keys.isDown(GLFW_KEY_A)) {
            angleY += secs;
        }

        if (keys.isDown(GLFW_KEY_D)) {
            angleY -= secs;
        }
        
        if (keys.isDown(GLFW_KEY_W)) {
            angleX += secs;
        }

        if (keys.isDown(GLFW_KEY_S)) {
            angleX -= secs;
        }
        
        if (keys.isDown(GLFW_KEY_SPACE)) {
            angleY = 0;
            angleX = 0;
        }
        
        if (keys.isPressed(GLFW_KEY_N)) {
            normals = !normals;
        }
        
        if(time <= 1.0f)
        {
        	percentage += (0.8f * secs);
        }
        else 
        {
        	percentage -= (0.8f * secs);
        	time = (percentage <= 0.0f)? 0.0f : time;
        }
        
        time += secs;
    }

    @Override
    public void draw() {
        fb.bind();
        Shader shader = material.getShader();
        shader.bind()
            .setUniform("uProjection", camera.getProjectionMatrix())
            .setUniform("uView", camera.getViewMatrix())
            .setUniform("uCameraPosition", camera.getPosition());        
        light.apply(shader);        
        shader.unbind();
    
        mesh.setUniform("uWorld", new Matrix4f().rotateX(angleX).rotateY(angleY));
        mesh.draw(material);
        fb.unbind();
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        Matrix3f kernel = new Matrix3f( 1f, 0f, -1f,
				   						2f, 0f, -2f,
				   					    1f, 0f, -1f);

        shader = postFX.getShader();
        shader.bind()
        	  .setUniform("uKernel", kernel)
        	  .setUniform("uPercentage", percentage)
        	  .unbind();
		
        canvas.draw(postFX);
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new TexturedQuads(), "Textures", 800, 600).show();
    }
}
