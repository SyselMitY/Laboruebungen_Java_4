package com.example.gutegadsen_backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpvoteRequestBody {
    public final String username;
    public final Long postId;
    public final Boolean upvoteState;
}
