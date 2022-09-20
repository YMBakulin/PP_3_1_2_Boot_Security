package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name="users")
public class User implements UserDetails{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is required")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 characters")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 30, message = "fullname should be between 2 and 30 characters")
    @Column(name = "fullname")
    private String fullname;
    @Min(value = 12, message = "Age should be greater than 12")
    @Column(name = "age")
    private byte age;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Transient
    private String stringOfAllUserRoles;

    public String getStringOfAllUserRoles() {
        return stringOfAllUserRoles;
    }

    public void setStringOfAllUserRoles(String stringOfAllUserRoles) {
        this.stringOfAllUserRoles = stringOfAllUserRoles;
    }

    public User() {
    }

    public User(Long id, String username, String password, String fullname, byte age, String email, Set<Role> userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.age = age;
        this.email = email;
        this.roles = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getStringOfRoles(User user) {
        StringBuilder str = new StringBuilder();
        Set<Role> roles = user.getRoles();
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            str.append(iterator.next().getRole().substring(5));
            if (iterator.hasNext()){
                str.append(", ");
            }
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }


}
