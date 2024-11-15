package org.footballclub.footballclubmanagement.business;

import org.footballclub.footballclubmanagement.dto.ClubDto;
import org.footballclub.footballclubmanagement.dto.PaginatedResponse;

public interface ClubService {
    ClubDto createClub(ClubDto clubDto);

    ClubDto updateClub(ClubDto clubDto,Long id);
    ClubDto getClubById(Long d);
    PaginatedResponse<ClubDto> getClubs(ClubDto filter,int page, int size);

}
