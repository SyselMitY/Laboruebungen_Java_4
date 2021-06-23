package com.example.gutegadsen_backend.json;

import com.example.gutegadsen_backend.model.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("username", value.getUsername());
        if (value.getProfilePicture() != null)
            gen.writeNumberField("profilePictureId", value.getProfilePicture().getId());
        else
            gen.writeObjectField("profilePictureId",null);
        gen.writeEndObject();
    }
}
