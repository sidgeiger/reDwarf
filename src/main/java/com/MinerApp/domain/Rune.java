package com.MinerApp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Runes")
@Data
@NoArgsConstructor
public class Rune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Rune_Name")
    private String name;

    private int bonus;

    private boolean banned;

    @ManyToOne
    @JoinColumn(name = "Item")
    private Item item;

}
