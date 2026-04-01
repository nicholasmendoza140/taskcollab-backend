package com.nmen.task;

import com.nmen.config.TeamSecurity;
import com.nmen.project.ProjectRepository;
import com.nmen.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamSecurity teamSecurity;

    public Task createTask(CreateTaskRequest request, Integer projectId) {
        var email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow();
        var project = projectRepository.findById(projectId)
                .orElseThrow();
        var task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .assignee(request.getAssignee())
                .status(TaskStatus.TODO)
                .project(project)
                .createdBy(user)
                .build();
        taskRepository.save(task);
        return task;
    }

    public List<Task> getTasks(Integer projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow();
        return taskRepository.findTasksByProject(project);
    }

    public Task getTask(Integer taskId) {
        var email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        var userId = userRepository.findByEmail(email)
                .orElseThrow().getId();
        var task = taskRepository.findById(taskId)
                .orElseThrow();
        if (!teamSecurity.isTeamMember(task.getProject().getTeam().getId(), userId)) {
            throw new AccessDeniedException("Forbidden");
        }
        return task;
    }

    public Task updateTask(Integer taskId, UpdateTaskRequest request) {
        var email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        var userId = userRepository.findByEmail(email)
                .orElseThrow().getId();
        var task = taskRepository.findById(taskId)
                .orElseThrow();
        if (!teamSecurity.isTeamMember(task.getProject().getTeam().getId(), userId)) {
            throw new AccessDeniedException("Forbidden");
        }

        if (request.getName() != null) {
            task.setName(request.getName());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getAssignee() != null) {
            task.setAssignee(request.getAssignee());
        }

        return taskRepository.save(task);
    }
}
