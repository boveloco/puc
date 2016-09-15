#version 330

in vec3 aPosition;
in vec2 aTexCoord;
		
uniform mat4 uWorld;
uniform mat4 uView;

uniform mat4 uProjection;
uniform mat4 uReflexView;
uniform vec3 uCameraPosition;


out vec4 refractPos;
out vec4 reflectPos;
out vec3 vViewPath;

void main(void) 
{
    vec4 worldPos = uWorld * vec4(aPosition, 1.0);
    gl_Position = uProjection * uView * worldPos;
    refractPos = gl_Position;
    
	reflectPos = uProjection * uReflexView * worldPos;
    vViewPath = uCameraPosition - worldPos.xyz;
}