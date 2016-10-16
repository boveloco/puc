#version 330

uniform sampler2D uTexture;
uniform int width;
uniform int height;

in vec2 vTexCoord;
out vec4 outColor;

void main(void) 
{	    
    float dx = 1.0 / width;
	float dy = 1.0 / height;
	
    vec4 color1 = texture(uTexture, vTexCoord + vec2(-dx, -dy));
	vec4 color2 = texture(uTexture, vTexCoord + vec2(-dx, 0)) * 2.0;
	vec4 color3 = texture(uTexture, vTexCoord + vec2(-dx, dy));
	
	vec4 color4 = texture(uTexture, vTexCoord + vec2(dx, -dy));
	vec4 color5 = texture(uTexture, vTexCoord + vec2(dx, 0)) * 2.0;
	vec4 color6 = texture(uTexture, vTexCoord + vec2(dx, dy));

    vec3 color =  texture(uTexture, vTexCoord).rgb * 2.0;
    
    
    outColor = vec4(color * (color1 + color2 + color3 + -color4 - color5 - color6).rrr, 1.0);
}