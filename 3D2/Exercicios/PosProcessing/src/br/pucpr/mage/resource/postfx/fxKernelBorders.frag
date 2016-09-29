#version 330 

uniform sampler2D uTexture;
uniform mat3 uKernel;
uniform int width;
uniform int height;
 
in vec2 vTexCoord;
out vec4 outColor;

void main(void)
{
	float dx = 1.0 / width;    
	float dy = 1.0 / height;
	    
	vec4 color1 = texture(uTexture, vTexCoord + vec2(-dx, -dy)) * uKernel[0][0]; 
	vec4 color2 = texture(uTexture, vTexCoord + vec2(-dx, 0) * uKernel[1][0]);
	vec4 color3 = texture(uTexture, vTexCoord + vec2(-dx, dy)) * uKernel[2][0];
	    
	vec4 color4 = texture(uTexture, vTexCoord + vec2(0, -dy)) * uKernel[0][1];
	vec4 color5 = texture(uTexture, vTexCoord + vec2(0, 0)) * uKernel[1][1];
	vec4 color6 = texture(uTexture, vTexCoord + vec2(0, dy)) * uKernel[2][1];
	   
	vec4 color7 = texture(uTexture, vTexCoord + vec2(dx, -dy)) * uKernel[0][2]; 
	vec4 color8 = texture(uTexture, vTexCoord + vec2(dx, 0)) * uKernel[1][2];
	vec4 color9 = texture(uTexture, vTexCoord + vec2(dx, dy)) * uKernel[2][2];
	
	outColor = vec4((color1 + color2 + color3 + 
					 color4 + color5 + color6 +
					 color7 + color8 + color9).xyz, 1.0);
}