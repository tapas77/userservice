package com.auth1.auth.learning.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Token extends BaseModel {
    @OneToOne
    private User user;
    private String value;
    private Date createdAt;
    private Date expireAt;
    private boolean deleted;
}
