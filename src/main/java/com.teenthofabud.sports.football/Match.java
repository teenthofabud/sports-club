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
import java.util.stream.Collectors;

import static com.teenthofabud.sports.football.Schedule.TIME_FORMATTER;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Match implements Comparable<Match>{

    //TODO: Roster is derived from team attributes
    @Setter
    private Team home;
    @Setter
    private Team away;
    @Setter
    private Team goldenGoalScorer;
    @Setter
    private Integer homeScore;
    @Setter
    private Integer awayScore;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Builder
    public Match(Team home, Team away, Team goldenGoalScorer, Integer homeScore, Integer awayScore) {
        this.home = home;
        this.away = away;
        this.goldenGoalScorer = goldenGoalScorer;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }


    @Override
    public int compareTo(Match o) {
        return Integer.compare(this.getHome().compareTo(o.getHome()), this.getAway().compareTo(o.getAway()));
    }

    private static Map<Long, Match> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    public static Collection<Match> get() {
        return COLLECTION.values();
    }

    public void validate(Boolean relaxed) {
        if(this.getHome() == null) {
            throw new IllegalArgumentException("Home can't be empty");
        } else {
            try {
                this.getHome().validate(true);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Home is invalid");
            }
        }
        if(this.getAway() == null) {
            throw new IllegalArgumentException("Away can't be empty");
        } else {
            try {
                this.getAway().validate(true);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Away is invalid");
            }
        }
        if(this.getGoldenGoalScorer() == null) {
            throw new IllegalArgumentException("Golden goal scorer can't be empty");
        } else {
            try {
                this.getGoldenGoalScorer().validate(true);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Golden goal scorer is invalid");
            }
        }
        if(this.getHomeScore() == null) {
            throw new IllegalArgumentException("Home score can't be empty");
        } else if(this.getHomeScore() < 0) {
            throw new IllegalArgumentException("Home score is invalid");
        }
        if(this.getAwayScore() == null) {
            throw new IllegalArgumentException("Away score can't be empty");
        } else if(this.getAwayScore() < 0) {
            throw new IllegalArgumentException("Away score is invalid");
        }
    }

    public static Match add(Match match, Boolean relaxed) {
        match.validate(relaxed);
        match.setId(ID++);
        //TODO: automatically set home and away determine teams from match.
        COLLECTION.put(match.getId(), match);
        return match;
    }

    public static Match remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Match does not exist with ID " + id);
        }
        Match match = COLLECTION.remove(id);
        return match;
    }

    public static Match get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Match does not exist with ID " + id);
        }
        Match match = COLLECTION.get(id);
        return match;
    }

    public static Team parseTeam(Long id, String teamId, String context) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Match does not exist with ID " + id);
        }
        if(teamId == null || teamId.length() == 0) {
            throw new IllegalArgumentException(context + " Id can't be empty");
        }
        Long teamIdentifier = null;
        try {
            teamIdentifier = Long.parseLong(teamId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(context + " Id is invalid");
        }
        Team team = null;
        try {
            team = Team.get(teamIdentifier);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(context + " Id not found");
        }
        return team;
    }

    public static Match editHome(Long id, String homeId) {
        Team team = parseTeam(id, homeId, "Home");
        Match match = COLLECTION.get(id);
        match.setHome(team);
        COLLECTION.put(id, match);
        return match;
    }

    public static Match editAway(Long id, String awayId) {
        Team team = parseTeam(id, awayId, "Away");
        Match match = COLLECTION.get(id);
        match.setAway(team);
        COLLECTION.put(id, match);
        return match;
    }

    public static Match editGoldenGoalScorer(Long id, String goldenGoalTeamId) {
        Team team = parseTeam(id, goldenGoalTeamId, "Golden goal scorer");
        Match match = COLLECTION.get(id);
        match.setGoldenGoalScorer(team);
        COLLECTION.put(id, match);
        return match;
    }

    private static Integer parseScore(Long id, String score, String context) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Match does not exist with ID " + id);
        }
        if(score == null) {
            throw new IllegalArgumentException(context + " score can't be empty");
        }
        Integer scoreInt = null;
        try {
            scoreInt = Integer.parseInt(score);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(context + " score is invalid");
        }
        if(scoreInt < 0) {
            throw new IllegalArgumentException(context + " score is invalid");
        }
        return scoreInt;
    }

    public static Match editHomeScore(Long id, String homeScore) {
        Integer homeScoreNumber = parseScore(id, homeScore, "Home");
        Match match = COLLECTION.get(id);
        match.setHomeScore(homeScoreNumber);
        COLLECTION.put(id, match);
        return match;
    }

    public static Match editAwayScore(Long id, String awayScore) {
        Integer awayScoreNumber = parseScore(id, awayScore, "Away");
        Match match = COLLECTION.get(id);
        match.setAwayScore(awayScoreNumber);
        COLLECTION.put(id, match);
        return match;
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
                          .members(new ArrayList<>(Arrays.asList(m1, m2, m3, m4, m5, m6)))
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
                            .type(Team.TeamType.HOME)
                            .roster(r1).build();
        Team awayTeam = Team.builder()
                            .players(awayPlayers)
                            .type(Team.TeamType.AWAY)
                            .roster(r1).build();
        homeTeam = Team.add(homeTeam, true);
        awayTeam = Team.add(awayTeam, true);
        Match match = Match.builder()
                .away(awayTeam)
                .home(homeTeam)
                .awayScore(3)
                .homeScore(2)
                .goldenGoalScorer(homeTeam) //TODO: run simulation with strategy pattern to determine golden goal
                           .build();
        match = Match.add(match, true);
        Match.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Match.editAway(match.getId(), homeTeam.getId().toString());
        Match.editHome(match.getId(), awayTeam.getId().toString());
        Match.editGoldenGoalScorer(match.getId(), awayTeam.getId().toString());
        Match.editAwayScore(match.getId(), "3");
        Match.editAwayScore(match.getId(), "2");
        Match.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Match.remove(match.getId());
        Match.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
