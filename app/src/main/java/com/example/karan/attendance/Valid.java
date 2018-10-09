package com.example.karan.attendance;

/**
 * Created by sai on 3/11/2018.
 */

public class Valid {
    String teacherId;
    String class1;
    String subject1;
    String Roll1;
    String Roll2;
    String Roll3;

    public Valid(String teacherId, String class1, String subject1, String roll1, String roll2, String roll3) {
        this.teacherId = teacherId;
        this.class1 = class1;
        this.subject1 = subject1;
        Roll1 = roll1;
        Roll2 = roll2;
        Roll3 = roll3;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getClass1() {
        return class1;
    }

    public String getSubject1() {
        return subject1;
    }

    public String getRoll1() {
        return Roll1;
    }

    public String getRoll2() {
        return Roll2;
    }

    public String getRoll3() {
        return Roll3;
    }

}
