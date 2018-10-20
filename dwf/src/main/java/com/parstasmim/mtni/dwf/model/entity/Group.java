package com.parstasmim.mtni.dwf.model.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "app_group")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@NamedQueries({
//        @NamedQuery(name = "group.getUserBy", query = "select g from Group g where g.id=:search")
//})
public class Group extends BaseEntity {

    private List<User> users;
    private List<Access> accesses;
    private List<Action> actions;

    @OneToMany(mappedBy = "group")
    public List<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }

    @ManyToMany(mappedBy = "groups", cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "app_access",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "action_id")})
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
