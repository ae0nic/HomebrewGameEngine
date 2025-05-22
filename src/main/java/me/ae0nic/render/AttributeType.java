package me.ae0nic.render;

import static org.lwjgl.opengl.GL45.*;

public enum AttributeType {
    BOOL(1, GL_BOOL, 1), INT(1, GL_INT, 4), UINT(1, GL_UNSIGNED_INT, 4), FLOAT(1, GL_FLOAT, 4), DOUBLE(1, GL_DOUBLE, 4),
    VEC2_BOOL(2, GL_BOOL, 2*1), VEC2_INT(2, GL_INT, 2*4), VEC2_UINT(2, GL_UNSIGNED_INT, 2*4), VEC2_FLOAT(2, GL_FLOAT, 2*4), VEC2_DOUBLE(2, GL_DOUBLE, 2*8),
    VEC3_BOOL(3, GL_BOOL, 3*1), VEC3_INT(3, GL_INT, 3*4), VEC3_UINT(3, GL_UNSIGNED_INT, 3*4), VEC3_FLOAT(3, GL_FLOAT, 3*4), VEC3_DOUBLE(3, GL_DOUBLE, 3*8),
    VEC4_BOOL(4, GL_BOOL, 4*1), VEC4_INT(4, GL_INT, 4*4), VEC4_UINT(4, GL_UNSIGNED_INT, 4*4), VEC4_FLOAT(4, GL_FLOAT, 4*4), VEC4_DOUBLE(4, GL_DOUBLE, 4*8),
    MAT2_BOOL(2*2, GL_BOOL, 2*2*1), MAT2_INT(2*2, GL_INT, 2*2*4), MAT2_UINT(2*2, GL_UNSIGNED_INT, 2*2*4), MAT2_FLOAT(2*2, GL_FLOAT, 2*2*4), MAT2_DOUBLE(2*2, GL_DOUBLE, 2*2*8),
    MAT2x3_BOOL(2*3, GL_BOOL, 2*3*1), MAT2x3_INT(2*3, GL_INT, 2*3*4), MAT2x3_UINT(2*3, GL_UNSIGNED_INT, 2*3*4), MAT2x3_FLOAT(2*3, GL_FLOAT, 2*3*4), MAT2x3_DOUBLE(2*3, GL_DOUBLE, 2*3*8),
    MAT2x4_BOOL(2*4, GL_BOOL, 2*4*1), MAT2x4_INT(2*4, GL_INT, 2*4*4), MAT2x4_UINT(2*4, GL_UNSIGNED_INT, 2*4*4), MAT2x4_FLOAT(2*4, GL_FLOAT, 2*4*4), MAT2x4_DOUBLE(2*4, GL_DOUBLE, 2*4*8),
    MAT3_BOOL(3*3, GL_BOOL, 3*3*1), MAT3_INT(3*3, GL_INT, 3*3*4), MAT3_UINT(3*3, GL_UNSIGNED_INT, 3*3*4), MAT3_FLOAT(3*3, GL_FLOAT, 3*3*4), MAT3_DOUBLE(3*3, GL_DOUBLE, 3*3*8),
    MAT3x2_BOOL(3*2, GL_BOOL, 3*2*1), MAT3x2_INT(3*2, GL_INT, 3*2*4), MAT3x2_UINT(3*2, GL_UNSIGNED_INT, 3*2*4), MAT3x2_FLOAT(3*2, GL_FLOAT, 3*2*4), MAT3x2_DOUBLE(3*2, GL_DOUBLE, 3*2*8),
    MAT3x4_BOOL(3*4, GL_BOOL, 3*4*1), MAT3x4_INT(3*4, GL_INT, 3*4*4), MAT3x4_UINT(3*4, GL_UNSIGNED_INT, 3*4*4), MAT3x4_FLOAT(3*4, GL_FLOAT, 3*4*4), MAT3x4_DOUBLE(3*4, GL_DOUBLE, 3*4*8),
    MAT4_BOOL(4*4, GL_BOOL, 4*4*1), MAT4_INT(4*4, GL_INT, 4*4*4), MAT4_UINT(4*4, GL_UNSIGNED_INT, 4*4*4), MAT4_FLOAT(4*4, GL_FLOAT, 4*4*4), MAT4_DOUBLE(4*4, GL_DOUBLE, 4*4*8),
    MAT4x2_BOOL(4*2, GL_BOOL, 4*2*1), MAT4x2_INT(4*2, GL_INT, 4*2*4), MAT4x2_UINT(4*2, GL_UNSIGNED_INT, 4*2*4), MAT4x2_FLOAT(4*2, GL_FLOAT, 4*2*4), MAT4x2_DOUBLE(4*2, GL_DOUBLE, 4*2*8),
    MAT4x3_BOOL(4*3, GL_BOOL, 4*3*1), MAT4x3_INT(4*3, GL_INT, 4*3*4), MAT4x3_UINT(4*3, GL_UNSIGNED_INT, 4*3*4), MAT4x3_FLOAT(4*3, GL_FLOAT, 4*3*4), MAT4x3_DOUBLE(4*3, GL_DOUBLE, 4*3*8);
    
    private int length;
    private int type;
    private int size;
    
    private AttributeType(int length, int type, int size) {
        this.length = length;
        this.type = type;
        this.size = size;
    }

    public int getLength() {
        return length;
    }
    public int getType() {
        return type;
    }
    public int getSize() {
        return size;
    }
}
