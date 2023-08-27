package tayamee20.github.io.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Item> items;

    public void updateItem(Item item){
         items = new ArrayList<>();
         items.add(item);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryplus", "root", "root");
            String updateQuery = "UPDATE plot SET productName=?, pricePerKg=?, location=?, imageUrl=?, totalQuantity=?, quantitySold=?, quantityLeft=?, movedToWareHouse=?, productGrade=?, ploughingTime=?, reapingTime=?, wareHouseID=? WHERE plot_id=?";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);

            // Set the updated values
            preparedStatement.setString(1, item.getProductName());
            preparedStatement.setInt(2, item.getPricePerKg());
            preparedStatement.setString(3, item.getLocation());
            preparedStatement.setString(4, item.getImageUrl());
            preparedStatement.setInt(5, item.getTotalQuantity());
            preparedStatement.setInt(6, item.getQuantitySold());
            preparedStatement.setInt(7, item.getQuantityLeft());
            preparedStatement.setString(8, item.getMovedToWareHouse());
            preparedStatement.setString(9, item.getProductGrade());
            preparedStatement.setString(10, item.getPloughingTime());
            preparedStatement.setString(11, item.getReapingTime());
            preparedStatement.setInt(12, item.getWareHouseID());
            preparedStatement.setInt(13, item.getPlot_id());

            // Execute the update
            preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }



    public int loginUser(String username,String password){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection
             conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryplus", "root", "root");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT userName, password FROM user WHERE userName=? AND password=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Authentication successful, a matching user was found in the database
                return 1;
            } else {
                // Authentication failed, no matching user found
                return 0;
            }
        } catch (
                SQLException e) {
            // Handle any database errors here
            e.printStackTrace();
            return -1; // Return an error code
        }
    }


    public List<Item> databaseSearchPlot(String query) {
        List<Item> response = new ArrayList<>();

        try {
            // Connect to your MySQL database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryplus", "root", "root");

            // Construct and execute the SQL query
            String sql = "SELECT * FROM plot WHERE plot_id LIKE ? OR productName LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet resultSet = stmt.executeQuery();

            // Fetch search results
            while (resultSet.next()) {
                // Create a new Item object for each row
                Item item = new Item();
                item.setPlot_id(resultSet.getInt("plot_id"));
                item.setProductName(resultSet.getString("productName"));
                item.setLocation(resultSet.getString("location"));
                item.setImageUrl(resultSet.getString("imageUrl"));
                item.setTotalQuantity(resultSet.getInt("totalQuantity"));
                item.setQuantitySold(resultSet.getInt("quantitySold"));
                item.setQuantityLeft(resultSet.getInt("quantityLeft"));
                item.setMovedToWareHouse(resultSet.getString("movedToWarehouse"));
                item.setProductGrade(resultSet.getString("productGrade"));
                item.setPloughingTime(resultSet.getString("ploughingTime"));
                item.setReapingTime(resultSet.getString("reapingTime"));
                item.setWareHouseID(resultSet.getInt("wareHouseId"));

                // Add the Item object to the response list
                response.add(item);
            }

            // Close the database connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;

    }

    public List<Item> wareHouseItems() throws SQLException {

        List<Item> response = new ArrayList<>();

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryplus", "root", "root");
        // SQL query to join the 'plot' and 'warehouse' tables and filter by 'movedToWareHouse'
        String sqlQuery = "SELECT\n" +
                "        p.plot_id,\n" +
                "        p.productName,\n" +
                "        p.pricePerKg,\n" +
                "        p.location,\n" +
                "        p.imageUrl,\n" +
                "        p.totalQuantity,\n" +
                "        p.quantitySold,\n" +
                "        p.quantityLeft,\n" +
                "        p.movedToWareHouse,\n" +
                "        p.productGrade,\n" +
                "        p.ploughingTime,\n" +
                "        p.reapingTime,\n" +
                "        p.wareHouseID,\n" +
                "        w.warehouseId,\n" +
                "        w.warehouseName\n" +
                "        FROM\n" +
                "        plot p\n" +
                "        JOIN\n" +
                "        warehouse w ON p.WareHouseId = w.warehouseId\n" +
                "        WHERE\n" +
                "        p.movedToWarehouse = 'yes';";
        PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            // Create a new Item object for each row
            Item item = new Item();
            item.setPlot_id(resultSet.getInt("plot_id"));
            System.out.println(resultSet.getString("wareHouseName"));
            item.setProductName(resultSet.getString("productName"));
            item.setLocation(resultSet.getString("location"));
            item.setQuantitySold(resultSet.getInt("quantitySold"));
            item.setProductGrade(resultSet.getString("productGrade"));
            item.setReapingTime(resultSet.getString("reapingTime"));
            item.setWareHouseID(resultSet.getInt("wareHouseId"));
            item.setWareHouseName(resultSet.getString("wareHouseName"));


            // Add the Item object to the response list
            response.add(item);
        }
        return response;
    }

    public void addusersQuery(User user) {
         List<User> users = new ArrayList<>();
        users.add(user);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryplus", "root", "root");
            String query = "Insert into user (userName, password,userType, salary) values(?, ?,?,?)";
            PreparedStatement preparedStatement;  preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setLong(2, user.getPassword());
            preparedStatement.setString(3, user.getUserType());
            preparedStatement.setLong(4, user.getSalary());
            preparedStatement.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
