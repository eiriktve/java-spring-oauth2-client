package no.stackcanary.javaspringoauth2client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.stackcanary.javaspringoauth2client.error.ApplicationRuntimeException;
import no.stackcanary.javaspringoauth2client.resource.dto.request.EmployeeRequest;
import no.stackcanary.javaspringoauth2client.resource.dto.response.EmployeeResponse;
import no.stackcanary.javaspringoauth2client.resource.dto.response.IdResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final ResourceServerConsumerService resourceService;
    private final ObjectMapper mapper;

    public Optional<EmployeeResponse> getEmployee(String id) {
        return resourceService.getEmployee(id);
    }

    public Optional<EmployeeResponse> updateEmployee(String id, EmployeeRequest request) {
        return resourceService.updateEmployee(id, request);
    }

    public Optional<IdResponse> deleteEmployee(String id) {
        return resourceService.deleteEmployee(id);
    }

    public IdResponse createEmployee(EmployeeRequest request) {
        try {
            log.info("Attempting to create the following employee in the resource server: {}", mapper.writeValueAsString(request));
            return resourceService.createEmployee(request);
        } catch (JsonProcessingException e) {
            throw new ApplicationRuntimeException(e);
        }
    }
}
