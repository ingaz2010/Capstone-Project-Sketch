package com.israr.sketch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "voters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    private String phone;

    @Column(nullable = false, unique = true)
    private String idNumber;
    @Column(nullable = false, unique = true)
    private String ssn;
    @Column(nullable = false)
    private String dob;
    private String gender;
    private String address;
    private String city;
   // @Column(nullable = false)
    private String state;
    private String zip;
   // @Column(nullable = false)
    private String county;
    private String party;

    private boolean voted = false;

    @ManyToOne
    private County countyObj;

//   @OneToMany(targetEntity = Candidate.class, cascade = CascadeType.ALL)
//    private List<Candidate> candidatesVotedFor;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
        joinColumns = {@JoinColumn(name = "voter_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "voter_election",
//            joinColumns = {@JoinColumn(name = "voter_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "election_id", referencedColumnName = "id")})
//    private List<Election> elections;

}
