package com.example.gutegadsen_backend.json;

import com.example.gutegadsen_backend.exception.ImageNotFoundException;
import com.example.gutegadsen_backend.exception.TagNotFoundException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class TagNotFoundExceptionSerializer extends StdSerializer<TagNotFoundException> {

    public TagNotFoundExceptionSerializer() {
        this(null);
    }

    public TagNotFoundExceptionSerializer(Class<TagNotFoundException> t) {
        super(t);
    }

    @Override
    public void serialize(TagNotFoundException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("error","Tag not found");
        gen.writeStringField("message",value.getMessage());
        gen.writeStringField("tagName",value.getTagName());
    }
}
