package me.ae0nic.render.shader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

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
