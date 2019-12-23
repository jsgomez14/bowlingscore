package gamecalculator;

import java.util.Arrays;

public class Frame implements FrameInterface{
    private int[] throwIndex;

    public Frame(){
        throwIndex= new int[3];
        Arrays.stream(throwIndex).forEach( thrw -> thrw = -1);
    }

    public int getTotal(){
        return Arrays.stream(throwIndex).sum();
    }
    public int[] getThrowIndex() {
        return throwIndex;
    }

    public void setThrowIndex(int[] throwIndex) {
        this.throwIndex = throwIndex;
    }
}
