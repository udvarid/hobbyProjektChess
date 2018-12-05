package playground;

import chess.Figure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Playground {

    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();

        dogs.add(new Boxex("Rocky", 5, 10));
        dogs.add(new Boxex("Frakk", 3, 20));
        dogs.add(new Boxex("Rumcájsz", 2, 18));

        List<Dog> dogs2 = new ArrayList<>();

        System.out.println("Original");
        for (Dog dog : dogs) {

            dogs2.add((Dog) dog.clone());
            dog.setAge(dog.getAge() + 1);
            dog.getLegs().setBackLeft(0);

            System.out.println(dog);
            System.out.println(dog.getLegs());


        }

        System.out.println("Clone version");
        for (Dog dog : dogs2) {
            System.out.println(dog);
            System.out.println(dog.getLegs());
        }

        System.out.println("----------original----------");
        for (Finger fingers : dogs.get(0).getLegs().getFingers()) {
            fingers.setNumber(fingers.getNumber() * 10);
        }
        for (Finger fingers : dogs.get(0).getLegs().getFingers()) {
            System.out.println(fingers);
        }
        System.out.println("-----------clone----------");
        for (Finger fingers : dogs2.get(0).getLegs().getFingers()) {
            System.out.println(fingers);
        }


    }
}
