package com.example.spricesheetapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.VirtualThreadTaskExecutor;

@Configuration
public class UploadExecutorConfig {

    @Bean(name= "uploadTaskExecutor")
    public TaskExecutor uploadTaskExecutor() {
        return new VirtualThreadTaskExecutor("upload-thread");
    }
}
