package me.ae0nic.render.shader;

public class Attribute {
    private final AttributeType type;
    private final String name;
    private final int location;
    public Attribute(AttributeType type, String name, int location) {
        this.type = type;
        this.name = name;
        this.location = location;
    }
    
    public AttributeType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }
}
