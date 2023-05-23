package org.application.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "`order`")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "total")
    private double orderTotal;
    @ManyToOne
    @JoinColumn(name = "waiter_id")
    private User waiter;
    @ManyToOne
    @JoinColumn(name = "chef_id")
    private User chef;
    @OneToMany
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private List<OrderFood> orderFoods;
}