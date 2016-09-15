#version 330

uniform vec3 uLightDir;

uniform vec3 uAmbientLight;
uniform vec3 uDiffuseLight;
uniform vec3 uSpecularLight;

uniform vec3 uAmbientMaterial;
uniform vec3 uDiffuseMaterial;
uniform vec3 uSpecularMaterial;

uniform float uSpecularPower;

uniform sampler2D uTex0;
uniform sampler2D uTex1;
uniform sampler2D uTex2;
uniform sampler2D uTex3;

uniform bool isClipping;

in vec3 vNormal;
in vec3 vViewPath;
in vec2 vTexCoord;
in vec4 vTexWeight;
in float vDepth;
in float vClip;

out vec4 outColor;

void main() {
	if (isClipping && vClip < 0.0) {
		discard;
	}
	
    vec3 L = normalize(uLightDir);
	vec3 N = normalize(vNormal);

    vec3 ambient = uAmbientLight * uAmbientMaterial;
    
    float diffuseIntensity = max(dot(N, -L), 0.0);
    vec3 diffuse = diffuseIntensity * uDiffuseLight * uDiffuseMaterial;
       
    //Calculo do componente especular
	float specularIntensity = 0.0;
	if (uSpecularPower > 0.0) {
		vec3 V = normalize(vViewPath);
		vec3 R = reflect(L, N);
		specularIntensity = pow(max(dot(R, V), 0.0), uSpecularPower);
	}
    vec3 specular = specularIntensity * uSpecularLight * uSpecularMaterial;
    
   	//Multi textura
   	vec2 nearCoord = vec2(vTexCoord.s, vTexCoord.t) * 50.0;   	
    vec4 texelNear = texture(uTex0, nearCoord) * vTexWeight.w + 
                 texture(uTex1, nearCoord) * vTexWeight.z +
                 texture(uTex2, nearCoord) * vTexWeight.y +
                 texture(uTex3, nearCoord) * vTexWeight.x;

    vec2 farCoord = vec2(vTexCoord.s, vTexCoord.t) * 10.0;   
	vec4 texelFar = texture(uTex0, farCoord) * vTexWeight.w + 
	                texture(uTex1, farCoord) * vTexWeight.z +
	                texture(uTex2, farCoord) * vTexWeight.y +
	                texture(uTex3, farCoord) * vTexWeight.x;

    //Interpolação da textura
    float blendDistance = 0.99;
    float blendWidth = 100.0;
    float blendFactor = clamp((vDepth - blendDistance) * blendWidth, 0.0, 1.0);
	vec4 texel = mix(texelNear, texelFar, blendFactor);

    //Combina os componentes para a cor final   
    vec3 color = clamp(texel.rgb * (ambient + diffuse) + specular, 0.0, 1.0);
    outColor = vec4(color, texel.a);
}