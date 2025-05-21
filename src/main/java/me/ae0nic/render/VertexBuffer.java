package me.ae0nic.render;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL45.*;

public class VertexBuffer {
    protected int vbo;
    protected int vao;
    protected ArrayList<Attribute> attributes = new ArrayList<>();
    protected int stride = 0;
    protected ArrayList<Integer> offsets = new ArrayList<>();
    protected boolean attributesEnabled = false;
    
    public VertexBuffer(int vbo) {
        this.vbo = vbo;
        this.vao = glGenVertexArrays();
    }
    
    public void addAttribute(Attribute a) {
        if (attributesEnabled) 
            throw new IllegalStateException("Cannot add new attribute after calling enableAttributes()!");
        stride += a.getSize();
        attributes.add(a);
    }
    
    public void enableAttributes() {
        int offset = 0;
        glBindVertexArray(vao);
        for (int i = 0; i < attributes.size(); i++) {
            Attribute a = attributes.get(i);
            glVertexAttribPointer(i, a.getLength(), a.getType(), false, stride, offset);
            offset += a.getSize();
            glEnableVertexAttribArray(i);
        }
    }
    
    public int getVbo() {
        return vbo;
    }
    
    public int getVao() {
        return vao;
    }
    
    public void bind() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
    }
    
    public void setData(float[] data, int usage) {
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage);
    }
    
}
