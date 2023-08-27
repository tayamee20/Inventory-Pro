package tayamee20.github.io.controller;

import tayamee20.github.io.model.Database;
import tayamee20.github.io.model.User;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {
    Database database;



    @PostMapping("/addUser")
    public int  addUser(@RequestBody User user) {
        database=new Database();
        database.addusersQuery(user);

        return 1;
    }



    @GetMapping("/getUsers")
    @ResponseBody
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try {
            // Establish a database connection (Ensure your MySQL server is running)
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryplus", "root", "root");

            // Use a prepared statement to execute your SQL query
            String sql = "SELECT userName, userType, salary,password FROM user"; // Adjust your SQL query as needed
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getInt("password"));
                user.setUserType(resultSet.getString("userType"));
                user.setSalary(resultSet.getInt("salary"));
                users.add(user);
            }

            // Close database resources
            resultSet.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }



    @GetMapping("/deleteUser")
    @ResponseBody
    public int login(@RequestParam("username") String username ){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryplus", "root", "root");
            String query = "DELETE FROM user WHERE userName = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return  1;
    }





}
