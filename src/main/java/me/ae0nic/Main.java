package me.ae0nic;

import me.ae0nic.render.Pipeline;
import me.ae0nic.render.Window;
import org.joml.*;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GLCapabilities;

import java.lang.Math;
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

    Camera camera = new Camera(new Vector3f(0, 0, 0), new Vector2f(0.0f, 0.0f));
    MouseInput mouseInput = new MouseInput();

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

    private void input() {
        mouseInput.input(window);

        if (this.window.isKeyPressed(GLFW_KEY_ESCAPE)) {
            this.window.setShouldClose(true);
        }
        if (this.window.isKeyPressed(GLFW_KEY_S)) {
            camera.move(new Vector3f(0.0f, 0.0f, 1e-1f));
        }
        if (this.window.isKeyPressed(GLFW_KEY_W)) {
            camera.move(new Vector3f(0.0f, 0.0f, -1e-1f));
        }
        if (this.window.isKeyPressed(GLFW_KEY_A)) {
            camera.move(new Vector3f(-1e-1f, 0.0f, 0.0f));
        }
        if (this.window.isKeyPressed(GLFW_KEY_D)) {
            camera.move(new Vector3f(1e-1f, 0.0f, 0.0f));
        }
        if (this.window.isKeyPressed(GLFW_KEY_E)) {
            camera.move(new Vector3f(0.0f, 1e-1f, 0.0f));
        }
        if (this.window.isKeyPressed(GLFW_KEY_Q)) {
            camera.move(new Vector3f(0.0f, -1e-1f, 0.0f));
        }
        camera.rotate(mouseInput.getDisplVec().x, mouseInput.getDisplVec().y);
    }

    private void init() {
        window = new Window(600, 600, "Hello World");
        Vector2i size = window.getSize();
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        window.setPos(new Vector2i((vidmode.width() - size.x) / 2,
                (vidmode.height() - size.y) / 2));
        window.makeCurrent();
        GLCapabilities capabilities = createCapabilities();
        glfwSwapInterval(1);

        pipeline = new Pipeline(Paths.get("shaders/config/project.json"));
        pipeline.getVertexBuffer().setData(vertices, GL_STATIC_DRAW);
        pipeline.setProjectionMatrix(new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR).mul(camera.getCameraMatrix().invert()));
        pipeline.setProjectionMatrixUniform("projection");

        mouseInput.init(window);

    }
    
    private void loop() {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        while (!window.shouldClose()) {
            input();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            pipeline.setProjectionMatrix(new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR).mul(camera.getCameraMatrix().invert()));
            pipeline.draw(indices);
            
            window.swap();
            glfwPollEvents();

            window.setVisible(true);
        }
    }
}