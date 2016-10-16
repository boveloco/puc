package br.pucpr.cg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;

public class MeshFactory {
    public static Mesh createSquare() {
        return new MeshBuilder()
         .addVector3fAttribute("aPosition", 
               -0.5f,  0.5f, 0.0f,  //0
                0.5f,  0.5f, 0.0f,  //1
               -0.5f, -0.5f, 0.0f,  //2
                0.5f, -0.5f, 0.0f)  //3
         .addVector2fAttribute("aTexCoord", 
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f, 
                1.0f, 1.0f)
         .addVector3fAttribute("aNormal",
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f)         
        .setIndexBuffer(
                0,  2,  3,
                0,  3,  1)
        .create();
    }
    
    public static Mesh createCanvas() {
        return new MeshBuilder()
                .addVector2fAttribute("aPosition", 
                       -1.0f,  1.0f,  //0
                        1.0f,  1.0f,  //1
                       -1.0f, -1.0f,  //2
                        1.0f, -1.0f)  //3
                .addVector2fAttribute("aTexCoord", 
                       0.0f, 1.0f, 
                       1.0f, 1.0f, 
                       0.0f, 0.0f, 
                       1.0f, 0.0f)
               .setIndexBuffer(
                       0,  2,  3,
                       0,  3,  1)
               .create();
    }
    
    public static Mesh createCube() {        
        return new MeshBuilder()
        .addVector3fAttribute("aPosition", 
            //Face próxima
             -0.5f,  0.5f,  0.5f,  //0
              0.5f,  0.5f,  0.5f,  //1
             -0.5f, -0.5f,  0.5f,  //2
              0.5f, -0.5f,  0.5f,  //3
            //Face afastada
             -0.5f,  0.5f, -0.5f,  //4
              0.5f,  0.5f, -0.5f,  //5
             -0.5f, -0.5f, -0.5f,  //6
              0.5f, -0.5f, -0.5f,  //7
            //Face superior
             -0.5f,  0.5f,  0.5f,  //8
              0.5f,  0.5f,  0.5f,  //9
             -0.5f,  0.5f, -0.5f,  //10
              0.5f,  0.5f, -0.5f,  //11
            //Face inferior
             -0.5f, -0.5f,  0.5f,  //12
              0.5f, -0.5f,  0.5f,  //13
             -0.5f, -0.5f, -0.5f,  //14
              0.5f, -0.5f, -0.5f,  //15 
            //Face direita
              0.5f, -0.5f,  0.5f,  //16
              0.5f,  0.5f,  0.5f,  //17
              0.5f, -0.5f, -0.5f,  //18
              0.5f,  0.5f, -0.5f,  //19
            //Face esquerda
             -0.5f, -0.5f,  0.5f,   //20
             -0.5f,  0.5f,  0.5f,   //21
             -0.5f, -0.5f, -0.5f,  //22
             -0.5f,  0.5f, -0.5f)  //23      
        .addVector3fAttribute("aNormal",
            //Face próxima
              0.0f,  0.0f,  1.0f,
              0.0f,  0.0f,  1.0f,
              0.0f,  0.0f,  1.0f,
              0.0f,  0.0f,  1.0f,
            //Face afastada
              0.0f,  0.0f, -1.0f,
              0.0f,  0.0f, -1.0f,
              0.0f,  0.0f, -1.0f,
              0.0f,  0.0f, -1.0f,
            //Face superior
              0.0f,  1.0f,  0.0f,
              0.0f,  1.0f,  0.0f,
              0.0f,  1.0f,  0.0f,
              0.0f,  1.0f,  0.0f,
            //Face inferior
              0.0f, -1.0f,  0.0f,
              0.0f, -1.0f,  0.0f,
              0.0f, -1.0f,  0.0f,
              0.0f, -1.0f,  0.0f,
            //Face direita
              1.0f,  0.0f,  0.0f,
              1.0f,  0.0f,  0.0f,
              1.0f,  0.0f,  0.0f,
              1.0f,  0.0f,  0.0f,
            //Face esquerda
             -1.0f,  0.0f,  0.0f,
             -1.0f,  0.0f,  0.0f,
             -1.0f,  0.0f,  0.0f,
             -1.0f,  0.0f,  0.0f)        
        .setIndexBuffer(
            //Face próxima
              0,  2,  3,
              0,  3,  1,
            //Face afastada
              4,  7,  6,
              4,  5,  7,
            //Face superior
              8, 11, 10,
              8,  9, 11,
            //Face inferior
             12, 14, 15,
             12, 15, 13,
            //Face direita
             16, 18, 19,
             16, 19, 17,
            //Face esquerda
             20, 23, 22,
             20, 21, 23)
        .create();
    }   
    
