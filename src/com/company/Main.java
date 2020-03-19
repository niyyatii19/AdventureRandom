package com.company;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static Location location = new Location();

    public static void main(String[] args) throws IOException {



        Map<String, String > vocabulary = new HashMap<>();
        vocabulary.put("QUIT","Q");
        vocabulary.put("NORTH","N");
        vocabulary.put("EAST","E");
        vocabulary.put("WEST","W");
        vocabulary.put("SOUTH","S");
        vocabulary.put("DOWN", "D");
        vocabulary.put("UP","U");

       Locations currentLocation = location.getlocation(1);
        while (true) {
            System.out.println(currentLocation.getDescription());

            if (currentLocation.getLocationId() == 0) {
                break;
            }
            Map<String, Integer> exits = currentLocation.getExits();
            System.out.print("Available exits are: ");
            for(String exit: exits.keySet()){
                System.out.print(exit +",");
            }
            System.out.println();
            String direction = scanner.nextLine().toUpperCase();

            if(direction.length()> 1){
                String[] words = direction.split(" ");
                for(String word: words){
                    if(vocabulary.containsKey(word)){
                        direction = vocabulary.get(word);
                    }
                }
            }
            if(exits.containsKey(direction)){
               currentLocation = location.getlocation(currentLocation.getExits().get(direction));
            }  else{
               System.out.println("You can not go into that direction");
            }

            location.close();
        }
    }
}
