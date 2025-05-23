#version 450 core
layout (location = 0) in vec3 aPos;
uniform bool offset;

void main() {
    gl_Position = vec4(aPos, 1.0);
    if (offset) {
        gl_Position.x += 0.2;
    }
}