package me.ae0nic.render;

import me.ae0nic.render.shader.Attribute;
import me.ae0nic.render.shader.Shader;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL45.*;

import java.nio.file.Path;
import java.util.List;

public class Pipeline {
    private Shader program;
    private VertexBuffer vertexBuffer;
    private Matrix4f projectionMatrix;
    private String projectionMatrixUniform = null;

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

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public void setProjectionMatrixUniform(String uniformName) {
        projectionMatrixUniform = uniformName;
        setUniform(uniformName, projectionMatrix.get(new float[16]));
    }

    public void setUniform(String uniformName, Object value) {
        program.setUniform(uniformName, value);
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

    private void drawSetup() {
        vertexBuffer.bind();
        program.use();
        if (projectionMatrixUniform != null)
            setUniform(projectionMatrixUniform, projectionMatrix.get(new float[16]));

    }

    public void draw() {
        drawSetup();

        glDrawArrays(GL_TRIANGLES, 0, vertexBuffer.size);
    }

    public void draw(int[] ebo) {
        drawSetup();
        int eboAddress = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboAddress);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ebo, GL_STATIC_DRAW);
        glDrawElements(GL_TRIANGLES, ebo.length, GL_UNSIGNED_INT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
