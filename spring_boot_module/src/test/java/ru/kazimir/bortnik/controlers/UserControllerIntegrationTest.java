package ru.kazimir.bortnik.controlers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.kazimir.bortnik.app.SpringBootModuleApplication;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootModuleApplication.class)
public class UserControllerIntegrationTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = "CUSTOMER")
    @Test
    public void shouldPutOnThePageForCustomer() throws Exception {
        mvc.perform(get("/items"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "CUSTOMER")
    @Test
    public void shouldRedirectTo403PageForCustomer() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = "ADMINISTRATOR")
    @Test
    public void shouldPutOnThePageForAdmin() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }
    @WithMockUser(authorities = "ADMINISTRATOR")
    @Test
    public void shouldRedirectTo403PageForAdmin() throws Exception {
        mvc.perform(get("/items"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void shouldRedirectOnUsersPageForAdminWhenLogin() throws Exception {
        mvc.perform(post("/login")
                .param("username", "bortnik.ru")
                .param("password", "admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    public void shouldRedirectOnLoginPageForSlashPage() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void shouldRedirectOnItemsPageForUserWhenLogin() throws Exception {
        mvc.perform(post("/login")
                .param("username", "kazimir.ru")
                .param("password", "users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items"));
    }

}
