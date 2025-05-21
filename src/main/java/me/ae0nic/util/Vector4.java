package me.ae0nic.util;

public class Vector4 extends Vector3 {
    public float w;
    public Vector4() {
        this(0, 0, 0, 0);
    }
    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
}
