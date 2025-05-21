package me.ae0nic.util;

public class IntVector3 extends IntVector2 {
    public int z;
    public IntVector3() {
        this(0, 0, 0);
    }
    public IntVector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
