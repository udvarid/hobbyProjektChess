package playground;

import java.util.ArrayList;
import java.util.List;

public class Legs implements Cloneable {

    private int frontLeft = 1;
    private int frontRight = 1;
    private int backLeft = 1;
    private int backRight = 1;
    private List<Finger> fingers = new ArrayList<>();

    public Legs() {
        for (int i = 1; i<= 10; i++) {
            fingers.add(new Finger());
        }
    }

    public int getFrontLeft() {
        return frontLeft;
    }

    @Override
    public String toString() {
        return "Legs{" +
                "frontLeft=" + frontLeft +
                ", frontRight=" + frontRight +
                ", backLeft=" + backLeft +
                ", backRight=" + backRight +
                '}';
    }

    public void setFrontLeft(int frontLeft) {
        this.frontLeft = frontLeft;
    }

    public int getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(int frontRight) {
        this.frontRight = frontRight;
    }

    public int getBackLeft() {
        return backLeft;
    }

    public List<Finger> getFingers() {
        return fingers;
    }

    public void setFingers(List<Finger> fingers) {
        this.fingers = fingers;
    }

    public void setBackLeft(int backLeft) {
        this.backLeft = backLeft;
    }

    public int getBackRight() {
        return backRight;
    }

    public void setBackRight(int backRight) {
        this.backRight = backRight;
    }

    @Override
    protected Object clone(){
        Legs legs = null;
        try {
            legs =  (Legs) super.clone();
        } catch (CloneNotSupportedException e) {
            legs = new Legs();
        }


        for (Finger finger : legs.fingers) {
            finger = (Finger) finger.clone();
        }

        return legs;
    }
}

