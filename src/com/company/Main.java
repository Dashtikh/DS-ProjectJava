package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Person person = new Person();
    static ArrayList<Person> personArrayList = new ArrayList<Person>();
    static ArrayList<Edges> edgesArrayList = new ArrayList<Edges>();


    public static void main(String[] args) {

        int menu;
        int id = 0, id1 = 0, id2 = 0, relation = 0;
        String fName;
        String lName;
        boolean sex;
        int bDate;
        int dDate;
        int spouseId;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("insert menu item: 1 add person , 2 print persons , 3 set relation , 4 print relation , 5 the most children");
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
                    id++;
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
                    if (spouseId != 0) {
                        relation(id, spouseId, 2);
                    }
                    break;
                case 2:
                    //print list
                    printList();
                    break;
                case 3:
                    //set relation
                    System.out.println("set father-son relation ( 1=p1 is son of p2)");
                    Scanner scanner8 = new Scanner(System.in);
                    Scanner scanner9 = new Scanner(System.in);
                    Scanner scanner10 = new Scanner(System.in);
                    id1 = scanner8.nextInt();
                    id2 = scanner9.nextInt();
                    relation = scanner10.nextInt();
                    relation(id1, id2, relation);

                    break;
                case 4:
                    printEdgeList();
                    break;
                case 5:
                    System.out.println("search for child numbers");
                    int[] a = new int[id + 1];
                    int[] b = new int[id + 1];
                    int swap, j,k;

                    for (int i = 1; i <= id; i++) {
                        a[i] = searchForChildren(i);
                        b[i] = searchForChildren(i);
                    }
                    for (j = 1; j < id; j++) {
                        if (b[j] > b[j + 1]) {
                            swap = b[j + 1];
                            b[j + 1] = b[j];
                            b[j] = swap;
                        }
                    }
                    for (k=1;k<=id;k++){
                        if (a[k]==b[id]){
                            System.out.println("id "+k+" has the most child with "+a[k]+" child");
                        }
                    }

                    break;


                default:
                    System.out.println("not in the list");
            }


        }
    }

    public static int searchForChildren(int id) {
        int counter = 0;
        for (Edges edges : edgesArrayList) {
            if (edges.getToId() == id && edges.getRelation() == 1) {
                counter++;
                counter = counter + searchForChildren(edges.getFromId());
            }

        }

        return counter;
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

    public static void printEdgeList() {
        for (Edges print : edgesArrayList) {
            if (print.getRelation() == 2) {
                System.out.println(print.getToId() + " is wife of" + print.getFromId());
            }
            if (print.getRelation() == 1) {
                System.out.println(print.getFromId() + " is son of" + print.getToId());
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

    public static void relation(int id1, int id2, int relation) {
        Edges e = new Edges();
        e.setFromId(id1);
        e.setToId(id2);
        e.setRelation(relation);
        edgesArrayList.add(e);

    }
}
