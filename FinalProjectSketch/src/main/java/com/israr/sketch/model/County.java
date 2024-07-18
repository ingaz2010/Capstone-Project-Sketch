package com.israr.sketch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class County {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String State;

    private Long candidate1votes;
    private Long candidate2votes;
    private Long candidate3votes;
    private Long candidate4votes;
    private Long candidate5votes;
    private Long candidate6votes;

    @OneToMany(targetEntity = Voter.class, cascade = CascadeType.ALL)

    private List<Voter> voters = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(
//            name = "county_election",
//            joinColumns = @JoinColumn(name = "id"),
//            inverseJoinColumns = @JoinColumn(name = "election_id",referencedColumnName = "id")
//    )
//    private List<Election> elections = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "county_candidate",
            joinColumns = {@JoinColumn(name = "county_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "candidate_id", referencedColumnName = "id")}
    )
    private List<Candidate> candidates = new ArrayList<>();
}
