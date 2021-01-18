package org.tuxdevelop.spring.batch.lightmin.server.fe.model.job.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class MapJobListenerConfigurationModel {

    @Getter
    private final Map<String, Set<ListenerJobConfigurationModel>> jobConfigurations;

    public MapJobListenerConfigurationModel() {
        this.jobConfigurations = new HashMap<>();
    }

    public void add(final String jobName, final ListenerJobConfigurationModel jobConfigurationModel) {
        if (this.jobConfigurations.containsKey(jobName)) {
            log.trace("job configuration set already initialized for job {}", jobName);
        } else {
            this.jobConfigurations.put(jobName, new HashSet<>());
        }
        this.jobConfigurations.get(jobName).add(jobConfigurationModel);
    }
}
