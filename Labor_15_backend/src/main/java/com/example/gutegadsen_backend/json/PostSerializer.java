package com.example.gutegadsen_backend.json;

import com.example.gutegadsen_backend.model.Post;
import com.example.gutegadsen_backend.model.Tag;
import com.example.gutegadsen_backend.model.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class PostSerializer extends StdSerializer<Post> {

    public PostSerializer() {
        this(null);
    }

    public PostSerializer(Class<Post> t) {
        super(t);
    }

    @Override
    public void serialize(Post value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeObjectField("date", value.getPostDate());
        gen.writeStringField("title", value.getTitle());
        gen.writeObjectField("user", value.getUser());
        gen.writeNumberField("upvoteCount", value.getUpvoteList().size());

        gen.writeArrayFieldStart("tags");
        for (Tag tag : value.getTagList()) {
            gen.writeString(tag.getName());
        }
        gen.writeEndArray();

        gen.writeNumberField("postImageId", value.getImage().getId());
        gen.writeEndObject();
    }
}
