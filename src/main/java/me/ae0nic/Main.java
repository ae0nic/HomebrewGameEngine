package me.ae0nic;

import me.ae0nic.render.Pipeline;
import me.ae0nic.render.Window;
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

    float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f, 0.5f, 0.0f,
    };

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

        pipeline = new Pipeline(Paths.get("shaders/config/test.json"));
        pipeline.getVertexBuffer().setData(vertices, GL_STATIC_DRAW);
    }
    
    private void loop() {

        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            pipeline.draw();
            
            window.swap();
            glfwPollEvents();

            window.setVisible(true);
        }
    }
}