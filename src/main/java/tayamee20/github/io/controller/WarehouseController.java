package tayamee20.github.io.controller;

import tayamee20.github.io.model.Database;
import tayamee20.github.io.model.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.List;

@RestController
public class WarehouseController {
    List<Item> response;
    @GetMapping("/warehouse-items")
    public List<Item> getWarehouseItems() throws SQLException {

        Database database=new Database();
        response=database.wareHouseItems();


        return response;
    }
}







