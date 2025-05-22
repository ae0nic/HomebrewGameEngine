package me.ae0nic.render.shader;

import me.ae0nic.render.Attribute;
import me.ae0nic.render.Uniform;
import org.w3c.dom.Attr;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Config {
    private final List<Attribute> attributes;
    private final List<Uniform> uniforms;
    private final String vertexPath;
    private final String fragmentPath;
    
    public Config(List<Attribute> attributes, List<Uniform> uniforms, String vertexPath, String fragmentPath) {
        this.attributes = Collections.unmodifiableList(attributes);
        this.uniforms = Collections.unmodifiableList(uniforms);
        this.vertexPath = vertexPath;
        this.fragmentPath = fragmentPath;
    }
    
    public List<Attribute> getAttributes() {
        return attributes;
    }
    
    public List<Uniform> getUniforms() {
        return uniforms;
    }
    
    public Path getVertexPath() {
        return Paths.get(vertexPath);
    }
    
    public Path getFragmentPath() {
        return Paths.get(fragmentPath);
    }
}
