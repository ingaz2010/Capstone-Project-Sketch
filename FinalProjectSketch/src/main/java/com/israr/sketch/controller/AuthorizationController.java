package com.israr.sketch.controller;

import com.israr.sketch.dao.CandidateDao;
import com.israr.sketch.dao.CountyDao;
import com.israr.sketch.dao.ElectionDao;
import com.israr.sketch.dao.VoterDao;
import com.israr.sketch.dto.CandidateDto;
import com.israr.sketch.dto.ElectionDto;
import com.israr.sketch.dto.VoterDto;
import com.israr.sketch.model.*;
import com.israr.sketch.security.CustomVoterDetailsService;
import com.israr.sketch.service.VoteService;
import com.israr.sketch.service.impl.AdminUploadService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@Slf4j
public class AuthorizationController {
    private final VoterDao voterDao;
    private final CandidateDao candidateDao;
    private final ElectionDao electionDao;
   // private final AdminUploadService adminUploadService;
    private VoteService voteService;
   private CountyDao countyDao;
    private CustomVoterDetailsService customVoterDetailsService;
    private VoterChoice voterChoice;

    @Autowired
    public AuthorizationController(VoteService voteService, VoterDao voterDao, CandidateDao candidateDao, ElectionDao electionDao, CountyDao countyDao //, AdminUploadService adminUploadService
    ) {
        this.voteService = voteService;
        this.voterDao = voterDao;
        this.candidateDao = candidateDao;
        this.electionDao = electionDao;
        this.countyDao = countyDao;
       // this.adminUploadService = adminUploadService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        VoterDto voter = new VoterDto();
        model.addAttribute("voter", voter);
        return "register-voter";
    }

    @PostMapping("/register/save")
    public String registerVoter(@Valid @ModelAttribute("voter") VoterDto voter, BindingResult bindingResult, Model model) {
        Voter existing = voteService.findVoterByIdNumber(voter.getIdNumber());
        if (existing != null) {
            bindingResult.rejectValue("idNumber", null, "Account with this State Id already exists");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("voter", voter);
            return "register-voter";
        }
        voteService.saveVoter(voter);
        return "redirect:/register?success";
    }

    @GetMapping("/confirmInfo")
    public String confirmInfo( Model model, Principal p) {
        String username = p.getName();
        System.out.println("Username: " + username);
        Voter voterDto = voteService.findVoterByEmail(username);
        VoterDto existing = new VoterDto();
        existing.setFirstName(voterDto.getFirstName());
        existing.setLastName(voterDto.getLastName());
        existing.setEmail(voterDto.getEmail());
        existing.setPhone(voterDto.getPhone());
        existing.setDob(voterDto.getDob());
        existing.setGender(voterDto.getGender());
        existing.setAddress(voterDto.getAddress());
        existing.setCity(voterDto.getCity());
        existing.setState(voterDto.getState());
        existing.setZip(voterDto.getZip());
        existing.setCounty(voterDto.getCounty());
        existing.setParty(voterDto.getParty());
        model.addAttribute("voter", existing);
        return "confirmInfo";
    }

    @PutMapping("/confirmInfo/save")
    public String confirmAndSaveVoterChanges( Model model, @Valid @ModelAttribute("voter") VoterDto voterDto, Principal p){
        String username = p.getName();
        Voter existing = voteService.findVoterByEmail(username);
        voterDto.setId(existing.getId());

        System.out.println("going to update voter " + existing.getId());
//        existing.setFirstName(voterDto.getFirstName());
//        existing.setLastName(voterDto.getLastName());
//        existing.setEmail(voterDto.getEmail());
//        existing.setPhone(voterDto.getPhone());
//        existing.setDob(voterDto.getDob());
//        existing.setGender(voterDto.getGender());
//        existing.setAddress(voterDto.getAddress());
//        existing.setCity(voterDto.getCity());
//        existing.setState(voterDto.getState());
//        existing.setZip(voterDto.getZip());
//        existing.setCounty(voterDto.getCounty());
//        existing.setParty(voterDto.getParty());
//        voterDao.save(existing);
//        System.out.println("going to update voter " + existing.getIdNumber());
//        System.out.println("voterId: " + voterDto.getId());
        voteService.saveVoter(voterDto);
        log.info("Updating voter with ID={}", voterDto.getIdNumber());
        return "redirect:/confirmInfo?success";
    }

