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
public class Result implements Comparable<Result> {

    @Override
    public int compareTo(Result o) {
        return Integer.compare(this.getMatch().compareTo(o.getMatch()),
                               Integer.compare(this.getWinner().compareTo(o.getWinner()),
                               this.getLoser().compareTo(o.getLoser())));
    }

    @Setter
    private Match match;
    @Setter
    private Team winner;
    @Setter
    private Team loser;
    @Setter
    private Integer pointsForWinner;
    @Setter
    private Integer pointsForLoser;
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private static Map<Long, Result> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    public static Collection<Result> get() {
        return COLLECTION.values();
    }

    @Builder
    public Result(Match match, Team winner, Team loser, Integer pointsForWinner, Integer pointsForLoser) {
        this.match = match;
        this.winner = winner;
        this.loser = loser;
        this.pointsForWinner = pointsForWinner;
        this.pointsForLoser = pointsForLoser;
    }


    public void validate(Boolean relaxed) {
        if(this.getMatch() == null) {
            throw new IllegalArgumentException("Match can't be empty");
        } else {
            try {
                this.getMatch().validate(true);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Match is invalid");
            }
        }
        if(this.getWinner() == null) {
            throw new IllegalArgumentException("Winner can't be empty");
        } else {
            try {
                this.getWinner().validate(true);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Winner is invalid");
            }
        }
        if(this.getLoser() == null) {
            throw new IllegalArgumentException("Loser can't be empty");
        } else {
            try {
                this.getLoser().validate(true);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Loser is invalid");
            }
        }
        if(this.getPointsForWinner() == null) {
            throw new IllegalArgumentException("Points for winner can't be empty");
        } else if (this.getPointsForWinner() <= 0) {
            throw new IllegalArgumentException("Points for winner is invalid");
        }
        if(this.getPointsForLoser() == null) {
            throw new IllegalArgumentException("Points for loser can't be empty");
        } else if (this.getPointsForLoser() <= 0) {
            throw new IllegalArgumentException("Points for loser is invalid");
        }
    }

    private static Team parseTeam(Long id, String teamId, String context) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Result does not exist with ID " + id);
        }
        if(teamId == null || teamId.length() == 0) {
            throw new IllegalArgumentException(context + " team Id can't be empty");
        }
        Long teamIdentifier = null;
        try {
            teamIdentifier = Long.parseLong(teamId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(context + " team Id is invalid");
        }
        Team team = null;
        try {
            team = Team.get(teamIdentifier);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(context + " team Id not found");
        }
        return team;
    }

    public static Result editWinner(Long id, String teamId) {
        Team team = parseTeam(id, teamId, "Winner");
        Result result = COLLECTION.get(id);
        result.setWinner(team);
        COLLECTION.put(id, result);
        return result;
    }

    public static Result editLoser(Long id, String teamId) {
        Team team = parseTeam(id, teamId, "Loser");
        Result result = COLLECTION.get(id);
        result.setLoser(team);
        COLLECTION.put(id, result);
        return result;
    }

    public static Integer parsePoints(Long id, String points, String context) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Result does not exist with ID " + id);
        }
        Integer pointsInt = null;
        try {
            pointsInt = Integer.parseInt(points);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Points for " + context + " is invalid");
        }
        return pointsInt;
    }

    public static Result editPointsForWinner(Long id, String pointsForWinner) {
        Integer pointsForWinnerInt = parsePoints(id, pointsForWinner, "winner");
        Result result = COLLECTION.get(id);
        result.setPointsForWinner(pointsForWinnerInt);
        COLLECTION.put(id, result);
        return result;
    }

    public static Result editPointsForLoser(Long id, String pointsForLoser) {
        Integer pointsForLoserInt = parsePoints(id, pointsForLoser, "loser");
        Result result = COLLECTION.get(id);
        result.setPointsForLoser(pointsForLoserInt);
        COLLECTION.put(id, result);
        return result;
    }

    public static Result add(Result result, Boolean relaxed) {
        result.validate(relaxed);
        result.setId(ID++);
        COLLECTION.put(result.getId(), result);
        return result;
    }

    public static Result remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Result does not exist with ID " + id);
        }
        Result result = COLLECTION.remove(id);
        return result;
    }

    public static Result get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Result does not exist with ID " + id);
        }
        Result result = COLLECTION.get(id);
        return result;
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
        /*Team fullTimeWinner = match.getAwayScore() > match.getHomeScore() ?
                match.getAway() : match.getAwayScore() < match.getHomeScore() ?
                match.getHome() : match.getAwayScore() == match.getHomeScore() ?
                match.getGoldenGoalScorer() : match.getGoldenGoalScorer(); //TODO: move complex calculation to service*/
        /*Team losingTeam = match.getAwayScore() > match.getHomeScore() ?
                match.getHome() : match.getAwayScore() < match.getHomeScore() ?
                match.getAway() : match.getGoldenGoalScorer().compareTo(match.getAway()) == 0 ?
                match.getHome() : match.getAway(); //TODO: move complex calculation to service*/
        /*Integer winningPoints = fullTimeWinner.compareTo(match.getGoldenGoalScorer()) == 0 ?
                (1 + 2 + 1) : (1 + 1); //TODO: move complex calculation to service
        Integer losingPoints = losingTeam.compareTo(match.getGoldenGoalScorer()) == 0 ?
                (1 + 1) : (1 + 0); //TODO: move complex calculation to service*/
        Team winningTeam = match.getGoldenGoalScorer();
        Team losingTeam = match.getGoldenGoalScorer().compareTo(match.getAway()) != 0 ? match.getAway() : match.getHome();
        Integer homePoints = match.getHomeScore() > match.getAwayScore() ?
                1 + 2 : match.getHomeScore() == match.getAwayScore() ?
                1 + 1 : 1 + 0;
        homePoints = match.getHome().compareTo(match.getGoldenGoalScorer()) == 0 ? homePoints + 1 : homePoints;
        Integer awayPoints = match.getHomeScore() > match.getAwayScore() ?
                1 + 0 : match.getHomeScore() == match.getAwayScore() ?
                1 + 1 : 1 + 2;
        awayPoints = match.getAway().compareTo(match.getGoldenGoalScorer()) == 0 ? awayPoints + 1 : awayPoints;
        Result res1 = Result.builder()
                .winner(winningTeam)
                .loser(losingTeam)
                .match(match)
                .pointsForLoser(match.getHome().compareTo(winningTeam) == 0 ? awayPoints : homePoints)
                .pointsForWinner(match.getHome().compareTo(winningTeam) == 0 ? homePoints : awayPoints)
                            .build();
        res1 = Result.add(res1, true);
        Result.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Result.editLoser(res1.getId(), res1.getWinner().getId().toString());
        Result.editWinner(res1.getId(), res1.getLoser().getId().toString());
        /*
        winningPoints = res1.getWinner().compareTo(match.getGoldenGoalScorer()) == 0 ?
                (1 + 2 + 1) : (1 + 1); //TODO: move complex re-calculation post initialization to service
        losingPoints = res1.getLoser().compareTo(match.getGoldenGoalScorer()) == 0 ?
                (1 + 1) : (1 + 0); //TODO: move complex re-calculation post initialization to service*/
        Result.editPointsForLoser(res1.getId(), res1.getWinner().compareTo(winningTeam) == 0 ? awayPoints.toString() : homePoints.toString());
        Result.editPointsForWinner(res1.getId(), res1.getLoser().compareTo(winningTeam) == 0 ? awayPoints.toString() : homePoints.toString());
        Result.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
        Result.remove(res1.getId());
        Result.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");
    }

}
