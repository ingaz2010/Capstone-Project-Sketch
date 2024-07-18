package com.israr.sketch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class VoterChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    Voter voter;

    @OneToOne
    Election election;

    @OneToOne
    @JoinColumn(name="candidate_id")
    Candidate candidate;

    @OneToOne
    County county;
}
