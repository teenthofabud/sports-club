package org.footballclub.footballclubmanagement.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="club")
public class ClubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="club_id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="abbreviation")
    private String abbreviation;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name="logo_url")
    private String logoUrl;
    @Column(name="website")
    private String website;

    @OneToMany(mappedBy = "club",fetch=FetchType.LAZY,cascade= CascadeType.ALL)
    private List<StadiumEntity> stadium;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id")
    private AddressEntity address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private LevelEntity level;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="management_id")
    private ManagementEntity management;

    public ClubEntity(String name, String email, String abbreviation, String phoneNumber, String logoUrl, String website){
        this.name= name;
        this.email = email;
        this.abbreviation= abbreviation;
        this.phoneNumber = phoneNumber;
        this.logoUrl= logoUrl;
        this.website=website;
    }

}
