package com.company;

import com.sun.javafx.css.parser.LadderConverter;
import com.sun.javafx.geom.Edge;

import javax.swing.plaf.IconUIResource;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Person person = new Person();
    static ArrayList<Person> personArrayList = new ArrayList<Person>();
    static ArrayList<Edges> edgesArrayList = new ArrayList<Edges>();


    public static void main(String[] args) {

        int menu;
        int id = 0;
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
                    System.out.println("the id is=" + id);
                    System.out.println("insert the firstname");
                    fName = scanner2.nextLine();
                    System.out.println("insert the lastname");
                    lName = scanner3.nextLine();
                    System.out.println("insert the gender by true=male & false=female");
                    sex = scanner4.nextBoolean();
                    System.out.println("insert the birth date ");
                    bDate = scanner5.nextInt();
                    System.out.println("insert the death date");
                    dDate = scanner6.nextInt();
                    System.out.println("insert the spouse id");
                    spouseId = scanner7.nextInt();
                    adder(id, fName, lName, sex, bDate, dDate, spouseId);
                    break;
                case 2:

                    printList();
                    break;
                case 3:
                    int[][] connection = new int[id][id];
                    System.out.println("connection matrix created!");
                    break;
                case 4:
                    System.out.println("setting connection!");
                    System.out.println("insert person1 and person2 to connect and choose relation");
                    Scanner scanner8 = new Scanner(System.in);
                    Scanner scanner9 = new Scanner(System.in);
                    Scanner scanner10 = new Scanner(System.in);


                default:
                    System.out.println("not in the list");
            }


        }
    }


    public static void printList() {
        for (Person print : personArrayList) {
            System.out.println("id=" + print.getId());
            System.out.println("fisrt name=" + print.getfName());
            System.out.println("last name=" + print.getlName());
            System.out.println("gender=" + print.isSex());
            System.out.println("birth date=" + print.getbDate());
            System.out.println("death date=" + print.getdDate());
            System.out.println("spouse id=" + print.getSpouseId());
            System.out.println("arraylist size=" + personArrayList.size());
            System.out.println("---------------");

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
    public static void relation(int id1 , int id2 , int relation){
        Edges e = new Edges();
        e.setFromId(id1);
        e.setToId(id2);
        e.setRelation(relation);

    }
}
