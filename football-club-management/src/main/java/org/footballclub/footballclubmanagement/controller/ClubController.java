package org.footballclub.footballclubmanagement.controller;

import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.footballclub.footballclubmanagement.business.ClubService;
import org.footballclub.footballclubmanagement.dto.ClubDto;
import org.footballclub.footballclubmanagement.dto.PaginatedResponse;
import org.footballclub.footballclubmanagement.repository.ClubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/club")
public class ClubController {
    private final ClubService clubService;
    private final ClubRepository clubRepository;

    public ClubController(ClubService clubService, ClubRepository clubRepository) {
        this.clubService = clubService;
        this.clubRepository = clubRepository;
    }

    @PostMapping("/createClub")
    public ResponseEntity<String> createClub(@RequestBody ClubDto clubDto) {
        log.info("Creating club: {}", clubDto);

        clubService.createClub(clubDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("CLub created successfully");

    }

    @PutMapping("/updateClub/{id}")
    public ResponseEntity<String> updateClub(@PathVariable Long id, @RequestBody ClubDto clubDto) {
        log.info("Updating club:{}", clubDto);
        clubService.updateClub(clubDto, id);
        return ResponseEntity.status(HttpStatus.OK).body("Club details updated successfully");
    }

    @GetMapping("/getClubById/{id}")
    public ResponseEntity<ClubDto> getClubById(@PathVariable Long id) {
        ClubDto club = clubService.getClubById(id);
        return ResponseEntity.ok(club);
    }

    @GetMapping("/getAllClubs")
    public ResponseEntity<PaginatedResponse<ClubDto>> getAllClubs(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        PaginatedResponse<ClubDto> response = clubService.getAllClubs(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/searchClubs")
    public ResponseEntity<PaginatedResponse<ClubDto>> searchClubs(@RequestBody ClubDto clubDto,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size) {
        PaginatedResponse<ClubDto> response = clubService.getClubByFilter(clubDto,page,size);
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

}
