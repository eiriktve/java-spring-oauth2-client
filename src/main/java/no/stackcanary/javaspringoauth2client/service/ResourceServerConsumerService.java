package no.stackcanary.javaspringoauth2client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import no.stackcanary.javaspringoauth2client.error.ResourceServerException;
import no.stackcanary.javaspringoauth2client.resource.dto.request.EmployeeRequest;
import no.stackcanary.javaspringoauth2client.resource.dto.response.EmployeeResponse;
import no.stackcanary.javaspringoauth2client.resource.dto.response.IdResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
@Slf4j
@Service
public class ResourceServerConsumerService {

    private static final String GET_PUT_DELETE_PATH = "/employee/{id}";
    private static final String POST_PATH = "/employee";
    private static final String OAUTH_CLIENT_REGISTRATION_ID = "stackcanary";

    private static final UnaryOperator<String> NOT_FOUND_MESSAGE = id -> "Resource server returned NotFound for Employee with id" + id;

    private final WebClient client;

    public Optional<EmployeeResponse> getEmployee(String id) {
        log.info("Fetching employee with id {} from resource server", id);
        return client.get()
                .uri(GET_PUT_DELETE_PATH, id)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(OAUTH_CLIENT_REGISTRATION_ID))
                .retrieve()
                .bodyToMono(EmployeeResponse.class)
                .map(Optional::of)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                    log.warn(NOT_FOUND_MESSAGE.apply(id));
                    return Mono.just(Optional.empty());
                })
                .onErrorMap(Throwable.class, t -> this.handleError(GET_PUT_DELETE_PATH, HttpMethod.GET, t))
                .block();
    }
    public Optional<EmployeeResponse> updateEmployee(String id, EmployeeRequest request) {
        log.info("Updating employee with id {}", id);
        return client.put()
                .uri(GET_PUT_DELETE_PATH, id)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(OAUTH_CLIENT_REGISTRATION_ID))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(EmployeeResponse.class)
                .map(Optional::of)
                .onErrorResume(WebClientResponseException.NotFound.class, notFound -> {
                    log.warn(NOT_FOUND_MESSAGE.apply(id));
                    return Mono.just(Optional.empty());
                })
                .onErrorMap(Throwable.class, t -> this.handleError(GET_PUT_DELETE_PATH, HttpMethod.PUT, t))
                .block();
    }

    public IdResponse createEmployee(EmployeeRequest request) {
        log.info("Creating new employee with name {}", request.firstName());
        return client.post()
                .uri(POST_PATH)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(OAUTH_CLIENT_REGISTRATION_ID))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(IdResponse.class)
                .onErrorMap(Throwable.class, t -> this.handleError(POST_PATH, HttpMethod.POST, t))
                .block();
    }

    public Optional<IdResponse> deleteEmployee(String id) {
        log.info("Deleting employee with id {}", id);
        return client.delete()
                .uri(GET_PUT_DELETE_PATH, id)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(OAUTH_CLIENT_REGISTRATION_ID))
                .retrieve()
                .bodyToMono(IdResponse.class)
                .map(Optional::of)
                .onErrorResume(WebClientResponseException.NotFound.class, notFound -> {
                    log.warn(NOT_FOUND_MESSAGE.apply(id));
                    return Mono.just(Optional.empty());
                })
                .onErrorMap(Throwable.class, t -> this.handleError(GET_PUT_DELETE_PATH, HttpMethod.DELETE, t))
                .block();
    }

    private Exception handleError(String path, HttpMethod method, Throwable t) {
        log.error(t.getMessage());
        val message =  "Exception occurred when calling resource server on path " + method.name() + " " + path;
        return new ResourceServerException(message, t);
    }
}
