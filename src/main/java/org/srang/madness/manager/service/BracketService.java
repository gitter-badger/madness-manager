package org.srang.madness.manager.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.srang.madness.manager.model.entities.Bracket;
import org.srang.madness.manager.model.entities.Game;
import org.srang.madness.manager.model.entities.Team;
import org.srang.madness.manager.model.forms.CreateMasterBracketForm;
import org.srang.madness.manager.model.repositories.BracketRepository;
import org.srang.madness.manager.model.repositories.TeamRepository;
import org.srang.madness.manager.model.repositories.UserRepository;
import org.srang.madness.manager.model.types.Region;
import org.srang.madness.manager.model.types.Round;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.srang.madness.manager.model.types.Region.RegionType.*;
import static org.srang.madness.manager.model.types.Round.SALACIOUS;

/**
 * Created by srang on 12/17/16.
 */
@Log
@Service
public class BracketService {
    @Autowired
    BracketRepository bracketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamService teamService;

    @Value("${org.srang.madness.region.size}")
    Integer regionSize;


//    public List<Integer> generateMatchups() {
//        List<Integer> firstTeams = new LinkedList<>(Arrays.asList(1,2,3,4,5,6,7,8));
//        List<Integer> ret = new ArrayList<>();
//        int count = firstTeams.size();
//        while(count > 1) {
//            if((count + 1 ) % 2 == 1) {
//                ret.add(0, firstTeams.remove(1));
//            } else {
//                ret.add(0, firstTeams.remove(count - 2));
//            }
//            count -= 1;
//            log.warning("count: " + count);
//        }
//        ret.add(0, firstTeams.remove(0));
//
//        return ret;
//    }

    public List<Integer> generateMatchups() {
        return Arrays.asList(1, 8, 5, 4, 6, 3, 7, 2);
    }

    public Bracket getMaster() {
        return bracketRepository.findMasterBracket();
    }

    public List<Region> regions() {
        return Arrays.asList(EAST, WEST, SOUTH, MIDWEST).stream()
                .map(Region.RegionType::region).collect(toList());
    }

    public List<Game> getRound(Bracket bracket, Round round) {
        return null;
    }

    public Team getWinner(Game game) {
        return null;
    }

    public Bracket createMaster(CreateMasterBracketForm form) {
        /* todo better way of finding admin */
        Bracket master = Bracket.builder()
                .user(userRepository.findByUsername("srang"))
                .isMaster(true)
                .name(form.getName())
                .build();
        Map<Integer, Team> teamMap = teamService.getTeams();
        List<Integer> matchups = generateMatchups();
        form.getRankedTeams().forEach((r, teams) -> {
            Region region = Region.RegionType.valueOf(r).region();
            IntStream.range(0, matchups.size()).forEach((index) -> {
                int rank = matchups.get(index);
                Integer gameIndex = 8 * (r - 1) + index;
                Game game = Game.builder()
                        .gameIndex(gameIndex)
                        .bracket(master)
                        .round(SALACIOUS.id())
                        .teamAlpha(teamMap.get(teams.get(rank)))
                        .teamBravo(teamMap.get(teams.get(regionSize + 1 - rank)))
                        .build();
                master.addGame(game);
            });
        });
        bracketRepository.save(master);
        return master;
    }
}
