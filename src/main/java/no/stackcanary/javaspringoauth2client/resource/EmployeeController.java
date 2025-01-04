package no.stackcanary.javaspringoauth2client.resource;

import lombok.RequiredArgsConstructor;
import no.stackcanary.javaspringoauth2client.resource.dto.EmployeeResponse;
import no.stackcanary.javaspringoauth2client.resource.dto.IdResponse;
import no.stackcanary.javaspringoauth2client.resource.model.Employee;
import no.stackcanary.javaspringoauth2client.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    final EmployeeService service;

    @PostMapping()
    public ResponseEntity<IdResponse> createEmployee(@RequestBody Employee requestBody) {
        return ResponseEntity.ok(service.createEmployee(requestBody));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable String id) {
        final EmployeeResponse employee = service.getEmployee(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String id, @RequestBody Employee requestBody) {
        final EmployeeResponse employee = service.updateEmployee(id, requestBody);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
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
