package com.info.springbatchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import com.info.springbatchdemo.model.Employee;



@Configuration
@EnableBatchProcessing
public class SpringBatchCSVLoadConfig {


    @Bean
    public Job csvloadjob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<Employee> itemReader,
                          ItemProcessor<Employee, Employee> processor, ItemWriter<Employee> dBWriter) {
        System.out.println("CSV load Invoked");
        Step step = stepBuilderFactory.get("EMP-file-load").
                <Employee, Employee>chunk(100)
                .reader(itemReader)
                .processor(processor)
                .writer(dBWriter)
                .build();

        return jobBuilderFactory.get("EMP-Load").incrementer(new RunIdIncrementer())
                .start(step).build();
    }

    @Bean
    public FlatFileItemReader<Employee> itemReader(@Value("${input}") Resource resource) {
        FlatFileItemReader<Employee> flatfileItemReader = new FlatFileItemReader<>();
        flatfileItemReader.setResource(resource);
        flatfileItemReader.setName("CSV-fileReader");
        flatfileItemReader.setLinesToSkip(1);
        flatfileItemReader.setLineMapper(lineMapper());
        return flatfileItemReader;
    }

    @Bean
    public LineMapper<Employee> lineMapper() {

        DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames(new String[]{"id", "empname", "businessunit", "salary"});
        BeanWrapperFieldSetMapper<Employee> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Employee.class);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }


}