    @GetMapping("/registercandidate")
    public String registerCandidateForm(Model model) {
        CandidateDto candidate = new CandidateDto();
        //AdminUpload image = new AdminUpload();
        model.addAttribute("candidate", candidate);
        //model.addAttribute("image", image);
        return "register-candidate";
    }

    @PostMapping("/registercandidate/save")
    public String registerCandidate(@Valid @ModelAttribute("candidate") CandidateDto candidate, BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file, Model model) throws IOException {
        Candidate existing = voteService.findCandidateByName(candidate.getName());
        if (existing != null) {
            bindingResult.rejectValue("name", null, "Candidate with this Name already exists");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("candidate", candidate);
            return "register-candidate";
        }

//        AdminUpload image = new AdminUpload();
//        String fileName = file.getOriginalFilename();
//        image.setImageName(fileName);
//        image.setContent(file.getBytes());
//        image.setSize(file.getSize());
//        image.setCandidate(existing);
//        adminUploadService.createUser(image);
//        model.addAttribute("success", "fileUploaded successfully");
//        adminUploadService.saveImage(image);
        voteService.saveCandidate(candidate);
        return "redirect:/register-candidate?success";
    }

    @GetMapping("/set-election")
    public String saveElectionForm(@ModelAttribute String position, Model model) {

        Election election = new Election();

        model.addAttribute("election", election);
        return "set-election";
    }

    @PostMapping("/set-election/save")
    public String saveElection(@Valid @ModelAttribute("election") ElectionDto election, BindingResult bindingResult, Model model) {
        Election existing = null;
        List<Election> elections = electionDao.findByIsActive(election.getIsActive());
        for (Election e : elections) {
            if (e.getPosition() == election.getPosition()) {
                existing = e;
            }
        }
        if (existing != null) {
            bindingResult.rejectValue("name", null, "This section of the ballot already exists");
        }

        voteService.saveElection(election);
        return "redirect:/set-election?success";
    }

    @GetMapping("/election")
    public String electionForm(Model model) {
        Election current = null;
        List<Election> election = electionDao.findByIsActive(true);
        for (Election e : election) {
            if (e.getPosition().equals("President")) {
                current = e;
            }
        }
        System.out.println("Election for: " + current);
        model.addAttribute("election", election);

        List<Candidate> candidates = current.getCandidates();
        //List<Candidate> candidates = voteService.findCandidatesByRole("President");
        // Candidate candidates = voteService.findCandidateByName("Joe");

        System.out.println(candidates);
        model.addAttribute("candidates", candidates);

        return "election1";
    }


    @PostMapping("/election/save")
    public String election1(Model model, Election election, Principal p, Candidate candidate) {
        VoterChoice voterChoice = new VoterChoice();
        String username = p.getName();
        System.out.println("Username: " + username);
        Voter voter = voteService.findVoterByEmail(username);
        voterChoice.setVoter(voter);
        voterChoice.setElection(election);
        System.out.println("Got to this point");
        System.out.println("Voting: " + voter.getFirstName());
        if (!voter.isVoted()) {
            voter.setVoted(true);
            System.out.println(voter.getFirstName() + "Voted successfully");
            //Candidate candidate = voteService.getCandidateByName("name");
            System.out.println("For candidate: " + candidate);
            //Candidate c = voteService.getCandidateByName(name);
            CandidateDto candidateDto = new CandidateDto();
            Candidate candidate1 = voteService.findCandidateByName("name");
            System.out.println("Selected Candidate: " + candidate1);
            voterChoice.setCandidate(candidate);
            candidate.setVotes(candidate.getVotes()+1);
            String countyName = voter.getCounty();
            County county = countyDao.findByName(countyName);
            int candidateId = candidate.getId().intValue();
            switch(candidateId){
                case 1:
                    county.setCandidate1votes(county.getCandidate1votes() + 1);
                    break;
                case 2:
                    county.setCandidate2votes(county.getCandidate2votes() + 1);
                    break;
                case 3:
                    county.setCandidate3votes(county.getCandidate3votes() + 1);
                    break;
                case 4:
                    county.setCandidate4votes(county.getCandidate4votes() + 1);
                    break;
                case 5:
                    county.setCandidate5votes(county.getCandidate5votes() + 1);
                    break;
                case 6:
                    county.setCandidate6votes(county.getCandidate6votes() + 1);
                    break;
                default:
                    System.out.println("Invalid Voter Choice");
                    break;
            }

            voteService.saveVoterChoice(voterChoice);
            candidateDao.save(candidate);

            voterDao.save(voter);
        }
        return "redirect:/election?success";

    }

@GetMapping("/review")
    public String reviewForm(Model model, Principal p) {
    String username = p.getName();
    System.out.println("Username: " + username);
    Voter voter = voteService.findVoterByEmail(username);
    List<VoterChoice> selected = voteService.findVoterChoicesByVoterEmail(username);
    model.addAttribute("selected", selected);
    return "review";
}

@PutMapping("/review/save")
    public String submitReview(Model model, Principal p){
        String username = p.getName();
        Voter voter = voteService.findVoterByEmail(username);
        if(!voter.isVoted()){
            voter.setVoted(true);
        }
        VoterDto voterDto = new VoterDto();
        voterDto.setId(voter.getId());
        voter.setFirstName(voterDto.getFirstName());
        voterDto.setLastName(voter.getLastName());
        voterDto.setEmail(voter.getEmail());
        voterDto.setPhone(voter.getPhone());
        voterDto.setDob(voter.getDob());
        voterDto.setGender(voter.getGender());
        voterDto.setAddress(voter.getAddress());
        voterDto.setCity(voter.getCity());
        voterDto.setState(voter.getState());
        voterDto.setZip(voter.getZip());
        voterDto.setCounty(voter.getCounty());
        voterDto.setParty(voter.getParty());
//        System.out.println("going to update voter " + existing.getIdNumber());
//        System.out.println("voterId: " + voterDto.getId());
        voteService.saveVoter(voterDto);
        return "redirect:/review/save?success";
}

@GetMapping("candidates-info")
    public String viewCandidatesInfo(Model model){
        List<Candidate> candidates = candidateDao.findAll();
        model.addAttribute("candidates", candidates);
        return "candidateInfo";
}

