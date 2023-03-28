package org.application.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.application.models.User;
import org.application.services.UserService;
import org.application.services.impl.UserServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    //private static final HikariConfig config ;
    private static HikariDataSource ds;
    //= new HikariConfig("src/main/resources/hikari.properties");
    static {
        //config= new HikariConfig("/hikari.properties");
        //ds = new HikariDataSource(config);
        try{
            initDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private static void initDataSource() throws NamingException {
        Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        ds = (HikariDataSource)envContext.lookup("jdbc/myDs");
    }

    public static Connection getConnection() throws SQLException{
        return ds.getConnection();
    }

    public static void closePool() {
        ds.close();
    }

    public static void main(String[] args) throws SQLException {
        try (Connection con = ds.getConnection()) {
            UserService userService = new UserServiceImpl();
            User user = userService.loginUser("admin", "admin");
            System.out.println(user);
        }
        finally {
            ConnectionPool.closePool();
        }
//        System.out.println("Food:");
//        List<Food> all2 = new FoodDAOImpl(con).getAll();
//        for (Food food : all2) {
//            System.out.println(food);
//        }
//
//
//        System.out.println("OrderFood:");
//        List<OrderFood> all3 = new OrderFoodDAOImpl(con).getAll();
//        for (OrderFood orderFood : all3) {
//            System.out.println(orderFood);
//            }
//
////        System.out.println("Orders:");
////        Order all4 = new OrderDAOImpl(con).get(1);
////
////        System.out.println(all4);
//
//
//        System.out.println("All orders:");
//        List<Order> all4 = new OrderDAOImpl(con).getAll();
//        for (Order order : all4) {
//            System.out.println(order);
//        }
//
//        System.out.println("Users:");
//        List<User> all = new UserDAOImpl(con).getAll();
//        for (User usr : all) {
//            System.out.println(usr);
//        }
//
//        UserDAO userDAO = new UserDAOImpl(con);
//        User user1 = userDAO.getByLogin("johnchef");
//        user1.setEmail("john111@gmail.com");
//        userDAO.update(user1);
//        System.out.println("Users:");
//
//        List<User> all5 = new UserDAOImpl(con).getAll();
//        for (User usr : all5) {
//            System.out.println(usr);
//        }
//
//        UserDAOImpl userDAOImpl2 = new UserDAOImpl(con);
//        User user2 = userDAOImpl2.get(6);
//        user2.setEmail("john1112222@gmail.com");
//        userDAOImpl2.update(user2);
//        System.out.println("Users:");
//
//        List<User> all6 = new UserDAOImpl(con).getAll();
//        for (User usr : all6) {
//            System.out.println(usr);
//        }
//        } finally {
//                ConnectionPool.closePool();
//        }
    }
}
