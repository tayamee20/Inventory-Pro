package tayamee20.github.io.controller;

import tayamee20.github.io.model.Database;
import tayamee20.github.io.model.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {
    List<Item> response;
    @GetMapping("/search")
    @ResponseBody
    public List<Item> search(@RequestParam("query") String query) {

        Database database=new Database();
        response=database.databaseSearchPlot(query);

        return response;
    }

}


