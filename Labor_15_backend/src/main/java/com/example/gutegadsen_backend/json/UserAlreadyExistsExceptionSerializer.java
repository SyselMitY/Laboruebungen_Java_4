package com.example.gutegadsen_backend.json;

import com.example.gutegadsen_backend.exception.UserAlreadyExistsException;
import com.example.gutegadsen_backend.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class UserAlreadyExistsExceptionSerializer extends StdSerializer<UserAlreadyExistsException> {

    public UserAlreadyExistsExceptionSerializer() {
        this(null);
    }

    public UserAlreadyExistsExceptionSerializer(Class<UserAlreadyExistsException> t) {
        super(t);
    }

    @Override
    public void serialize(UserAlreadyExistsException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("error","User already exists");
        gen.writeStringField("message",value.getMessage());
        gen.writeStringField("username",value.getUsername());
    }
}
