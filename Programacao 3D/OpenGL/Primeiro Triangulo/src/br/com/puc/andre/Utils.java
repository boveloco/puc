package br.com.puc.andre;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Utils {
	public static int compileShader(int shaderType, String code) {
		// compila o shader
		int shader = glCreateShader(shaderType);
		glShaderSource(shader, code);
		glCompileShader(shader);

		// testa pela existencia de erros

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new RuntimeException("Unable to compile shader."
					+ glGetShaderInfoLog(shader));
		}

		return shader;
	}

	public static int linkPorgram(int... shaders) {
		// cria o program e associa o shader
		int program = glCreateProgram();
		for (int shader : shaders) {
			glAttachShader(program, shader);
		}
		// faz o linking e testa por erros
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
			throw new RuntimeException("Unable to link shaders."
					+ glGetProgramInfoLog(program));
		}
		//deleta os shaders
		for (int shader : shaders) {
			glDetachShader(program, shader);
			glDeleteShader(shader);
		}
		return program;
	}
	
	
}
