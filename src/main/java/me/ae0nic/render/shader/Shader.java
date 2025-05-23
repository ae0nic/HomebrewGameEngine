package me.ae0nic.render.shader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Attr;

import static org.lwjgl.opengl.GL45.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Shader {
    private String fragmentCode;
    private String vertexCode;
    private Config shaderConfig;
    private int program;

    public Shader(Path configPath) {
        try {
            URL configResource = Shader.class.getClassLoader().getResource(configPath.toString());
            if (configResource == null) {
                throw new FileNotFoundException("The file at " + configPath + " does not exist!");
            }
            String config = IOUtils.toString(configResource);
            Gson gson = new GsonBuilder().registerTypeAdapter(Config.class, new ConfigDeserializer()).create();
            shaderConfig = gson.fromJson(config, Config.class);
            Path vertexPath = shaderConfig.getVertexPath();
            vertexPath = configPath.getParent().resolveSibling("vertex").resolve(vertexPath);
            Path fragmentPath = shaderConfig.getFragmentPath();
            fragmentPath = configPath.getParent().resolveSibling("fragment").resolve(fragmentPath);
            readSource(vertexPath, fragmentPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void readSource(Path vertexPath, Path fragmentPath) {
        try {
            URL fragmentResource = Shader.class.getClassLoader().getResource(fragmentPath.toString());
            if (fragmentResource == null) {
                throw new FileNotFoundException("The file at " + fragmentPath + " does not exist!");
            }
            URL vertexResource = Shader.class.getClassLoader().getResource(vertexPath.toString());
            if (vertexResource == null) {
                throw new FileNotFoundException("The file at " + vertexPath + " does not exist!");
            }
            fragmentCode = IOUtils.toString(fragmentResource);
            vertexCode = IOUtils.toString(vertexResource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Config getConfig() {
        return shaderConfig;
    }
    
    public void compile() {
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        
        glShaderSource(vertexShader, vertexCode);
        glShaderSource(fragmentShader, fragmentCode);
        
        int[] success = new int[1];
        glCompileShader(vertexShader);
        glGetShaderiv(vertexShader, GL_COMPILE_STATUS, success);
        if (success[0] != 1) {
            String s = glGetShaderInfoLog(vertexShader);
            System.err.println(s);
        }
        success = new int[1];
        glCompileShader(fragmentShader);
        glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, success);
        
        if (success[0] != 1) {
            String s = glGetShaderInfoLog(fragmentShader);
            System.err.println(s);
        }
        program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        success = new int[1];
        glGetProgramiv(program, GL_LINK_STATUS, success);
        if (success[0] != 1) {
            String s = glGetProgramInfoLog(program);
            System.err.println(s);
        }
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }
    
    public String getVertexCode() {
        return vertexCode;
    }

    public void setUniform(String name, Object values) {
        Uniform uniform = null;
        for (Uniform u : shaderConfig.getUniforms()) {
            if (u.getName().equals(name))
                uniform = u;
        }
        if (uniform == null) {
            throw new IllegalArgumentException("The named uniform was not declared in configuration!");
        }
        int location = glGetUniformLocation(program, name);
        if (location == -1) {
            throw new IllegalStateException("The uniform does not exist in the shader source! (Check for shader compilation errors)");
        }
        if (!uniform.getType().getParameterType().equals(values.getClass())) {
            throw new IllegalArgumentException("The type of the declared uniform does not match the type of the argument!");
        }

        switch (values) { // Prepare for 130 lines of switch statement :(
            case Float f: {
                glUniform1f(location, f);
                break;
            }
            case float[] f: {
                if (f.length != uniform.getType().getLength())
                    throw new IllegalArgumentException("The vector length of the argument must match the vector length of the declared uniform!");
                switch (f.length) {
                    case 2:
                        glUniform2fv(location, f);
                        break;
                    case 3:
                        glUniform3fv(location, f);
                        break;
                    case 4: // Can either be a 4D vector or a 2x2 matrix
                        if (uniform.getType().equals(AttributeType.VEC4_FLOAT))
                            glUniform4fv(location, f);
                        else
                            glUniformMatrix2fv(location, false, f); // TODO: Add a configuration option to enable transpose
                        break;
                    case 6: // Can either be a 2x3 matrix or a 3x2 matrix
                        if (uniform.getType().equals(AttributeType.MAT2x3_FLOAT))
                            glUniformMatrix2x3fv(location, false, f);
                        else
                            glUniformMatrix3x2fv(location, false, f);
                        break;
                    case 8: // Can either be a 2x4 matrix or a 4x2 matrix
                        if (uniform.getType().equals(AttributeType.MAT2x4_FLOAT))
                            glUniformMatrix2x4fv(location, false, f);
                        else
                            glUniformMatrix4x2fv(location, false, f);
                        break;
                    case 9:
                        glUniformMatrix3fv(location, false, f);
                        break;
                    case 12: // Can either be a 3x4 matrix or a 4x3 matrix
                        if (uniform.getType().equals(AttributeType.MAT3x4_FLOAT))
                            glUniformMatrix3x4fv(location, false, f);
                        else
                            glUniformMatrix4x3fv(location, false, f);
                        break;
                    case 16:
                        glUniformMatrix4fv(location, false, f);
                        break;
                }
                break;
            }
            case Integer i: { // We have to check if the int should be unsigned
                if (uniform.getType().equals(AttributeType.INT))
                    glUniform1i(location, i);
                else
                    glUniform1ui(location, i);
                break;
            }
            case int[] i: {
                if (i.length != uniform.getType().getLength())
                    throw new IllegalArgumentException("The vector length of the argument must match the vector length of the declared uniform!");
                switch (i.length) {
                    case 2:
                        if (uniform.getType().equals(AttributeType.INT))
                            glUniform2iv(location, i);
                        else
                            glUniform2uiv(location, i);
                        break;
                    case 3:
                        if (uniform.getType().equals(AttributeType.INT))
                            glUniform3iv(location, i);
                        else
                            glUniform3uiv(location, i);
                        break;
                    case 4:
                        if (uniform.getType().equals(AttributeType.INT))
                            glUniform4iv(location, i);
                        else
                            glUniform4uiv(location, i);
                        break;
                }
                break;
            }
            case Double d: {
                glUniform1d(location, d);
                break;
            }
            case double[] d: {
                if (d.length != uniform.getType().getLength())
                    throw new IllegalArgumentException("The vector length of the argument must match the vector length of the declared uniform!");
                switch (d.length) {
                    case 2:
                        glUniform2dv(location, d);
                        break;
                    case 3:
                        glUniform3dv(location, d);
                        break;
                    case 4: // Can either be a 4D vector or a 2x2 matrix
                        if (uniform.getType().equals(AttributeType.VEC4_FLOAT))
                            glUniform4dv(location, d);
                        else
                            glUniformMatrix2dv(location, false, d); // TODO: Add a configuration option to enable transpose
                        break;
                    case 6: // Can either be a 2x3 matrix or a 3x2 matrix
                        if (uniform.getType().equals(AttributeType.MAT2x3_FLOAT))
                            glUniformMatrix2x3dv(location, false, d);
                        else
                            glUniformMatrix3x2dv(location, false, d);
                        break;
                    case 8: // Can either be a 2x4 matrix or a 4x2 matrix
                        if (uniform.getType().equals(AttributeType.MAT2x4_FLOAT))
                            glUniformMatrix2x4dv(location, false, d);
                        else
                            glUniformMatrix4x2dv(location, false, d);
                        break;
                    case 9:
                        glUniformMatrix3dv(location, false, d);
                        break;
                    case 12: // Can either be a 3x4 matrix or a 4x3 matrix
                        if (uniform.getType().equals(AttributeType.MAT3x4_FLOAT))
                            glUniformMatrix3x4dv(location, false, d);
                        else
                            glUniformMatrix4x3dv(location, false, d);
                        break;
                    case 16:
                        glUniformMatrix4dv(location, false, d);
                        break;
                }
                break;
            }
            case Boolean b: {
                glUniform1i(location, b ? 1 : 0);
                break;
            }
            case boolean[] b: {
                if (b.length != uniform.getType().getLength())
                    throw new IllegalArgumentException("The vector length of the argument must match the vector length of the declared uniform!");
                int[] tmp = new int[b.length];
                for (int i = 0; i < b.length; i++) {
                    tmp[i] = b[i] ? 1 : 0;
                }
                switch (b.length) {
                    case 2:
                        glUniform2iv(location, tmp);
                        break;
                    case 3:
                        glUniform3iv(location, tmp);
                        break;
                    case 4:
                        glUniform4iv(location, tmp);
                        break;
                }
                break;
            }
            default:
                throw new RuntimeException("Both the argument and the declared uniform type are unsupported (if you see this, it is a bug)");

        }

    }
    
    public String getFragmentCode() {
        return fragmentCode;
    }
    
    public int getProgram() {
        return program;
    }
    
    public void use() {
        glUseProgram(program);
    }
}
