package com.marcin.ania.ToDoAPP.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "ImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @Lob
    @Column(name = "imagedata")
    private byte[] imageData;

    @OneToOne(mappedBy = "avatarData", fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    private UserInfo userinfo;
}
