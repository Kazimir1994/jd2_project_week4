package ru.kazimir.bortnik.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.kazimir.bortnik.ItemRepository;
import ru.kazimir.bortnik.exceptions.ItemRepositoryException;
import ru.kazimir.bortnik.model.Item;
import ru.kazimir.bortnik.model.enums.ItemStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kazimir.bortnik.exceptions.messageexception.ErrorMessagesRepository.QUERY_FAILED;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

    @Override
    public List<Item> getItems(Connection connection) {
        String sqlQuery = "SELECT * FROM Items";
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    itemList.add(getItem(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(String.format(QUERY_FAILED, sqlQuery), e);
        }
        return itemList;
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getLong("id"));
        item.setName(resultSet.getString("name"));
        ItemStatus itemStatus = ItemStatus.valueOf(resultSet.getString("status"));
        item.setItemStatus(itemStatus);
        return item;
    }
}
