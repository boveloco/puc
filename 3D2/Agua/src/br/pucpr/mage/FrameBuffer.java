package br.pucpr.mage;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class FrameBuffer {
    private int id;
    private int idDepth;
    
    private int width;
    private int height;
    
    private Texture texture;    
    
    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        
        texture = new Texture(width, height, new TextureParameters(GL_LINEAR));
        
        idDepth = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, idDepth);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT16, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        
        //Configura o frame buffer
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);    
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.getId(), 0);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, idDepth);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);       
    }
    
    public static FrameBuffer forCurrentViewport() {        
        IntBuffer viewport = BufferUtils.createIntBuffer(4);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);
        
        viewport.get(); //x
        viewport.get(); //y
        int w = viewport.get();
        int h = viewport.get();
        return new FrameBuffer(w, h);
    }
    
    public int getId() {
        return id;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Texture getTexture() {
        return texture;
    }
    
    public FrameBuffer bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        return this;
    }
    
    public FrameBuffer unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        return this;
    }
}
