package ru.kazimir.bortnik.model;

import ru.kazimir.bortnik.model.enums.ItemStatus;

public class Item {
    private Long id;
    private String name;
    private ItemStatus itemStatus;

    public Item(Long id, String name, ItemStatus itemStatus) {
        this.id = id;
        this.name = name;
        this.itemStatus = itemStatus;
    }

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemStatus=" + itemStatus +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }
}
