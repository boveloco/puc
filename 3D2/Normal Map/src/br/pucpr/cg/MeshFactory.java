package br.pucpr.cg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Vector2f;
import org.joml.Vector3f;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;

public class MeshFactory {
    
    public static Mesh createSquareWithNormalMap() {
        List<Vector3f> positions = Arrays.asList(
            new Vector3f(-0.5f,  0.5f, 0.0f),
            new Vector3f(0.5f,  0.5f, 0.0f),
            new Vector3f(-0.5f, -0.5f, 0.0f),
            new Vector3f(0.5f, -0.5f, 0.0f)
        );
        
        List<Integer> indices = Arrays.asList(
                0, 2, 3, 
                0, 3, 1);
        
        List<Vector2f> texCoords = Arrays.asList(
            new Vector2f(0.0f, 0.0f), 
            new Vector2f(1.0f, 0.0f), 
            new Vector2f(0.0f, 1.0f), 
            new Vector2f(1.0f, 1.0f)
        );
        
        List<Vector3f> tangents = new ArrayList<Vector3f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        
        for (int i = 0; i < positions.size(); i++) {
            tangents.add(new Vector3f());
            normals.add(new Vector3f());
        }
        
        for (int i = 0; i < indices.size(); i+= 3) {
            int i1 = indices.get(i);
            int i2 = indices.get(i+1);
            int i3 = indices.get(i+2);
            
            Vector3f v1 = positions.get(i1);
            Vector3f v2 = positions.get(i2);
            Vector3f v3 = positions.get(i3);
            
            Vector2f texCoord1 = texCoords.get(i1);
            Vector2f texCoord2 = texCoords.get(i2);
            Vector2f texCoord3 = texCoords.get(i3);
            
            Vector3f side1 = new Vector3f(v2).sub(v1);
            Vector3f side2 = new Vector3f(v3).sub(v1);
            
            Vector2f texSide1 = new Vector2f(texCoord2).sub(texCoord1);
            Vector2f texSide2 = new Vector2f(texCoord3).sub(texCoord1);
            
            float f = 1.0f / (texSide1.x * texSide2.y - texSide2.x * texSide1.y);

            Vector3f tangent = new Vector3f(
                f * (texSide2.y * side1.x - texSide1.y * side2.x),
                f * (texSide2.y * side1.y - texSide1.y * side2.y),
                f * (texSide2.y * side1.z - texSide1.y * side2.z)
            ).normalize();

            tangents.get(i1).add(tangent);
            tangents.get(i2).add(tangent);
            tangents.get(i3).add(tangent);

            Vector3f normal = new Vector3f(side1).cross(side2);
            normals.get(i1).add(normal);
            normals.get(i2).add(normal);
            normals.get(i3).add(normal);
        }
        
        for (int i = 0; i < tangents.size(); i++) {
            tangents.get(i).normalize();
            normals.get(i).normalize();
        }
        
        return new MeshBuilder()
            .addVector3fAttribute("aPosition", positions)
            .addVector2fAttribute("aTexCoord", texCoords)
            .addVector3fAttribute("aTangent", tangents)
            .addVector3fAttribute("aNormal", normals)
            .setIndexBuffer(indices)
            .create();
    }
    
    public static Mesh createSquare() {
        MeshBuilder builder = new MeshBuilder()
         .addVector3fAttribute("aPosition", 
               -0.5f,  0.5f, 0.0f,  //0
                0.5f,  0.5f, 0.0f,  //1
               -0.5f, -0.5f, 0.0f,  //2
                0.5f, -0.5f, 0.0f)  //3
         .addVector3fAttribute("aNormal",
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f,
                 0.0f, 0.0f, 1.0f)
         .addVector2fAttribute("aTexCoord", 
                0.0f, 0.0f, 
                1.0f, 0.0f, 
                0.0f, 1.0f, 
                1.0f, 1.0f) 
        .setIndexBuffer(
                0,  2,  3,
                0,  3,  1);
            
        return builder.create();
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

}
