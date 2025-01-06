package no.stackcanary.javaspringoauth2client.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebclientConfiguration {

    @Primary
    @Bean
    public ReactiveClientRegistrationRepository clientRegistrations(OAuth2ClientProperties oAuth2ClientProperties) {
        final List<ClientRegistration> registrations = new ArrayList<>(
                new OAuth2ClientPropertiesMapper(oAuth2ClientProperties)
                        .asClientRegistrations()
                        .values());
        return new InMemoryReactiveClientRegistrationRepository(registrations);
    }

    @Bean
    public ClientHttpConnector clientConnector(@Value("${webclient-log-level}") String logLevel) {
        // Great for debugging, but limit this bean to specific profiles, as it logs the Authorization header
        return new ReactorClientHttpConnector(HttpClient.create()
                .wiretap(this.getClass().getCanonicalName(),
                        LogLevel.valueOf(logLevel.toUpperCase()),
                        AdvancedByteBufFormat.TEXTUAL));
    }

    @Bean
    public WebClient webclient(@Value("${resource-server-url}") String url,
                               ClientHttpConnector connector,
                               ReactiveClientRegistrationRepository clientRegistrations) {
        return WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(24 * 1024 * 1024))
                .clientConnector(connector)
                .filter(new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations,
                                new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations))))
                .baseUrl(url)
                .build();
    }
}
