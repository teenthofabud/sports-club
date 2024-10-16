package org.footballclub.footballclubmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String type;
}
