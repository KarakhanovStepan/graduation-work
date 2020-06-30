package ru.home.main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "global_settings")
@Data
public class GlobalSetting
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String value;
}
