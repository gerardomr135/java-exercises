package com.fireal99.roomwebapp.controllers;

import com.fireal99.roomwebapp.models.StaffMember;
import com.fireal99.roomwebapp.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    private final StaffService staffService;
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String getAllStaff(Model model){
        List<StaffMember> staff = staffService.getAllStaff();
        model.addAttribute("staff", staff);
        return "staff";
    }
}
