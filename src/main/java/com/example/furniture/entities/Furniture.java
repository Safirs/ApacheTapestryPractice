package com.example.furniture.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.RegEx;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@SuppressWarnings("serial")
@Entity
@Table(name = "furniture")
public class Furniture implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@NonVisual
	public Long id;

	@Column(name = "type")
	@NotNull
	@Pattern(regexp = "^(Chair|Table)$", message = "Type must be Chair or Table")
	public String type;

	@Column(name = "name")
	@NotNull
	public String name;

	@Column(name = "price")
	@NotNull
	public BigDecimal price;
	
	@PrePersist
	@PreUpdate
	public void validate() throws ValidationException {

	}

//	public Furniture() { }
//	  
//	public Furniture(Long id, String type, String name, BigDecimal price) {
//		this.id = id;
//		this.type = type;
//		this.name = name;
//		this.price = price; 
//	}
//	  
//	  public Furniture(String type, String name, BigDecimal price) {
//		  this.type =type;
//		  this.name = name;
//		  this.price = price; }
//	  
	  
//	  
//	  @Override public int hashCode() {
//		  return id == null ? super.hashCode() :id.hashCode(); }
//	  
//	  @Override public boolean equals(Object obj) {
//		  if (this == obj) return true;
//	  if (obj == null) return false; if (getClass() != obj.getClass()) return
//	  false; Furniture other = (Furniture) obj; return Objects.equals(id, other.id)
//	  && Objects.equals(name, other.name) && Objects.equals(price, other.price) &&
//	  Objects.equals(type, other.type); }
	 
	 
	@Override
	public String toString() {
		return "Furniture [id=" + id + ", type=" + type + ", name=" + name + ", price=" + price + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
