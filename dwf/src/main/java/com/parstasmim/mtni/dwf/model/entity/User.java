package com.parstasmim.mtni.dwf.model.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@NamedStoredProcedureQueries(
//        @NamedStoredProcedureQuery(
//                name = "sp_person_phones",
//                procedureName = "sp_person_phones",
//                parameters = {
//                        @StoredProcedureParameter(
//                                name = "personId",
//                                type = Long.class,
//                                mode = ParameterMode.IN
//                        ),
//                        @StoredProcedureParameter(
//                                name = "personPhones",
//                                type = Class.class,
//                                mode = ParameterMode.REF_CURSOR
//                        )
//                }
//        )
//)


@Table(name = "app_user")
@Entity
//@NamedQueries({
//        @NamedQuery(name = "user.authenticateUser", query = "SELECT u FROM User u WHERE u.userName=:userName AND u.password=:password"),
//        @NamedQuery(name = "user.authorizeUser", query = "SELECT u FROM User u WHERE u.userName=:userName AND u.password=:password"),
//        @NamedQuery(name = "user.getUsersList", query = "SELECT u FROM User u"),
//        @NamedQuery(name = "user.getUserBy", query = "SELECT u FROM User u WHERE u.id=:search")
//})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends BaseEntity {

    private String userName;
    private String email;
    private String password;
    private Date birthDate;
    private Blob image;
    private List<Access> accesses = new ArrayList();
    private List<Group> groups = new ArrayList();
    private List<Action> actions = new ArrayList();

    @Column(nullable = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(length = 2048000)
    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @OneToMany(mappedBy = "user")
    public List<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }

    @ManyToMany
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "app_access",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "action_id")})
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
