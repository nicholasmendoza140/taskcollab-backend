package com.nmen.team;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;


    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamRequest request) {
        return ResponseEntity.ok(teamService.create(request));
    }

    @PostMapping("/{teamId}/members")
    public ResponseEntity<TeamMembership> addMember(@PathVariable Integer teamId, @RequestBody AddMemberRequest request) {
        return ResponseEntity.ok(teamService.addMember(request, teamId));
    }

    @GetMapping
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

}
