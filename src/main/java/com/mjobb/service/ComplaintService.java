package com.mjobb.service;

import com.mjobb.entity.Complaint;
import com.mjobb.request.ComplaintRequest;

import java.util.List;

public interface ComplaintService {

    Complaint userComplaintToUser(ComplaintRequest request);

    Complaint userComplaintToJob(ComplaintRequest request);

    List<Complaint> getAllComplaints();

    Complaint acceptComplaint(Long id);

    Complaint rejectComplaint(Long id);
}
