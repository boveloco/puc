package br.pucpr.mage.phong;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.HashMap;
import java.util.Map;

import br.pucpr.mage.Material;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Texture;

public class SimpleMaterial implements Material {
    private Map<String, Texture> textures = new HashMap<>();
    private Shader shader;
    
    public SimpleMaterial(String shader) {
        this.shader = Shader.loadProgram(shader);
    }
    
    public Material setTexture(String name, Texture texture) {
        if (texture == null) {
            textures.remove(name);
        } else {
            textures.put(name, texture);
        }
        return this;
    }
    
    @Override
    public void apply() {
        int texCount = 0;        
        for (Map.Entry<String, Texture> entry : textures.entrySet()) {
            glActiveTexture(GL_TEXTURE0 + texCount);
            entry.getValue().bind();
            shader.setUniform(entry.getKey(), texCount);
            texCount = texCount + 1;            
        }
    }

    @Override
    public void setShader(Shader shader) {
        if (shader == null) {
            throw new IllegalArgumentException("Shader cannot be null!");
        }

        this.shader = shader;
    }

    @Override
    public Shader getShader() {
        return shader;
    }
}
