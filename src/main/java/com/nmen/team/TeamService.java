package com.nmen.team;

import com.nmen.auth.AuthenticationService;
import com.nmen.user.Role;
import com.nmen.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final TeamMembershipRepository teamMembershipRepository;

    public Team create(CreateTeamRequest request) {

        var email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow();
        var team = Team.builder()
                .name(request.getName())
                .createdBy(user)
                .createdAt(Instant.now())
                .build();
        teamRepository.save(team);

        var teamMembership = TeamMembership.builder()
                .team(team)
                .user(user)
                .role(TeamRole.OWNER)
                .build();

        teamMembershipRepository.save(teamMembership);
        return team;
    }

    public TeamMembership addMember(AddMemberRequest request, Integer teamId) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var team = teamRepository.findById(teamId)
                .orElseThrow();
        var teamMembership = TeamMembership.builder()
                .team(team)
                .user(user)
                .role(TeamRole.USER)
                .build();
        teamMembershipRepository.save(teamMembership);
        return teamMembership;

    }
}
