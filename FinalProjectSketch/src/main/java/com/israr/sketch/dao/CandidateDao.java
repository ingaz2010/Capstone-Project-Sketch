package com.israr.sketch.dao;

import com.israr.sketch.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateDao extends JpaRepository<Candidate, Long> {
    List<Candidate> findByRole(String candidateRole);
    Candidate findById(long id);

    Candidate findByName(String name);
}
