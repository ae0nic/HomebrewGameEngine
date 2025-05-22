package me.ae0nic.render.shader;

public class Uniform {
    private final AttributeType type;
    private final String name;
    public Uniform(AttributeType type, String name) {
        this.type = type;
        this.name = name;
    }
    public AttributeType getType() {
        return type;
    }
    public String getName() {
        return name;
    }
}
