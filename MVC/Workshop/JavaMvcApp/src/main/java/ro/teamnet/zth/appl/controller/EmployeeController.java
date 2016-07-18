package ro.teamnet.zth.appl.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.appl.domain.Employee;
import ro.teamnet.zth.appl.service.EmployeeService;
import ro.teamnet.zth.appl.service.EmployeeServiceImpl;

import java.util.List;

/**
 * Created by user on 7/14/2016.
 */

@MyController(urlPath = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    @MyRequestMethod(urlPath = "/all")
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @MyRequestMethod(urlPath = "/one")
    public Employee getOneEmployee(@MyRequestParam(name="id") Long id) {
        return employeeService.findOneEmployee(id);
    }

    @MyRequestMethod(urlPath = "/one", methodType = "DELETE")
    public void deleteOneEmployee(@MyRequestParam(name="id") Long id) {
        employeeService.deleteOne(id);
    }

}
