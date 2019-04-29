package ru.kazimir.bortnik.controlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.kazimir.bortnik.UserService;
import ru.kazimir.bortnik.app.SpringBootModuleApplication;
import ru.kazimir.bortnik.model.RoleDTO;
import ru.kazimir.bortnik.model.UserDTO;
import ru.kazimir.bortnik.model.enums.RoleEnum;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = SpringBootModuleApplication.class)
@WebAppConfiguration
public class UserAPIControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;

    @Before
    public void init() {
        ApiUserController userAPIController = new ApiUserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userAPIController).build();
    }

    @Test
    public void saveTheUserAndGetTheAtatus201() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("1234");
        userDTO.setUsername("name");
        userDTO.setEmail("dad@s");
        RoleDTO roleDTO = new RoleDTO();
        RoleEnum roleEnum = RoleEnum.ADMINISTRATOR;
        roleDTO.setRoleEnum(roleEnum);
        userDTO.setRole(roleDTO);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonQuery = mapper.writeValueAsBytes(userDTO);
        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(jsonQuery))
                .andExpect(status().isCreated());
    }


}
