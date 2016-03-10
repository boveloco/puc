package materia;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class RGBAf  {
    RGBAf(){}
    
    RGBAf(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    RGBAf(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
            
        private float a;
        private float r;
        private float g;
        private float b;
        
        public float getA(){
            return this.a;
        }
        public float getR(){
            return this.r;
        }
        
        public float getG(){
            return this.g;
        }
        
        public float getB(){
            return this.b;
        }
        
        public void setR(float r){
            this.r = r;
        }
        public void setG(float g){
            this.g = g;
        }
        public void setB(float b){
            this.b = b;
        }
        public void setA(float a){
            this.a = a;
        }
        
        public RGBAf getRGBf(BufferedImage img, int x, int y){
            RGBAf out = new RGBAf();
            Color c;
            c = new Color(img.getRGB(x, y));
            out.setR(c.getRed() / 255);
            out.setG(c.getGreen() / 255);
            out.setB(c.getBlue() / 255);
            
            
            return out;
        }
        public RGBAf getRGBAf(BufferedImage img, int x, int y){
            RGBAf out = new RGBAf();
            Color c;
            c = new Color(img.getRGB(x, y));
            out.setR(c.getRed() / 255);
            out.setG(c.getGreen() / 255);
            out.setB(c.getBlue() / 255);
            out.setA(c.getAlpha() / 255);
            
            return out;
        }
        
        public void setRGBf(int r, int g, int b, int x, int y){
            
            this.setR(r / 255);
            this.setG(g / 255);
            this.setB(b / 255);
        }
        
        public void setRGBAf(int r, int g, int b,int a, int x, int y){
            
            this.setR(r / 255);
            this.setG(g / 255);
            this.setB(b / 255);
            this.setA(a / 255);
            
        }
        
}
