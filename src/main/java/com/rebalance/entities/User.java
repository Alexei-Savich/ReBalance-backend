package com.rebalance.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user")
    private Set<UserGroup> groups = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user")
    private Set<Expense> expenses;
}