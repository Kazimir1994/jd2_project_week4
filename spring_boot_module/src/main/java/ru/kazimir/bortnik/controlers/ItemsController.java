package ru.kazimir.bortnik.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kazimir.bortnik.ItemService;
import ru.kazimir.bortnik.model.ItemDTO;

import java.util.List;

@Controller
public class ItemsController {

    private final ItemService itemService;
    private static final Logger logger = LoggerFactory.getLogger(ItemsController.class);

    @Autowired
    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        List<ItemDTO> itemDTOList = itemService.getItems();
        model.addAttribute("items", itemDTOList);
        logger.info("Show items {}", itemDTOList);

        return "/items";
    }
}
