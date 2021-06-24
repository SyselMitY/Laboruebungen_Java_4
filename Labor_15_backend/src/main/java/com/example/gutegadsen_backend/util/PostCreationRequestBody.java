package com.example.gutegadsen_backend.util;

import com.example.gutegadsen_backend.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
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
    private final String username;

    public Set<Tag> getTagsAsSet() {
        return Arrays.stream(tags)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
