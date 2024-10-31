package org.footballclub.footballclubmanagement.converter;

import org.footballclub.footballclubmanagement.dto.ClubDto;
import org.footballclub.footballclubmanagement.model.ClubEntity;
import org.springframework.stereotype.Component;

@Component
public class ClubConverter {
    public ClubEntity convertDtoToEntity(ClubDto clubDto){
        if(clubDto == null){
            throw new NullPointerException("Club dto cannot be null");
        }
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setName(clubDto.getName());
        clubEntity.setEmail(clubDto.getEmail());
        clubEntity.setPhoneNumber(clubDto.getPhoneNumber());
        clubEntity.setLogoUrl(clubDto.getLogoUrl());
        clubEntity.setWebsite(clubDto.getWebsite());
        clubEntity.setAbbreviation(clubDto.getAbbreviation());

        return clubEntity;
    }
    public ClubDto convertEntityToDto(ClubEntity clubEntity){
        if(clubEntity == null){
            throw new NullPointerException("Club entity cannot be null");
        }
        ClubDto clubDto = new ClubDto();
        clubDto.setName(clubEntity.getName());
        clubDto.setEmail(clubEntity.getEmail());
        clubDto.setPhoneNumber(clubEntity.getPhoneNumber());
        clubDto.setLogoUrl(clubEntity.getLogoUrl());
        clubDto.setWebsite(clubEntity.getWebsite());
        clubDto.setAbbreviation(clubEntity.getAbbreviation());

        return clubDto;
    }
}
