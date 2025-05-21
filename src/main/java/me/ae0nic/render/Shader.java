package me.ae0nic.render;

import org.apache.commons.io.IOUtils;

import static org.lwjgl.opengl.GL45.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;


public class Shader {
    private String fragmentCode;
    private String vertexCode;
    private int program;
    
    public Shader(String vertexPath, String fragmentPath) {
        try {
            URL fragmentResource = Shader.class.getClassLoader().getResource(fragmentPath);
            if (fragmentResource == null) {
                throw new FileNotFoundException("The file at " + fragmentPath + " does not exist!");
            }
            URL vertexResource = Shader.class.getClassLoader().getResource(vertexPath);
            if (vertexResource == null) {
                throw new FileNotFoundException("The file at " + vertexPath + " does not exist!");
            }
            fragmentCode = IOUtils.toString(fragmentResource.openStream());
            vertexCode = IOUtils.toString(vertexResource.openStream());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
