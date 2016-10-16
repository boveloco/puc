package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.pucpr.mage.FrameBuffer;
import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Texture;
import br.pucpr.mage.Window;
import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.MultiTextureMaterial;
import br.pucpr.mage.postfx.PostFXMaterial;
import br.pucpr.mage.phong.*;

public class Skydome implements Scene {
    public static final String PATH = "../img/opengl/";
    
    private Keyboard keys = Keyboard.getInstance();
    
    //Dados da cena
    private Camera camera = new Camera();
    private DirectionalLight light;

    //Dados da malha
    private Mesh mesh;
    private MultiTextureMaterial material;
    
    private Mesh skyMesh;
    private SkydomeMaterial skyMaterial;
        
    private float lookX = 0.0f;
    private float lookY = 0.0f;

    private float deltaTime = 0.0f;
    
    private Mesh canvas;
    private FrameBuffer fb;
    private PostFXMaterial postFX;
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glPolygonMode(GL_FRONT_FACE, GL_LINE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        camera.getPosition().set(0.0f, 20.0f, 145.0f);        
        
        light = new DirectionalLight(
                new Vector3f( 1.0f, -1.0f, -1.0f),    //direction
                new Vector3f( 0.1f,  0.1f,  0.1f), //ambient
                new Vector3f( 1.0f,  1.0f,  1.0f),    //diffuse
                new Vector3f( 1.0f,  1.0f,  1.0f));   //specular

        skyMesh = MeshFactory.createSphere(30, 30);
        skyMaterial = new SkydomeMaterial();
        skyMaterial.setTexture(new Texture(PATH + "textures/cloud1.png"), new Texture(PATH + "textures/cloud2.png"));
        
        try {
            mesh = MeshFactory.loadTerrain(new File(PATH + "heights/river.png"), 0.4f, 3);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }
        material = new MultiTextureMaterial(
                new Vector3f(1.0f, 1.0f, 1.0f), //ambient
                new Vector3f(0.9f, 0.9f, 0.9f), //diffuse
                new Vector3f(0.0f, 0.0f, 0.0f), //specular
                0.0f);                          //specular power
        
        material.setTexture(new Texture(PATH + "textures/sand.png"),
						    new Texture(PATH + "textures/grass.png"),
						    new Texture(PATH + "textures/rock.png"),
						    new Texture(PATH + "textures/snow.png"));
        //BlendMap
        material.setTexture("uBlendMap", new Texture(PATH + "textures/BlendMap.png"));
        
        canvas = MeshFactory.createCanvas();
        fb = FrameBuffer.forCurrentViewport();
        postFX = PostFXMaterial.defaultPostFX("fxNone", fb);
    }

    @Override
    public void update(float secs) {
        float SPEED = 1000 * secs;
        
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }
        
        if (keys.isDown(GLFW_KEY_LEFT_SHIFT)) {
            SPEED *= 10;
        }
        
        if (keys.isDown(GLFW_KEY_LEFT)) {
            lookX -= SPEED * secs;
        } else if (keys.isDown(GLFW_KEY_RIGHT)) {
            lookX += SPEED * secs;
        }
        
        if (keys.isDown(GLFW_KEY_UP)) {
            lookY += SPEED * secs;
        } else if (keys.isDown(GLFW_KEY_DOWN)) {
            lookY -= SPEED * secs;
        }    
        
        if (keys.isDown(GLFW_KEY_SPACE)) {
            lookX = 0;
            lookY = 0;              
        }
        camera.getTarget().set(lookX, lookY, 0.0f);
        deltaTime += secs;
    }

    public void drawScene() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        Shader shader = material.getShader();
        shader.bind()
            .setUniform("uProjection", camera.getProjectionMatrix())
            .setUniform("uView", camera.getViewMatrix())
            .setUniform("uCameraPosition", camera.getPosition());        
        light.apply(shader);        
        shader.unbind();
    
        mesh.setUniform("uWorld", new Matrix4f().rotateY((float)Math.toRadians(85)));
        mesh.draw(material);
        
        shader = skyMaterial.getShader();
        shader.bind()
            .setUniform("uProjection", camera.getProjectionMatrix())
            .setUniform("uView", camera.getViewMatrix())
            .setUniform("uDeltaTime", deltaTime);               
        shader.unbind();
    
        skyMesh.setUniform("uWorld", new Matrix4f().scale(300));
        skyMesh.draw(skyMaterial);
    }
    
    @Override
    public void draw() {        
        fb.bind();
        drawScene();
        fb.unbind();
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);        
        canvas.draw(postFX);
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new Skydome(), "SKYDOME", 1024, 748).show();
    }
}
