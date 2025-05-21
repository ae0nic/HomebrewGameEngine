package me.ae0nic.render;

import me.ae0nic.util.IntVector2;
import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL.*;
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
    
    public IntVector2 getSize() {
        assert !destroyed : "Cannot use a destroyed window!";
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(window, width, height);
        return new IntVector2(width[0], height[0]);
    }
    
    public void setPos(IntVector2 pos) {
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
