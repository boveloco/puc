package br.pucpr.mage.phong;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.HashMap;
import java.util.Map;

import br.pucpr.mage.Material;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Texture;

public class SkydomeMaterial implements Material {
	private Map<String, Texture> textures = new HashMap<>();
    private Shader shader = Shader.loadProgram("/br/pucpr/mage/resource/phong/skydome");
    
    public SkydomeMaterial setTexture(Texture ...textures) {
    	
    	for(int i = 0; i < textures.length; i++)
    	{
    		setTexture("uTex" + i, textures[i]);
    	}
    		
        return this;
    }
    
    public SkydomeMaterial setTexture(String name, Texture texture) {
        if (texture == null) {
            textures.remove(name);
        } else {
            textures.put(name, texture);
        }
        return this;
    }
    
	@Override
	public void setShader(Shader shader) {
		// TODO Auto-generated method stub
		if (shader == null) {
            throw new IllegalArgumentException("Shader cannot be null!");
        }
        this.shader = shader;
	}
	
	@Override
	public Shader getShader() {
		// TODO Auto-generated method stub
		return this.shader;
	}
	
	@Override
	public void apply() {
		// TODO Auto-generated method stub
		int texCount = 0;        
        for (Map.Entry<String, Texture> entry : textures.entrySet()) {
            glActiveTexture(GL_TEXTURE0 + texCount);
            entry.getValue().bind();
            shader.setUniform(entry.getKey(), texCount);
            texCount = texCount + 1;            
        }
		
	}
    
    
}
