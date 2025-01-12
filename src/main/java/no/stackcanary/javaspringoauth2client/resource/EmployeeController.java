package no.stackcanary.javaspringoauth2client.resource;

import lombok.RequiredArgsConstructor;
import no.stackcanary.javaspringoauth2client.resource.dto.request.EmployeeRequest;
import no.stackcanary.javaspringoauth2client.resource.dto.response.IdResponse;
import no.stackcanary.javaspringoauth2client.resource.dto.response.EmployeeResponse;
import no.stackcanary.javaspringoauth2client.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    final EmployeeService service;

    @PostMapping()
    public ResponseEntity<IdResponse> createEmployee(@RequestBody EmployeeRequest requestBody) {
        return new ResponseEntity<>(service.createEmployee(requestBody), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable String id) {
        final EmployeeResponse employeeResponse = service.getEmployee(id);
        if (employeeResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String id, @RequestBody EmployeeRequest request) {
        final EmployeeResponse employeeResponse = service.updateEmployee(id, request);
        if (employeeResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> deleteEmployee(@PathVariable String id) {
        final IdResponse idResponse = service.deleteEmployee(id);
        if (idResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(idResponse);
    }
}
