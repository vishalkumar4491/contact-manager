package com.contact_manager.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="user")
@Table(name="users")        // It will create table with following name in db if not write thi line then  it will make table with user
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements  UserDetails{
    @Id
    private String userId;
    private String name;
    private String email;

    @Getter(value=AccessLevel.NONE)
    private String password;
    private String phoneNumber;
    private String about;
    private String profilePic;

    @Getter(value=AccessLevel.NONE)
    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    private String emailVerificationToken;

    // how user login or signup Google, fb, twitter, self etc
    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerUserId;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // list of rles[ADMIN, USER]
        // convert to collection of SimpleGrantedAuthority
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getUsername() {
        // for this project username is email id
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


 }
