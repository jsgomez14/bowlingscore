package gamecalculator;
import bowlinggame.BowlingGame;
import java.util.*;

public class GameCalculator implements GameCalculatorInterface{

    public static final String FRAME_LINE =
            "Frame        1        2        3        4        5        6        " +
                    "7        8        9        10";
    private static final int MAX_ROUNDS = 10;
    private static final int MAX_ROLLS = MAX_ROUNDS*2+3;
    private static final String STRIKE_SYMBOL = "X";
    private static final String SPARE_SYMBOL = "/";



    /**
     * Bowling game to print.
     */
    private BowlingGame bowlingGame;

    /**
     * Lines which are going to be printed.
     */
    private String[] linesToPrint;

    /**
     * Frames of a player.
     */
    private Map<String, Frame[]> framesPerPlayer;

    /**
     * Throws of a player.
     */
    private Map<String, int[]> throwsPerPlayer;


    /**
     * Number of players.
     */
    private int numPlayers;

    /**
     * Calculate the scoresheet of a given bowling game
     * @param bowlingGame given bowling game.
     */
    public GameCalculator(BowlingGame bowlingGame) {
        this.bowlingGame = bowlingGame;
        // Players
        Set<String> players = bowlingGame.getPlayers();
        // Number of players.
        this.numPlayers = players.size();
        // Players  Pins Hit
        List<String> thrws = bowlingGame.getThrws();
        // Initialize title to print, and first words of each line.
        initialization(players);

        // It handles pinfalls line per player.
        Map<String, String> pinfallsLineHandler = new HashMap<>();
        initializePinfallsLine(players,pinfallsLineHandler);

        // It handles score line per player.
        Map<String, Integer> scoreLineHandler = new HashMap<>();
        initializeScoreLine(players, scoreLineHandler);

        // Starts calculating the game.
        calculateGame(thrws, players, pinfallsLineHandler, scoreLineHandler);
        concatenation(players, pinfallsLineHandler, scoreLineHandler);
    }

    private void concatenation(Set<String> players, Map<String, String> pinfallsLineHandler, Map<String, Integer> scoreLineHandler) {
        int pIndex = 1;
        for (String player: players) {
            this.linesToPrint[pIndex+1] = this.linesToPrint[pIndex+1] + pinfallsLineHandler.get(player);
            pIndex+=3;
        }
    }


    /**
     * Initialize the bowling score sheet to print
     * @param players bowling game players
     */
    private void initialization(Set<String> players) {
        this.framesPerPlayer = new HashMap<>();
        this.throwsPerPlayer = new HashMap<>();
        this.linesToPrint = new String[1+(players.size()*3)];
        this.linesToPrint[0] = FRAME_LINE;
        int pIndex = 1;
        for (String player: players){
            framesPerPlayer.put(player, new Frame[MAX_ROUNDS]);
            throwsPerPlayer.put(player, new int[MAX_ROLLS]);
            this.linesToPrint[pIndex] = player;
            this.linesToPrint[pIndex+1] ="Pinfalls    ";
            this.linesToPrint[pIndex+2] = "Score    ";
            pIndex+=3;
        }
    }
    /**
     * Initialize a score output line per player handler.
     * @param players set of players
     * @param scoreLineHandler score output line per player handler
     */
    private void initializeScoreLine(Set<String> players, Map<String, Integer> scoreLineHandler) {
        players.forEach(player -> scoreLineHandler.put(player, 0));
    }

    /**
     * Initialize a pinfalls output line per player handler.
     * @param players set of players
     * @param pinfallsLineHandler pinfalls output line per player handler
     */
    private void initializePinfallsLine(Set<String> players, Map<String, String> pinfallsLineHandler) {
        players.forEach(player -> pinfallsLineHandler.put(player,""));
    }

    /**
     * Creates a Frame
     * @return Frame
     */
    public Frame newFrame(){
        return new Frame();
    }

