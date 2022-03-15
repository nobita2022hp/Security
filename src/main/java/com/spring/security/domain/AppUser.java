package com.spring.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    private Long userId;

    private String userName;
    private String encryptedPassword;
}
