package com.nmen.task;

import com.nmen.user.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private User assignee;
}
