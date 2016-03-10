package materia;

import java.awt.Color;
import java.awt.image.BufferedImage;





public class Exercicios {
	public BufferedImage bright(BufferedImage img, float intensity){
		BufferedImage out = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());	
		for (int i = 0; i < img.getHeight(); i++) {
				for (int j = 0; j < img.getWidth(); j++) {
					//img.setRGB(i, j, (int) (img.getRGB(i, j) * intensity));
					
					Color pixel = new Color( img.getRGB(i,j));
					
					int r = (int) (pixel.getRed() * intensity);
					int g = (int) (pixel.getGreen() * intensity) ;
					int b = (int) (pixel.getBlue() * intensity);
					
                                        if (r>255){r = 255;}
                                        if (g>255){g = 255;}
                                        if (b>255){b = 255;}
                                        
					out.setRGB(i, j, new Color(r,g,b).getRGB());
				}
			}
		return img;
	}
	
	public BufferedImage grayscale(BufferedImage img){
		BufferedImage out = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());
                for (int i = 0; i < img.getHeight(); i++) {
                    for (int j = 0; j < img.getWidth(); j++) {
                        Color c = new Color(img.getRGB(i, j));
                        int r = (int) (c.getRed() * 0.3);
                        int g = (int)(c.getGreen() * 0.59);
                        int b = (int)(c.getBlue() * 0.11);
                        out.setRGB(i, j, new Color(r,g,b).getRGB());
                    }
                }
                
		return out;
	}
        
        public BufferedImage threshold(BufferedImage img, int value){
            BufferedImage out = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());
            
            
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    Color c = new Color(img.getRGB(i, j));
                    int r = c.getRed();
                    int g = c.getGreen();
                    int b = c.getBlue();
                    
                    if (c.getRed() >= value)
                        r = 255;
                    if(c.getGreen() >= value)
                        g = 255;
                    if(c.getBlue() >= value)
                        b = 255;
                    out.setRGB(i, j, new Color(r,g,b).getRGB());
                }
            }
            return out;
        }
        
        public BufferedImage subtract(BufferedImage img1, BufferedImage img2){
            if(img1.getHeight() != img2.getHeight() && img1.getWidth() != img2.getWidth())
                return null; 
            
            BufferedImage out = new BufferedImage(img1.getHeight(), img1.getWidth(), img1.getType());
            for (int i = 0; i < img1.getHeight(); i++) {
                for (int j = 0; j < img1.getWidth(); j++) {
                    Color c = new Color(img1.getRGB(i, j));
                    Color d = new Color(img2.getRGB(i, j));
                    int r = c.getRed()- d.getRed();
                    int g = c.getGreen() - d.getGreen();
                    int b = c.getBlue() - d.getBlue();
                    
                    if (r < 0 ){r = 0;}
                    if (g < 0 ){g = 0;}
                    if (b < 0 ){b = 0;}
                    
                    out.setRGB(i, j, new Color(r, g ,b).getRGB());
                }
            }
            return out;
        }
        public BufferedImage add(BufferedImage img1, BufferedImage img2){
            if(img1.getHeight() != img2.getHeight() && img1.getWidth() != img2.getWidth())
                return null; 
            
            BufferedImage out = new BufferedImage(img1.getHeight(), img1.getWidth(), img1.getType());
            for (int i = 0; i < img1.getHeight(); i++) {
                for (int j = 0; j < img1.getWidth(); j++) {
                    Color c = new Color(img1.getRGB(i, j));
                    Color d = new Color(img2.getRGB(i, j));
                    int r = c.getRed()+ d.getRed();
                    int g = c.getGreen() + d.getGreen();
                    int b = c.getBlue() + d.getBlue();
                    
                    if (r > 255 ){r = 255;}
                    if (g > 255 ){g = 255;}
                    if (b > 255 ){b = 255;}
                    
                    out.setRGB(i, j, new Color(r, g ,b).getRGB());
                }
            }
            return out;
        }
        public BufferedImage lerp(BufferedImage img1, BufferedImage img2, float percent){
            BufferedImage out = new BufferedImage(img1.getHeight(), img1.getWidth(), img1.getType());
            
            for (int i = 0; i < img1.getHeight(); i++) {
                for (int j = 0; j < img1.getWidth(); j++) {
                    Color c = new Color(img1.getRGB(i, j));
                    Color d = new Color(img2.getRGB(i, j));
                    int r = (int) (c.getRed() * (1.0f - percent) + (d.getRed() * percent));
                    int g = (int) (c.getGreen() * (1.0f - percent) + (d.getGreen() * percent));
                    int b = (int) (c.getBlue() * (1.0f - percent) + (d.getBlue() * percent));
                    
                    if (r > 255 ){r = 255;}
                    if (g > 255 ){g = 255;}
                    if (b > 255 ){b = 255;}
                    
                    out.setRGB(i, j, new Color(r, g ,b).getRGB());
                }
            }
            
            return out;
        }
        
        
}