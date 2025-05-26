package me.ae0nic.render;

import me.ae0nic.render.shader.AttributeType;

import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL45.*;

public class VertexBuffer {
    protected int vbo;
    protected int vao;
    protected ArrayList<AttributeType> attributes = new ArrayList<>();
    protected int stride = 0;
    protected boolean attributesEnabled = false;

    public VertexBuffer() {
        this(glGenBuffers());
    }

    public VertexBuffer(int vbo) {
        this.vbo = vbo;
        this.vao = glGenVertexArrays();
    }
    
    public void addAttribute(AttributeType a) {
        if (attributesEnabled) 
            throw new IllegalStateException("Cannot add new attribute after calling enableAttributes()!");
        stride += a.getSize();
        attributes.add(a);
    }
    
    public void enableAttributes() {
        int offset = 0;
        glBindVertexArray(vao);
        for (int i = 0; i < attributes.size(); i++) {
            AttributeType a = attributes.get(i);
            glVertexAttribPointer(i, a.getLength(), a.getType(), false, stride, offset);
            offset += a.getSize();
            glEnableVertexAttribArray(i);
        }
    }
    
    public void bind() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
    }

    public void setData(Object data, int usage) {
        bind();
        switch (data) {
            case short[] s:
                glBufferData(GL_ARRAY_BUFFER, s, usage);
                break;
            case int[] i:
                glBufferData(GL_ARRAY_BUFFER, i, usage);
                break;
            case long[] l:
                glBufferData(GL_ARRAY_BUFFER, l, usage);
                break;
            case float[] f:
                glBufferData(GL_ARRAY_BUFFER, f, usage);
                break;
            case double[] d:
                glBufferData(GL_ARRAY_BUFFER, d, usage);
                break;
            case ByteBuffer b:
                glBufferData(GL_ARRAY_BUFFER, b, usage);
                break;
            case ShortBuffer s:
                glBufferData(GL_ARRAY_BUFFER, s, usage);
                break;
            case IntBuffer i:
                glBufferData(GL_ARRAY_BUFFER, i, usage);
                break;
            case LongBuffer l:
                glBufferData(GL_ARRAY_BUFFER, l, usage);
                break;
            case FloatBuffer f:
                glBufferData(GL_ARRAY_BUFFER, f, usage);
                break;
            case DoubleBuffer d:
                glBufferData(GL_ARRAY_BUFFER, d, usage);
                break;
            case null, default:
                throw new IllegalArgumentException("Data is not a valid type!");
        }
        enableAttributes();

    }
    
}
