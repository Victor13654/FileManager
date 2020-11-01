package com.exhanger.vvasiuk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table( name = "number")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilesNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int home;

    @Column
    private int dev;

    @Column
    private int test;

    @Column
    private int summ;
}
