package validator;

import java.util.*;

public class ContentValidator implements ContentValidatorInterface {

    public static final String[] VALID_SCORES =
            {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "F"};


    @Override
    public boolean validTenThrowsAllPlayers(List<String> game) throws ContentException{
        //Handles if a player has a previous throw.
        Stack<String> playerValHelper = new Stack<>();
        //Save number of throws of a player <player, # throws>
        Map<String, Integer> countHelper = new HashMap<>();
        for (String thrw: game) {
            String[] thrwVals = thrw.split("\\t");
            String score = thrwVals[1];
            String player = thrwVals[0];
            if(playerValHelper.isEmpty()){
                if(score.equals("10")){
                    Integer count = countHelper.get(player);
                    if(count != null) {
                        if(count == 9) playerValHelper.push(player);
                        else countHelper.put(player, count+1);
                    }
                    else countHelper.put(player, 1);
                } else if(validScoreValue(score)) {
                    playerValHelper.push(player);
                } else{
                    throw new ContentException("The score '"+score+"' by '"+player+"' is an invalid value.");
                }
            } else {
                if(countHelper.get(player) != null){
                    if(countHelper.get(player) < 9){
                        String currPlayer = playerValHelper.pop();
                        handleCurrentPlayer(currPlayer,player,countHelper);
                    } else if(countHelper.get(player) == 9) {
                        String currPlayer = playerValHelper.get(0);
                        handleCurrentPlayer(currPlayer, player, countHelper);
                    } else {
                        String currPlayer = playerValHelper.pop();
                        handleCurrentPlayer(currPlayer,player, countHelper);
                    }
                }
                else {
                    String currPlayer = playerValHelper.pop();
                    handleCurrentPlayer(currPlayer, player, countHelper);
                }
            }
        }
        return countHelper.values().stream().allMatch(thrws -> thrws == 10);
    }

    /**
     * It verifies if a given score value is between 0 and 10 or a foul
     * @param value score value to verify
     * @return true if it is a valid value, false otherwise.
     */
    private boolean validScoreValue(String value) {
        return Arrays.asList(VALID_SCORES).contains(value);
    }

    private void handleCurrentPlayer(String currPlayer, String player, Map<String, Integer> countHelper) throws ContentException{
        if(currPlayer.equals(player)){
            Integer count = countHelper.get(player);
            if(count == null) countHelper.put(player, 1);
            else if(count != 10) countHelper.put(player, count+1);
        } else{
            throw new ContentException("The program spected '"+player+"' throw, but recieved a '"+currPlayer+"' throw.");
        }
    }
}
