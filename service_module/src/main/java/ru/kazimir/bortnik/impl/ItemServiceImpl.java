package ru.kazimir.bortnik.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kazimir.bortnik.ItemRepository;
import ru.kazimir.bortnik.ItemService;
import ru.kazimir.bortnik.connection.ConnectionDataBaseService;
import ru.kazimir.bortnik.converter.ItemConverter;
import ru.kazimir.bortnik.exceptions.ConnectionDataBaseExceptions;
import ru.kazimir.bortnik.exceptions.ItemRepositoryException;
import ru.kazimir.bortnik.exceptions.ItemServiceException;
import ru.kazimir.bortnik.model.Item;
import ru.kazimir.bortnik.model.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kazimir.bortnik.exceptions.messageexception.ErrorMessagesService.NO_CONNECTION;
import static ru.kazimir.bortnik.exceptions.messageexception.ErrorMessagesService.QUERY_FAILED;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;
    private final ConnectionDataBaseService connectionDataBaseService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter, ConnectionDataBaseService connectionDataBaseService) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
        this.connectionDataBaseService = connectionDataBaseService;
    }

    @Override
    public List<ItemDTO> getItems() {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        try (Connection connection = connectionDataBaseService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Item> itemList = itemRepository.getItems(connection);
                for (Item item : itemList) {
                    itemDTOList.add(itemConverter.toDTO(item));
                }
                connection.commit();
                return itemDTOList;
            } catch (SQLException | ItemRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ItemServiceException(QUERY_FAILED, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionDataBaseExceptions(NO_CONNECTION, e);
        }
    }
}
