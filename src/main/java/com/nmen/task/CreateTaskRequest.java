package com.nmen.task;

import com.nmen.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    private String name;

    private String description;

    private Integer assigneeId;
}
