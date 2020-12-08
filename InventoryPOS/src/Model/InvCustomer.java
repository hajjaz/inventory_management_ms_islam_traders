/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hajjaz
 */

@Entity
@Table(name = "inv_customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvCustomer.findAll", query = "SELECT i FROM InvCustomer i"),
    @NamedQuery(name = "InvCustomer.findByCustomerId", query = "SELECT i FROM InvCustomer i WHERE i.customerId = :customerId"),
    @NamedQuery(name = "InvCustomer.findByCustomerName", query = "SELECT i FROM InvCustomer i WHERE i.customerName = :customerName"),
    @NamedQuery(name = "InvCustomer.findByCustomerContact", query = "SELECT i FROM InvCustomer i WHERE i.customerContact = :customerContact"),
    @NamedQuery(name = "InvCustomer.findByCustomerEmail", query = "SELECT i FROM InvCustomer i WHERE i.customerEmail = :customerEmail"),
    @NamedQuery(name = "InvCustomer.findByCustomerAddress", query = "SELECT i FROM InvCustomer i WHERE i.customerAddress = :customerAddress")})

public class InvCustomer implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_contact")
    private String customerContact;
    @Column(name = "customer_address")
    private String customerAddress;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "due_amount")
    private String dueAmount;
    
    public InvCustomer() {
    }

    public InvCustomer(Integer customerId) {
        this.customerId = customerId;
    }
    
    public InvCustomer(Integer customerId, String customerName, String customerContact, String customerAddress, String customerEmail, String dueAmount) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.dueAmount = dueAmount;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvCustomer)) {
            return false;
        }
        InvCustomer other = (InvCustomer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InvCustomer[ customerId=" + customerId + " ]";
    }
}
