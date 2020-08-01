package com.company;

import com.sun.deploy.security.SelectableSecurityManager;
import com.sun.javafx.geom.Edge;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Person person = new Person();
    static ArrayList<Person> personArrayList = new ArrayList<Person>();
    static ArrayList<Edges> edgesArrayList = new ArrayList<Edges>();


    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Dashtikh", "dashti1565");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Dashtikh", "dashti1565");
        Connection connection1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Dashtikh", "dashti1565");
        Connection conn1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Dashtikh", "dashti1565");
        PreparedStatement preparedStatement = connection.prepareStatement("insert into person (Id , fname , lName , SEX , bDate , dDate , spouseId) values (?,?,?,?,?,?,?)");
        PreparedStatement preparedStatement1 = connection1.prepareStatement("insert into Edges (fromid , toid , relation) values (?,?,?)");
        Statement statement = conn.createStatement();
        Statement statement1 = conn1.createStatement();

        int menu;
        int id = 0, id1 = 0, id2 = 0, relation = 0, id3 = 0, finder = 0, idfinder = 0;
        String fName;
        String lName;
        boolean sex;
        int bDate;
        int dDate;
        int spouseId;
        Scanner scanner = new Scanner(System.in);
        String sql = "SELECT ID , FNAME  , LNAME , SEX , BDATE , DDATE , SPOUSEID FROM person";
        String sql1 = "SELECT fromid , toid  , relation FROM edges";
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSet resultSet1 = statement1.executeQuery(sql1);
        while (resultSet.next()) {
            id = resultSet.getInt("ID");
            id3++;
            fName = resultSet.getString("FNAME");
            lName = resultSet.getString("LNAME");
            if (resultSet.getInt("SEX") == 1)
                sex = true;
            else sex = false;
            bDate = resultSet.getInt("BDATE");
            dDate = resultSet.getInt("DDATE");
            spouseId = resultSet.getInt("SPOUSEID");
            adder(id, fName, lName, sex, bDate, dDate, spouseId);
        }
        resultSet.close();
        while (resultSet1.next()) {
            id1 = resultSet1.getInt("FROMID");
            id2 = resultSet1.getInt("TOID");
            relation = resultSet1.getInt("RELATION");
            relation(id1, id2, relation);

        }
        resultSet1.close();
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
                    id3++;
                    System.out.println("the id is=" + id3);
                    preparedStatement.setLong(1, id3);
                    System.out.println("insert the firstname");
                    fName = scanner2.nextLine();
                    preparedStatement.setString(2, fName);
                    System.out.println("insert the lastname");
                    lName = scanner3.nextLine();
                    preparedStatement.setString(3, lName);
                    System.out.println("insert the gender by true=male & false=female");
                    sex = scanner4.nextBoolean();
                    if (sex == true) {
                        preparedStatement.setLong(4, 1);
                    } else preparedStatement.setLong(4, 0);
                    System.out.println("insert the birth date ");
                    bDate = scanner5.nextInt();
                    preparedStatement.setLong(5, bDate);
                    System.out.println("insert the death date");
                    dDate = scanner6.nextInt();
                    preparedStatement.setLong(6, dDate);
                    System.out.println("insert the wife id");
                    spouseId = scanner7.nextInt();
                    preparedStatement.setLong(7, spouseId);
                    preparedStatement.executeUpdate();

                    adder(id3, fName, lName, sex, bDate, dDate, spouseId);
                    if (spouseId != 0) {
                        relation(id3, spouseId, 2);
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
                    preparedStatement1.setLong(1, id1);
                    id2 = scanner9.nextInt();
                    preparedStatement1.setLong(2, id2);
                    relation = scanner10.nextInt();
                    preparedStatement1.setLong(3, relation);

                    relation(id1, id2, relation);
                    preparedStatement1.executeUpdate();
                    break;
                case 4:
                    printEdgeList();
                    break;
                case 5:
                    System.out.println("search for child numbers");
                    int[] a = new int[id + 1];
                    int[] b = new int[id + 1];
                    int swap, j, k;

                    for (int i = 1; i <= id3; i++) {
                        a[i] = searchForChildren(i);
                        b[i] = searchForChildren(i);
                    }
                    for (j = 1; j < id3; j++) {
                        if (b[j] > b[j + 1]) {
                            swap = b[j + 1];
                            b[j + 1] = b[j];
                            b[j] = swap;
                        }
                    }
                    for (k = 1; k <= id3; k++) {
                        if (a[k] == b[id]) {
                            System.out.println("id " + k + " has the most child with " + a[k] + " child");
                        }
                    }

                    break;
                case 6:
                    Scanner scanner11 = new Scanner(System.in);
                    System.out.println("choose the relation that you wanna find id : 1-father 2-mother");
                    finder = scanner11.nextInt();
                    Scanner scanner12 = new Scanner(System.in);
                    switch (finder) {
                        case 1:

                            System.out.println("father finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            System.out.println(fatherFinder(idfinder));
                            break;
                        case 2:
                            System.out.println("mother finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            System.out.println(motherFinder(idfinder));
                            break;
                        case 3:
                            System.out.println("amoo finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            System.out.println(amooFinder(idfinder));
                            break;
                        case 4:
                            System.out.println("amme finder, insert id: ");
                            idfinder=scanner12.nextInt();
                            System.out.println(ammeFinder(idfinder));
                            break;
                        default:
                            System.out.println("not in the list");
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
                System.out.println(print.getFromId() + " is wife of" + print.getToId());
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

    public static int fatherFinder(int id) {
        for (Edges father : edgesArrayList) {
            if (father.getFromId() == id && father.getRelation() == 1)
                return father.getToId();

        }

        return 0;
    }

    public static int motherFinder(int id) {
        for (Edges mother : edgesArrayList) {
            if (mother.getToId() == fatherFinder(id) && mother.getRelation() == 2)
                return mother.getFromId();
        }
        return 0;
    }

    public static String amooFinder(int id) {
        return sonFinder(fatherFinder(fatherFinder(id)),id);

    }
    public static String ammeFinder(int id){
        return girlFinder(fatherFinder(fatherFinder(id)),id);
    }

    public static String sonFinder(int id,int node) {
        String sons="ids:";
        int counter=0;
        for (Edges son : edgesArrayList) {
            if (son.getToId() == id && son.getRelation()==1 )
                if (son.getFromId()!=fatherFinder(node) && personArrayList.get(son.getFromId()-1).isSex()==true)
                sons=sons+" "+son.getFromId();
        }
         return sons;
    }
    public static String girlFinder(int id,int node) {
        String girls="ids:";
        int counter=0;
        for (Edges girl : edgesArrayList) {
            if (girl.getToId() == id && girl.getRelation()==1 )
                if (girl.getFromId()!=fatherFinder(node) && personArrayList.get(girl.getFromId()-1).isSex()==false)
                    girls=girls+" "+girl.getFromId();
        }
        return girls;
    }

}
