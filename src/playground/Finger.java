package playground;

public class Finger implements Cloneable {
    private int number;

    public Finger() {
        this.number = (int) (Math.random() * 1000);
    }

    @Override
    protected Object clone() {
        Finger finger = null;
        try {
            finger = (Finger) super.clone();
        } catch (CloneNotSupportedException e) {
            finger =  new Finger();
        }

        return finger;
    }

    @Override
    public String toString() {
        return "Finger{" +
                "number=" + number +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
