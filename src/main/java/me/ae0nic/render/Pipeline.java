package me.ae0nic.render;

import me.ae0nic.render.shader.Attribute;
import me.ae0nic.render.shader.Shader;

import static org.lwjgl.opengl.GL45.*;

import java.nio.file.Path;
import java.util.List;

public class Pipeline {
    private Shader program;
    private VertexBuffer vertexBuffer;
    public Pipeline(Shader program) {
        useProgram(program);
    }

    public Pipeline(Path program) {
        this(new Shader(program));
    }

    public void useProgram(Shader program) {
        this.program = program;
        this.program.compile();
        vertexBuffer = new VertexBuffer();
        List<Attribute> attributeList = program.getConfig().getAttributes();
        for (Attribute a : attributeList) {
            vertexBuffer.addAttribute(a.getType());
        }
    }

    public void useProgram(Path program) {
        useProgram(new Shader(program));
    }

    public Shader getProgram() {
        return program;
    }

    public VertexBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public void draw() {
        vertexBuffer.bind();
        program.use();
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    public void draw(int ebo) {
        vertexBuffer.bind();
        program.use();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glDrawElements(GL_TRIANGLES, 3, GL_INT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
