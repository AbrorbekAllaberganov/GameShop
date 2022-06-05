package com.example.Game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Attachment implements Serializable {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    protected UUID id;

    private String link;

    private String uploadPath;

    private String hashId;

    private String name;

    private Long fileSize;

    private String contentType;

    private String extension;

}
