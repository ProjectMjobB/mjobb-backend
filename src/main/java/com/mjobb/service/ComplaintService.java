package com.mjobb.service;

import com.mjobb.request.ComplaintRequest;

public interface ComplaintService {

    void userComplaintToUser(ComplaintRequest request);

    void userComplaintToJob(ComplaintRequest request);
}
