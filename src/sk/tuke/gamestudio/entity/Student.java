package sk.tuke.gamestudio.entity;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int ident;

    private String name;

    private int credits;

    public Student() {
    }

    public Student(String name, int credits){
        this.name = name;
        this.credits = credits;

    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

}
