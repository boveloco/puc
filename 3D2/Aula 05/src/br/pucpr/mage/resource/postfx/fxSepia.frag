#version 330

uniform sampler2D uTexture;

in vec2 vTexCoord;
out vec4 outColor;

void main(void) 
{	    
    mat4 sepiaMatrix;
	sepiaMatrix[0] = vec4(0.393, 0.349, 0.272, 0.000);
    sepiaMatrix[1] = vec4(0.769, 0.686, 0.534, 0.000);
    sepiaMatrix[2] = vec4(0.189, 0.168, 0.131, 0.000);
	sepiaMatrix[3] = vec4(0.000, 0.000, 0.000, 1.000);	

    outColor = sepiaMatrix * texture(uTexture, vTexCoord);
}