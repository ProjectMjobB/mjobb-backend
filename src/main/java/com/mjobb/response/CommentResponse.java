package com.mjobb.response;

import com.mjobb.entity.User;
import lombok.Data;

@Data
public class CommentResponse {

    private Long id;
    private String comment;
    private Long point;
    private User fromUser;
    private User toUser;
}
