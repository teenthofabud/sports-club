package org.footballclub.footballclubmanagement.converter;

import com.googlecode.jmapper.JMapper;
import org.footballclub.footballclubmanagement.dto.ClubDto;
import org.footballclub.footballclubmanagement.model.ClubEntity;
import org.springframework.stereotype.Component;

@Component
public class ClubMapper {
    private static final JMapper<ClubDto, ClubEntity> mapperToDto = new JMapper<>(ClubDto.class,ClubEntity.class);
    private static final JMapper<ClubEntity, ClubDto> mapperToEntity = new JMapper<>(ClubEntity.class, ClubDto.class);

    public ClubDto convertEntityToDto(ClubEntity clubEntity){
        return mapperToDto.getDestination(clubEntity);
    }
    public ClubEntity convertDtoToEntity(ClubDto clubDto){
        return mapperToEntity.getDestination(clubDto);
    }
}
