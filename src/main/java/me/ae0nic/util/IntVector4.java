package me.ae0nic.util;

public class IntVector4 extends IntVector3 {
    public int w;
    
    public IntVector4() {
        this(0, 0, 0, 0);
    }
    
    public IntVector4(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
}
