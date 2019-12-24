package ten.pin.bowling.score.app.gamecalculator;

public interface FrameInterface {
    int getTotal();
    int[] getThrowIndex();
    void setThrowIndex(int[] throwIndex);
}
