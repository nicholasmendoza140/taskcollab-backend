package com.nmen.team;

import com.nmen.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Integer> {

    List<TeamMembership> findTeamMembershipByUser(User user);
}
