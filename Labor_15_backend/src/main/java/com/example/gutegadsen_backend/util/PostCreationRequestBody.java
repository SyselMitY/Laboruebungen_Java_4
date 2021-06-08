package com.example.gutegadsen_backend.util;

import com.example.gutegadsen_backend.db.TagRepository;
import com.example.gutegadsen_backend.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostCreationRequestBody {
    @NotBlank
    private final String title;
    private final String[] tags;
    private final String imageDataString;

    public Set<Tag> getTagsAsSet() {
        return Arrays.stream(tags)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