	private static float calcLinear(float min, float max, float value, boolean inverse) {
		float range = max - min;
		float result = (value - min) / range;
		result = result < 0 ? 0 : (result > 1 ? 1 : result);
		return inverse ? 1 - result : result;
	}
	
	private static float calcPiramid(float min, float max, float value) {
		float mid = (min + max) / 2.0f;
		return value <= mid ? 
				calcLinear(min, mid, value, false) : 
			    calcLinear(mid, max, value, true);
	}
    
    public static Mesh loadTerrain(File file, float scale, int texRepeat) throws IOException {
        BufferedImage img = ImageIO.read(file);

        int width = img.getWidth();
        int depth = img.getHeight();
        
        float hw = width / 2.0f;
        float hd = depth / 2.0f;
        
        float [][]kernel = new float[][]{
        	{1.0f/16, 2.0f/16, 1.0f/16},
        	{2.0f/16, 4.0f/16, 2.0f/16},
        	{1.0f/16, 2.0f/16, 1.0f/16}
        };
        		
        
        // Criação dos vértices
        int maxHeight = 0;
        List<Vector3f> positions = new ArrayList<>();        
        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {

            	float tone1 = 0;
                
                for(int ky = 0; ky < 3; ky++)
                {
                    for(int kx = 0; kx < 3; kx++)
                    {
                        int px = x + (kx - 1);
                        int py = z + (ky - 1);
                        
                        if(px < 0 || px >= width||
                           py < 0 || py >= depth)
                        {
                            continue;
                        }
                        
                        Color pixel = new Color(img.getRGB(px, py));
                        
                        float r = (pixel.getRed() * kernel[ky][kx]);
                        tone1 += r;
                    }
                }
                float h = tone1 * scale ;
    			
                positions.add(new Vector3f(x - hw, h, z - hd));
                int red = new Color(img.getRGB(x, z)).getRed();
                
                if (maxHeight < red) {
                    maxHeight = red;
                }
            }
        }

        //Criação dos índices
        List<Integer> indices = new ArrayList<>();
        for (int z = 0; z < depth - 1; z++) {
            for (int x = 0; x < width - 1; x++) {
                int zero = x + z * width;
                int one = (x + 1) + z * width;
                int two = x + (z + 1) * width;
                int three = (x + 1) + (z + 1) * width;

                indices.add(zero);
                indices.add(three);
                indices.add(one);

                indices.add(zero);
                indices.add(two);
                indices.add(three);
            }
        }
        
        //Criacao da lista das normais
        List<Vector3f> normals = new ArrayList<Vector3f>();
        for (int i = 0; i < positions.size(); i++) {
            normals.add(new Vector3f());
        }
        
        //Calculo das normais
        for (int i = 0; i < indices.size(); i += 3) {
            int i1 = indices.get(i);
            int i2 = indices.get(i+1);
            int i3 = indices.get(i+2);
            
            Vector3f v1 = positions.get(i1);
            Vector3f v2 = positions.get(i2);
            Vector3f v3 = positions.get(i3);
                        
            Vector3f side1 = new Vector3f(v2).sub(v1);
            Vector3f side2 = new Vector3f(v3).sub(v1);
            
            Vector3f normal = new Vector3f(side1).cross(side2);

            normals.get(i1).add(normal);
            normals.get(i2).add(normal);
            normals.get(i3).add(normal);
        }
        
        for (Vector3f normal : normals) {
            normal.normalize();
        }
        
        //Calculo das texturas
        float tx = 1.0f / width * texRepeat;
        float ty = 1.0f / depth * texRepeat;
        List<Vector2f> texCoords = new ArrayList<>();
                
        for (int z = 0; z < depth - 1; z++) {
            for (int x = 0; x < width - 1; x++) {
                texCoords.add(new Vector2f(x * tx, z * ty));
            }
        }

