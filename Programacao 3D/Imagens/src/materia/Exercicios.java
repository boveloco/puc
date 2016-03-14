package materia;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Exercicios {
	public BufferedImage bright(BufferedImage img, float intensity) {
		BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
		
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight();j++) {
				// img.setRGB(i, j, (int) (img.getRGB(j, i) * intensity));

				Color pixel = new Color(img.getRGB(i, j));
				//System.out.println("Red =" + pixel.getRed());
				//System.out.println("Green =" +pixel.getGreen());
				//System.out.println("Blue =" + pixel.getBlue());
				int r = (int) (pixel.getRed() * intensity);
				int g = (int) (pixel.getGreen() * intensity);
				int b = (int) (pixel.getBlue() * intensity);

				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}
				//System.out.println("r =" + r);
				//System.out.println("g =" + g);
				//System.out.println("b =" + b);
				out.setRGB(i, j, new Color(r, g, b).getRGB());
			}
		}
		return img;
	}

	public BufferedImage grayscale(BufferedImage img) {
		BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight();j++) {
				Color c = new Color(img.getRGB(i, j));
				double r = c.getRed() * 0.3;
				double g = c.getGreen() * 0.59;
				double b = c.getBlue() * 0.11;
				out.setRGB(i, j, new Color((int)r,(int) g,(int) b).getRGB());
			}
		}

		return out;
	}

	public BufferedImage threshold(BufferedImage img, int value) {
		BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight();j++) {
				Color c = new Color(img.getRGB(i, j));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();

				if (c.getRed() >= value)
					r = 255;
				if (c.getGreen() >= value)
					g = 255;
				if (c.getBlue() >= value)
					b = 255;
				out.setRGB(i, j, new Color(r, g, b).getRGB());
			}
		}
		return out;
	}

	public BufferedImage subtract(BufferedImage img1, BufferedImage img2) {
		if (img1.getHeight() != img2.getHeight() && img1.getWidth() != img2.getWidth())
			return null;

		BufferedImage out = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
		for (int i = 0; i < img1.getWidth(); i++) {
			for (int j = 0; j < img1.getHeight();j++) {
				Color c = new Color(img1.getRGB(i, j));
				Color d = new Color(img2.getRGB(i, j));
				int r = c.getRed() - d.getRed();
				int g = c.getGreen() - d.getGreen();
				int b = c.getBlue() - d.getBlue();

				if (r < 0) {
					r = 0;
				}
				if (g < 0) {
					g = 0;
				}
				if (b < 0) {
					b = 0;
				}

				out.setRGB(i, j, new Color(r, g, b).getRGB());
			}
		}
		return out;
	}

	public BufferedImage add(BufferedImage img1, BufferedImage img2) {
		if (img1.getHeight() != img2.getHeight() && img1.getWidth() != img2.getWidth())
			return null;

		BufferedImage out = new BufferedImage(img1.getWidth(),img1.getHeight(), img1.getType());
		for (int i = 0; i < img1.getWidth(); i++) {
			for (int j = 0; j < img1.getHeight();j++) {
				Color c = new Color(img1.getRGB(i, j));
				Color d = new Color(img2.getRGB(i, j));
				int r = c.getRed() + d.getRed();
				int g = c.getGreen() + d.getGreen();
				int b = c.getBlue() + d.getBlue();

				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}

				out.setRGB(i, j, new Color(r, g, b).getRGB());
			}
		}
		return out;
	}

	public BufferedImage lerp(BufferedImage img1, BufferedImage img2, float percent) {
		BufferedImage out = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());

		for (int i = 0; i < img1.getWidth(); i++) {
			for (int j = 0; j < img1.getHeight();j++) {
				Color c = new Color(img1.getRGB(i, j));
				Color d = new Color(img2.getRGB(i, j));
				int r = (int) (c.getRed() * (1.0f - percent) + (d.getRed() * percent));
				int g = (int) (c.getGreen() * (1.0f - percent) + (d.getGreen() * percent));
				int b = (int) (c.getBlue() * (1.0f - percent) + (d.getBlue() * percent));

				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}
				if (r < 0) {
					r = 0;
				}
				if (g < 0) {
					g = 0;
				}
				if (b < 0) {
					b = 0;
				}
				

				out.setRGB(i, j, new Color(r, g, b).getRGB());
			}
		}

		return out;
	}

	public BufferedImage egaTable(BufferedImage img) {
		BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight();j++) {
				Color c = new Color(img.getRGB(i, j));
				float r = (c.getRed() % 64) * 64;
				float g = (c.getGreen() % 64) * 64;
				float b = (c.getBlue() % 64) * 64;
				
				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}
				
				out.setRGB(i, j, new Color((int)r, (int)g, (int)b).getRGB());
			}

		}

		return out;
	}

	public BufferedImage linha(BufferedImage img, int x1, int x2, int y1, int y2, Color color) {
		BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
		// equacao da reta
		float x;
		float m;
		if ((x2 - x1) == 0){
			m = 0;
		} else {
		    m = (y2 - y1) / (x2 - x1);			
		}
		// y = y1 + m * x + m * x1;
		if (y1 > y2) {
			for (int i = y1; i < y2; i++) {
				x = (i - y1 + m * x1) / m;
				Color c = new Color(0, 0, 0);
				out.setRGB(i, (int) x, c.getRGB());
			}
		} else if (y2 < y1) {
			for (int i = y2; i > y1; i--) {
				x = (i - y1 + m * x1) / m;
				Color c = new Color(0, 0, 0);
				out.setRGB(i, (int) x, c.getRGB());
			}
		} else {
			for (int i = y2; i < y1; i--) {
				x = (i - y1 + m * x1) / m;
				Color c = new Color(0, 0, 0);
				out.setRGB(i, (int) x, c.getRGB());
			}
		}
		return out;
	}
}