package org.footballclub.footballclubmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubDto {
    private String name;
    private String email;
    private String abbreviation;
    private String phoneNumber;
    private String logoUrl;
    private String website;
}
