package ru.kazimir.bortnik;

import ru.kazimir.bortnik.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {
    List<Item> getItems(Connection connection);
}
