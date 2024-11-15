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
    public PaginatedResponse<ClubDto> getClubs(ClubDto filter, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ClubEntity> clubEntityPage;
            if (filter != null) {
                clubEntityPage = clubRepository.findByFilter(
                        filter.getName(),
                        filter.getEmail(),
                        filter.getAbbreviation(),
                        pageable);

            } else {
                clubEntityPage = clubRepository.findAll(pageable);
            }
            Page<ClubDto> clubDtoPage = clubEntityPage.map(clubMapper::convertEntityToDto);
            return getClubDtoPaginatedResponse(clubDtoPage);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching clubs",e);
        }
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


}
