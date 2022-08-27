package com.mjobb.service.impl;

import com.mjobb.entity.Complaint;
import com.mjobb.entity.JobAdvertisement;
import com.mjobb.entity.User;
import com.mjobb.request.ComplaintRequest;
import com.mjobb.service.ComplaintService;
import com.mjobb.service.JobAdvertisementService;
import com.mjobb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class  ComplaintServiceImpl implements ComplaintService {

    private final UserService userService;
    private final JobAdvertisementService jobAdvertisementService;

    @Override
    public void userComplaintToUser(ComplaintRequest request) {
        User fromUser = userService.getCurrentUser();
        User toUser = userService.getUserById(request.getToUserId());

        Complaint complaint = Complaint.builder()
                .reason(request.getReason())
                .description(request.getDescription())
                .build();

        List<Complaint> complaints = CollectionUtils.isEmpty(toUser.getComplaints()) ? new ArrayList<>() : toUser.getComplaints();
        List<Complaint> complaintHistories = CollectionUtils.isEmpty(fromUser.getComplaints()) ? new ArrayList<>() : fromUser.getComplaintHistory();

        complaints.add(complaint);
        complaintHistories.add(complaint);

        fromUser.setComplaintHistory(complaintHistories);
        toUser.setComplaints(complaints);


        userService.saveAndFlush(toUser);
        userService.saveAndFlush(fromUser);

    }

    @Override
    public void userComplaintToJob(ComplaintRequest request) {
        User fromUser = userService.getCurrentUser();
        JobAdvertisement jobAdvertisement = jobAdvertisementService.getJobAdvertisementById(request.getJobId());

        Complaint complaint = Complaint.builder()
                .reason(request.getReason())
                .description(request.getDescription())
                .build();

        List<Complaint> complaints = CollectionUtils.isEmpty(jobAdvertisement.getComplaints()) ? new ArrayList<>() : jobAdvertisement.getComplaints();
//        List<Complaint> complaintHistories = CollectionUtils.isEmpty(fromUser.getComments()) ? new ArrayList<>() : fromUser.getComplaintHistory();

        complaints.add(complaint);
//        complaintHistories.add(complaint);

//        fromUser.setComplaintHistory(complaintHistories);
        jobAdvertisement.setComplaints(complaints);


        jobAdvertisementService.saveAndFlush(jobAdvertisement);
        userService.saveAndFlush(fromUser);
    }
}
