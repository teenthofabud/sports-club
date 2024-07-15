package com.teenthofabud.sports.football;

import lombok.*;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Player implements Comparable<Player> {

    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String email;
    @Setter
    private String phoneNumber;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Builder
    public Player(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    private static Map<Long, Player> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    @Override
    public int compareTo(Player o) {
        return this.phoneNumber.compareTo(o.getPhoneNumber());
    }

    public void validate() {
        if(this.getFirstName() == null || this.getFirstName().length() == 0) {
            throw new IllegalArgumentException("Name can't be empty");
        }
        if(this.getEmail() == null || this.getEmail().length() == 0) {
            throw new IllegalArgumentException("Email can't be empty");
        }
        if(this.getLastName() == null || this.getLastName().length() == 0) {
            throw new IllegalArgumentException("Website can't be empty");
        }
    }

    public static Player add(Player player) {
        player.validate();
        player.setId(ID++);
        COLLECTION.put(player.getId(), player);
        return player;
    }

    public static Player remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Player does not exist with ID " + id);
        }
        Player player = COLLECTION.remove(id);
        return player;
    }

    public static Player get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Player does not exist with ID " + id);
        }
        Player player = COLLECTION.get(id);
        return player;
    }

    public static Collection<Player> get() {
        return COLLECTION.values();
    }

    public static Player editFirstName(Long id, String firstName) {
        Player player = editHelper(id, "firstName", firstName);
        player.setFirstName(firstName);
        COLLECTION.put(id, player);
        return player;
    }

    public static Player editLastName(Long id, String lastName) {
        Player player = editHelper(id, "lastName", lastName);
        player.setLastName(lastName);
        COLLECTION.put(id, player);
        return player;
    }

    public static Player editEmail(Long id, String email) {
        Player player = editHelper(id, "email", email);
        player.setEmail(email);
        COLLECTION.put(id, player);
        return player;
    }

    public static Player editPhoneNumber(Long id, String phoneNumber) {
        Player player = editHelper(id, "phoneNumber", phoneNumber);
        player.setPhoneNumber(phoneNumber);
        COLLECTION.put(id, player);
        return player;
    }
    public static void resetPlayerCollection(){
        COLLECTION.clear();
        ID = 1L;
    }

    private static Player editHelper(Long id, String key, String value) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Player does not exist with ID " + id);
        }
        if(value == null || value.length() == 0) {
            throw new IllegalArgumentException(key + " can't be empty");
        }
        Player player = COLLECTION.get(id);
        return player;
    }

    public static void main(String[] args) {
        Player p1 = Player.builder().firstName("first name 1").email("email1@verein.com").lastName("last name 1").phoneNumber("1234").build();
        Player p2 = Player.builder().firstName("first name 2").email("email2@verein.com").lastName("last name 2").phoneNumber("4567").build();
        p1 = Player.add(p1);
        p2 = Player.add(p2);
        Player.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Player p3 = Player.builder().firstName("first name 3").email("email3@verein.com").lastName("last name 3").phoneNumber("9876").build();
        p3 = Player.add(p3);
        Player.editEmail(p1.getId(), "new@email.de");
        Player.editPhoneNumber(p2.getId(), "9999");
        Player.editFirstName(p2.getId(), "new first name 2");
        Player.editLastName(p3.getId(), "new last name 3");
        Player.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Player.remove(p2.getId());
        Player.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
