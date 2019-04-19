package com.taj.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usertaj")
public class UserTaj {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usertaj_id")
    private int id;
	
    @Column(name = "username")
    @NotEmpty(message = "*Please provide your username")
    private String username;
	
    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;
   
    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "active")
    private int active;
    
    @ManyToMany(fetch=FetchType.LAZY,
    		cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
//    @NotEmpty(message = "*At least one role please")
    @JoinTable(name = "USERTAJ_ROLE", 
    joinColumns = @JoinColumn(name = "USERTAJ_ID"), 
    inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;
    
    @OneToMany(mappedBy = "userTaj", cascade = CascadeType.MERGE)
    private Set<Joke> jokes;
    
    @OneToMany(mappedBy = "userTaj", cascade = CascadeType.MERGE)
    private Set<UserTajRating> userTajRatings;

}
