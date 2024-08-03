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
public class Association implements Comparable<Association> {

    @Setter
    private String name;
    @Setter
    private String email;
    @Setter
    private String website;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Builder
    public Association(String name, String email, String website) {
        this.name = name;
        this.email = email;
        this.website = website;
    }

    private static Map<Long, Association> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    @Override
    public int compareTo(Association o) {
        return this.name.compareTo(o.getName());
    }

    public void validate() {
        if(this.getName() == null || this.getName().length() == 0) {
            throw new IllegalArgumentException("Name can't be empty");
        }
        if(this.getEmail() == null || this.getEmail().length() == 0) {
            throw new IllegalArgumentException("Email can't be empty");
        }
        if(this.getWebsite() == null || this.getWebsite().length() == 0) {
            throw new IllegalArgumentException("Website can't be empty");
        }
    }

    public static Association add(Association association) {
        association.validate();
        association.setId(ID++);
        COLLECTION.put(association.getId(), association);
        return association;
    }

    public static Association remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Association does not exist with ID " + id);
        }
        Association association = COLLECTION.remove(id);
        return association;
    }

    public static Association get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Association does not exist with ID " + id);
        }
        Association association = COLLECTION.get(id);
        return association;
    }

    public static Collection<Association> get() {
        return COLLECTION.values();
    }

    public static Association editName(Long id, String name) {
        Association association = editHelper(id, "name", name);
        association.setName(name);
        COLLECTION.put(id, association);
        return association;
    }
    public static Association editEmail(Long id, String email) {
        Association association = editHelper(id, "email", email);
        association.setEmail(email);
        COLLECTION.put(id, association);
        return association;
    }

    public static Association editWebsite(Long id, String website) {
        Association association = editHelper(id, "website", website);
        association.setWebsite(website);
        COLLECTION.put(id, association);
        return association;
    }

    private static Association editHelper(Long id, String key, String value) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Association does not exist with ID " + id);
        }
        if(value == null || value.length() == 0) {
            throw new IllegalArgumentException(key + " can't be empty");
        }
        Association association = COLLECTION.get(id);
        return association;
    }

    public static void main(String[] args) {
        Association c1 = Association.builder().name("name 1").email("email1@verein.com").website("web1.verein.com").build();
        Association c2 = Association.builder().name("name 2").email("email2@verein.com").website("web2.verein.com").build();
        c1 = Association.add(c1);
        c2 = Association.add(c2);
        Association.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Association c3 = Association.builder().name("name 3").email("email3@verein.com").website("web3.verein.com").build();
        c3 = Association.add(c3);
        Association.editEmail(c1.getId(), "new@email.de");
        Association.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Association.remove(c2.getId());
        Association.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
