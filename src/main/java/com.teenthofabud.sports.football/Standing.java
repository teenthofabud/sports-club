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
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.teenthofabud.sports.football.Schedule.TIME_FORMATTER;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Standing implements Comparable<Standing> {

    @Setter
    private Integer position;
    @Setter
    private Player player;
    @Setter
    private Integer points;
    @Setter
    private Integer participation;
    @Setter
    private Double pointsPerGame;

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private static Map<Long, Standing> COLLECTION;
    private static Long ID;

    static {
        COLLECTION = new TreeMap<>();
        ID = 1L;
    }

    public static Collection<Standing> get() {
        return COLLECTION.values();
    }

    @Builder
    public Standing(Integer position, Player player, Integer points, Integer participation, Double pointsPerGame) {
        this.position = position;
        this.player = player;
        this.points = points;
        this.participation = participation;
        this.pointsPerGame = pointsPerGame;
    }
    
    @Override
    public int compareTo(Standing o) {
        return Integer.compare(o.getPoints().compareTo(this.getPoints()), o.getParticipation().compareTo(this.getParticipation()));
    }

    public void validate() {
        if(this.getPlayer() == null) {
            throw new IllegalArgumentException("Player can't be empty");
        } else {
            try {
                this.getPlayer().validate();
                //TODO: check player in player context
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Player is invalid");
            }
        }
        if(this.getPosition() == null) {
            throw new IllegalArgumentException("Position can't be empty");
        } else if (this.getPosition() <= 0) {
            throw new IllegalArgumentException("Position is invalid");
        }
        if(this.getPoints() == null) {
            throw new IllegalArgumentException("Points can't be empty");
        } else if (this.getPoints() <= 0) {
            throw new IllegalArgumentException("Points is invalid");
        }
        if(this.getParticipation() == null) {
            throw new IllegalArgumentException("Participation can't be empty");
        } else if (this.getParticipation() <= 0) {
            throw new IllegalArgumentException("Participation is invalid");
        }
        if(this.getPointsPerGame() == null) {
            throw new IllegalArgumentException("Points per game can't be empty");
        } else if (this.getPointsPerGame() <= 0) {
            throw new IllegalArgumentException("Points per game is invalid");
        }
    }

    public static Standing add(Standing standing) {
        standing.validate();
        standing.setId(ID++);
        COLLECTION.put(standing.getId(), standing);
        return standing;
    }

    public static Standing remove(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Standing does not exist with ID " + id);
        }
        Standing standing = COLLECTION.remove(id);
        return standing;
    }

    public static Standing get(Long id) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Standing does not exist with ID " + id);
        }
        Standing standing = COLLECTION.get(id);
        return standing;
    }

    public static Standing getByPlayer(Long playerId) {
        Optional<Standing> standingOptional = COLLECTION.values().stream().filter(
                s -> s.getPlayer().getId().compareTo(playerId) == 0).findFirst();
        if(standingOptional.isEmpty()) {
            throw new NoSuchElementException("Standing does not exist for player ID " + playerId);
        }
        return standingOptional.get();
    }

    public static Integer parseNumeric(Long id, String points, String context) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Standing does not exist with ID " + id);
        }
        Integer pointsInt = null;
        try {
            pointsInt = Integer.parseInt(points);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Points for " + context + " is invalid");
        }
        return pointsInt;
    }

    public static Standing editPoints(Long id, String points) {
        Integer pointsInt = parseNumeric(id, points, "points");
        Standing standing = COLLECTION.get(id);
        standing.setPoints(pointsInt);
        COLLECTION.put(id, standing);
        return standing;

    }

    public static Standing editParticipation(Long id, String participation) {
        Integer participationInt = parseNumeric(id, participation, "participation");
        Standing standing = COLLECTION.get(id);
        standing.setParticipation(participationInt);
        COLLECTION.put(id, standing);
        return standing;
    }

    public static Standing editPosition(Long id, String position) {
        Integer positionInt = parseNumeric(id, position, "position");
        Standing standing = COLLECTION.get(id);
        standing.setPosition(positionInt);
        COLLECTION.put(id, standing);
        return standing;
    }

    public static Standing editPointsPerGame(Long id, String pointsPerGame) {
        if(!COLLECTION.containsKey(id)) {
            throw new NoSuchElementException("Standing does not exist with ID " + id);
        }
        Double ppg = null;
        try {
            ppg = Double.parseDouble(pointsPerGame);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Points per game is invalid");
        }
        Standing standing = COLLECTION.get(id);
        standing.setPointsPerGame(ppg);
        COLLECTION.put(id, standing);
        return standing;
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
        Result finalRes = res1;
        AtomicInteger i = new AtomicInteger(1);
        res1.getWinner().getPlayers().forEach(p -> {
            //TODO: modify existing standing
            Standing standing = Standing.builder()
                    .points(finalRes.getPointsForWinner())
                    .participation(1)
                    .pointsPerGame((double)finalRes.getPointsForWinner()/1)
                    .position(i.getAndIncrement())
                    .player(p)
                                        .build();
            Standing.add(standing);
        });
        res1.getLoser().getPlayers().forEach(p -> {
            //TODO: modify existing standing or create new
            Standing standing = Standing.builder()
                                        .points(finalRes.getPointsForLoser()) // cumulative
                                        .participation(1) // cumulative
                                        .pointsPerGame((double)finalRes.getPointsForLoser()/1)
                                        .position(i.getAndIncrement())
                                        .player(p)
                                        .build();
            Standing.add(standing);
        });
        Standing.get().forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------");

    }


}
