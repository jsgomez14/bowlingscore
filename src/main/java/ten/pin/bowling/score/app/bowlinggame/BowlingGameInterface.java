package ten.pin.bowling.score.app.bowlinggame;

import java.util.List;
import java.util.Set;

public interface BowlingGameInterface {
    /**
     * Getter method of attribute "players"
     * @return value of attribute "players"
     */
    Set<String> getPlayers();

    /**
     * Setter method of attribute "players"
     * @param players value to set
     */
    void setPlayers(Set<String> players);

    /**
     * Getter method of attribute "thrws"
     * @return value of attribute "thrws"
     */
    List<String> getThrws();

    /**
     * Setter method of attribute "thrws"
     * @param thrws value to set
     */
    void setThrws(List<String> thrws);

}
