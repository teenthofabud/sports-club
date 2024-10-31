package org.footballclub.footballclubmanagement.business;

import jakarta.validation.Valid;
import org.footballclub.footballclubmanagement.dto.ClubDto;
import org.footballclub.footballclubmanagement.dto.PaginatedResponse;

public interface ClubService {
    ClubDto createClub(@Valid ClubDto clubDto);
    ClubDto updateClub(ClubDto clubDto,Long id);
    ClubDto getClubById(Long d);
    PaginatedResponse<ClubDto> getAllClubs(int page, int size);
    PaginatedResponse<ClubDto> getClubByFilter(ClubDto clubDto, int page, int size);

}
