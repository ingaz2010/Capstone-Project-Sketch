package com.israr.sketch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private String party;
    private String description;
    //private String year;

    private long votes = 0;

//    @OneToOne
//    private AdminUpload image;

    @ManyToMany(mappedBy = "candidates")
    private Set<County> countiesRepresents;

    @OneToOne
    private AdminUpload image;
}
