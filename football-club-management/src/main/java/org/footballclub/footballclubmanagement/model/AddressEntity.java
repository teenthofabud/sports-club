package org.footballclub.footballclubmanagement.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    private String state;

    private String zip;

    private String type;

    @OneToOne(mappedBy = "address" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StadiumEntity stadium;

    @OneToOne(mappedBy = "address", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private ClubEntity club;

    public AddressEntity(String street,String city, String state,String zip,String type){
        this.street =street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.type = type;
    }

}
