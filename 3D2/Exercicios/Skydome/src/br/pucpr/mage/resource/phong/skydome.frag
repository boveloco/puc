#version 330

uniform sampler2D uTex0;
uniform sampler2D uTex1;
uniform float uDeltaTime;

in float vY;
in vec2 vTexCoord;

out vec4 outColor;

void main(void)
{
	//set the max and min color to mix
	vec3 cMin = vec3(0.516, 0.986, 1.000);
	vec3 cMax= vec3(0.133, 0.353, 0.725);
	
	//mix colors with the Height of the dome
	vec3 color = mix(cMin, cMax, vY);
	
	//control clouds
	vec2 dir0 = vec2(0.02, 0) * uDeltaTime;
	vec2 dir1 = vec2(-0.04, 0) * uDeltaTime;
	
	vec4 color0 = texture(uTex0, vTexCoord + dir0);
	vec4 color1 = texture(uTex1, vTexCoord + dir1);
	
	outColor = vec4(color, 1.0) + color0 + color1;
}