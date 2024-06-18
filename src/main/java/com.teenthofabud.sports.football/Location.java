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
public class Location implements Comparable<Location> {

    @Setter
    private String name;
    @Setter
    private String address;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Builder
    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    private static Map<Long, Location> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    @Override
    public int compareTo(Location o) {
        return this.name.compareTo(o.getName());
    }

    public void validate() {
        if(this.getName() == null || this.getName().length() == 0) {
            throw new IllegalArgumentException("Name can't be empty");
        }
        if(this.getAddress() == null || this.getAddress().length() == 0) {
            throw new IllegalArgumentException("Address can't be empty");
        }
    }

    public static Location add(Location player) {
        player.validate();
        player.setId(ID++);
        COLLECTION.put(player.getId(), player);
        return player;
    }

    public static Location remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Location does not exist with ID " + id);
        }
        Location player = COLLECTION.remove(id);
        return player;
    }

    public static Location get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Location does not exist with ID " + id);
        }
        Location player = COLLECTION.get(id);
        return player;
    }

    public static Collection<Location> get() {
        return COLLECTION.values();
    }

    public static Location editName(Long id, String name) {
        Location player = editHelper(id, "name", name);
        player.setName(name);
        COLLECTION.put(id, player);
        return player;
    }

    public static Location editAddress(Long id, String address) {
        Location player = editHelper(id, "address", address);
        player.setAddress(address);
        COLLECTION.put(id, player);
        return player;
    }

    private static Location editHelper(Long id, String key, String value) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Location does not exist with ID " + id);
        }
        if(value == null || value.length() == 0) {
            throw new IllegalArgumentException(key + " can't be empty");
        }
        Location player = COLLECTION.get(id);
        return player;
    }

    public static void main(String[] args) {
        Location l1 = Location.builder().name("location 1").address("address 1").build();
        Location l2 = Location.builder().name("location 2").address("address 2").build();
        l1 = Location.add(l1);
        l2 = Location.add(l2);
        Location.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Location.editAddress(l1.getId(), "new address 1");
        Location.editName(l2.getId(), "new location 2");
        Location.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Location.remove(l2.getId());
        Location.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
