package kr.hnu.android_project1;

import java.io.Serializable;

public class Person implements Serializable {
    private String id;
    private String pw;
    private String name;

    public Person(String i, String p, String n) {
        id = i;
        pw = p;
        name = n;
    }
    public String getId() {
        return id;
    }
    public String getPw() {
        return pw;
    }
    public String getName() {
        return name;
    }
}
