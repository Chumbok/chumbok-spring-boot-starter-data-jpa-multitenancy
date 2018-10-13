package com.chumbok.multitenancy;

import com.chumbok.multitenancy.annotation.EnableMultitenantJpaRepositories;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class DataJpaMultitenancyIT {

    @Autowired
    private TenantAware tenantAware;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldFetchEntitiesThatBelongsToUsersOrgTenant() throws Exception {

        // User1 create/saves Hello resource
        mimicUser("User1");
        mockMvc.perform(post("/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"User1UUID\", \"message\": \"User1SampleMessage\" }"))
                .andExpect(jsonPath("$.id").value("User1UUID"))
                .andExpect(jsonPath("$.message").value("User1SampleMessage"))
                .andExpect(jsonPath("$.org").value("User1Org"))
                .andExpect(jsonPath("$.tenant").value("User1Tenant"))
                .andDo(print());

        // User2 create/saves Hello resource
        mimicUser("User2");
        mockMvc.perform(post("/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"User2UUID\", \"message\": \"User2SampleMessage\" }"))
                .andExpect(jsonPath("$.id").value("User2UUID"))
                .andExpect(jsonPath("$.message").value("User2SampleMessage"))
                .andExpect(jsonPath("$.org").value("User2Org"))
                .andExpect(jsonPath("$.tenant").value("User2Tenant"))
                .andDo(print());

        // Assert if User1 gets only one Hello resource
        mimicUser("User1");
        mockMvc.perform(get("/hello"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("User1UUID"))
                .andExpect(jsonPath("$[0].message").value("User1SampleMessage"))
                .andExpect(jsonPath("$[0].org").value("User1Org"))
                .andExpect(jsonPath("$[0].tenant").value("User1Tenant"))
                .andDo(print());

        // Assert if User2 gets only one Hello resource
        mimicUser("User2");
        mockMvc.perform(get("/hello"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("User2UUID"))
                .andExpect(jsonPath("$[0].message").value("User2SampleMessage"))
                .andExpect(jsonPath("$[0].org").value("User2Org"))
                .andExpect(jsonPath("$[0].tenant").value("User2Tenant"))
                .andDo(print());
    }

    @Test
    public void shouldThrowExceptionWhenOneUserTriesToUpdateAnotherUsersResource() throws Exception {

        // User1 create/saves Hello resource
        mimicUser("User3");
        mockMvc.perform(post("/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"User3UUID\", \"message\": \"User3SampleMessage\" }"))
                .andExpect(jsonPath("$.id").value("User3UUID"))
                .andExpect(jsonPath("$.message").value("User3SampleMessage"))
                .andExpect(jsonPath("$.org").value("User3Org"))
                .andExpect(jsonPath("$.tenant").value("User3Tenant"))
                .andDo(print());

        mimicUser("User4");
        mockMvc.perform(put("/hello/User3UUID")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"User3UUID\", \"message\": \"User3SampleMessage\" }"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Hello[User3UUID] does not exist."))
                .andDo(print());

    }

    @Test
    public void shouldThrowExceptionWhenOneUserTriesToDeleteAnotherUsersResource() throws Exception {

        // User1 create/saves Hello resource
        mimicUser("User5");
        mockMvc.perform(post("/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"User5UUID\", \"message\": \"User5SampleMessage\" }"))
                .andExpect(jsonPath("$.id").value("User5UUID"))
                .andExpect(jsonPath("$.message").value("User5SampleMessage"))
                .andExpect(jsonPath("$.org").value("User5Org"))
                .andExpect(jsonPath("$.tenant").value("User5Tenant"))
                .andDo(print());

        mimicUser("User6");
        mockMvc.perform(delete("/hello/User5UUID"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Hello[User5UUID] does not exist."))
                .andDo(print());

    }

    @Test
    public void shouldNotAbleToFetchOtherUsersResource() throws Exception {

        // User1 create/saves Hello resource
        mimicUser("User7");
        mockMvc.perform(post("/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"User7UUID\", \"message\": \"User7SampleMessage\" }"))
                .andExpect(jsonPath("$.id").value("User7UUID"))
                .andExpect(jsonPath("$.message").value("User7SampleMessage"))
                .andExpect(jsonPath("$.org").value("User7Org"))
                .andExpect(jsonPath("$.tenant").value("User7Tenant"))
                .andDo(print());

        mimicUser("User8");
        mockMvc.perform(get("/hello/User7UUID"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Hello[User7UUID] does not exist."))
                .andDo(print());

    }

    private void mimicUser(String user) {
        ((JpaTenantTestConfig.TenantAwareImpl) tenantAware).userIdentifier(user);
    }

    @Configuration
    @EnableMultitenantJpaRepositories
    static class JpaTenantTestConfig {

        @Bean
        public TenantAware<String> tenantAware() {
            return new TenantAwareImpl();
        }

        static class TenantAwareImpl implements TenantAware<String> {

            private String user;

            public void userIdentifier(String user) {
                this.user = user;
            }

            @Override
            public Optional<String> getOrg() {
                return Optional.of(user + "Org");
            }

            @Override
            public Optional<String> getTenant() {
                return Optional.of(user + "Tenant");
            }
        }
    }
}
