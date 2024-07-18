package com.israr.sketch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "uploaded-images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageName;
    private Long size;
    private byte[] content;

//    @OneToOne
//    private Candidate candidate;
}
