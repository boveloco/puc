package br.pucpr.cg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Vector3f;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;

public class MeshFactory {
    public static Mesh createSquare() {
        return new MeshBuilder()
         .addVector3fAttribute("aPosition", 
               -0.5f,  0.5f, 0.0f,  //0
                0.5f,  0.5f, 0.0f,  //1
               -0.5f, -0.5f, 0.0f,  //2
                0.5f, -0.5f, 0.0f )
         .addVector2fAttribute("aTexCoord", 
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f, 
                1.0f, 1.0f)
         .addVector3fAttribute("aNormal",
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f,
                 
                 1.0f, 0.0f, 0.0f,  //16
                 1.0f, 0.0f, 0.0f,  //17
                 1.0f, 0.0f, 0.0f,  //18
                 1.0f, 0.0f, 0.0f)         
        .setIndexBuffer(
                0,  2,  3,
                0,  3,  1
                )
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
        .addVector2fAttribute("aTexCoord", 
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f, 
                1.0f, 1.0f,
                
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f, 
                1.0f, 1.0f,
                
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f, 
                1.0f, 1.0f,
                
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f,
                1.0f, 1.0f,
                
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f, 
                1.0f, 1.0f,
                
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f, 
                1.0f, 1.0f)
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
    
    public static Mesh loadTerrain(File file, float scale) throws IOException {
        BufferedImage img = ImageIO.read(file);

        int width = img.getWidth();
        int depth = img.getHeight();
        
        float hw = width / 2.0f;
        float hd = depth / 2.0f;
        
        // Criação dos vértices
        List<Vector3f> positions = new ArrayList<>();        
        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                int tone = new Color(img.getRGB(x, z)).getRed();
                positions.add(new Vector3f(x - hw, tone * scale, z - hd));
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
        
        return new MeshBuilder()
                    .addVector3fAttribute("aPosition", positions)
                    .addVector3fAttribute("aNormal", normals)
                    .setIndexBuffer(indices)
                    .create();
    }

    public static Mesh createSphere(int p_fatiasH, int p_fatiasV, float raio) {
    	float []positions = vertices(p_fatiasH, p_fatiasV, raio);
    	int []index = setIndices(p_fatiasH, p_fatiasV);
    	float []normals = setNormal(index, positions);
    	
        return new MeshBuilder()
        .addVector3fAttribute("aPosition",
        		positions
            ) 
        .addVector2fAttribute("aTexCoord", setCoordTexture(p_fatiasH, p_fatiasV))
             .addVector3fAttribute("aNormal",
            		 normals
            )   
        .setIndexBuffer(
        		index
            )
        //.loadShader("/br/pucpr/resource/phong")
        .create();
    }  
    
    //seta os vertices pela quantidade de fatias
    private static float []vertices(int p_fatiasH, int p_fatiasV, float raio)
    {
    	int angH = 360 / p_fatiasV;
    	int angV = 180 / (p_fatiasH);
    	int qtdVertices = ((p_fatiasH - 1) * (p_fatiasV )) + 2;
    	
    	float []vert = new float[qtdVertices * 3];
    	vert[0] = 0.0f;
    	vert[1] = raio;
    	vert[2] = 0.0f;
    	
    	int controle = 3;
    	
    	for(int i = 90 - angV; i > -90; i -= angV)
    	{
    		for(int j = 0; j < 360; j += angH)
    		{
    			float []auxVert = setCoordenadasCartesianas(new float[]{raio, i, j});
    			
    			vert[controle] = auxVert[0];
    			vert[controle + 1] = auxVert[1];
    			vert[controle + 2] = auxVert[2];
    			
    			controle += 3;
    		}
    	}
    	
    	vert[controle] = 0.0f;
		vert[controle + 1] = -raio;
		vert[controle + 2] = 0.0f;

    	return vert;
    }
    
    //transforma de coordenadas polares para cartesianas
    private static float []setCoordenadasCartesianas(float[] vet)
    {
    	float x, y, z;
    	float PI = 3.14159265359f;
    	float i = 360.0f;
    	
    	x = (float) ((vet[0] * (float)(Math.cos(vet[1] * (PI/180))) * (float)Math.cos(vet[2] * (PI/180))));
    	y = (float) (vet[0] * Math.sin(vet[1] * (PI/180)));
    	
    	float seno = (vet[2] == 180)? 0 : (float) Math.sin(vet[2] * (PI/180));
    	float cosseno = (vet[1] == 90)? 0 : (vet[1] == -90)? 0 :(float) Math.cos(vet[1] * (PI/180));
    	
    	seno = (seno > 1.0f)? 1.0f : (seno < -1.0f)? -1.0f : seno;
    	cosseno = (cosseno > 1.0f)? 1.0f : (cosseno < -1.0f)? -1.0f : cosseno;
    	
    	z = (vet[0] * seno) * cosseno;
    	
    	return new float[]{x, y, z};
    }
    
    //seta os indices por camada. Dividida em 3 camadas as duas tampas
    //a de cima e a de baixo e a malha do meio.
    private static int []setIndices(int p_linhasHor, int p_linhasVert)
    {
    	int qtdVertices = ((p_linhasHor - 1) * p_linhasVert) * 6;
    	
    	int []aux = new int[qtdVertices] ;
    	
    	int limite = (p_linhasVert) * 3;
    	
    	for(int i = 0, j = 1; i < limite; i += 3, j++)
    	{
    		aux[i] = 0;
    		aux[i + 1] = j;
    		if(j >= p_linhasVert)
    		{
    			aux[i + 2] = 1;
    			break;
    		}
    		else
    			aux[i + 2] = j+1;
    	}
    	
    	int controle = limite;
    	limite = ((p_linhasHor - 2) * (p_linhasVert)) * 6;
    	
    	int v1 = 1, v2 = p_linhasVert + 1;
    	
    	for(int i = 0; i < limite; i+=6)
    	{
    		if(v1 % p_linhasVert == 0)
    		{
    			aux[controle] = v1;
        		aux[controle + 1] = v2;
        		aux[controle + 2] = v2 - (p_linhasVert - 1);
        		aux[controle + 3] = v1;
        		aux[controle + 4] = v2 - (p_linhasVert - 1);
        		aux[controle + 5] = v1 - (p_linhasVert - 1);
    		}
    		else
    		{
    			aux[controle] = v1;
    			aux[controle + 1] = v2;
    			aux[controle + 2] = v2 + 1;
    			aux[controle + 3] = v1;
    			aux[controle + 4] = v2 + 1;
    			aux[controle + 5] = v1 + 1;
    			
    		}
    		controle += 6;
    		v1++;
    		v2++;
    	}
    	
    	limite = (p_linhasVert) * 3;
    	
    	for(int i = 0; i < limite; i+=3)
    	{
    		aux[controle] = v2;
    		
    		if(v1 % p_linhasVert == 0)
    		{
    			aux[controle + 1] = v1 - (p_linhasVert - 1);
    		}
    		else
    			aux[controle + 1] = v1 + 1;
    		
    		aux[controle + 2] = v1;
    		controle+= 3;
    		v1++;
    	}
    	
    	return aux;
    }
    
    //Calculo da normal
    private static float []setNormal(int []indices, float []posicao)
    {
    	int []aux = indices;
    	
    	float []normals = new float[posicao.length];
    	
    	
    	for(int i = 0; i < indices.length; i += 3)
    	{
    		int i1 = aux[i]*3;
    		int i2 = aux[i + 1]*3;
    		int i3 = aux[i + 2]*3;
    		
    		float []v1 = new float[]{posicao[i1] , posicao[i1 + 1], posicao[i1 + 2]};
    		float []v2 = new float[]{posicao[i2] , posicao[i2 + 1], posicao[i2 + 2]};
    		float []v3 = new float[]{posicao[i3] , posicao[i3 + 1], posicao[i3 + 2]};
    		
    		float []r1 = new float[]{v2[0] - v1[0], v2[1] - v1[1], v2[2] - v1[2]};
    		float []r2 = new float[]{v3[0] - v1[0], v3[1] - v1[1], v3[2] - v1[2]};
    		
    		float []normal = new float[]{r1[1] * r2[2] - r1[2] * r2[1],
    									 r1[2] * r2[0] - r1[0] * r2[2],
    									 r1[0] * r2[1] - r1[1] * r2[0]};
    		
    		normals[i1] += normal[0];
    		normals[i1 + 1] += normal[1];
    		normals[i1 + 2] += normal[2];
    		
    		normals[i2] += normal[0];
    		normals[i2 + 1] += normal[1];
    		normals[i2 + 2] += normal[2];
    		
    		normals[i3] += normal[0];
    		normals[i3 + 1] += normal[1];
    		normals[i3 + 2] += normal[2];
    	}
    	
    	for(int i = 0; i < normals.length - 2; i++)
    	{
    		float m = (float) (1.0f / Math.sqrt(normals[i] * normals[i] + 
												normals[i + 1] * normals[i + 1] + 
												normals[i + 2] * normals[i + 2]));
    		
    		normals[i] *= m;
    		normals[i + 1] *= m;
    		normals[i + 2] *= m;
    	}
    	
    	return normals;
    }
    
    private static float []setCoordTexture(int p_fatiasH, int p_fatiasV)
    {
    	float x = (float)1 / p_fatiasH;
    	float y = (float)1 / p_fatiasV;
    	
    	int nMeio = ((p_fatiasV - 2) * p_fatiasH) * 8;
    	int nPolo = (2 * p_fatiasH) * 6;
    	
    	float []coord = new float[nMeio + nPolo];
    	
    	float x1, y1, x2, y2, x3, y3, x4, y4;
    	
    	int cont = 0;
    	int j = 0;
    	
    	for(int i = cont; i < nPolo/2; i+=6)
    	{
    		x1 = (x * (j+1)) / 2; y1 = 0;
    		x2 = x * j; y2 = y;
    		x3 = (x * (j+1)); y3 = y;
    		
    		coord[i] = x1;coord[i+1] = y1;
    		coord[i+2] = x2;coord[i+3] = y2;
    		coord[i+4] = x3;coord[i+5] = y3;
    		cont+=6;
    		j++;
    	}
    	
    	j = 0;
    	int h = 1;
    	
    	for(int i = cont; i < nMeio; i++)
    	{
    		x1 = x * j; y1 = y * h;
    		x2 = x * (j+1); y2 = y * h;
    		x3 = x * j; y3 = y * (h+1); 
    		x4 = x * (j+1); y4 = y * (h+1);
    		
    		coord[i] = x1;coord[i+1] = y1;
    		coord[i+2] = x2;coord[i+3] = y2;
    		coord[i+4] = x3;coord[i+5] = y3;
    		coord[i+5] = x4;coord[i+6] = y4;
    		
    		cont+=6;
    		j++;
    		h++;
    	}
    	
    	j = 0;
    	for(int i = cont; i < coord.length; i+=6)
    	{
    		x1 = x * j; y1 = y * p_fatiasV;
    		x2 = x * (j+1); y2 = y;
    		x3 = x; y3 = y;
    		
    		coord[i] = x1;coord[i+1] = y1;
    		coord[i+2] = x2;coord[i+3] = y2;
    		coord[i+4] = x3;coord[i+5] = y3;
    		cont+=6;
    		j++;
    	}
    	for(int i = 0; i < coord.length - 1; i++)
    	{
    		System.out.println(coord[i] + ", " + coord[i+1]);
    	}
    	
    	return coord;
    }
   
    public static Mesh createCanvas()
    {
    	return new MeshBuilder()
    			.addVector2fAttribute("aPosition",
    			      -1.0f,  1.0f, //0
    			       1.0f,  1.0f, //1
    			      -1.0f, -1.0f,//2
    			       1.0f, -1.0f)//3
    			.addVector2fAttribute("aTexCoord",
    				   0.0f, 1.0f,
    				   1.0f, 1.0f,
    				   0.0f, 0.0f,
    				   1.0f, 0.0f)
    			.setIndexBuffer(
    					0, 2, 3,
    					0, 3, 1)
    			.create();
    }
}