    @GetMapping("/election2")
    public String electionForm2(Model model) {
        Election current = new Election();
        List<Election> election = electionDao.findByIsActive(true);
        for (Election e : election) {
            if (e.getPosition().equals("Senator")) {
                current = e;
            }
        }
        System.out.println("Election for: " + current);
        model.addAttribute("election", election);

        List<Candidate> candidates = current.getCandidates();
        //List<Candidate> candidates = voteService.findCandidatesByRole("President");
        // Candidate candidates = voteService.findCandidateByName("Joe");

        System.out.println(candidates);
        model.addAttribute("candidates", candidates);

        return "election2";
    }


    @PostMapping("/election2/save")
    public String election2(Model model, Election election, Principal p, Candidate candidate) {
        VoterChoice voterChoice = new VoterChoice();
        String username = p.getName();
        System.out.println("Username: " + username);
        Voter voter = voteService.findVoterByEmail(username);
        voterChoice.setVoter(voter);
        voterChoice.setElection(election);
        System.out.println("Got to this point");
        System.out.println("Voting: " + voter.getFirstName());
        if (!voter.isVoted()) {
            voter.setVoted(true);
            System.out.println(voter.getFirstName() + "Voted successfully");
            //Candidate candidate = voteService.getCandidateByName("name");
            System.out.println("For candidate: " + candidate);
            //Candidate c = voteService.getCandidateByName(name);
            CandidateDto candidateDto = new CandidateDto();
            Candidate candidate1 = voteService.findCandidateByName("name");
            System.out.println("Selected Candidate: " + candidate1);
            voterChoice.setCandidate(candidate);
            candidate.setVotes(candidate.getVotes()+1);
            voteService.saveVoterChoice(voterChoice);
            candidateDao.save(candidate);

            voterDao.save(voter);
        }
        return "redirect:/election2?success";

    }

    @GetMapping("/results")
    public String viewResults(Model model) {
        List<VoterChoice> voterChoices = voteService.findAllVoterChoices();
        List<Candidate> candidates = candidateDao.findAll();
        List<County> counties = countyDao.findAll();

        model.addAttribute("candidates", candidates);
        model.addAttribute("voterChoices", voterChoices);
        model.addAttribute("counties", counties);

        List<Long> countyVotes = new ArrayList<>();

        Map<Candidate, Long> votesForCandidate= new HashMap<>();
        for( County county : counties){
            for(Candidate candidate : county.getCandidates()) {
                votesForCandidate.put(candidate, voteService.getVotesByCandidate(candidate.getName()));
            }
        }
        model.addAttribute("countyVotes", votesForCandidate);



        return "viewResults";
    }

}