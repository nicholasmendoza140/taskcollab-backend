package com.nmen.project;

import com.nmen.config.TeamSecurity;
import com.nmen.team.Team;
import com.nmen.team.TeamRepository;
import com.nmen.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TeamSecurity teamSecurity;

    public Project createProject(CreateProjectRequest request, Integer teamId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow();
        var project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .team(team)
                .createdAt(Instant.now())
                .build();
        projectRepository.save(project);
        return project;
    }

    public List<Project> getProjects(Integer teamId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow();
        return projectRepository.findProjectsByTeam(team);
    }

    public Project getProject(Integer projectId) {
        var email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        var userId = userRepository.findByEmail(email)
                .orElseThrow().getId();
        var project = projectRepository.findById(projectId)
                .orElseThrow();
        if (!teamSecurity.isTeamMember(project.getTeam().getId(), userId)) {
            throw new AccessDeniedException("Forbidden");
        }
        return project;
    }
}
