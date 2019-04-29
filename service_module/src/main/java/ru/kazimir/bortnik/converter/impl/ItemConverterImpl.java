package ru.kazimir.bortnik.converter.impl;

import org.springframework.stereotype.Component;
import ru.kazimir.bortnik.converter.ItemConverter;
import ru.kazimir.bortnik.model.Item;
import ru.kazimir.bortnik.model.ItemDTO;

@Component
public class ItemConverterImpl implements ItemConverter {
    @Override
    public ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setStatus(item.getItemStatus());
        itemDTO.setName(item.getName());
        return itemDTO;
    }

    @Override
    public Item fromDTO(ItemDTO itemDTO) {
        throw new UnsupportedOperationException();
    }
}
