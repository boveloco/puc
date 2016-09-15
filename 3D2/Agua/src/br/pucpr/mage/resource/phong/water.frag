#version 330

uniform sampler2D uRefraction;
uniform sampler2D uReflection;

in vec4 refractPos;
in vec4 reflectPos;

in vec3 vViewPath;

out vec4 outColor;


void main(void) 
{
	
	vec2 refractCoords = refractPos.xy / refractPos.w / 2.0 + 0.5;
	vec2 reflectCoords = reflectPos.xy / refractPos.w / 2.0 + 0.5;

	vec4 refractColor = texture(uRefraction, refractCoords);
	vec4 reflectColor = texture(uReflection, reflectCoords);
	
	vec4 dullColor = vec4(0.3, 0.3, 0.5, 1.0);
	
	float fresnel = dot(normalize(vViewPath), vec3(0,1,0));
	
	outColor = mix(mix(reflectColor, refractColor, fresnel), dullColor, 0.1);

}