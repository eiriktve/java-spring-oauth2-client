package no.stackcanary.javaspringoauth2client.service;

import lombok.RequiredArgsConstructor;
import no.stackcanary.javaspringoauth2client.resource.dto.Employee;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class EmployeeService {

    private final WebClient client;

    public Employee getEmployeeFromResourceServer() {

        return null;
    }
}
