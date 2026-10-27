package com.fireal99.roomwebapp.data;

import com.fireal99.roomwebapp.models.StaffMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StaffMemberRepository extends JpaRepository<StaffMember, String> {
}
