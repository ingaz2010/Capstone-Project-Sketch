package com.israr.sketch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String position;

    @Column(name = "is_active")
    private Boolean isActive;
    private String year;
//    private int votesNumber1;
//    private int votesNumber2;
//    private int votesNumber3;

//    @OneToMany(targetEntity = Voter.class, cascade = CascadeType.ALL)
//    private List<Voter> votesForCandiate1;
//
//    @OneToMany(targetEntity = Voter.class, cascade = CascadeType.ALL)
//    private List<Voter> votesForCandiate2;
//
//    @OneToMany(targetEntity = Voter.class, cascade = CascadeType.ALL)
//    private List<Voter> votesForCandiate3;

    @OneToMany(targetEntity = Candidate.class, cascade = CascadeType.ALL)
    private List<Candidate> candidates;

    @ManyToMany
    private List<County> counties;

//    @OneToMany(targetEntity = Voter.class, cascade = CascadeType.ALL)
//    @ManyToMany(mappedBy = "elections")
//   private List<Voter> voters;
}
