package ru.home.main.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tag
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nsme;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNsme() {
        return nsme;
    }

    public void setNsme(String nsme) {
        this.nsme = nsme;
    }
}
