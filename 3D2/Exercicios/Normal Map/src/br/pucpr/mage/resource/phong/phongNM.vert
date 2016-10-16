#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;

uniform vec3 uCameraPosition;

in vec3 aPosition;
in vec2 aTexCoord;

in vec3 aNormal;
in vec3 aTangent;

out vec3 vViewPath;
out vec2 vTexCoord;
out mat3 vTBN;

void main() {
	vec4 worldPos = uWorld * vec4(aPosition, 1.0);
    gl_Position =  uProjection * uView * worldPos;
    vViewPath = uCameraPosition - worldPos.xyz;
    vTexCoord = aTexCoord;
    
    vec3 bitangent = cross(aTangent, aNormal);
    vec3 T = normalize(vec3(uWorld * vec4(aTangent, 0.0)));
   	vec3 B = normalize(vec3(uWorld * vec4(bitangent, 0.0)));
   	vec3 N = normalize(vec3(uWorld * vec4(aNormal, 0.0))); 
   	vTBN = mat3(T, B, N);
}