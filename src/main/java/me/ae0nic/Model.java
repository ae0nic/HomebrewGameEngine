package me.ae0nic;

import org.joml.Matrix4f;

public class Model {
    private float[] vertices;
    private Matrix4f transformation;

    public Model(float[] vertices, Matrix4f transformation) {
        this.vertices = vertices;
        this.transformation = transformation;
    }

    public void translate(float x, float y, float z) {
        transformation = transformation.translate(x, y, z);
    }

    public Matrix4f getTransformation() {
        return transformation;
    }

    public float[] getVertices() {
        return vertices;
    }
}
