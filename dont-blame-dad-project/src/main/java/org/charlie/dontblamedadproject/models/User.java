package org.charlie.dontblamedadproject.models;


import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class User extends AbstractEntity {

//    @Id
//    @GeneratedValue
//    private int id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_-]{4,11}", message = "Invalid username")
    private String username;

    @NotNull
    private String pwHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//    @NotNull
//    @Email
//    private String email;
//
//    @NotNull
//    private String phonenumber;

    @ManyToMany
    private List<Child> children;

    public User() {}


    public User(String username, String password) {
        this.username = username;
        this.pwHash = hashPassword(password);
    }

//    public int getId() {
//        return id;
//    }

    public void addChild(Child child) { children.add(child);}

    public List<Child> getChildren() { return children; }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {this.username = username;}

    private static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//
//    public String getPhonenumber() {
//        return phonenumber;
//    }
//
//    public void setPhonenumber(String phonenumber) {
//        this.phonenumber = phonenumber;
//    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

}