package bowlinggame;

import validator.ContentException;
import validator.ContentValidator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BowlingGame implements BowlingGameInterface{
    private Set<String> players;
    private List<String> thrws;
    private ContentValidator contentValidator;

    public BowlingGame(List<String> game) throws ContentException {
        this.players = extractPlayers(game);
        contentValidator = new ContentValidator();
        if(!contentValidator.validTenThrowsAllPlayers(game))
            throw new ContentException("There is a player that has not thrown 10 times.");
        this.thrws=game;
    }

    private Set<String> extractPlayers(List<String> game) {
        return game.stream().map(thrw -> thrw.split("\\t")[0]).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    @Override
    public List<String> getThrws() {
        return thrws;
    }

    @Override
    public void setThrws(List<String> thrws) {
        this.thrws = thrws;
    }
}
