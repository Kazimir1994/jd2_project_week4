package ru.kazimir.bortnik.controlers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.kazimir.bortnik.UserService;
import ru.kazimir.bortnik.model.RoleDTO;
import ru.kazimir.bortnik.model.UserDTO;
import ru.kazimir.bortnik.model.enums.RoleEnum;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    private UsersController itemsController;
    @Mock
    private UserService userService;

    private List<UserDTO> users = new ArrayList<>();

    @Before
    public void init() {
        itemsController = new UsersController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemsController).build();
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("1234");
        userDTO.setUsername("name");
        userDTO.setEmail("dad@s");
        RoleDTO roleDTO = new RoleDTO();
        RoleEnum roleEnum = RoleEnum.ADMINISTRATOR;
        roleDTO.setRoleEnum(roleEnum);
        userDTO.setRole(roleDTO);
        users.add(userDTO);

        UserDTO userDTO2 = new UserDTO();
        userDTO.setPassword("1234");
        userDTO.setUsername("name");
        userDTO.setEmail("dad@s");
        RoleDTO roleDTO2 = new RoleDTO();
        RoleEnum roleEnum2 = RoleEnum.ADMINISTRATOR;
        roleDTO.setRoleEnum(roleEnum2);
        userDTO.setRole(roleDTO2);
        users.add(userDTO2);
    }

    @Test
    public void shouldSeeAllUsers() throws Exception {
        when(userService.getUsers()).thenReturn(users);
        this.mockMvc.perform(get("/users.html")).andExpect(status().isOk())
                .andExpect(model().attribute("users", equalTo(users)));
    }
}
