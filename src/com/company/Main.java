package com.company;

import com.sun.javafx.css.parser.LadderConverter;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Person person = new Person();
    static ArrayList<Person> personArrayList = new ArrayList<Person>();

    public static void main(String[] args) {
        int menu;
        int id;
        String fName;
        String lName;
        boolean sex;
        int bDate;
        int dDate;
        int spouseId;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("insert menu item");
            menu = scanner.nextInt();
            switch (menu) {
                case 1:
                    Scanner scanner1 = new Scanner(System.in);
                    Scanner scanner2 = new Scanner(System.in);
                    Scanner scanner3 = new Scanner(System.in);
                    Scanner scanner4 = new Scanner(System.in);
                    Scanner scanner5 = new Scanner(System.in);
                    Scanner scanner6 = new Scanner(System.in);
                    Scanner scanner7 = new Scanner(System.in);
                    System.out.println("insert the id");
                    id = scanner1.nextInt();
                    System.out.println("insert the firstname");
                    fName = scanner2.nextLine();
                    System.out.println("insert the lastname");
                    lName = scanner3.nextLine();
                    System.out.println("insert the gender by true=male & false=female");
                    sex = scanner4.nextBoolean();
                    System.out.println("insert the birth date ");
                    bDate = scanner5.nextInt();
                    System.out.println("insert the die date");
                    dDate = scanner6.nextInt();
                    System.out.println("insert the spouse id");
                    spouseId = scanner7.nextInt();
                    adder(id, fName, lName, sex, bDate, dDate, spouseId);
                    break;
                case 2:
                    for (Person print : personArrayList) {
                        System.out.println(print.getId());
                        System.out.println(print.getfName());
                        System.out.println(print.getlName());
                        System.out.println(print.isSex());
                        System.out.println(print.getbDate());
                        System.out.println(print.getdDate());
                        System.out.println(print.getSpouseId());


                    }
                    break;
                default:
                    System.out.println("not in the list");
            }


        }
    }


    public static void adder(int id, String fName, String lName, boolean sex, int bDate, int dDate, int spouseId) {
        Person p = new Person();
        p.setId(id);
        p.setfName(fName);
        p.setlName(lName);
        p.setSex(sex);
        p.setbDate(bDate);
        p.setdDate(dDate);
        p.setSpouseId(spouseId);

        personArrayList.add(p);
    }
}
