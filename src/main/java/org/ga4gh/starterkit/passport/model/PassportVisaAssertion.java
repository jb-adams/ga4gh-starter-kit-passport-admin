package org.ga4gh.starterkit.passport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.passport.utils.SerializeView;

@Entity
@Table(name = "passport_visa_assertion")
public class PassportVisaAssertion implements HibernateEntity<Long> {

    /* Non-relational entity attributes */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "status")
    @JsonView(SerializeView.Always.class)
    private String status;

    /* Relational entity attributes */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonView(SerializeView.VisaRelational.class)
    private PassportUser passportUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visa_id")
    @JsonView(SerializeView.UserRelational.class)
    private PassportVisa passportVisa;

    /* Constructors */

    public PassportVisaAssertion() {

    }

    public PassportVisaAssertion(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    /* Other API methods */

    @Override
    public void loadRelations() {

    }

    /* Setters and Getters */

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPassportUser(PassportUser passportUser) {
        this.passportUser = passportUser;
    }

    public PassportUser getPassportUser() {
        return passportUser;
    }

    public void setPassportVisa(PassportVisa passportVisa) {
        this.passportVisa = passportVisa;
    }

    public PassportVisa getPassportVisa() {
        return passportVisa;
    }

    @Override
    public boolean equals(Object that) {
        return (this == that) || ((that instanceof PassportVisaAssertion) && this.getId().equals(((PassportVisaAssertion) that).getId()));
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
