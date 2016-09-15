#version 330

uniform vec3 uColorLow;
uniform vec3 uColorHigh;

uniform vec2 uCloud1Offset;
uniform vec2 uCloud2Offset;

uniform sampler2D uCloud1;
uniform sampler2D uCloud2;

in float vY;
in vec2 vTexCoord;

out vec4 outColor;

void main(void) 
{
    vec3 color = mix(uColorLow, uColorHigh, vY);
    
    vec3 cloud1 = vec3(texture(uCloud1, (vTexCoord * 2.0) + uCloud1Offset));
    vec3 cloud2 = vec3(texture(uCloud2, (vTexCoord * 2.0) + uCloud2Offset));
    vec3 clouds = clamp(cloud1 + cloud2, 0.0, 0.7);
    outColor = vec4(clamp(color + clouds, 0.0, 1.0), 1.0);
}