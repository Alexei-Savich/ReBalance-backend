package com.rebalance.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@Table
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private Double amount;

    @Column
    private String description;

    @Column
    private LocalDate date;

    @Column
    private String category;

    @Column
    private Long globalId;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "expense")
    private Set<ExpenseUsers> expenseUsers;
}