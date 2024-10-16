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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "management")
public class ManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="management_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name="contact")
    private String contact;


    @OneToOne(mappedBy = "management", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ClubEntity club;

    public ManagementEntity(String name,String contact){
        this.name =name;
        this.contact = contact;
    }

}
