package org.footballclub.footballclubmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id", foreignKey = @ForeignKey(name = "fk_stadium_address"))
    private AddressEntity address;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="club_id", foreignKey = @ForeignKey(name = "fk_stadium_club"))
    private ClubEntity club;

    public StadiumEntity(String name, String type){
        this.name = name;
        this.type = type;
    }
}
