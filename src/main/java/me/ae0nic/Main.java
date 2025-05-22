package me.ae0nic;

import me.ae0nic.render.shader.AttributeType;
import me.ae0nic.render.shader.Shader;
import me.ae0nic.render.VertexBuffer;
import me.ae0nic.render.Window;
import me.ae0nic.util.IntVector2;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GLCapabilities;

import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL.*;

public class Main {
    private Window window;
    Shader shader;

    VertexBuffer vertexBuffer;
    float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f, 0.5f, 0.0f
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
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });
        IntVector2 size = window.getSize();
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        window.setPos(new IntVector2((vidmode.width() - size.x) / 2,
                (vidmode.height() - size.y) / 2));
        window.makeCurrent();
        GLCapabilities capabilities = createCapabilities();
        glfwSwapInterval(1);
        window.setVisible(true);
        
        vertexBuffer = new VertexBuffer(glGenBuffers());
        
        shader = new Shader(Paths.get("shaders/config/test.json"));
        shader.compile();
        
        vertexBuffer.setData(vertices, GL_STATIC_DRAW);
        vertexBuffer.addAttribute(AttributeType.VEC3_FLOAT);
        vertexBuffer.enableAttributes();
    }
    
    private void loop() {

        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            shader.use();
            float[] offset = {(float) Math.sin(System.currentTimeMillis() / 1000.), 0.0f, 0.0f};
            int location = glGetUniformLocation(shader.getProgram(), "offset");
            glUniform3fv(location, offset);
            shader.use();
            vertexBuffer.bind();
            glDrawArrays(GL_TRIANGLES, 0, 3);
            
            window.swap();
            glfwPollEvents();
        }
    }
}