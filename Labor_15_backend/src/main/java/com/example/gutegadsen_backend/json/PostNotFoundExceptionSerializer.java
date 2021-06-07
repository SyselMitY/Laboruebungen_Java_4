package com.example.gutegadsen_backend.json;

import com.example.gutegadsen_backend.exception.PostNotFoundException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class PostNotFoundExceptionSerializer extends StdSerializer<PostNotFoundException> {

    public PostNotFoundExceptionSerializer() {
        this(null);
    }

    public PostNotFoundExceptionSerializer(Class<PostNotFoundException> t) {
        super(t);
    }

    @Override
    public void serialize(PostNotFoundException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("error","Post not found");
        gen.writeStringField("message",value.getMessage());
        gen.writeNumberField("postId",value.getPostId());
    }
}
