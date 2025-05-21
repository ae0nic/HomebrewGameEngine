package me.ae0nic.render;

public class Pipeline {
    private int program;
    private VertexBuffer vertexBuffer;
    public Pipeline(int program) {
        this.program = program;
    }
    public void setVertexBuffer(VertexBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }
    public void draw() {
        
    }
    public void draw(int veo) {
        
    }
}
