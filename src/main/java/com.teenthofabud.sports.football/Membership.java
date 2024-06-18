package com.teenthofabud.sports.football;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Membership implements Comparable<Membership> {

    @Override
    public int compareTo(Membership o) {
        return Integer.compare(this.association.compareTo(o.getAssociation()), this.player.compareTo(o.getPlayer()));
    }

    public static enum MembershipType {
        MEMBER,
        GUEST;
    }

    @Setter
    private Association association;
    @Setter
    private Player player;
    @Setter
    private MembershipType nature;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Builder
    public Membership(Association association, Player player, MembershipType nature) {
        this.association = association;
        this.player = player;
        this.nature = nature;
    }

    private static Map<Long, Membership> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    public void validate() {
        if(this.getAssociation() == null) {
            throw new IllegalArgumentException("Association can't be empty");
        } else {
            try {
                this.getAssociation().validate();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Association is invalid");
            }
        }
        if(this.getPlayer() == null) {
            throw new IllegalArgumentException("Player can't be empty");
        } else {
            try {
                this.getPlayer().validate();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Player is invalid");
            }
        }
        if(this.getNature() == null) {
            throw new IllegalArgumentException("Nature can't be empty");
        }
    }

    public static Membership add(Membership membership) {
        membership.validate();
        membership.setId(ID++);
        COLLECTION.put(membership.getId(), membership);
        return membership;
    }

    public static Membership remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Membership does not exist with ID " + id);
        }
        Membership membership = COLLECTION.remove(id);
        return membership;
    }

    public static Membership get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Membership does not exist with ID " + id);
        }
        Membership membership = COLLECTION.get(id);
        return membership;
    }

    public static Collection<Membership> get() {
        return COLLECTION.values();
    }

    public static Membership editNature(Long id, String nature) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Membership does not exist with ID " + id);
        }
        MembershipType membershipType = null;
        try {
            membershipType = MembershipType.valueOf(nature);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Membership not supported");
        }
        Membership membership = COLLECTION.get(id);
        membership.setNature(membershipType);
        COLLECTION.put(id, membership);
        return membership;
    }

    public static void main(String[] args) {
        Association c1 = Association.builder().name("name 1").email("email1@verein.com").website("web1.verein.com").build();
        Association c2 = Association.builder().name("name 2").email("email2@verein.com").website("web2.verein.com").build();
        c1 = Association.add(c1);
        c2 = Association.add(c2);
        Player p1 = Player.builder().firstName("first name 1").email("email1@verein.com").lastName("last name 1").phoneNumber("1234").build();
        Player p2 = Player.builder().firstName("first name 2").email("email2@verein.com").lastName("last name 2").phoneNumber("4567").build();
        Player p3 = Player.builder().firstName("first name 3").email("email3@verein.com").lastName("last name 3").phoneNumber("9876").build();
        p1 = Player.add(p1);
        p2 = Player.add(p2);
        p3 = Player.add(p3);
        Membership m1 = Membership.builder().association(c1).player(p2).nature(MembershipType.MEMBER).build();
        Membership m2 = Membership.builder().association(c1).player(p3).nature(MembershipType.MEMBER).build();
        Membership m3 = Membership.builder().association(c2).player(p1).nature(MembershipType.MEMBER).build();
        Membership.add(m1);
        Membership.add(m2);
        Membership.add(m3);
        Membership.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Membership.editNature(m2.getId(), MembershipType.GUEST.name());
        Membership.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Membership.remove(m3.getId());
        Membership.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
