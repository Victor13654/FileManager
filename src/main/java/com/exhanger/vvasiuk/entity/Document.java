package com.exhanger.vvasiuk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "document")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "documentName")
    private String documentName;

    public Document(String documentName) {
        this.documentName = documentName;
    }
}
