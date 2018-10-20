package com.parstasmim.mtni.dwf.model.entity;

import javax.persistence.*;

@Table(name = "app_access")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Access extends BaseEntity {

    private User user;
    private Group group;
    private Action action;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(foreignKey = @ForeignKey(name = "USER_ID_FK"))
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(foreignKey = @ForeignKey(name = "GROUP_ID_FK"))
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(foreignKey = @ForeignKey(name = "ACTION_ID_FK"))
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
