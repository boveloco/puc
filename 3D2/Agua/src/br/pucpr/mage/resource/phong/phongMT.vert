#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;

uniform vec3 uCameraPosition;
uniform vec4 uClippingPlane;

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;
in vec4 aTexWeight;

out vec3 vNormal;
out vec3 vViewPath;
out vec2 vTexCoord;
out vec4 vTexWeight;
out float vDepth;
out float vClip;

void main() {
	vec4 worldPos = uWorld * vec4(aPosition, 1.0);
    gl_Position =  uProjection * uView * worldPos;
    vNormal = (uWorld * vec4(aNormal, 0.0)).xyz;
    vViewPath = uCameraPosition - worldPos.xyz;
    vTexCoord = aTexCoord;    
        
    //Normaliza os pesos da textura para uma mistura homogÃ�nea
    float t = aTexWeight.x + aTexWeight.y + aTexWeight.z + aTexWeight.w;    
    vTexWeight = aTexWeight / t;
    
    //Calcula o vDepth.
    vDepth = gl_Position.z / gl_Position.w;
    vClip = dot(vec4(aPosition, 1.0), uClippingPlane);
    
}