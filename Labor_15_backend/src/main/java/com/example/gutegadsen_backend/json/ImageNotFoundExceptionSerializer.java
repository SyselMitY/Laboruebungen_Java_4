package com.example.gutegadsen_backend.json;

import com.example.gutegadsen_backend.exception.ImageNotFoundException;
import com.example.gutegadsen_backend.exception.PostNotFoundException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ImageNotFoundExceptionSerializer extends StdSerializer<ImageNotFoundException> {

    public ImageNotFoundExceptionSerializer() {
        this(null);
    }

    public ImageNotFoundExceptionSerializer(Class<ImageNotFoundException> t) {
        super(t);
    }

    @Override
    public void serialize(ImageNotFoundException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("error","Image not found");
        gen.writeStringField("message",value.getMessage());
        gen.writeNumberField("imageId",value.getImageId());
    }
}
