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
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Schedule implements Comparable<Schedule> {

    @Override
    public int compareTo(Schedule o) {
        return Integer.compare(this.association.compareTo(o.getAssociation()), this.location.compareTo(o.getLocation()));
    }

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static enum PlayingSeason {
        SUMMER,
        WINTER
    }

    @Setter
    private Association association;
    @Setter
    private Location location;
    @Setter
    private LocalDate startDate;
    @Setter
    private LocalDate endDate;
    @Setter
    private LocalTime time;
    @Setter
    private PlayingSeason season;
    @Setter
    private DayOfWeek dayOfWeek;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Builder
    public Schedule(Association association, Location location, LocalDate startDate, LocalDate endDate, LocalTime time, PlayingSeason season, DayOfWeek dayOfWeek) {
        this.association = association;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.season = season;
        this.dayOfWeek = dayOfWeek;
    }

    private static Map<Long, Schedule> COLLECTION;
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
        if(this.getLocation() == null) {
            throw new IllegalArgumentException("Location can't be empty");
        } else {
            try {
                this.getLocation().validate();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Location is invalid");
            }
        }
        if(this.getStartDate() == null) {
            throw new IllegalArgumentException("Start Date can't be empty");
        }
        if(this.getEndDate() == null) {
            throw new IllegalArgumentException("End Date can't be empty");
        }
        if(this.getTime() == null) {
            throw new IllegalArgumentException("Time can't be empty");
        }
        if(this.getSeason() == null) {
            throw new IllegalArgumentException("Season can't be empty");
        }
        if(this.getDayOfWeek() == null) {
            throw new IllegalArgumentException("Day Of Week can't be empty");
        }
    }

    public static Schedule editLocation(Long id, String locationId) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Schedule does not exist with ID " + id);
        }
        if(locationId == null || locationId.length() == 0) {
            throw new IllegalArgumentException("Location Id can't be empty");
        }
        Long locationIdentifier = null;
        try {
            locationIdentifier = Long.parseLong(locationId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Location Id is invalid");
        }
        Location location = null;
        try {
            location = Location.get(locationIdentifier);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Location Id not found");
        }
        Schedule schedule = COLLECTION.get(id);
        schedule.setLocation(location);
        COLLECTION.put(id, schedule);
        return schedule;
    }

    public static Schedule editStartDate(Long id, String startDate) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Schedule does not exist with ID " + id);
        }
        if(startDate == null || startDate.length() == 0) {
            throw new IllegalArgumentException("Start Date can't be empty");
        }
        LocalDate startDt = null;
        try {
            startDt = LocalDate.parse(startDate, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Start Date not supported");
        }
        if(startDt.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start Date is invalid");
        }
        Schedule schedule = COLLECTION.get(id);
        schedule.setStartDate(startDt);
        COLLECTION.put(id, schedule);
        return schedule;
    }

    public static Schedule editEndDate(Long id, String endDate) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Schedule does not exist with ID " + id);
        }
        if(endDate == null || endDate.length() == 0) {
            throw new IllegalArgumentException("End Date can't be empty");
        }
        LocalDate endtDt = null;
        try {
            endtDt = LocalDate.parse(endDate, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("End Date not supported");
        }
        Schedule schedule = COLLECTION.get(id);
        if(endtDt.isBefore(LocalDate.now()) || endtDt.isBefore(schedule.getStartDate())) {
            throw new IllegalArgumentException("End Date is invalid");
        }
        schedule.setEndDate(endtDt);
        COLLECTION.put(id, schedule);
        return schedule;
    }

    public static Schedule editTime(Long id, String time) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Schedule does not exist with ID " + id);
        }
        if(time == null || time.length() == 0) {
            throw new IllegalArgumentException("Time can't be empty");
        }
        LocalTime t = null;
        try {
            t = LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Time not supported");
        }
        Schedule schedule = COLLECTION.get(id);
        schedule.setTime(t);
        COLLECTION.put(id, schedule);
        return schedule;
    }

    public static Schedule add(Schedule schedule) {
        schedule.validate();
        schedule.setId(ID++);
        COLLECTION.put(schedule.getId(), schedule);
        return schedule;
    }

    public static Schedule remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Schedule does not exist with ID " + id);
        }
        Schedule schedule = COLLECTION.remove(id);
        return schedule;
    }

    public static Schedule get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Schedule does not exist with ID " + id);
        }
        Schedule schedule = COLLECTION.get(id);
        return schedule;
    }

    public static Collection<Schedule> get() {
        return COLLECTION.values();
    }

    public static Schedule editSeason(Long id, String season) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Schedule does not exist with ID " + id);
        }
        PlayingSeason playingSeason = null;
        try {
            playingSeason = PlayingSeason.valueOf(season);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Season not supported");
        }
        Schedule schedule = COLLECTION.get(id);
        schedule.setSeason(playingSeason);
        COLLECTION.put(id, schedule);
        return schedule;
    }

    public static Schedule editDayOfWeek(Long id, String dayOfWeek) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Schedule does not exist with ID " + id);
        }
        DayOfWeek dow = null;
        try {
            dow = DayOfWeek.valueOf(dayOfWeek);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Day Of Week not supported");
        }
        Schedule schedule = COLLECTION.get(id);
        schedule.setDayOfWeek(dow);
        COLLECTION.put(id, schedule);
        return schedule;
    }

    public static void main(String[] args) {
        Association c1 = Association.builder().name("name 1").email("email1@verein.com").website("web1.verein.com").build();
        c1 = Association.add(c1);
        Location l1 = Location.builder().name("location 1").address("address 1").build();
        Location l2 = Location.builder().name("location 2").address("address 2").build();
        l1 = Location.add(l1);
        l2 = Location.add(l2);
        LocalTime time1 = LocalTime.parse("18:30", TIME_FORMATTER);
        LocalTime time2 = LocalTime.parse("19:00", TIME_FORMATTER);
        Schedule s1 = Schedule.builder()
                              .association(c1).location(l1).startDate(LocalDate.now()).endDate(LocalDate.now().plusMonths(6)).time(time1)
                              .season(PlayingSeason.SUMMER).dayOfWeek(DayOfWeek.TUESDAY).build();
        Schedule s2 = Schedule.builder()
                              .association(c1).location(l2).startDate(LocalDate.now().plusMonths(6)).endDate(LocalDate.now().plusMonths(12)).time(time2)
                              .season(PlayingSeason.WINTER).dayOfWeek(DayOfWeek.TUESDAY).build();
        s1 = Schedule.add(s1);
        s2 = Schedule.add(s2);
        Schedule.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Schedule.editEndDate(s2.getId(), LocalDate.now().plusMonths(9).format(DATE_FORMATTER));
        Schedule.editStartDate(s1.getId(), LocalDate.now().plusMonths(1).format(DATE_FORMATTER));
        Schedule.editLocation(s1.getId(), l2.getId().toString());
        Schedule.editDayOfWeek(s1.getId(), DayOfWeek.FRIDAY.name());
        Schedule.editTime(s2.getId(), LocalTime.now().format(TIME_FORMATTER));
        Schedule.editSeason(s2.getId(), PlayingSeason.SUMMER.name());
        Schedule.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Schedule.remove(s2.getId());
        Schedule.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
