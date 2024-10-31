package org.footballclub.footballclubmanagement.business;

import org.footballclub.footballclubmanagement.converter.ClubConverter;
import org.footballclub.footballclubmanagement.dto.ClubDto;
import org.footballclub.footballclubmanagement.dto.PaginatedResponse;
import org.footballclub.footballclubmanagement.exceptions.ClubDetailsValidationExceptions;
import org.footballclub.footballclubmanagement.exceptions.ClubNotFoundException;
import org.footballclub.footballclubmanagement.model.ClubEntity;
import org.footballclub.footballclubmanagement.repository.ClubRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;
    private final ClubConverter clubConverter;

    public ClubServiceImpl(ClubRepository clubRepository, ClubConverter clubConverter) {
        this.clubRepository = clubRepository;
        this.clubConverter = clubConverter;
    }

    @Override
    public ClubDto createClub(ClubDto clubDto) {
        if (clubDto == null) {
            throw new NullPointerException("CLub details cannot be null");
        }
        ClubEntity clubEntity = clubConverter.convertDtoToEntity(clubDto);
        ClubEntity savedClubEntity = clubRepository.save(clubEntity);
        return clubConverter.convertEntityToDto(savedClubEntity);
    }

    @Override
    public ClubDto updateClub(ClubDto clubDto, Long id) {
        ClubEntity findExistingId = clubRepository.findById(id).orElseThrow(() -> new ClubNotFoundException("User does not exist with id" + id));
        findExistingId.setName(clubDto.getName());
        findExistingId.setEmail(clubDto.getEmail());
        findExistingId.setLogoUrl(clubDto.getLogoUrl());
        findExistingId.setAbbreviation(clubDto.getAbbreviation());
        findExistingId.setWebsite(clubDto.getWebsite());
        findExistingId.setPhoneNumber(clubDto.getPhoneNumber());
        ClubEntity updatedClub = clubRepository.save(findExistingId);

        return clubConverter.convertEntityToDto(updatedClub);
    }

    @Override
    public ClubDto getClubById(Long id) {
        ClubEntity findDetailsById = clubRepository.findById(id).orElseThrow(() -> new ClubNotFoundException("Club not found with id" + id));
        return clubConverter.convertEntityToDto(findDetailsById);
    }

    @Override
    public PaginatedResponse<ClubDto> getAllClubs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClubEntity> clubEntityPage = clubRepository.findAll(pageable);
        Page<ClubDto> clubDtoPage = clubEntityPage.map(
                club -> new ClubDto(club.getName(),
                                    club.getEmail(),
                                    club.getAbbreviation(),
                                    club.getLogoUrl(),
                                    club.getWebsite(),
                                    club.getPhoneNumber()
                ));

        return new PaginatedResponse<>(
                clubDtoPage.getContent(),
                clubDtoPage.getNumber(),
                clubDtoPage.getTotalPages(),
                clubDtoPage.getTotalElements(),
                clubDtoPage.getSize()
        );
    }

    @Override
    public PaginatedResponse<ClubDto> getClubByFilter(ClubDto clubDto, int page, int size) {
        if (clubDto == null) {
            throw new NullPointerException("Club filter cannot be null");
        }
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ClubEntity> filterClubs = clubRepository.findByFilter(clubDto.getName(),
                                                                       clubDto.getEmail(),
                                                                       clubDto.getAbbreviation(), pageable);
            List<ClubDto> clubDtoList = filterClubs.getContent().stream()
                                                   .map(clubConverter::convertEntityToDto)
                                                   .collect(Collectors.toList());
            return new PaginatedResponse<>(
                    clubDtoList,
                    filterClubs.getNumber(),
                    filterClubs.getTotalPages(),
                    filterClubs.getTotalElements(),
                    filterClubs.getSize());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching filtered clubs");
        }
    }

}
