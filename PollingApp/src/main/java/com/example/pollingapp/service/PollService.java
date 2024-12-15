package com.example.pollingapp.service;

import com.example.pollingapp.dto.PollRequestDTO;
import com.example.pollingapp.dto.PollResponseDTO;
import com.example.pollingapp.dto.VoteDTO;
import com.example.pollingapp.entity.Option;
import com.example.pollingapp.entity.Poll;
import com.example.pollingapp.entity.Vote;
import com.example.pollingapp.repository.PollRepository;
import com.example.pollingapp.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    public PollResponseDTO createPoll(PollRequestDTO pollRequestDTO){
        Poll poll = new Poll();
        poll.setQuestion(pollRequestDTO.getQuestion());
        List<Option> options = pollRequestDTO.getOptions().stream()
                .map(optionText -> {
                    Option option = new Option();
                    option.setText(optionText);
                    return option;
                }).collect(Collectors.toList());
        poll.setOptions(options);
        Poll savedPoll = pollRepository.save(poll);
        return mapPollToPollResponseDTO(savedPoll);

    }

    public PollResponseDTO getPoll(Long id) {
        Poll poll = pollRepository.findById(id).orElse(null);
        if(poll == null){
            return null;
        }
        return mapPollToPollResponseDTO(poll);
    }

    public boolean submitVote(VoteDTO voteDTO, Long pollId) {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if(poll == null){
            return false;
        }
        Option selectedOption =  poll.getOptions().stream()
                .filter(option -> option.getId().equals(voteDTO.getOptionId()))
                .findFirst()
                .orElse(null);
        if(selectedOption == null){
            return false;
        }
        Vote vote = new Vote();
        vote.setOption(selectedOption);
        voteRepository.save(vote);
        return true;
    }

    private PollResponseDTO mapPollToPollResponseDTO(Poll poll){
        PollResponseDTO pollResponseDTO = new PollResponseDTO();
        pollResponseDTO.setId(poll.getId());
        pollResponseDTO.setQuestion(poll.getQuestion());
        pollResponseDTO.setOptions(poll.getOptions());
        return pollResponseDTO;
    }

}