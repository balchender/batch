package com.info.springbatchdemo.batch;

import com.info.springbatchdemo.model.Employee;
import com.info.springbatchdemo.repository.EmployeeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class DBWriter implements ItemWriter<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void write(List<? extends Employee> emps) throws Exception {
        System.out.println("Employee Details has been Saved:" +emps);
        employeeRepository.save(emps);
    }
}
