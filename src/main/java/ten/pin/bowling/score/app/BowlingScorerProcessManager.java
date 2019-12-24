package ten.pin.bowling.score.app;

import ten.pin.bowling.score.app.bowlinggame.BowlingGame;
import ten.pin.bowling.score.app.gamecalculator.GameCalculator;
import ten.pin.bowling.score.app.reader.FileReader;
import ten.pin.bowling.score.app.reader.InvalidTSVFormatException;
import ten.pin.bowling.score.app.validator.ContentException;

import java.io.IOException;

public class BowlingScorerProcessManager {
    public static void main(String[] args) {
        try {
            BowlingGame bowlingGame = new BowlingGame(new FileReader().read(args[0]));
            System.out.println("The given bowling game has a valid format");
            GameCalculator gameCalculator = new GameCalculator(bowlingGame);
            gameCalculator.printGame();
        } catch (IOException e){
            System.out.println("There was an error when trying to read the given bowling game: "+ e.getMessage());
        } catch (ContentException e){
            System.out.println(e.getMessage());
        } catch (InvalidTSVFormatException e){
            System.out.println(e.getMessage());
        }
    }
}
