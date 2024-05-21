package com.MinerApp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Item_Name")
    private String name;

    private int bonus;

    private int value;

    @ManyToOne
    @JoinColumn(name = "Dwarf")
    private Dwarf dwarf;

    @OneToMany(mappedBy = "item") //mapped by the specific field, not the type / class
    private List<Rune> runes;

}
