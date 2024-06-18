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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.teenthofabud.sports.football.Schedule.TIME_FORMATTER;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Team implements Comparable<Team> {

    private static final Integer MIN_PLAYERS = 5;
    private static final Integer MAX_PLAYERS = 11;

    @Override
    public int compareTo(Team o) {
        return Integer.compare(this.getRoster().compareTo(o.getRoster()), this.getType().compareTo(o.getType()));
    }

    public static enum TeamType {
        HOME,
        AWAY;
    }

    @Setter
    private Roster roster;
    @Setter
    private TeamType type;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private AtomicInteger size;
    @Setter
    private List<Player> players;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Builder
    public Team(Roster roster, TeamType type, List<Player> players, Integer size) {
        this.roster = roster;
        this.type = type;
        this.size = new AtomicInteger(0);
        this.players = players;
    }

    private static Map<Long, Team> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    public static Collection<Team> get() {
        return COLLECTION.values();
    }

    public void validate(Boolean relaxed) {
        if(this.getRoster() == null) {
            throw new IllegalArgumentException("Roster can't be empty");
        } else {
            try {
                this.getRoster().validate(true);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Roster is invalid");
            }
        }
        if(this.getType() == null) {
            throw new IllegalArgumentException("Type can't be empty");
        }
        if(this.getPlayers() == null || this.getPlayers().isEmpty()) {
            throw new IllegalArgumentException("Player can't be empty");
        } else if(!relaxed && (this.getPlayers().size() < MIN_PLAYERS || this.getPlayers().size() > MAX_PLAYERS)) {
            throw new IllegalArgumentException("Player limit not satisfied");
        } else {
            try {
                this.getPlayers().forEach(Player::validate);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Player is invalid");
            }
        }
    }

    public static Team add(Team team, Boolean relaxed) {
        team.validate(relaxed);
        team.setId(ID++);
        COLLECTION.put(team.getId(), team);
        return team;
    }


    public static Team remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Team does not exist with ID " + id);
        }
        Team team = COLLECTION.remove(id);
        return team;
    }

    public Integer addPlayer(Player player) {
        if(this.getPlayers().size() == MAX_PLAYERS) {
            throw new IllegalStateException("Maximum player limit already reached");
        }
        try {
            player.validate();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Player is invalid");
        }
        // TODO: player exists in membership context (indirectly reference player through membership)
        if(this.getPlayers().contains(player)) {
            throw new IllegalStateException("Player already included");
        }
        this.getPlayers().add(player);
        int count = size.incrementAndGet();
        return count;
    }

    public Integer removeMember(Membership member) {
        if(this.getPlayers().size() == MIN_PLAYERS) {
            throw new IllegalStateException("Minimum player limit already reached");
        }
        try {
            member.validate();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Player is invalid");
        }
        // TODO: player exists in membership context (indirectly reference player through membership)
        if(!this.getPlayers().contains(member)) {
            throw new IllegalStateException("Player not included");
        }
        this.getPlayers().remove(member);
        int count = size.decrementAndGet();
        return count;
    }

    public static Team editType(Long id, String type) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Team does not exist with ID " + id);
        }
        Team.TeamType teamType = null;
        try {
            teamType = Team.TeamType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Season not supported");
        }
        Team team = COLLECTION.get(id);
        team.setType(teamType);
        COLLECTION.put(id, team);
        return team;
    }

    public static Team editRoster(Long id, String rosterId) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Team does not exist with ID " + id);
        }
        if(rosterId == null || rosterId.length() == 0) {
            throw new IllegalArgumentException("Roster Id can't be empty");
        }
        Long rosterIdentifier = null;
        try {
            rosterIdentifier = Long.parseLong(rosterId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Roster Id is invalid");
        }
        Roster roster = null;
        try {
            roster = Roster.get(rosterIdentifier);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Roster Id not found");
        }
        Team team = COLLECTION.get(id);
        team.setRoster(roster);
        COLLECTION.put(id, team);
        return team;
    }

    public static Team get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Team does not exist with ID " + id);
        }
        Team team = COLLECTION.get(id);
        return team;
    }

    public static void main(String[] args) {
        Association c1 = Association.builder().name("name 1").email("email1@verein.com").website("web1.verein.com").build();
        c1 = Association.add(c1);
        Player p1 = Player.builder().firstName("first name 1").email("email1@verein.com").lastName("last name 1").phoneNumber("1234").build();
        Player p2 = Player.builder().firstName("first name 2").email("email2@verein.com").lastName("last name 2").phoneNumber("4567").build();
        Player p3 = Player.builder().firstName("first name 3").email("email3@verein.com").lastName("last name 3").phoneNumber("8761").build();
        Player p4 = Player.builder().firstName("first name 4").email("email4@verein.com").lastName("last name 4").phoneNumber("7612").build();
        Player p5 = Player.builder().firstName("first name 5").email("email5@verein.com").lastName("last name 5").phoneNumber("8876").build();
        Player p6 = Player.builder().firstName("first name 6").email("email6@verein.com").lastName("last name 6").phoneNumber("1213").build();
        p1 = Player.add(p1);
        p2 = Player.add(p2);
        p3 = Player.add(p3);
        p4 = Player.add(p4);
        p5 = Player.add(p5);
        p6 = Player.add(p6);
        Membership m1 = Membership.builder().association(c1).player(p1).nature(Membership.MembershipType.MEMBER).build();
        Membership m2 = Membership.builder().association(c1).player(p2).nature(Membership.MembershipType.GUEST).build();
        Membership m3 = Membership.builder().association(c1).player(p3).nature(Membership.MembershipType.MEMBER).build();
        Membership m4 = Membership.builder().association(c1).player(p4).nature(Membership.MembershipType.GUEST).build();
        Membership m5 = Membership.builder().association(c1).player(p5).nature(Membership.MembershipType.MEMBER).build();
        Membership m6 = Membership.builder().association(c1).player(p6).nature(Membership.MembershipType.GUEST).build();
        m1 = Membership.add(m1);
        m2 = Membership.add(m2);
        m3 = Membership.add(m3);
        m4 = Membership.add(m4);
        m5 = Membership.add(m5);
        m6 = Membership.add(m6);
        Location l1 = Location.builder().name("location 1").address("address 1").build();
        l1 = Location.add(l1);
        LocalTime time1 = LocalTime.parse("18:30", TIME_FORMATTER);
        Schedule s1 = Schedule.builder()
                              .association(c1).location(l1).startDate(LocalDate.now()).endDate(LocalDate.now().plusMonths(6)).time(time1)
                              .season(Schedule.PlayingSeason.SUMMER).dayOfWeek(DayOfWeek.TUESDAY).build();
        s1 = Schedule.add(s1);
        Roster r1 = Roster.builder()
                          .schedule(s1)
                          .members(new ArrayList<>(Arrays.asList(m1, m2)))
                          .ball(m1)
                          .bibs(m2)
                          .build();
        r1 = Roster.add(r1, true);
        List<Player> homePlayers = new ArrayList<>(r1.getTotalMembers().get()/2);
        List<Player> awayPlayers = new ArrayList<>(r1.getTotalMembers().get()/2);
        //TODO: configurable team distribution strategy from presets
        homePlayers = r1.getMembers().stream().filter(m -> m.getNature().compareTo(Membership.MembershipType.MEMBER) == 0)
                        .map(Membership::getPlayer).collect(Collectors.toList());
        awayPlayers = r1.getMembers().stream().filter(m -> m.getNature().compareTo(Membership.MembershipType.GUEST) == 0)
                        .map(Membership::getPlayer).collect(Collectors.toList());
        Team homeTeam = Team.builder()
                .players(homePlayers)
                .type(TeamType.HOME)
                .roster(r1).build();
        Team awayTeam = Team.builder()
                            .players(awayPlayers)
                            .type(TeamType.AWAY)
                            .roster(r1).build();
        homeTeam = Team.add(homeTeam, true);
        awayTeam = Team.add(awayTeam, true);
        Team.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        homeTeam.addPlayer(m5.getPlayer());
        awayTeam.addPlayer(m6.getPlayer());
        Team.editType(homeTeam.getId(), TeamType.AWAY.name());
        Team.editType(awayTeam.getId(), TeamType.HOME.name());
        Team.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Team.remove(homeTeam.getId());
        Team.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
