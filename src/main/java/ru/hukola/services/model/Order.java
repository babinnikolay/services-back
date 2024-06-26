package ru.hukola.services.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author Babin Nikolay
 */
@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(name = "order_date")
    @NotNull
    private Date date;
    @NotNull
    @ManyToOne(cascade=CascadeType.MERGE)
    private Client client;
    private String description;
    private float amount;
    private boolean paid;
    @ManyToOne
    private User user;
}
