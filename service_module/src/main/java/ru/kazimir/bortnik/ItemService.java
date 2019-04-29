package ru.kazimir.bortnik;

import ru.kazimir.bortnik.model.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> getItems();
}
