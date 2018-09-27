package com.info.springbatchdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
public class Employee {

    @Id
    @Column(name="ID")
    private Integer id;

    private String empname;

    private String businessunit;
    @Column(name="SALARY")
    private Long salary;

    public Employee() {
    }

    public Employee(Integer id, String empname, String businessunit, Long salary) {
        this.id = id;
        this.empname = empname;
        this.businessunit = businessunit;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpname() {
        return empname;
    }
    @Column(name="EMP_NAME")
    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getBusinessunit() {
        return businessunit;
    }
    @Column(name="BUSINESS_UNIT")
    public void setBusinessunit(String businessunit) {
        this.businessunit = businessunit;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }


}
