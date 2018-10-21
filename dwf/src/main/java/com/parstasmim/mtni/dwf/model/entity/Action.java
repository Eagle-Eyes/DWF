package com.parstasmim.mtni.dwf.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "app_action")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@NamedQueries({
//        @NamedQuery(name = "action.getUserBy", query = "SELECT a FROM Action a where a.id=:search"),
//        @NamedQuery(name = "action.getUsersList", query = "SELECT a FROM Action a"),
//        @NamedQuery(name = "action.update", query = "UPDATE Action a SET a.url = :url, a.requestType = :requestType, a.accessibility = :accessibility WHERE a.displayName = :displayName")
//})
public class Action extends BaseEntity {

    //    private boolean needAuthentication;
    private boolean accessibility;
    private String url;
    private String requestType;
    private List<Access> accesses = new ArrayList();
    private List<User> users = new ArrayList();
    private List<Group> groups = new ArrayList();

    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }

    @ManyToMany(mappedBy = "actions", cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @ManyToMany(mappedBy = "actions", cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Column
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Column
    public boolean isAccessibility() {
        return accessibility;
    }

    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }

//    @Column
//    public boolean isNeedAuthentication() {
//        return needAuthentication;
//    }
//
//    public void setNeedAuthentication(boolean needAuthentication) {
//        this.needAuthentication = needAuthentication;
//    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getDisplayName().toCharArray()).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (o == this)
            return true;

        if (o.getClass() != getClass())
            return false;

        BaseEntity e = (BaseEntity) o;

        return new EqualsBuilder().
                append(getDisplayName(), e.getDisplayName()).
                isEquals();
    }

    public List<String> urlsArray() {
        List<String> urls = Arrays.asList(getUrl().replace(" ", "").replace("[", "").replace("]", "").split(","));

        return urls;
    }

    public List<String> requestTypesArray() {
        List<String> urls = Arrays.asList(getRequestType().replace(" ", "").replace("[", "").replace("]", "").split(","));

        return urls;
    }
}
