package org.tuxdevelop.spring.batch.lightmin.service;


import org.tuxdevelop.spring.batch.lightmin.api.resource.admin.JobConfiguration;
import org.tuxdevelop.spring.batch.lightmin.api.resource.admin.JobConfigurations;

import java.util.Collection;
import java.util.Map;

/**
 * @author Marcel Becker
 * @since 0.3
 */
public interface AdminServiceEntry {

    /**
     * Creates and saves a new {@link JobConfiguration}
     *
     * @param jobConfiguration {@link JobConfiguration} to save.
     */
    void saveJobConfiguration(JobConfiguration jobConfiguration);

    /**
     * Udpates a existing {@link JobConfiguration}
     *
     * @param jobConfiguration {@link JobConfiguration} to update.
     */
    void updateJobConfiguration(JobConfiguration jobConfiguration);

    /**
     * Deletes an existing {@link JobConfiguration} of the given id.
     *
     * @param jobConfigurationId The id of the {@link org.tuxdevelop.spring.batch.lightmin.admin.domain
     *                           .JobConfiguration} to delete.
     */
    void deleteJobConfiguration(final Long jobConfigurationId);

    /**
     * Retrieves all known {@link JobConfiguration} for a given job
     * name
     *
     * @param jobName the given name of the job
     * @return all known {@link JobConfigurations} for a
     * given job
     * name
     */
    JobConfigurations getJobConfigurationsByJobName(String jobName);

    /**
     * Retrieves a Map of String and all known {@link org.tuxdevelop.spring.batch.lightmin.admin.domain
     * .JobConfiguration} . The key of the maps belongs to a job name.
     *
     * @param jobNames Collection of job names get the configurations for.
     * @return all known {@link JobConfiguration}s
     */
    Map<String, JobConfigurations> getJobConfigurationMap(final Collection<String> jobNames);

    /**
     * Retrieves all known {@link org.tuxdevelop.spring.batch.lightmin.admin.domain
     * .JobConfiguration}s.
     *
     * @param jobNames names of the {@link org.springframework.batch.core.Job}s, to get the configurations for
     * @return all known {@link JobConfiguration}s
     */
    JobConfigurations getJobConfigurations(final Collection<String> jobNames);

    /**
     * Retrieves a {@link JobConfiguration} for a given id.
     *
     * @param jobConfigurationId id of the {@link JobConfiguration}
     * @return the {@link JobConfiguration} for the id
     */
    JobConfiguration getJobConfigurationById(Long jobConfigurationId);

    /**
     * Stops the Scheduler of the {@link JobConfiguration} of the
     * given id
     *
     * @param jobConfigurationId id of the {@link JobConfiguration}
     */
    void stopJobConfiguration(Long jobConfigurationId);

    /**
     * Starts the Scheduler of the {@link JobConfiguration} of the
     * given id
     *
     * @param jobConfigurationId id of the {@link JobConfiguration}
     */
    void startJobConfiguration(Long jobConfigurationId);
}
