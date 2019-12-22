package validator;

import java.util.List;

public interface ContentValidatorInterface {
    /**
     * It verifies if players have thrown 10 times
     * @return true if all players have 10 throws each, false otherwise.
     */
    boolean validTenThrowsAllPlayers(List<String> game) throws ContentException;
}
