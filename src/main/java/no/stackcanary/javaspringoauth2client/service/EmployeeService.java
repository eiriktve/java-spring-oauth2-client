package no.stackcanary.javaspringoauth2client.service;

import lombok.RequiredArgsConstructor;
import no.stackcanary.javaspringoauth2client.resource.dto.EmployeeResponse;
import no.stackcanary.javaspringoauth2client.resource.dto.IdResponse;
import no.stackcanary.javaspringoauth2client.resource.model.Employee;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ResourceServerConsumerService resourceService;

    public EmployeeResponse getEmployee(String id) {
        return resourceService.getEmployee(id)
                .map(EmployeeResponse::new)
                .orElseGet(() -> null);
    }
    public EmployeeResponse updateEmployee(String id, Employee employee) {
        return resourceService.updateEmployee(id, employee)
                .map(EmployeeResponse::new)
                .orElseGet(() -> null);
    }

    public IdResponse createEmployee(Employee employee) {
        return resourceService.createEmployee(employee)
                .map(employeeId -> new IdResponse(String.valueOf(employeeId), "Employee created"))
                .orElseGet(() -> null);
    }

    public IdResponse deleteEmployee(String id) {
        return resourceService.deleteEmployee(id)
                .map(employeeId -> new IdResponse(String.valueOf(employeeId), "Employee deleted"))
                .orElseGet(() -> null);
    }
}
