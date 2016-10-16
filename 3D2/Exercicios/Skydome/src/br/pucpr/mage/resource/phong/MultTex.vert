
#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;

uniform vec3 uCameraPosition;

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;

in vec2 aBlendMapCoord;

out vec3 vNormal;
out vec3 vViewPath;
out vec2 vTexCoord;

out vec2 vBlendMapCoord;

out float vDepth;

void main() {
	vec4 worldPos = uWorld * vec4(aPosition, 1.0);
    gl_Position =  uProjection * uView * worldPos;
    vNormal = (uWorld * vec4(aNormal, 0.0)).xyz;
    vViewPath = uCameraPosition - worldPos.xyz;
    vTexCoord = aTexCoord; 
    vDepth = gl_Position.z / gl_Position.w;   
    
    vBlendMapCoord = aBlendMapCoord;
}