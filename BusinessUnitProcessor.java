package com.info.springbatchdemo.batch;

import com.info.springbatchdemo.model.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BusinessUnitProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) throws Exception {
        System.out.println("Item Processor BusinessUnit Code:" +employee.getBusinessunit());
        String businessUnitName= BusinessUnitEnum.valueOf(employee.getBusinessunit()).getId();
        System.out.println("Item Processor BusinessUnit Name:" +businessUnitName);
        employee.setBusinessunit(businessUnitName);
        return employee;
    }
}
