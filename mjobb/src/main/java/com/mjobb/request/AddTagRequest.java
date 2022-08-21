package com.mjobb.request;

import com.mjobb.entity.Tag;
import lombok.Data;

@Data
public class AddTagRequest {

    private Long tagId;
    private Long jobId;
}
