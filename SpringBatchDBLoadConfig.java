package com.info.springbatchdemo.config;

import com.info.springbatchdemo.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchDBLoadConfig {

    private static final String QUERY_FIND_EMPLOYEES =
            "SELECT * FROM EMPLOYEE ORDER BY ID ASC";

    private Resource outputResource = new FileSystemResource("D:\\springbatch\\outputData.csv");

    @Bean
    public Job csvWriteJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<Employee> databaseCSVItemReader,
                           ItemProcessor<Employee , Employee> businessUnitProcessor, ItemWriter<Employee> itemWriter) {
        System.out.println("DB load Invoked");
        Step step = stepBuilderFactory.get("EMP-db-load").
                <Employee, Employee>chunk(100)
                .reader(databaseCSVItemReader)
                .processor(businessUnitProcessor)
                .writer(writer())
                .build();

        return jobBuilderFactory.get("EMP-Db-Load").incrementer(new RunIdIncrementer())
                .start(step).build();
    }

    @Bean
    public ItemReader<Employee> databaseCSVItemReader(DataSource dataSource) {
        JdbcCursorItemReader<Employee> databaseReader = new JdbcCursorItemReader<>();
        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(QUERY_FIND_EMPLOYEES);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Employee.class));
        return databaseReader;
    }

    @Bean
    public FlatFileItemWriter<Employee> writer()
    {
        FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<Employee>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Employee>() {
                    {
                        setNames(new String[] { "id", "empname", "businessunit","salary" });
                    }
                });
            }
        });
        return writer;
    }
}
