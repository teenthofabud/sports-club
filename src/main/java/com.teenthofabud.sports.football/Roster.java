package com.teenthofabud.sports.football;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.teenthofabud.sports.football.Schedule.TIME_FORMATTER;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Roster implements Comparable<Roster> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    //private static final Integer MIN_PLAYERS = 10;
    private static final Integer MIN_PLAYERS = 3;
    private static final Integer MAX_PLAYERS = 22;

    @Setter
    private Schedule schedule;
    @Setter(AccessLevel.PRIVATE)
    private List<Membership> members;
    @Setter
    private Membership bibs;
    @Setter
    private Membership ball;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private LocalDate date;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private AtomicInteger totalMembers;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Override
    public int compareTo(Roster o) {
        return Integer.compare(this.schedule.compareTo(o.getSchedule()), this.date.compareTo(o.getDate()));
    }

    @Builder
    public Roster(Schedule schedule, List<Membership> members, Membership bibs, Membership ball) {
        this.schedule = schedule;
        this.members = members;
        this.bibs = bibs;
        this.ball = ball;
        this.totalMembers = new AtomicInteger(0);
        this.date = LocalDate.now(); // TODO: determine automatically using Schedule.startDate, Schedule.endDate, Schedule.dayOfWeek, Schedule.get()
    }

    private static Map<Long, Roster> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    public static Collection<Roster> get() {
        return COLLECTION.values();
    }

    public void validate(Boolean relaxed) {
        if(this.getSchedule() == null) {
            throw new IllegalArgumentException("Schedule can't be empty");
        } else {
            try {
                this.getSchedule().validate();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Association is invalid");
            }
        }
        if(this.getMembers() == null || this.getMembers().isEmpty()) {
            throw new IllegalArgumentException("Members can't be empty");
        } else if(!relaxed && (this.getMembers().size() < MIN_PLAYERS || this.getMembers().size() > MAX_PLAYERS)) {
            throw new IllegalArgumentException("Member limit not satisfied");
        } else {
            try {
                this.getMembers().forEach(Membership::validate);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Member is invalid");
            }
        }
        if(this.getBibs() == null) {
            throw new IllegalArgumentException("Bibs assignee member can't be empty");
        } else {
            try {
                this.getBibs().validate();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Bibs assignee member is invalid");
            }
        }
        if(this.getBall() == null) {
            throw new IllegalArgumentException("Ball assignee member can't be empty");
        } else {
            try {
                this.getBall().validate();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ball assignee member is invalid");
            }
        }
    }

    public Integer addMember(Membership member) {
        if(this.getMembers().size() == MAX_PLAYERS) {
            throw new IllegalStateException("Maximum member limit already reached");
        }
        try {
            member.validate();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Member is invalid");
        }
        // TODO: member exists in membership context
        if(this.getMembers().contains(member)) {
            throw new IllegalStateException("Member already included");
        }
        this.getMembers().add(member);
        int count = totalMembers.incrementAndGet();
        return count;
    }

    public Integer removeMember(Membership member) {
        if(this.getMembers().size() == MIN_PLAYERS) {
            throw new IllegalStateException("Minimum member limit already reached");
        }
        try {
            member.validate();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Member is invalid");
        }
        // TODO: member exists in membership context
        if(!this.getMembers().contains(member)) {
            throw new IllegalStateException("Member not included");
        }
        this.getMembers().remove(member);
        int count = totalMembers.decrementAndGet();
        return count;
    }

    public static Roster add(Roster roster, Boolean relaxed) {
        roster.validate(relaxed);
        roster.setId(ID++);
        COLLECTION.put(roster.getId(), roster);
        return roster;
    }


    public static Roster remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Roster does not exist with ID " + id);
        }
        Roster roster = COLLECTION.remove(id);
        return roster;
    }

    public static Roster get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Roster does not exist with ID " + id);
        }
        Roster roster = COLLECTION.get(id);
        return roster;
    }

    public static Roster editDate(Long id, String date) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Roster does not exist with ID " + id);
        }
        if(date == null || date.length() == 0) {
            throw new IllegalArgumentException("Date can't be empty");
        }
        LocalDate dt = null;
        try {
            dt = LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date not supported");
        }
        Roster roster = COLLECTION.get(id);
        if(dt.isBefore(roster.getSchedule().getStartDate()) || dt.isAfter(roster.getSchedule().getEndDate())) {
            throw new IllegalArgumentException("Date is invalid");
        }
        roster.setDate(dt);
        COLLECTION.put(id, roster);
        return roster;
    }

    public static Roster editSchedule(Long id, String scheduleId) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Roster does not exist with ID " + id);
        }
        if(scheduleId == null || scheduleId.length() == 0) {
            throw new IllegalArgumentException("Schedule Id can't be empty");
        }
        Long scheduleIdentifier = null;
        try {
            scheduleIdentifier = Long.parseLong(scheduleId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Schedule Id is invalid");
        }
        Schedule schedule = null;
        try {
            schedule = Schedule.get(scheduleIdentifier);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Schedule Id not found");
        }
        Roster roster = COLLECTION.get(id);
        roster.setSchedule(schedule);
        COLLECTION.put(id, roster);
        return roster;
    }

    public static Roster editBibs(Long id, String membershipId) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Roster does not exist with ID " + id);
        }
        if(membershipId == null || membershipId.length() == 0) {
            throw new IllegalArgumentException("Membership Id can't be empty");
        }
        Long membershipIdentifier = null;
        try {
            membershipIdentifier = Long.parseLong(membershipId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Membership Id is invalid");
        }
        Membership member = null;
        try {
            member = Membership.get(membershipIdentifier);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Membership Id not found");
        }
        Roster roster = COLLECTION.get(id);
        if(!roster.getMembers().contains(member)) {
            throw new IllegalStateException("Membership Id no subscribed to this schedule");
        }
        roster.setBibs(member);
        COLLECTION.put(id, roster);
        return roster;
    }

    public static Roster editBall(Long id, String membershipId) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Roster does not exist with ID " + id);
        }
        if(membershipId == null || membershipId.length() == 0) {
            throw new IllegalArgumentException("Membership Id can't be empty");
        }
        Long memberIdentifier = null;
        try {
            memberIdentifier = Long.parseLong(membershipId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Membership Id is invalid");
        }
        Membership member = null;
        try {
            member = Membership.get(memberIdentifier);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Membership Id not found");
        }
        Roster roster = COLLECTION.get(id);
        if(!roster.getMembers().contains(member)) {
            throw new IllegalStateException("Membership Id no subscribed to this schedule");
        }
        roster.setBall(member);
        COLLECTION.put(id, roster);
        return roster;
    }

    public static void main(String[] args) {
        Association c1 = Association.builder().name("name 1").email("email1@verein.com").website("web1.verein.com").build();
        c1 = Association.add(c1);
        Player p1 = Player.builder().firstName("first name 1").email("email1@verein.com").lastName("last name 1").phoneNumber("1234").build();
        Player p2 = Player.builder().firstName("first name 2").email("email2@verein.com").lastName("last name 2").phoneNumber("4567").build();
        Player p3 = Player.builder().firstName("first name 3").email("email3@verein.com").lastName("last name 3").phoneNumber("8761").build();
        Player p4 = Player.builder().firstName("first name 4").email("email4@verein.com").lastName("last name 4").phoneNumber("7612").build();
        p1 = Player.add(p1);
        p2 = Player.add(p2);
        p3 = Player.add(p3);
        p4 = Player.add(p4);
        Membership m1 = Membership.builder().association(c1).player(p1).nature(Membership.MembershipType.MEMBER).build();
        Membership m2 = Membership.builder().association(c1).player(p2).nature(Membership.MembershipType.GUEST).build();
        Membership m3 = Membership.builder().association(c1).player(p3).nature(Membership.MembershipType.MEMBER).build();
        Membership m4 = Membership.builder().association(c1).player(p4).nature(Membership.MembershipType.GUEST).build();
        m1 = Membership.add(m1);
        m2 = Membership.add(m2);
        m3 = Membership.add(m3);
        m4 = Membership.add(m4);
        Location l1 = Location.builder().name("location 1").address("address 1").build();
        l1 = Location.add(l1);
        LocalTime time1 = LocalTime.parse("18:30", TIME_FORMATTER);
        Schedule s1 = Schedule.builder()
                              .association(c1).location(l1).startDate(LocalDate.now()).endDate(LocalDate.now().plusMonths(6)).time(time1)
                              .season(Schedule.PlayingSeason.SUMMER).dayOfWeek(DayOfWeek.TUESDAY).build();
        s1 = Schedule.add(s1);
        Roster r1 = Roster.builder()
                .schedule(s1)
                .members(
                        new ArrayList<>(Arrays.asList(m1, m2))
                        )
                .ball(m1)
                .bibs(m2)
                          .build();
        r1 = Roster.add(r1, true);
        Roster.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        r1.addMember(m3);
        r1.addMember(m4);
        Roster.editBall(r1.getId(), m2.getId().toString());
        Roster.editBibs(r1.getId(), m1.getId().toString());
        Roster.editDate(r1.getId(), LocalDate.now().plusDays(9).format(DATE_FORMATTER));
        Roster.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Roster.remove(r1.getId());
        Roster.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
