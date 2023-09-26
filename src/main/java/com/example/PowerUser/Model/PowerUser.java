package com.example.PowerUser.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "power_users", schema = "powerful_users", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "username"}))
public class PowerUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int id;

    @NotBlank
    @Length(min = 6, max = 30, message = "Name should be at least 6 characters")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String username;

    private String password;

    @NotEmpty
    private String address;

    @Min(value = 18, message = "Age should be less than 18")
    @Max(value = 65, message = "Age should not be more than 65")
    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

    public PowerUser(){
    }

    public PowerUser(int id, String fullName, String email, String address, int age) {
        this.id = id;
        this.fullName = fullName;
        this.username = email;
        this.address = address;
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
