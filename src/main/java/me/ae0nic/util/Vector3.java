package me.ae0nic.util;

public class Vector3 extends Vector2 {
    public float z;
    public Vector3() {
        this(0, 0, 0);
    }
    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

