package com.nmen.team;

import com.nmen.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;


    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamRequest request) {
        return ResponseEntity.ok(teamService.create(request));
    }

    @PreAuthorize("@teamSecurity.isTeamMember(#teamId, principal.id)")
    @PostMapping("/teams/{teamId}/members")
    public ResponseEntity<TeamMembership> addMember(@PathVariable Integer teamId, @RequestBody AddMemberRequest request) {
        return ResponseEntity.ok(teamService.addMember(request, teamId));
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<Team> getTeam(@PathVariable Integer teamId) {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

}
