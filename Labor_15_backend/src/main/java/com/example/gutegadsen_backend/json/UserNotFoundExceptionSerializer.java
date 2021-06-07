package com.example.gutegadsen_backend.json;

import com.example.gutegadsen_backend.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class UserNotFoundExceptionSerializer extends StdSerializer<UserNotFoundException> {

    public UserNotFoundExceptionSerializer() {
        this(null);
    }

    public UserNotFoundExceptionSerializer(Class<UserNotFoundException> t) {
        super(t);
    }

    @Override
    public void serialize(UserNotFoundException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("error","User not found");
        gen.writeStringField("message",value.getMessage());
        gen.writeStringField("username",value.getUsername());
    }
}
