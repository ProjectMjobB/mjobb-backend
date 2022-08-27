package com.mjobb.service;

import com.mjobb.entity.Complaint;
import com.mjobb.request.ComplaintRequest;

public interface ComplaintService {

    Complaint userComplaintToUser(ComplaintRequest request);

    Complaint userComplaintToJob(ComplaintRequest request);
}
