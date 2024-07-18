package com.israr.sketch.dao;

import com.israr.sketch.model.Candidate;
import com.israr.sketch.model.VoterChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoterChoiceDao extends JpaRepository<VoterChoice, Long> {
    //Long countDistinctByCandidateSelected(Candidate candidateSelected);
   // @Query("SELECT COUNT(*) FROM voter_choice inner join voters v on voter_choice.voter_id = voters.id where candidate_selected_candidate_id =? and v.county=?")
   // Long countCandidateVotesInEachCounty(Long candidateId, String county);
   // Long countByCandidate(Candidate candidate);
}
