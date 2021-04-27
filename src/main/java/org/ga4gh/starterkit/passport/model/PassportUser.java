package org.ga4gh.starterkit.passport.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.passport.utils.SerializeView;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "passport_user")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PassportUser implements HibernateEntity<String> {

    /* Non-relational entity attributes */

    @Id
    @Column(name = "id")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String id;

    @Column(name = "username")
    @NonNull
    @JsonProperty(required = true)
    @JsonView(SerializeView.Always.class)
    private String username;

    @Column(name = "first_name")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String firstName;

    @Column(name = "last_name")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String lastName;

    @Column(name = "email")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String email;

    @Column(name = "password_salt")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String passwordSalt;

    @Column(name = "password_hash")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String passwordHash;

    /* Relational entity attributes */

    @OneToMany(mappedBy = "passportUser",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonView(SerializeView.UserRelational.class)
    private List<PassportVisaAssertion> passportVisaAssertions;

    /* Constructors */

    public PassportUser() {
        passportVisaAssertions = new ArrayList<>();
    }

    public PassportUser(String id, String username, String firstName, String lastName, String email, String passwordSalt, String passwordHash) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
        passportVisaAssertions = new ArrayList<>();
    }

    /* Other API methods */

    @Override
    public void loadRelations() {
        Hibernate.initialize(getPassportVisaAssertions());
    }

    /* Setters and Getters */

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassportVisaAssertions(List<PassportVisaAssertion> passportVisaAssertions) {
        // this.passportVisaAssertions = passportVisaAssertions;
        this.passportVisaAssertions.clear();
        this.passportVisaAssertions.addAll(passportVisaAssertions);
    }

    public List<PassportVisaAssertion> getPassportVisaAssertions() {
        return passportVisaAssertions;
    }
}
