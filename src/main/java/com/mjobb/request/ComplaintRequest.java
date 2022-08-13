package com.mjobb.request;

import lombok.Data;

@Data
public class ComplaintRequest {

    private String reason;
    private String description;
    private Long toUserId;
    private Long jobId;

}
