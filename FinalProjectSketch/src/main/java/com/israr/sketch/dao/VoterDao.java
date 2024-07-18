package com.israr.sketch.dao;

import com.israr.sketch.model.County;
import com.israr.sketch.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterDao extends JpaRepository<Voter, Long> {

    Voter findByEmail(String email);
    Voter findByIdNumber(String idNumber);
    Voter findBySsn(String ssn);

}
