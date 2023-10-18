package kz.bitlab.models;

import java.time.LocalDate;

public class User {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private LocalDate birthDate;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User() {
    }

    public User(Long id, String email, String password, String fullName, LocalDate birthDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.birthDate = birthDate;
    }
}
