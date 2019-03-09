package com.taj.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;


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
    
    

}
