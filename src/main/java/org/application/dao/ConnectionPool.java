package org.application.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.application.dao.impl.*;
import org.application.models.*;

import java.util.List;

public class ConnectionPool {
//    private static final HikariConfig config;
//    private static final HikariDataSource ds;
//
//    static {
//        config = new HikariConfig("src/main/resources/hikari.properties");
//        ds = new HikariDataSource(config);
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return ds.getConnection();
//    }
//
//    public static void closePool() {
//        ds.close();
//    }

    public static void main(String[] args){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Restaurant");
            EntityManager em=emf.createEntityManager();
            em.getTransaction().begin();

            User user = new UserDAOImpl(em).get(6);
            System.out.println(user);
            System.out.println("-----------------");

            Order order = new OrderDAOImpl(em).get(1);
            System.out.println(order);
            System.out.println("-----------------");

            order.setChef(null);
            new OrderDAOImpl(em).update(order);
            System.out.println(order);
            System.out.println("-----------------");

            List<OrderFood> orderFood = new OrderFoodDAOImpl(em).getByOrderId(1);
            System.out.println(orderFood);
            System.out.println("-----------------");

            Role role = new RoleDAOImpl(em).get(8);
            System.out.println(role);
            System.out.println("-----------------");

            Food food = new FoodDAOImpl(em).get(2);
            System.out.println(food);
            System.out.println("-----------------");
//            user.setLogin("user1");
//            new UserDAOImpl(em).update(user);
//            new UserDAOImpl(em).delete(27);

//            User user1 = new User();
//            user1.setLogin("user3");
//            user1.setPassword("user2");
//            user1.setEmail("user3@gmail.com");
//            user1.setFirstName("user2");
//            user1.setLastName("user2");
//            user1.setSalary(0.0);
//            user1.setRole(new RoleDAOImpl(em).get(8));
//            new UserDAOImpl(em).create(user1);
            em.getTransaction().commit();
            em.close();
            emf.close();
     }
}
