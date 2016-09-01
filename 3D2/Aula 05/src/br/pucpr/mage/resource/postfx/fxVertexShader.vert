#version 330

in vec2 aPosition;
in vec2 aTexCoord;

out vec2 vTexCoord;

void main(void) 
{
    gl_Position = vec4(aPosition, 0.0, 1.0);
	vTexCoord = aTexCoord;
}