package org.tuxdevelop.spring.batch.lightmin.server.web;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tuxdevelop.spring.batch.lightmin.admin.domain.*;
import org.tuxdevelop.spring.batch.lightmin.admin.repository.JobConfigurationRepository;
import org.tuxdevelop.spring.batch.lightmin.server.ITConfigurationApplication;
import org.tuxdevelop.spring.batch.lightmin.server.ITJobConfiguration;
import org.tuxdevelop.spring.batch.lightmin.server.repository.LightminApplicationRepository;
import org.tuxdevelop.spring.batch.lightmin.service.ListenerService;
import org.tuxdevelop.spring.batch.lightmin.service.SchedulerService;

import java.util.Collection;

import static org.assertj.core.api.Fail.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
@SpringApplicationConfiguration(classes = {ITConfigurationApplication.class, ITJobConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class CommonControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private JobConfigurationRepository jobConfigurationRepository;
    @Autowired
    private Job simpleJob;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private LightminApplicationRepository lightminApplicationRepository;
    @Autowired
    private ListenerService listenerService;

    protected MockMvc mockMvc;
    protected Long launchedJobExecutionId;
    protected Long launchedJobInstanceId;
    protected Long launchedStepExecutionId;
    protected JobConfiguration addedJobConfiguration;
    protected JobExecution jobExecution;
    protected String applicationId;
    protected JobConfiguration addedListenerJobConfiguration;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        addJobConfigurations();
        addJobListenerConfiguration();
        launchSimpleJob();
        applicationId = lightminApplicationRepository.findAll().iterator().next().getId();
    }

    protected void addJobConfigurations() {
        final JobSchedulerConfiguration jobSchedulerConfiguration = new JobSchedulerConfiguration();
        jobSchedulerConfiguration.setBeanName("testBean");
        jobSchedulerConfiguration.setFixedDelay(100000L);
        jobSchedulerConfiguration.setInitialDelay(100000L);
        jobSchedulerConfiguration.setJobSchedulerType(JobSchedulerType.PERIOD);
        jobSchedulerConfiguration.setTaskExecutorType(TaskExecutorType.ASYNCHRONOUS);
        jobSchedulerConfiguration.setSchedulerStatus(SchedulerStatus.INITIALIZED);
        final JobConfiguration jobConfiguration = new JobConfiguration();
        jobConfiguration.setJobName("simpleJob");
        jobConfiguration.setJobConfigurationId(1L);
        jobConfiguration.setJobIncrementer(JobIncrementer.DATE);
        jobConfiguration.setJobSchedulerConfiguration(jobSchedulerConfiguration);
        addedJobConfiguration = jobConfigurationRepository.add(jobConfiguration);
        schedulerService.registerSchedulerForJob(addedJobConfiguration);
    }

    protected void addJobListenerConfiguration() {
        final JobListenerConfiguration jobListenerConfiguration = new JobListenerConfiguration();
        jobListenerConfiguration.setListenerStatus(ListenerStatus.STOPPED);
        jobListenerConfiguration.setPollerPeriod(1000L);
        jobListenerConfiguration.setFilePattern("*.txt");
        jobListenerConfiguration.setSourceFolder("src/test");
        jobListenerConfiguration.setTaskExecutorType(TaskExecutorType.SYNCHRONOUS);
        jobListenerConfiguration.setBeanName("myTestBean");
        jobListenerConfiguration.setJobListenerType(JobListenerType.LOCAL_FOLDER_LISTENER);
        final JobConfiguration jobConfiguration = new JobConfiguration();
        jobConfiguration.setJobName("simpleJob");
        jobConfiguration.setJobConfigurationId(1L);
        jobConfiguration.setJobIncrementer(JobIncrementer.DATE);
        jobConfiguration.setJobListenerConfiguration(jobListenerConfiguration);
        addedListenerJobConfiguration = jobConfigurationRepository.add(jobConfiguration);
        listenerService.registerListenerForJob(addedListenerJobConfiguration);
    }

    private void launchSimpleJob() {
        try {
            final JobExecution execution = jobLauncher.run(simpleJob, new JobParametersBuilder().toJobParameters());
            launchedJobExecutionId = execution.getId();
            launchedJobInstanceId = execution.getJobInstance().getId();
            final Collection<StepExecution> stepExecutions = execution.getStepExecutions();
            for (final StepExecution stepExecution : stepExecutions) {
                launchedStepExecutionId = stepExecution.getId();
            }
            jobExecution = execution;

        } catch (final Exception e) {
            fail(e.getMessage());
        }
    }

}