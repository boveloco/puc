#version 330

uniform sampler2D uTexture;
in vec2 vTexCoord;
out vec4 outColor;

void main(void) 
{	    
    outColor = texture(uTexture, vTexCoord);
}