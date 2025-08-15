package me.ae0nic;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Vector2f rotation;
    public Camera(Vector3f position, Vector2f rotation) {
        this.position = position;
        this.rotation = rotation;
    }
    public Matrix4f getCameraMatrix() {
        Matrix4f cameraMatrix = new Matrix4f();
        cameraMatrix.identity();
        cameraMatrix.translate(position);
        cameraMatrix
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                .rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0));

        return cameraMatrix;
    }
    public void move(Vector3f translate) {
        translate.rotateY((float) Math.toRadians(rotation.y));
        this.position.add(translate);
    }
    public void rotate(float yaw, float pitch) {
        this.rotation.add(yaw, pitch);
        rotation.y = rotation.y % 360;
        rotation.x = Math.clamp(rotation.x, -90, 90);
    }
}
