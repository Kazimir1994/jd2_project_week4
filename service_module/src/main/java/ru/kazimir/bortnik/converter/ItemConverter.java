package ru.kazimir.bortnik.converter;

import ru.kazimir.bortnik.model.Item;
import ru.kazimir.bortnik.model.ItemDTO;

public interface ItemConverter {
    ItemDTO toDTO(Item item);

    Item fromDTO(ItemDTO itemDTO);
}
