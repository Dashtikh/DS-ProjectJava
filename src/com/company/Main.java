package com.company;

import com.sun.deploy.security.SelectableSecurityManager;
import com.sun.javafx.geom.Edge;
import jdk.internal.dynalink.beans.StaticClass;
import oracle.jdbc.proxy.annotation.Pre;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.WStringSeqHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static Person person = new Person();
    static ArrayList<Person> personArrayList = new ArrayList<Person>();
    static ArrayList<Edges> edgesArrayList = new ArrayList<Edges>();
    static ArrayList<Integer> route = new ArrayList<Integer>();


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
        int v = id3 + 1;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(v);
        for (int i = 1; i <= v; i++) {
            adj.add(new ArrayList<Integer>());
        }
        int counter = 0;
        for (Edges etesal : edgesArrayList) {
            addEdge(adj, etesal.getFromId(), etesal.getToId());
            counter++;
            if (counter == v - 1)
                break;
        }


        while (true) {
            System.out.println("insert menu item: 1 add person , 2 print persons , 3 set relation , 4 print relation , 5 the most children , 6 finding by relation , 7 relation between two id ");
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
                    System.out.println("set father-child relation ( 1=p1 is child of p2)");
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
                            System.out.println("id " + k + " has the most child with " + a[k] + " children");
                        }
                    }

                    break;
                case 6:
                    Scanner scanner11 = new Scanner(System.in);
                    System.out.println("choose the relation that you wanna find id : 1-father 2-mother 3-amoo 4-amme 5-brother 6-sister 7-daee 8-khale 9-bajenaq 10-jari 11-havoo");
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
                            for (int i = 0; i < 10; i++) {
                                if (amooFinder(idfinder)[i] != 0)
                                    System.out.println(amooFinder(idfinder)[i]);
                            }
                            break;
                        case 4:
                            System.out.println("amme finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            for (int i = 0; i < 10; i++) {
                                if (ammeFinder(idfinder)[i] != 0)
                                    System.out.println(ammeFinder(idfinder)[i]);
                            }
                            break;
                        case 5:
                            System.out.println("Brother finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            for (int i = 0; i < 10; i++) {
                                if (brotherFinder(idfinder)[i] != 0)
                                    System.out.println(brotherFinder(idfinder)[i]);
                            }
                            break;
                        case 6:
                            System.out.println("Sister finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            for (int i = 0; i < 10; i++) {
                                if (sisterFinder(idfinder)[i] != 0)
                                    System.out.println(sisterFinder(idfinder)[i]);
                            }

                            break;
                        case 7:
                            System.out.println("Daee finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            for (int i = 0; i < 10; i++) {
                                if (daeeFinder(idfinder)[i] != 0)
                                    System.out.println(daeeFinder(idfinder)[i]);
                            }
                            break;
                        case 8:
                            System.out.println("Khale finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            for (int i = 0; i < 10; i++) {
                                if (khaleFinder(idfinder)[i] != 0)
                                    System.out.println(khaleFinder(idfinder)[i]);
                            }

                            break;
                        case 9:
                            System.out.println("Bajenaq finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            for (int i = 0; i < 10; i++) {
                                if (bajenaqFinder(idfinder)[i] != 0)
                                    System.out.println(bajenaqFinder(idfinder)[i]);
                            }
                            break;
                        case 10:
                            System.out.println("jari finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            for (int i = 0; i < 10; i++) {
                                if (jari(idfinder)[i] != 0)
                                    System.out.println(jari(idfinder)[i]);
                            }
                            break;
                        case 11:
                            System.out.println("havoo finder, insert id: ");
                            idfinder = scanner12.nextInt();
                            for (int i = 0; i < 10; i++) {
                                if (havoo(idfinder)[i] != 0)
                                    System.out.println(havoo(idfinder)[i]);
                            }

                            break;

                        default:
                            System.out.println("not in the list");
                    }

                    break;

                case 7:
                    System.out.println("relation between two id, insert id1 and id2: ");
                    Scanner scanner13 = new Scanner(System.in);
                    Scanner scanner14 = new Scanner(System.in);
                    int i = scanner13.nextInt(), l = scanner14.nextInt();
                    for (int y = 0; y < v; y++) {
                        if (printShortestDistance(adj, i, l, v)[y] != 0)
                            route.add(printShortestDistance(adj, i, l, v)[y]);
                    }
                    for (int x = 0; x < route.size(); x++)
                        System.out.print(route.get(x) + " ");
                    System.out.println();
                    System.out.println(l + " is " + relationBetween(route) + " of " + i);


                    break;
                default:
                    System.out.println("not in the list");
            }


        }
    }

    public static int searchForChildren(int id) {
        int counter = 0;
        for (Edges edges : edgesArrayList) {
            //farzandan pesar
            if (edges.getToId() == id && edges.getRelation() == 1) {
                counter++;
                counter = counter + searchForChildren(edges.getFromId());
            }
            if (edges.getFromId()==id && edges.getRelation()==2){
                counter=counter+searchForChildren(edges.getToId());
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

    public static int shoharFinder(int id) {
        for (Edges shohar : edgesArrayList) {
            if (shohar.getFromId() == id && shohar.getRelation() == 2)
                return shohar.getToId();
        }
        return 0;
    }

    public static int zanFinder(int id) {
        for (Edges shohar : edgesArrayList) {
            if (shohar.getToId() == id && shohar.getRelation() == 2)
                return shohar.getFromId();
        }
        return 0;
    }


    public static int[] amooFinder(int id) {
        return sonFinder(fatherFinder(fatherFinder(id)), id);
    }

    public static int[] ammeFinder(int id) {
        return daughterFinder(fatherFinder(fatherFinder(id)), id);
    }


    public static int[] sonFinder(int id, int node) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges son : edgesArrayList) {
            if (son.getToId() == id && son.getRelation() == 1)
                if (son.getFromId() != fatherFinder(node) && personArrayList.get(son.getFromId() - 1).isSex() == true) {
                    a[counter] = son.getFromId();
                    counter++;
                }

        }
        return a;

    }

    public static int[] sonFinder1(int id, int node) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges son : edgesArrayList) {
            if (son.getToId() == id && son.getRelation() == 1)
                if (son.getFromId() != node && personArrayList.get(son.getFromId() - 1).isSex() == true) {
                    a[counter] = son.getFromId();
                    counter++;
                }

        }
        return a;

    }

    public static int[] sonFinder2(int id, int node) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges son : edgesArrayList) {
            if (son.getToId() == id && son.getRelation() == 1)
                if (son.getFromId() != motherFinder(node) && personArrayList.get(son.getFromId() - 1).isSex() == true) {
                    a[counter] = son.getFromId();
                    counter++;
                }

        }
        return a;

    }

    public static int[] sonFinder3(int id) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges son : edgesArrayList) {
            if (son.getToId() == id && son.getRelation() == 1)
                if (personArrayList.get(son.getFromId() - 1).isSex() == true) {
                    a[counter] = son.getFromId();
                    counter++;
                }

        }
        return a;

    }

    public static int[] daughterFinder(int id, int node) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges daughter : edgesArrayList) {
            if (daughter.getToId() == id && daughter.getRelation() == 1)
                if (daughter.getFromId() != fatherFinder(node) && personArrayList.get(daughter.getFromId() - 1).isSex() == false) {
                    a[counter] = daughter.getFromId();
                    counter++;
                }
        }
        return a;

    }

    public static int[] daughterFinder1(int id, int node) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges daughter : edgesArrayList) {
            if (daughter.getToId() == id && daughter.getRelation() == 1)
                if (daughter.getFromId() != node && personArrayList.get(daughter.getFromId() - 1).isSex() == false) {
                    a[counter] = daughter.getFromId();
                    counter++;
                }
        }
        return a;

    }

    public static int[] daughterFinder2(int id, int node) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges daughter : edgesArrayList) {
            if (daughter.getToId() == id && daughter.getRelation() == 1)
                if (daughter.getFromId() != motherFinder(node) && personArrayList.get(daughter.getFromId() - 1).isSex() == false) {
                    a[counter] = daughter.getFromId();
                    counter++;
                }
        }
        return a;

    }

    public static int[] daughterFinder3(int id) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges daughter : edgesArrayList) {
            if (daughter.getToId() == id && daughter.getRelation() == 1)
                if (personArrayList.get(daughter.getFromId() - 1).isSex() == false) {
                    a[counter] = daughter.getFromId();
                    counter++;
                }
        }
        return a;

    }

    public static int[] brotherFinder(int id) {
        return sonFinder1(fatherFinder(id), id);
    }

    public static int[] sisterFinder(int id) {
        return daughterFinder1(fatherFinder(id), id);
    }

    public static int[] daeeFinder(int id) {
        return sonFinder2(fatherFinder(motherFinder(id)), id);
    }

    public static int[] khaleFinder(int id) {
        return daughterFinder2(fatherFinder(motherFinder(id)), id);
    }

    public static int[] bajenaqFinder(int id) {
        int[] a = new int[10];
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            if (shoharFinder(daughterFinder2(fatherFinder(zanFinder(id)), id)[i]) != id && shoharFinder(daughterFinder2(fatherFinder(zanFinder(id)), id)[i]) != 0) {
                a[counter] = shoharFinder(daughterFinder2(fatherFinder(zanFinder(id)), id)[i]);
                counter++;
            }
        }
        return a;
    }

    public static int[] jari(int id) {
        int[] a = new int[10];
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            if (zanFinder(sonFinder(fatherFinder(shoharFinder(id)), id)[i]) != id && zanFinder(sonFinder(fatherFinder(shoharFinder(id)), id)[i]) != 0){
                a[counter] = zanFinder(sonFinder(fatherFinder(shoharFinder(id)), id)[i]);}
        }
        return a;
    }

    public static int[] havoo(int id) {
        int[] a = new int[10];
        int counter = 0;
        for (Edges hav : edgesArrayList) {
            if (shoharFinder(id) == hav.getToId() && hav.getFromId() != id && hav.getRelation() == 2) {
                a[counter] = hav.getFromId();
                counter++;
            }
        }
        return a;
    }

    private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) {
        adj.get(i).add(j);
        adj.get(j).add(i);
    }

    private static int[] printShortestDistance(
            ArrayList<ArrayList<Integer>> adj,
            int s, int dest, int v) {
        int[] a = new int[v];
        int[] b = new int[v];
        // from s
        int pred[] = new int[v];
        int dist[] = new int[v];

        if (BFS(adj, s, dest, v, pred, dist) == false) {
            System.out.println("Given source and destination" + "are not connected");
            return b;
        }

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        //System.out.println("Shortest path length is: " + dist[dest]);

        //System.out.print("Path is: ");
        for (int i = path.size() - 1; i >= 0; i--) {
            //System.out.print(path.get(i) + " ");
            a[path.size() + 1 - i] = path.get(i);
        }
        return a;
    }

    private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src,
                               int dest, int v, int pred[], int dist[]) {

        LinkedList<Integer> queue = new LinkedList<Integer>();

        boolean visited[] = new boolean[v];

        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[src] = true;
        dist[src] = 0;
        queue.add(src);
        // bfs Algorithm
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }

    public static String relationBetween(ArrayList<Integer> route) {
        String relation = "";
        for (int i = route.size() - 1; i > 0; i--) {
            if (fatherFinder(route.get(0)) == route.get(i))
                return relation = relation + "pedar";
            else if (fatherFinder(fatherFinder(route.get(0))) == route.get(i))
                return relation = relation + "grandfather";
            else if (motherFinder(route.get(0)) == route.get(i))
                return relation = relation + "madar";
            else if (fatherFinder(motherFinder(route.get(0))) == route.get(i))
                return relation = relation + "grandfather";
            else if (motherFinder(fatherFinder(route.get(0))) == route.get(i))
                return relation = relation + "grandmother";
            else if (motherFinder(motherFinder(route.get(0))) == route.get(i))
                return relation = relation + "grandmother";
            for (int j = 0; j < 10; j++) {
                if (daeeFinder(route.get(0))[j] == route.get(i))
                    return relation = relation + "daee";
            }
            for (int j = 0; j < 10; j++) {
                if (brotherFinder(route.get(0))[j] == route.get(i))
                    return relation = relation + "baradar";
            }
            for (int j = 0; j < 10; j++) {
                if (sisterFinder(route.get(0))[j] == route.get(i))
                    return relation = relation + "khahar";
            }
            for (int j = 0; j < 10; j++) {
                if (khaleFinder(route.get(0))[j] == route.get(i))
                    return relation = relation + "khale";
            }
            for (int j = 0; j < 10; j++) {
                if (amooFinder(route.get(0))[j] == route.get(i))
                    return relation = relation + "amoo";
            }
            for (int j = 0; j < 10; j++) {
                if (ammeFinder(route.get(0))[j] == route.get(i))
                    return relation = relation + "amme";
            }
            for (int j = 0; j < 10; j++) {
                if (sonFinder3(route.get(0))[j] == route.get(i))
                    return relation = relation + "pesar";
            }
            for (int j = 0; j < 10; j++) {
                if (daughterFinder3(route.get(0))[j] == route.get(i))
                    return relation = relation + "dokhtar";
            }
            for (int j = 0; j < 10; j++) {
                if (bajenaqFinder(route.get(0))[j] == route.get(i))
                    return relation = relation + "bajenaq ";
            }
            for (int j = 0; j < 10; j++) {
                if (jari(route.get(0))[j] == route.get(i))
                    return relation = relation + "jari ";
            }
            for (int j = 0; j < 10; j++) {
                if (brotherFinder(route.get(i - 1))[j] == route.get(i))
                    relation = relation + "baradar ";
            }
            for (int j = 0; j < 10; j++) {
                if (sisterFinder(route.get(i - 1))[j] == route.get(i))
                    relation = relation + "khahar ";
            }
            for (int j = 0; j < 10; j++) {
                if (sonFinder3(route.get(i - 1))[j] == route.get(i))
                    relation = relation + "peasr ";
            }
            for (int j = 0; j < 10; j++) {
                if (daughterFinder3(route.get(i - 1))[j] == route.get(i))
                    relation = relation + "dokhtar ";
            }

            if (shoharFinder(route.get(i - 1)) == route.get(i))
                relation = relation + "shohar ";

            if (fatherFinder(route.get(i - 1)) == route.get(i))
                relation = relation + "pedar ";
            if (zanFinder(route.get(i - 1)) == route.get(i))
                relation = relation + "zan ";





        }
        return relation;
    }


}
