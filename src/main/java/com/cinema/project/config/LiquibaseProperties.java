package com.cinema.project.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LiquibaseProperties {
    private String changeLogPath;
}
