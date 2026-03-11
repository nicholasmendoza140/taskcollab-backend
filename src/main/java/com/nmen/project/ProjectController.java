package com.nmen.project;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("@teamSecurity.isTeamMember(#teamId, principal.id)")
    @PostMapping("/teams/{teamId}/projects")
    public ResponseEntity<Project> createProject(@PathVariable Integer teamId, @RequestBody CreateProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request, teamId));
    }

    @PreAuthorize("@teamSecurity.isTeamMember(#teamId, principal.id)")
    @GetMapping("/teams/{teamId}/projects")
    public ResponseEntity<List<Project>> getProjects(@PathVariable Integer teamId) {
        return ResponseEntity.ok(projectService.getProjects(teamId));
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable Integer projectId) {
        return ResponseEntity.ok(projectService.getProject(projectId));
    }
}
