package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.enumrole.UserRole;
import com.fanta.moneywithsoul.validator.OnlyLetters;
import com.fanta.moneywithsoul.validator.PastOrPresentDate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/** The type User. */
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
    @Column(name = "email", unique = true)
    private String email;

    @Size(min = 8, message = "Пароль повинен містити мінімум 8 символів")
    @Column(name = "password_hash")
    private String passwordHash;

    @PastOrPresentDate
    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserRole userStatus = UserRole.active;

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password hash.
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets password hash.
     *
     * @param passwordHash the password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets registered at.
     *
     * @return the registered at
     */
    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    /** Sets registered at. */
    public void setRegisteredAt() {
        this.registeredAt = Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * Gets user status.
     *
     * @return the user status
     */
    public UserRole getUserStatus() {
        return userStatus;
    }

    /**
     * Sets user status.
     *
     * @param userStatus the user status
     */
    public void setUserStatus(UserRole userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId the user id
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email
     * @param passwordHash the password hash
     * @param registeredAt the registered at
     * @param userStatus the user status
     */
    public User(
            Long userId,
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            Timestamp registeredAt,
            UserRole userStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.registeredAt = registeredAt;
        this.userStatus = userStatus;
    }

    /** Instantiates a new User. */
    public User() {}
}
