package ru.kazimir.bortnik.model;

import ru.kazimir.bortnik.model.enums.ItemStatus;

public class ItemDTO {
    private Long id;
    private String name;
    private ItemStatus status;

    public ItemDTO(Long id, String name, ItemStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public ItemDTO() {
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
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

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}