      //Calculo dos pesos
		List<Vector4f> texWeights = new ArrayList<>();
		for (int z = 0; z < depth; z++) {
			for (int x = 0; x < width; x++) {
				
				int tone = new Color(img.getRGB(x, z)).getRed();
				float h = tone / (float) maxHeight;
				
				Vector4f weight = new Vector4f(
									  calcLinear(0.00f, 0.16f, h, true),
									  calcPiramid(0.15f, 0.60f, h), 
									  calcPiramid(0.50f, 0.80f, h), 
									  calcLinear(0.75f, 1.00f, h, false));
				float t = weight.x + weight.y + weight.z + weight.w;
				weight.div(t);
				
				texWeights.add(weight);
			}
		}
        
		List<Vector2f> blendMapCoord = createBlendMap(texWeights, width, depth);
		
        return new MeshBuilder()
                    .addVector3fAttribute("aPosition", positions)
                    .addVector3fAttribute("aNormal", normals)
                    .addVector2fAttribute("aTexCoord", texCoords)
                    .addVector2fAttribute("aBlendMapCoord", blendMapCoord)
                    .setIndexBuffer(indices)
                    .create();
    }
    
    private static List<Vector2f> createBlendMap(List<Vector4f> texWeights, int width, int height)
    {
    	BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
    	//Calculo das texturas
        float tx = 1.0f / width;
        float ty = 1.0f / height;
        List<Vector2f> texCoords = new ArrayList<>();
        
    	for (int y = 0; y < height; y++) 
    	{
    		for (int x = 0; x < width; x++) 
    		{
    			int i = x + (y * width);
    			float r = texWeights.get(i).x;
    			float g = texWeights.get(i).y;
    			float b = texWeights.get(i).z;
    			float a = texWeights.get(i).w;
    			
    			r = (r < 0.0f)? 0.0f : (r > 1.0f)? 1.0f : r;
    			g = (g < 0.0f)? 0.0f : (g > 1.0f)? 1.0f : g;
    			b = (b < 0.0f)? 0.0f : (b > 1.0f)? 1.0f : b;
    			a = (a < 0.0f)? 0.0f : (a > 1.0f)? 1.0f : a;
    			
    			Color pixel = new Color(r, g, b, a);
    			
    			img.setRGB(x, y, pixel.getRGB());
    			
    			texCoords.add(new Vector2f(x * tx, y * ty));
    		}
    	}
    	try {
			ImageIO.write(img, "png", new File(Skydome.PATH + "textures/BlendMap.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return texCoords;
    }
    
    public static Mesh createSphere(int slices, int stacks)
    {
		List<Vector3f> vertices = new ArrayList<>();
		List<Vector2f> texCoords = new ArrayList<>();
		
		for (int slice = 0; slice <= slices; slice++) 
		{
			double theta = slice * Math.PI / slices;
			double sinTheta = Math.sin(theta);
			double cosTheta = Math.cos(theta);
			
			for (int stack = 0; stack <= stacks; stack++) 
			{
				double phi = stack * 2 * Math.PI / stacks;
				double sinPhi = Math.sin(phi);
				double cosPhi = Math.cos(phi);
				
				float x = (float) (cosPhi * sinTheta);
				float y = (float) (cosTheta);
				float z = (float) (sinPhi * sinTheta);
				float s = 1.0f - (stack / (float) stacks);
				float t = 1.0f - (slice / (float) slices);
				
				texCoords.add(new Vector2f(s, t));
				vertices.add(new Vector3f(x, y, z));
			}
    	}
		
		//Criação dos índices
        List<Integer> indices = new ArrayList<>();
        for (int slice = 0; slice <= slices; slice++)
        {
        	for (int stack = 0; stack <= stacks; stack++) 
            {
                int zero = stack + slice * slices;
                int one = (stack + 1) + slice * slices;
                int two = stack + (slice + 1) * slices;
                int three = (stack + 1) + (slice + 1) * slices;

                indices.add(zero);
                indices.add(three);
                indices.add(one);

                indices.add(zero);
                indices.add(two);
                indices.add(three);
            }
        }
        
        return new MeshBuilder()
        			.addVector3fAttribute("aPosition", vertices)
        			.addVector2fAttribute("aTexCoord", texCoords)
        			.setIndexBuffer(indices)
        			.create();
    }
}
