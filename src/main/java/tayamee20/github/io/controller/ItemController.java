package tayamee20.github.io.controller;


import tayamee20.github.io.model.Database;
import tayamee20.github.io.model.Item;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {
    private List<Item> items = new ArrayList<>();

    @PostMapping("/items/update")
    public int updatePlot(@RequestBody Item item) {
        Database database=new Database();
        database.updateItem(item);
        return 1;
    }
}


