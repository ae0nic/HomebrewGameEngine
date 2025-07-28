package me.ae0nic;

import me.ae0nic.render.Pipeline;
import me.ae0nic.render.Window;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GLCapabilities;

import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL.*;

public class Main {
    private Window window;
    Pipeline pipeline;

    float[] vertices = new float[]{ // REMBEMBER THAT NEGATIVE Z GOES INTO THE SCREEN!!!!
            -0.5f,  0.5f, -0.05f,
            -0.5f, -0.5f, -0.05f,
            0.5f, -0.5f, -0.05f,
            0.5f,  0.5f, -0.05f,
    };

    int[] indices = {
      0, 1, 3, 3, 1, 2
    };


    float FOV = (float) Math.toRadians(60.0f);
    float Z_NEAR = 0.01f;
    float Z_FAR = 1000.f;

    float aspectRatio = (float) 1.;

    Matrix4f transformation = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        
        init();
        loop();
        
        window.destroy();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        
    }

    private void init() {
        window = new Window(600, 600, "Hello World");
        window.setKeyCallback((window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
            }
        });
        Vector2i size = window.getSize();
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        window.setPos(new Vector2i((vidmode.width() - size.x) / 2,
                (vidmode.height() - size.y) / 2));
        window.makeCurrent();
        GLCapabilities capabilities = createCapabilities();
        glfwSwapInterval(1);

        pipeline = new Pipeline(Paths.get("shaders/config/project.json"));
        pipeline.getVertexBuffer().setData(vertices, GL_STATIC_DRAW);


    }
    
    private void loop() {

        pipeline.getProgram().setUniform("projection", transformation.get(new float[16]));
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            pipeline.draw(indices);
            
            window.swap();
            glfwPollEvents();

            window.setVisible(true);
        }
    }
}