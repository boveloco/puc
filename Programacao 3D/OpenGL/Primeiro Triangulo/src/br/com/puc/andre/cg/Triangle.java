package br.com.puc.andre.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;



import br.com.puc.andre.Utils;
import br.com.puc.andre.mage.Scene;
import br.com.puc.andre.mage.Window;

public class Triangle implements Scene {
	
	private int vao;
	private int positions;
	private int shader;
	
	private float[] vertexData = new float[] {
			-0.5f, -0.5f,
			 0.0f, 0.5f,
			 0.5f, -0.5f
	};
	
	private static final String VERTEX_SHADER = 
			"#version 330\n" +
			"in vec2 aPosition;\n" +
			"void main(){\n" +
			"   gl_Position = vec4(aPosition, 0.0, 1.0);\n" +
			"}";
	
	private static final String FRAGMENT_SHADER = 
			"#version 330\n" +
			"out vec4 out_color;\n" +
			"void main(){\n" +
			"	out_color = vec4(1.0, 0.0, 1.0, 1.0);\n" +
			"}";
	
	@Override
	public void init() {
		//PASSA PRA VGA AS MEMORIAS DOS VERTICES
		glClearColor(0.0f, 0.0f, 0.0f,1.0f);
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(vertexData.length);
		positionBuffer.put(vertexData).flip();
		
		positions = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		//SHADER
		int vertex = Utils.compileShader(GL_VERTEX_SHADER, VERTEX_SHADER);
		int fragment = Utils.compileShader(GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
		shader = Utils.linkPorgram(vertex, fragment);
	}

	@Override
	public void update(float secs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		glUseProgram(shader);
		//associa o buffer "positions" ao atributo "aPosition"
		int aPosition = glGetAttribLocation(shader, "aPosition");
		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0,0);
		
		//comanda o desenho
		glDrawArrays(GL_TRIANGLES, 0, 3);
		
		//faxina
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(aPosition);
		glBindVertexArray(0);
		glUseProgram(0);
	}

	@Override
	public void deinit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(long window, int key, int scancode, int action,
			int mods) {
		if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
			glfwSetWindowShouldClose(window,GLFW_TRUE);
		
	}
	
	public static void main(String[] args) {
		new Window(new Triangle()).show();
	}
}
