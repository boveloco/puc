#version 330

uniform sampler2D uTexture;
uniform int width;
uniform int height;
uniform mat3 uKernel;
uniform float uPercentage;

in vec2 vTexCoord;
out vec4 outColor;

void main(void)
{
	float px = 1.0 / width;
	float py = 1.0 / height;
				   
	
	vec4 ox = vec4(0,0,0,0);
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
			//if(j == 1)
			//	continue;
			ox += texture(uTexture, vTexCoord + vec2(px * (j - 1), py * (i - 1))) * uKernel[i][j];
		}
	}
	
	vec4 text = texture(uTexture, vTexCoord);
	
	outColor = vec4(mix(text, ox, uPercentage));
}

