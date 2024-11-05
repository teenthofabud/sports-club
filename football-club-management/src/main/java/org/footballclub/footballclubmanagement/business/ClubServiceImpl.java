package org.footballclub.footballclubmanagement.business;

import org.footballclub.footballclubmanagement.converter.ClubMapper;
import org.footballclub.footballclubmanagement.dto.ClubDto;
import org.footballclub.footballclubmanagement.dto.PaginatedResponse;
import org.footballclub.footballclubmanagement.exceptions.ClubNotFoundException;
import org.footballclub.footballclubmanagement.model.ClubEntity;
import org.footballclub.footballclubmanagement.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {
    @Value("${feature.updateClubEnabled:false}")
    private boolean updateClubEnabled;

    @Value("${feature.getAllClubDetailsByFilter:false}")
    private boolean getAllClubDetailsByFilterEnabled;

    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    public ClubServiceImpl(ClubRepository clubRepository, ClubMapper clubMapper) {
        this.clubRepository = clubRepository;
        this.clubMapper = clubMapper;
    }

    @Override
    public ClubDto createClub(ClubDto clubDto) {
        ClubEntity clubEntity = clubMapper.convertDtoToEntity(clubDto);
        ClubEntity savedClubEntity = clubRepository.save(clubEntity);
        return clubMapper.convertEntityToDto(savedClubEntity);
    }

    @Override
    public ClubDto updateClub(ClubDto clubDto, Long id) {
        if (!updateClubEnabled) {
            throw new UnsupportedOperationException("Update operation is currently disabled");
        }
        ClubEntity findExistingId = clubRepository.findById(id).orElseThrow(() -> new ClubNotFoundException("User does not exist with id" + id));
        ClubEntity updateClub = findExistingId.toBuilder()
                                              .name(clubDto.getName())
                                              .email(clubDto.getEmail())
                                              .logoUrl(clubDto.getLogoUrl())
                                              .abbreviation(clubDto.getAbbreviation())
                                              .website(clubDto.getWebsite())
                                              .phoneNumber(clubDto.getPhoneNumber())
                                              .build();

        ClubEntity savedClub = clubRepository.save(updateClub);

        return clubMapper.convertEntityToDto(savedClub);
    }

    @Override
    public ClubDto getClubById(Long id) {
        ClubEntity findDetailsById = clubRepository.findById(id).orElseThrow(() -> new ClubNotFoundException("Club not found with id" + id));
        return clubMapper.convertEntityToDto(findDetailsById);
    }

    @Override
    public PaginatedResponse<ClubDto> getAllClubs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClubEntity> clubEntityPage = clubRepository.findAll(pageable);
        Page<ClubDto> clubDtoPage = clubEntityPage.map(
                club -> ClubDto.builder()
                        .email(club.getEmail())
                        .abbreviation(club.getAbbreviation())
                        .logoUrl(club.getLogoUrl())
                        .website(club.getWebsite())
                        .phoneNumber(club.getPhoneNumber())
                        .build());

        return getClubDtoPaginatedResponse(clubDtoPage);
    }

    private static PaginatedResponse<ClubDto> getClubDtoPaginatedResponse(Page<ClubDto> clubDtoPage) {
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
        if(!getAllClubDetailsByFilterEnabled){
            throw new UnsupportedOperationException("Get all club details by filter disabled");
        }
        if (clubDto == null) {
            throw new NullPointerException("Club filter cannot be null");
        }
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ClubEntity> filterClubs = clubRepository.findByFilter(clubDto.getName(),
                                                                       clubDto.getEmail(),
                                                                       clubDto.getAbbreviation(), pageable);
            List<ClubDto> clubDtoList = filterClubs.getContent().stream()
                                                   .map(clubMapper::convertEntityToDto)
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
