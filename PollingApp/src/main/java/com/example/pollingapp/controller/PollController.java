package com.example.pollingapp.controller;

import com.example.pollingapp.dto.PollRequestDTO;
import com.example.pollingapp.dto.PollResponseDTO;
import com.example.pollingapp.dto.VoteDTO;
import com.example.pollingapp.service.PollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/polls")
public class PollController {
    @Autowired
    private PollService pollService;

    @PostMapping
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequestDTO pollRequestDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
        }
        PollResponseDTO pollResponseDTO =  pollService.createPoll(pollRequestDTO);
        return new ResponseEntity<>(pollResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollResponseDTO> getPoll(@PathVariable Long id){
        PollResponseDTO pollResponseDTO = pollService.getPoll(id);
        if(pollResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pollResponseDTO,HttpStatus.OK);
    }

    @PostMapping("/{pollId}/vote")
    public ResponseEntity<?> submitVote(@Valid @RequestBody VoteDTO voteDTO, @PathVariable Long pollId, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        boolean isVoted = pollService.submitVote(voteDTO,pollId);
        if(isVoted){
            return new ResponseEntity<>("Voted!",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error submitting vote",HttpStatus.BAD_REQUEST);
    }

}