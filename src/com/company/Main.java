package com.company;

import com.sun.javafx.css.parser.LadderConverter;

import java.util.ArrayList;

public class Main {
    static Person person = new Person();
    static ArrayList<Person> personArrayList = new ArrayList<Person>();

    public static void main(String[] args) {

        person.setName("mamad");
        person.setId(1);
        personArrayList.add(person);
        adder(1,"dan");
        adder(2,"ali");

        System.out.println(personArrayList.get(1).getName());
        System.out.println(personArrayList.get(2).getName());
    }


    public  static void adder(int id , String name) {
        Person p = new Person();
        p.setId(id);
        p.setName(name);
        personArrayList.add(p);
    }
}
