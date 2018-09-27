package com.info.springbatchdemo.config.controller;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/load")
public class EmployeeController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job csvloadjob;

    @Autowired
    private Job csvWriteJob;

    @GetMapping("/csv")
    public BatchStatus loadCSVFile() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        System.out.println("******** LOAD to CSV Invoked **********");
        Map<String, JobParameter> maps=new HashMap<>();
        maps.put("time",new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters=new JobParameters(maps);
        JobExecution jobExecution=jobLauncher.run(csvloadjob,jobParameters);
        System.out.println("************ CSV Load Job Execution Status: **************" +jobExecution.getStatus());
        while(jobExecution.isRunning()){
            System.out.println("Still it is running....");
        }
        return jobExecution.getStatus();
    }

    @GetMapping("/db")
    public BatchStatus writeToCSVFile() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        System.out.println("****** Write TO CSV Method Invoked **********");
        Map<String, JobParameter> maps=new HashMap<>();
        maps.put("time",new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters=new JobParameters(maps);
        JobExecution jobExecution=jobLauncher.run(csvWriteJob,jobParameters);
        System.out.println("********** DB Load Job Execution Status: *************" +jobExecution.getStatus());
        while(jobExecution.isRunning()){
            System.out.println("Still it is running....");
        }
        return jobExecution.getStatus();
    }

}
