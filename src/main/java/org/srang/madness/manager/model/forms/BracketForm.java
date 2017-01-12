package org.srang.madness.manager.model.forms;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.srang.madness.manager.model.entities.Bracket;
import org.srang.madness.manager.model.entities.Game;
import org.srang.madness.manager.model.types.Round;
import org.srang.madness.manager.service.BracketService;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.srang.madness.manager.model.types.Round.SALACIOUS;

/**
 * Created by srang on 1/8/17.
 */
@Getter
@Setter
@Log
public class BracketForm {


    public BracketForm() {
    }

    public BracketForm(final BracketService service) {
        Bracket master = service.getMaster();
        games = new HashMap<>();
        games.put(SALACIOUS, service.getRound(master, SALACIOUS).stream().collect(toMap(
                Game::getGameIndex,
                game -> {
                    GameTouple touple = new GameTouple();
                    touple.setTeamA(game.getTeamAlpha().getTeamId());
                    touple.setTeamB(game.getTeamBravo().getTeamId());
                    return touple;
                })
        ));
        service.rounds().stream().filter(round -> round != SALACIOUS)
                .forEach();
        Iterator<Round> roundIterator = rounds.iterator();
        roundIterator.next();
        roundIterator.forEachRemaining(round -> {
            games.put(round, new HashMap<>());
        });
    }

    public BracketForm(final BracketService service, Bracket existing) {
        // fill in bracket form from existing bracket
        games = new HashMap<>();
        service.rounds().forEach((round) -> {
            games.put(round, service.getRound(existing, round).stream().collect(toMap(
                    Game::getGameIndex,
                    game -> {
                        GameTouple touple = new GameTouple();
                        touple.setTeamA(game.getTeamAlpha().getTeamId());
                        touple.setTeamB(game.getTeamBravo().getTeamId());
                        return touple;
                    })
            ));
        });
    }

    @Getter
    @Setter
    private class GameTouple {
        @NotNull
        Integer teamA;
        @NotNull
        Integer teamB;
    }

    // < round, < game_index,gametouple >
    Map<Round, Map<Integer, GameTouple>> games;
    String name;
    Integer user;
}
