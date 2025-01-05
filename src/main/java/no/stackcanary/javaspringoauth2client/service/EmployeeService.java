package no.stackcanary.javaspringoauth2client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import no.stackcanary.javaspringoauth2client.error.ApplicationRuntimeException;
import no.stackcanary.javaspringoauth2client.resource.dto.EmployeeResponse;
import no.stackcanary.javaspringoauth2client.resource.dto.IdResponse;
import no.stackcanary.javaspringoauth2client.resource.model.Employee;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final ResourceServerConsumerService resourceService;

    private final ObjectMapper mapper = new ObjectMapper();

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
        try {
            val employeeAsString = mapper.writeValueAsString(employee);
            log.info("Attempting to create the following employee in the resource server: {}", employeeAsString);
        } catch (JsonProcessingException e) {
            throw new ApplicationRuntimeException(e);
        }

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
