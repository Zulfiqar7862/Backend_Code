package com.example.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity // Marks this class as a JPA entity
@Table(name = "items") // Specifies the table name in the database
@EqualsAndHashCode // Lombok annotation to generate equals and hashCode methods
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the primary key generation strategy
	@JsonIgnore // Ignores this field during JSON serialization
	private Long id;

	@Column(name = "quantity") // Maps this field to the 'quantity' column in the database
	@NotNull // Ensures this field is not null
	private int quantity;

	@Column(name = "subtotal") // Maps this field to the 'subtotal' column in the database
	@NotNull // Ensures this field is not null
	private BigDecimal subTotal;

	@ManyToOne(cascade = CascadeType.ALL) // Defines a many-to-one relationship with the Product entity
	@JoinColumn(name = "product_id") // Specifies the foreign key column
	private Product product;

	@ManyToMany(mappedBy = "items") // Defines a many-to-many relationship with the Order entity
	@JsonIgnore // Ignores this field during JSON serialization
	private List<Order> orders;

	// Default constructor
	public Item() {
	}

	// Constructor to initialize Item with quantity, product, and subtotal
	public Item(@NotNull int quantity, Product product, BigDecimal subTotal) {
		this.quantity = quantity;
		this.product = product;
		this.subTotal = subTotal;
	}

	// Getter and setter for id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Getter and setter for quantity
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// Getter and setter for subTotal
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	// Getter and setter for product
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	// Getter and setter for orders
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
