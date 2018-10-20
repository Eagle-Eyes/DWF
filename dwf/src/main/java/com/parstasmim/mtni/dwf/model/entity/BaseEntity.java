package com.parstasmim.mtni.dwf.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@JsonIgnoreProperties(value = {"registered_date", "update_date", "accesses", "groups"})
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    protected Long id;
    protected String displayName;
    protected Date registeredDate;
    protected Date updatedDate;

    public BaseEntity() {
    }

    //    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    //    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column
    @CreationTimestamp
    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    @Column
    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        return new HashCodeBuilder((int) (getId() % 2 == 0 ? getId() + 1 : getId()), PRIME).toHashCode();
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
                append(getId(), e.getId()).
                isEquals();
    }
}
