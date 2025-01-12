package no.stackcanary.javaspringoauth2client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import no.stackcanary.javaspringoauth2client.error.ApplicationRuntimeException;
import no.stackcanary.javaspringoauth2client.resource.dto.request.EmployeeRequest;
import no.stackcanary.javaspringoauth2client.resource.dto.response.IdResponse;
import no.stackcanary.javaspringoauth2client.resource.dto.response.EmployeeResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final ResourceServerConsumerService resourceService;
    private final ObjectMapper mapper;

    public EmployeeResponse getEmployee(String id) {
        return resourceService.getEmployee(id).orElseGet(() -> null);
    }
    public EmployeeResponse updateEmployee(String id, EmployeeRequest request) {
        return resourceService.updateEmployee(id, request).orElseGet(() -> null);
    }

    public IdResponse createEmployee(EmployeeRequest employeeResponse) {
        try {
            val employeeAsString = mapper.writeValueAsString(employeeResponse);
            log.info("Attempting to create the following employee in the resource server: {}", employeeAsString);
        } catch (JsonProcessingException e) {
            throw new ApplicationRuntimeException(e);
        }

        val response = resourceService.createEmployee(employeeResponse);
        return response;

    }

    public IdResponse deleteEmployee(String id) {
        return resourceService.deleteEmployee(id).orElseGet(() -> null);
    }
}
