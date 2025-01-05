package no.stackcanary.javaspringoauth2client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import no.stackcanary.javaspringoauth2client.error.ResourceServerException;
import no.stackcanary.javaspringoauth2client.resource.model.Employee;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ResourceServerConsumerService {

    private static final String GET_PUT_DELETE_PATH = "/employee/{id}";
    private static final String POST_PATH = "/employee";
    private static final String OAUTH_CLIENT_REGISTRATION_ID = "stackcanary";

    private final WebClient client;

    public Optional<Employee> getEmployee(String id) {
        return client.get()
                .uri(GET_PUT_DELETE_PATH, id)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(OAUTH_CLIENT_REGISTRATION_ID))
                .retrieve()
                .bodyToMono(Employee.class)
                .map(Optional::of)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                    log.warn("Resource server returned NotFound for Employee with id {}", id);
                    return Mono.just(Optional.empty());
                })
                .onErrorMap(Throwable.class, t -> this.handleError(POST_PATH, HttpMethod.GET, t))
                .block();
    }
    public Optional<Employee> updateEmployee(String id, Employee employee) {
        return client.put()
                .uri(GET_PUT_DELETE_PATH, id)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(OAUTH_CLIENT_REGISTRATION_ID))
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(Employee.class)
                .map(Optional::of)
                .onErrorResume(WebClientResponseException.NotFound.class, notFound -> {
                    log.warn("Resource server returned NotFound for Employee with id {}", id);
                    return Mono.just(Optional.empty());
                })
                .onErrorMap(Throwable.class, t -> this.handleError(GET_PUT_DELETE_PATH, HttpMethod.PUT, t))
                .block();
    }

    public Optional<Integer> createEmployee(Employee employee) {
        return client.post()
                .uri(POST_PATH)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(OAUTH_CLIENT_REGISTRATION_ID))
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(Integer.class)
                .map(Optional::of)
                .onErrorMap(Throwable.class, t -> this.handleError(GET_PUT_DELETE_PATH, HttpMethod.POST, t))
                .block();
    }

    public Optional<Integer> deleteEmployee(String id) {
        return client.delete()
                .uri(GET_PUT_DELETE_PATH, id)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(OAUTH_CLIENT_REGISTRATION_ID))
                .retrieve()
                .bodyToMono(Integer.class)
                .map(Optional::of)
                .onErrorResume(WebClientResponseException.NotFound.class, notFound -> {
                    log.warn("Resource server returned NotFound for Employee with id {}", id);
                    return Mono.just(Optional.empty());
                })
                .onErrorMap(Throwable.class, t -> this.handleError(GET_PUT_DELETE_PATH, HttpMethod.DELETE, t))
                .block();
    }

    private Exception handleError(String path, HttpMethod method, Throwable t) {
        val message =  "Exception occurred when calling resource server on path " + method.name() + " " + path;
        return new ResourceServerException(message, t);
    }
}
