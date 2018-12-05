package playground;

public class Dog implements Cloneable{

    private String name;
    private int age;
    private Legs legs;

    public Legs getLegs() {
        return legs;
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
        this.legs = new Legs();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Object clone() {
        Dog dog = null;
        try {
            dog = (Dog) super.clone();
        } catch (CloneNotSupportedException e) {
            dog = new Dog(this.name, this.age);
        }
        dog.legs = (Legs) dog.legs.clone();
        return dog;
    }
}
