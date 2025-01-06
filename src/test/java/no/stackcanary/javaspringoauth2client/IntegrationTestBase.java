package no.stackcanary.javaspringoauth2client;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static no.stackcanary.javaspringoauth2client.util.WiremockStubs.stubOauthIssuerTokenResponse;

/* By having integration tests extend this, you only spin up the spring context one time across all tests */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @Autowired
    protected WebTestClient testClient;

    @BeforeEach
    void setup() {
        WireMock.reset();
        stubOauthIssuerTokenResponse();
    }
}
