package playground;

public class Boxex extends Dog {

    private int numberOfTricks;
    private int numberOfBoxerFriends;

    public int getNumberOfTricks() {
        return numberOfTricks;
    }

    public void setNumberOfTricks(int numberOfTricks) {
        this.numberOfTricks = numberOfTricks;
    }

    public Boxex(String name, int age, int numberOfTricks) {
        super(name, age);
        this.numberOfTricks = numberOfTricks;
    }

    public int getNumberOfBoxerFriends() {
        return numberOfBoxerFriends;
    }

    public void boxerVauu() {
        System.out.println("boxervau");
        numberOfBoxerFriends = 5;
    }



    @Override
    public String toString() {
        return super.getName() + " age of " + super.getAge() +". Number of tricks: " + numberOfTricks;
    }
}
