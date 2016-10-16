package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Material;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Texture;
import br.pucpr.mage.Window;
import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.NormalMapMaterial;
import br.pucpr.mage.phong.PhongMaterial;

public class NormalMapping implements Scene {
    private static final String PATH = "../img/opengl/";
    
    private Keyboard keys = Keyboard.getInstance();
    private boolean normals = false;
    
    //Dados da cena
    private Camera camera = new Camera();
    private DirectionalLight light;

    //Dados da malha
    private Mesh mesh;
    
    private PhongMaterial material;              
    private NormalMapMaterial materialNM;
    
    private float dirX = 0.0f;
    private float dirY = 0.0f;
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glPolygonMode(GL_FRONT_FACE, GL_LINE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        camera.getPosition().z = 0.9f;
        
        light = new DirectionalLight(
                new Vector3f( 0.0f, 0.0f, -1.0f),     //direction
                new Vector3f( 0.02f,  0.02f,  0.02f), //ambient
                new Vector3f( 1.0f,  1.0f,  1.0f),    //diffuse
                new Vector3f( 1.0f,  1.0f,  1.0f));   //specular

        mesh = MeshFactory.createSquareWithNormalMap();
        
        Texture stone = new Texture(PATH + "textures/stone_t.png");
        material = new PhongMaterial(
                new Vector3f(1.0f, 1.0f, 1.0f), //ambient
                new Vector3f(0.7f, 0.7f, 0.7f), //diffuse
                new Vector3f(0.5f, 0.5f, 0.5f), //specular
                64.0f);                         //specular power
        material.setTexture(stone);
        
        materialNM = new NormalMapMaterial(
                new Vector3f(1.0f, 1.0f, 1.0f), //ambient
                new Vector3f(0.7f, 0.7f, 0.7f), //diffuse
                new Vector3f(0.5f, 0.5f, 0.5f), //specular
                64.0f);                         //specular power
        materialNM.setTexture(stone);
        materialNM.setNormalMap(new Texture(PATH + "normals/stone_n.png"));
    }

    @Override
    public void update(float secs) {
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }

        if (keys.isDown(GLFW_KEY_A)) {
            dirX += secs;
        }

        if (keys.isDown(GLFW_KEY_D)) {
            dirX -= secs;
        }
        
        if (keys.isDown(GLFW_KEY_W)) {
            dirY += secs;
        }

        if (keys.isDown(GLFW_KEY_S)) {
            dirY -= secs;
        }
        
        if (keys.isDown(GLFW_KEY_SPACE)) {
            dirX = 0;
            dirY = 0;
        }
        
        if (keys.isPressed(GLFW_KEY_N)) {
            normals = !normals;
        }
        
        light.getDirection().x = dirX;
        light.getDirection().y = dirY;
    }

@Override
public void draw() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    Material materialToDraw = normals ? materialNM : material;
    
    Shader shader = materialToDraw.getShader();
    shader.bind()
        .setUniform("uProjection", camera.getProjectionMatrix())
        .setUniform("uView", camera.getViewMatrix())
        .setUniform("uCameraPosition", camera.getPosition());        
    light.apply(shader);
    shader.unbind();

    mesh.setUniform("uWorld", new Matrix4f());
    mesh.draw(materialToDraw);
}

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new NormalMapping(), "Normal mapping", 1024, 768).show();
    }
}
