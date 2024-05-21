package com.MinerApp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Dwarf")
@Data
@NoArgsConstructor
public class Dwarf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Dwarf_Name", unique = true)
    private String name;

    private int productivity;

    @OneToMany(mappedBy = "dwarf") //mapped by the specific field, not the type / class
    private List<Item> items;
}
