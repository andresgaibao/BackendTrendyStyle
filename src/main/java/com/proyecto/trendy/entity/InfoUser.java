package com.proyecto.trendy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "info_users")
public class InfoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lastname;
    private String cc;
    private String num_cel;
    private String city_of_residence;
    private String address;
    private Date birthday_date;

}
