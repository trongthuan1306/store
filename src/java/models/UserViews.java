/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "userViews")
@NamedQueries({
    @NamedQuery(name = "UserViews.findAll", query = "SELECT u FROM UserViews u"),
    @NamedQuery(name = "UserViews.findByViewId", query = "SELECT u FROM UserViews u WHERE u.viewId = :viewId"),
    @NamedQuery(name = "UserViews.findByViewTime", query = "SELECT u FROM UserViews u WHERE u.viewTime = :viewTime")})
public class UserViews implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "viewId")
    private Integer viewId;
    @Column(name = "viewTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date viewTime;
    @JoinColumn(name = "account", referencedColumnName = "account")
    @ManyToOne
    private Accounts account;
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    @ManyToOne
    private Products productId;

    public UserViews() {
    }

    public UserViews(Integer viewId) {
        this.viewId = viewId;
    }

    public Integer getViewId() {
        return viewId;
    }

    public void setViewId(Integer viewId) {
        this.viewId = viewId;
    }

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (viewId != null ? viewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserViews)) {
            return false;
        }
        UserViews other = (UserViews) object;
        if ((this.viewId == null && other.viewId != null) || (this.viewId != null && !this.viewId.equals(other.viewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.UserViews[ viewId=" + viewId + " ]";
    }
    
}
