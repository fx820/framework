package com.realfinance.web.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_test")
@Data
public class Test {
    @Id
    private Integer id;

    @Column(name = "value")
    private String value;
}
