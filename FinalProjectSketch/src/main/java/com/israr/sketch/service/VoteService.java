package com.israr.sketch.service;

import com.israr.sketch.dto.CandidateDto;
import com.israr.sketch.dto.ElectionDto;
import com.israr.sketch.dto.VoterDto;
import com.israr.sketch.model.Candidate;
import com.israr.sketch.model.Voter;
import com.israr.sketch.model.VoterChoice;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface VoteService {

    void saveVoter(VoterDto voterDto);
    void saveCandidate(CandidateDto candidateDto);
    Voter findVoterByIdNumber(String idNumber);
    Voter findVoterByEmail(String email);
    Candidate findCandidateByName(String name);
    List<Candidate> findCandidatesByRole(String candidateRole);

    void saveElection(ElectionDto election);

    void vote(Long id, HttpSession session);

    Candidate getCandidateByName(String name);

    Voter findVoterById(Long voterId);

    void saveVoterChoice(VoterChoice voterChoice);

    List<VoterChoice> findVoterChoicesByVoterEmail(String username);

    List<VoterChoice> findAllVoterChoices();

    Long getVotesByCandidate(String name);
    //void voteFor(Long id);
    //Long countPopularVotesForCandidate(Long candidateId);
    // Long countVoterForCandidateEachCounty(Long candidateId, String countyName); */
}
