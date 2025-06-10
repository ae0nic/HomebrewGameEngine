package me.ae0nic.render;

import org.joml.Vector2i;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Window {
    static boolean initialized = false;
    boolean destroyed = false;
    long window;
    public Window(int width, int height, String title) {
        if (!initialized) {
            GLFWErrorCallback.createPrint(System.err).set();
            if (!glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW!");
            } else {
                initialized = true;
            }
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        
        if (window == NULL)
            throw new IllegalStateException("Unable to create the GLFW window!");
        
    }
    
    public long getWindow() {
        return window;
    }
    
    public void setKeyCallback(GLFWKeyCallbackI callback) {
        glfwSetKeyCallback(window, callback);
    }
    
    public Vector2i getSize() {
        assert !destroyed : "Cannot use a destroyed window!";
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(window, width, height);
        return new Vector2i(width[0], height[0]);
    }
    
    public void setPos(Vector2i pos) {
        assert !destroyed : "Cannot use a destroyed window!";
        glfwSetWindowPos(window, pos.x, pos.y);
    }
    
    public void destroy() {
        destroyed = true;
        Callbacks.glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }
    
    public void swap() {
        glfwSwapBuffers(window);
    }
    
    public void setVisible(boolean visible) {
        if (visible) {
            glfwShowWindow(window);
        } else {
            glfwHideWindow(window);
        }
    }
    
    public void makeCurrent() {
        glfwMakeContextCurrent(window);
    }
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }
}
