package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.validator.OnlyLetters;
import com.fanta.moneywithsoul.validator.PastOrPresentDate;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @NotEmpty(message = "Імя не може бути порожнім")
    @OnlyLetters(message = "Імя може бути вказано тільки в буквах")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Прізвище не може бути порожнім")
    @OnlyLetters(message = "Прізвище може бути вказано тільки в буквах")
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Електронна адреса не може бути пустою")
    @Email(message = "Введіть дійсну електронну адресу")
    @Column(name = "email")
    private String email;
    @Size(min = 8, message = "Пароль повинен містити мінімум 8 символів")
    @Column(name = "password_hash")
    private String passwordHash;
    @PastOrPresentDate
    @Column(name = "registered_at")
    private Timestamp registeredAt;
    @Pattern(regexp = "^(active|inactive|admin)$", message = "Статус користувача має бути активний, виключений або адмін")
    @Column(name = "user_status")
    private String userStatus;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt() {
        this.registeredAt =Timestamp.valueOf(LocalDateTime.now());
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public User(
            Long userId,
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            Timestamp registeredAt,
            String userStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.registeredAt = registeredAt;
        this.userStatus = userStatus;
    }
    public User() {}
}
