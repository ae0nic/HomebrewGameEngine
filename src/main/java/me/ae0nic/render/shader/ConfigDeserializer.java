package me.ae0nic.render.shader;

import com.google.gson.*;
import me.ae0nic.render.Attribute;
import me.ae0nic.render.AttributeType;
import me.ae0nic.render.Uniform;
import org.w3c.dom.Attr;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConfigDeserializer implements JsonDeserializer<Config> {
    @Override
    public Config deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        JsonArray uniforms = object.get("uniforms").getAsJsonArray();
        ArrayList<Uniform> uniformList = new ArrayList<>();
        uniforms.forEach(e -> {
            JsonArray a = e.getAsJsonArray();
            uniformList.add(new Uniform(
                    AttributeType.valueOf(a.get(0).getAsString()),
                    a.get(1).getAsString()
            ));
        });
        JsonArray attributes = object.get("attributes").getAsJsonArray();
        ArrayList<Attribute> attributeList = new ArrayList<>();
        attributes.forEach(e -> {
            JsonArray a = e.getAsJsonArray();
            attributeList.add(new Attribute(
                    AttributeType.valueOf(a.get(0).getAsString()),
                    a.get(1).getAsString(),
                    a.get(2).getAsInt()
            ));
        });
        return new Config(attributeList, uniformList,
                object.get("vertex").getAsString(), object.get("fragment").getAsString());
    }
}
