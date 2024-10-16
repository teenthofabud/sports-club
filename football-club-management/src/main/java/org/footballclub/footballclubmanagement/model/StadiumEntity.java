package org.footballclub.footballclubmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="stadium")
public class StadiumEntity {
    @Id
    @GeneratedValue
    @Column(name="stadium_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name="type")
    private String type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id")
    private AddressEntity address;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="club_id")
    private ClubEntity club;

    public StadiumEntity(String name, String type){
        this.name = name;
        this.type = type;
    }
}
