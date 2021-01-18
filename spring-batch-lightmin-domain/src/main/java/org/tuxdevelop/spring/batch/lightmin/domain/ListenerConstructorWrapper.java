package org.tuxdevelop.spring.batch.lightmin.domain;

import lombok.Data;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

/**
 * @author Marcel Becker
 * @version 0.3
 */
@Data
public class ListenerConstructorWrapper {

    private JobConfiguration jobConfiguration;
    private Job job;
    private JobParameters jobParameters;
    private JobIncrementer jobIncrementer;
    private JobLauncher jobLauncher;
}
