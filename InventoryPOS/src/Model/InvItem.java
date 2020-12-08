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
@Table(name = "inv_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvFooditem.findAll", query = "SELECT i FROM InvFooditem i"),
    @NamedQuery(name = "InvFooditem.findByItemId", query = "SELECT i FROM InvFooditem i WHERE i.itemId = :itemId"),
    @NamedQuery(name = "InvFooditem.findByCatId", query = "SELECT i FROM InvFooditem i WHERE i.catId = :catId"),
    @NamedQuery(name = "InvFooditem.findByItemName", query = "SELECT i FROM InvFooditem i WHERE i.itemName = :itemName"),
    @NamedQuery(name = "InvFooditem.findByItemPrice", query = "SELECT i FROM InvFooditem i WHERE i.itemPrice = :itemPrice"),
    @NamedQuery(name = "InvFooditem.findByItemSellingPrice", query = "SELECT i FROM InvFooditem i WHERE i.itemSellingPrice = :itemSellingPrice"),
    @NamedQuery(name = "InvFooditem.findByItemPicture", query = "SELECT i FROM InvFooditem i WHERE i.itemPicture = :itemPicture"),
    @NamedQuery(name = "InvFooditem.findByItemQuantity", query = "SELECT i FROM InvFooditem i WHERE i.itemQuantity = :itemQuantity")})

public class InvItem implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "item_id")
    private Integer itemId;
    @Column(name = "cat_id")
    private Integer catId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "item_price")
    private String itemPrice;
    @Column(name = "item_selling_price")
    private String itemSellingPrice;
    @Column(name = "item_picture")
    private String itemPicture;
    @Column(name = "item_quantity")
    private Integer itemQuantity;
    
    public InvItem() {
    }

    public InvItem(Integer itemId) {
        this.itemId = itemId;
    }
    
    public InvItem(Integer itemId, Integer catId, String itemName, String itemPrice, String itemSellingPrice, String itemPicture, Integer itemQuantity) {
        this.itemId = itemId;
        this.catId = catId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemSellingPrice = itemSellingPrice;
        this.itemPicture = itemPicture;
        this.itemQuantity = itemQuantity;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public String getItemSellingPrice() {
        return itemSellingPrice;
    }

    public void setItemSellingPrice(String itemPrice) {
        this.itemSellingPrice = itemPrice;
    }

    public String getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(String itemPicture) {
        this.itemPicture = itemPicture;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvItem)) {
            return false;
        }
        InvItem other = (InvItem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAL.Invitem[ itemId=" + itemId + " ]";
    }
}
