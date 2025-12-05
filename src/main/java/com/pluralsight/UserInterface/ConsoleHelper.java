package com.pluralsight.UserInterface;

import java.util.List;
import java.util.Scanner;


public class ConsoleHelper {
    private static Scanner scanner = new Scanner(System.in);

    public static String promptForString(String prompt){
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public static int promptForInt(String prompt){
        boolean isValid = false;
        int result = 0;
        do{
            try{
                System.out.print(prompt + ": ");
                result = scanner.nextInt();
                scanner.nextLine();
                isValid = true;
            }
            catch(Exception ex){
                scanner.nextLine();
                System.out.println("Invalid Entry, please enter a whole number");
//                ex.printStackTrace();
            }
        } while (!isValid);
        //after the catch
        return result;

    }

    public static int promptForIntAlt(String prompt){
        int result = 0 ;
        try{
            System.out.print(prompt + ": ");
            result = scanner.nextInt();
            scanner.nextLine();
        }
        catch(Exception ex){
            scanner.nextLine();
            System.out.println("Invalid Entry, please enter a whole number");
        }
        //after the catch
        return result;
    }

    public static float promptForFloat(String prompt){
        System.out.print(prompt + ": ");
        float result = scanner.nextFloat();
        scanner.nextLine();
        return result;
    }

    public static long promptForLong(String prompt){
        System.out.print(prompt + ": ");
        long result = scanner.nextLong();
        scanner.nextLine();
        return result;
    }
    public static void displayList(List<?> values){
        int totalNumber = values.size();
        int numberPerRow = 5;
        int indexOfNext = 0;
        while(indexOfNext < totalNumber){
            for(int i = 1 ; i <= numberPerRow ; i++){
                if (indexOfNext >= totalNumber){
                    System.out.println();
                    return;
                }
                System.out.printf("%15s", values.get(indexOfNext++).toString());
            }
            System.out.println();
        }
    }
}