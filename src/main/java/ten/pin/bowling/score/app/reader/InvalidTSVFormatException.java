package ten.pin.bowling.score.app.reader;

public class InvalidTSVFormatException extends Exception {
    public InvalidTSVFormatException(){
        super("The given file is not a 'Tab Separated Value' file.");
    }
}
