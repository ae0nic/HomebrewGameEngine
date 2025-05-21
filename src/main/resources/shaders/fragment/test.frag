#version 450 core
out vec4 FragColor;
uniform vec3 offset;

void main() {
    FragColor = vec4(vec3(1.0f, 0.5f, 0.2f)+offset, 1.0f);
}