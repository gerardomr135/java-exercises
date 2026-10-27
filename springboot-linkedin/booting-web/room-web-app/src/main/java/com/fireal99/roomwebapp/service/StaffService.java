package com.fireal99.roomwebapp.service;

import com.fireal99.roomwebapp.data.StaffMemberRepository;
import com.fireal99.roomwebapp.models.Position;
import com.fireal99.roomwebapp.models.StaffMember;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StaffService {
    private final StaffMemberRepository staffMemberRepository;
    public StaffService(StaffMemberRepository staffMemberRepository) {
        this.staffMemberRepository = staffMemberRepository;
    }

    public List<StaffMember> getAllStaff() {
        return staffMemberRepository.findAll();
    }
}
