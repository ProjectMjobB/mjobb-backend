package com.mjobb.request;

import lombok.Data;

@Data
public class CommentRequest {

    private String comment;
    private Long point;
    private Long toUserId;
    private Long jobId;

}
