package me.ae0nic.render.shader;

import static org.lwjgl.opengl.GL45.*;

public enum AttributeType {
    BOOL(1, GL_BOOL, 1, Boolean.class), INT(1, GL_INT, 4, Integer.class), UINT(1, GL_UNSIGNED_INT, 4, Integer.class), FLOAT(1, GL_FLOAT, 4, Float.class), DOUBLE(1, GL_DOUBLE, 4, Double.class),
    VEC2_BOOL(2, GL_BOOL, 2*1, boolean[].class), VEC2_INT(2, GL_INT, 2*4, int[].class), VEC2_UINT(2, GL_UNSIGNED_INT, 2*4, int[].class), VEC2_FLOAT(2, GL_FLOAT, 2*4, float[].class), VEC2_DOUBLE(2, GL_DOUBLE, 2*8, double[].class),
    VEC3_BOOL(3, GL_BOOL, 3*1, boolean[].class), VEC3_INT(3, GL_INT, 3*4, int[].class), VEC3_UINT(3, GL_UNSIGNED_INT, 3*4, int[].class), VEC3_FLOAT(3, GL_FLOAT, 3*4, float[].class), VEC3_DOUBLE(3, GL_DOUBLE, 3*8, double[].class),
    VEC4_BOOL(4, GL_BOOL, 4*1, boolean[].class), VEC4_INT(4, GL_INT, 4*4, int[].class), VEC4_UINT(4, GL_UNSIGNED_INT, 4*4, int[].class), VEC4_FLOAT(4, GL_FLOAT, 4*4, float[].class), VEC4_DOUBLE(4, GL_DOUBLE, 4*8, double[].class),
    MAT2_FLOAT(2*2, GL_FLOAT, 2*2*4, float[].class), MAT2_DOUBLE(2*2, GL_DOUBLE, 2*2*8, double[].class),
    MAT2x3_FLOAT(2*3, GL_FLOAT, 2*3*4, float[].class), MAT2x3_DOUBLE(2*3, GL_DOUBLE, 2*3*8, double[].class),
    MAT2x4_FLOAT(2*4, GL_FLOAT, 2*4*4, float[].class), MAT2x4_DOUBLE(2*4, GL_DOUBLE, 2*4*8, double[].class),
    MAT3_FLOAT(3*3, GL_FLOAT, 3*3*4, float[].class), MAT3_DOUBLE(3*3, GL_DOUBLE, 3*3*8, double[].class),
    MAT3x2_FLOAT(3*2, GL_FLOAT, 3*2*4, float[].class), MAT3x2_DOUBLE(3*2, GL_DOUBLE, 3*2*8, double[].class),
    MAT3x4_FLOAT(3*4, GL_FLOAT, 3*4*4, float[].class), MAT3x4_DOUBLE(3*4, GL_DOUBLE, 3*4*8, double[].class),
    MAT4_FLOAT(4*4, GL_FLOAT, 4*4*4, float[].class), MAT4_DOUBLE(4*4, GL_DOUBLE, 4*4*8, double[].class),
    MAT4x2_FLOAT(4*2, GL_FLOAT, 4*2*4, float[].class), MAT4x2_DOUBLE(4*2, GL_DOUBLE, 4*2*8, double[].class),
    MAT4x3_FLOAT(4*3, GL_FLOAT, 4*3*4, float[].class), MAT4x3_DOUBLE(4*3, GL_DOUBLE, 4*3*8, double[].class);
    
    private final int length;
    private final int type;
    private final int size;
    private final Class parameterType;
    
    private AttributeType(int length, int type, int size, Class parameterType) {
        this.length = length;
        this.type = type;
        this.size = size;
        this.parameterType = parameterType;
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
    public Class getParameterType() {
        return parameterType;
    }
}
