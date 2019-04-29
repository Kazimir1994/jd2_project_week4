package ru.kazimir.bortnik.controlers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.kazimir.bortnik.ItemService;
import ru.kazimir.bortnik.model.ItemDTO;
import ru.kazimir.bortnik.model.enums.ItemStatus;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ItemsControllerTest {
    private MockMvc mockMvc;
    private ItemsController itemsController;
    @Mock
    private ItemService itemService;

    private List<ItemDTO> items = Arrays.asList(new ItemDTO(1L, "name1", ItemStatus.GO),
            new ItemDTO(2L, "name2", ItemStatus.GO));

    @Before
    public void init() {
        itemsController = new ItemsController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemsController).build();
    }

    @Test
    public void shouldSeeAllItems() throws Exception {
        when(itemService.getItems()).thenReturn(items);
        this.mockMvc.perform(get("/items.html")).andExpect(status().isOk())
                .andExpect(model().attribute("items", equalTo(items)));
    }
}