    private void calculateGame(List<String> thrws, Set<String> players, Map<String, String> pinfallsLineHandler, Map<String, Integer> scoreLineHandler) {
        int hitPins;
        boolean extraThrows = false;
        intializeThrows(players);
        int throwsCounter = 1;
        int round = 1;
        int numPlayersT = 0;
        for (String thrw : thrws) {
            if(throwsCounter <= 3){
                String currPlayer = thrw.split("\\t")[0];
                Frame currFrame;
                if(framesPerPlayer.get(currPlayer)[round-1] == null){
                    currFrame= newFrame();
                    framesPerPlayer.get(currPlayer)[round-1] = currFrame;
                } else{
                    Frame temp = framesPerPlayer.get(currPlayer)[round-1];
                    currFrame= temp;
                }
                String sHitPins = thrw.split("\\t")[1];
                try{
                    hitPins = Integer.parseInt(sHitPins);
                } catch (NumberFormatException e){
                    hitPins = 0;
                }
                currFrame.getThrowIndex()[throwsCounter-1] = hitPins;
                //throwsPerPlayer.get(currPlayer)[throwsCounter*round-1] = hitPins;


                int frameTot = currFrame.getTotal();
                if(round == 10 && throwsCounter ==2 && currFrame.getThrowIndex()[0] == 10){
                    if(currFrame.getThrowIndex()[1] == 10) frameTot = 10;
                    else frameTot = currFrame.getThrowIndex()[1];
                } else if ( round == 10 && throwsCounter == 3 && currFrame.getThrowIndex()[1] == 10){
                    if(currFrame.getThrowIndex()[2] == 10) frameTot = 10;
                    else frameTot = currFrame.getThrowIndex()[2];
                } else if( round == 10 && throwsCounter == 3 && currFrame.getThrowIndex()[1] != 10){
                    frameTot = currFrame.getThrowIndex()[1]+currFrame.getThrowIndex()[2];
                }
                if(frameTot < 10 && throwsCounter == 2 && round < 10){
                    handleNormal(round, throwsCounter, currPlayer,currFrame, pinfallsLineHandler);
                    throwsCounter = 1;
                    numPlayersT++;
                    if(numPlayersT == numPlayers){
                        numPlayersT = 0;
                        round++;
                    }
                    continue;
                } else if(frameTot < 10 && throwsCounter == 2 && round ==10){
                    throwsCounter++;
                    continue;
                } else if( frameTot<10 && throwsCounter == 3 && round == 10){
                    handleNormal(round, throwsCounter,currPlayer, currFrame, pinfallsLineHandler);
                    throwsCounter = 1;
                    numPlayersT++;
                    if(numPlayersT == numPlayers) {
                        numPlayersT = 0;
                    }
                    continue;
                }

                if(frameTot == 10){
                    if(throwsCounter == 1){
                        handleStrike(round,currPlayer, pinfallsLineHandler);
                    } else if(throwsCounter == 2 && currFrame.getThrowIndex()[0] != 10){
                        handleSpare(round, currPlayer, currFrame, pinfallsLineHandler);
                    } else if(throwsCounter == 2 && currFrame.getThrowIndex()[0] == 10) {
                        handleStrike(round, currPlayer, pinfallsLineHandler);
                    } else if ( throwsCounter == 3 && currFrame.getThrowIndex()[2]==10){
                        handleStrike(round, currPlayer, pinfallsLineHandler);
                        throwsCounter = 1;
                        numPlayersT++;
                        continue;
                    }

                    if(round < 10){
                        throwsCounter = 1;
                        numPlayersT++;
                        if(numPlayersT == numPlayers) {
                            numPlayersT = 0;
                            round++;
                        }
                        continue;
                    } else if(round == 10){
                        throwsCounter++;
                    }
                }
            }

            if (round < 10 && throwsCounter ==1){
                throwsCounter++;
            }
        }
    }

    private void handleSpare(int round, String currPlayer, Frame currFrame, Map<String, String> pinfallsLineHandler) {
        String txt = pinfallsLineHandler.get(currPlayer);
        pinfallsLineHandler.put(currPlayer, txt+currFrame.getThrowIndex()[0]+"    "+SPARE_SYMBOL+"    ");
    }

    private void handleNormal(int round, int thrw, String currPlayer, Frame currFrame, Map<String, String> pinfallsLineHandler) {
        String txt = pinfallsLineHandler.get(currPlayer);
        if(round < 10){
            pinfallsLineHandler.put(currPlayer, txt+currFrame.getThrowIndex()[0]+"    "+currFrame.getThrowIndex()[1]+"    ");
        } else if (thrw != 1){
            pinfallsLineHandler.put(currPlayer, txt+currFrame.getThrowIndex()[1]+"    "+currFrame.getThrowIndex()[2]+"    ");
        }
    }

    private void handleStrike(int round, String currPlayer, Map<String, String> pinfallsLineHandler) {
        String txt = pinfallsLineHandler.get(currPlayer);
        if(round < 10){
            pinfallsLineHandler.put(currPlayer, txt+"    "+STRIKE_SYMBOL+"    ");
        } else {
            pinfallsLineHandler.put(currPlayer, txt+STRIKE_SYMBOL+"    ");
        }
    }

    private void intializeThrows(Set<String> players) {
        players.forEach( player -> Arrays.stream(throwsPerPlayer.get(player)).forEach(thrw -> thrw = 0));
    }

    /**
     * Prints the score sheet of the game.
     */
    public void printGame(){
        Arrays.stream(linesToPrint).forEach(System.out::println);
    }
}
