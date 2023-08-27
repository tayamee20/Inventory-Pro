package tayamee20.github.io.controller;

import tayamee20.github.io.model.Database;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class loginController {

    @GetMapping("/login")
    @ResponseBody
    public int login(@RequestParam("username") String username, @RequestParam("password") String password) {

        Database database=new Database();
        int returnedNum=database.loginUser(username,password);
        if (returnedNum==1) {
            // Authentication successful, a matching user was found in the database
            return 1;
        } else {
            // Authentication failed, no matching user found
            return 0;
        }

    }
}
