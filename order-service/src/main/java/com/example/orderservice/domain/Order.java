package com.example.orderservice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity // Marks this class as a JPA entity
@Table(name = "orders") // Specifies the table name in the database
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the primary key generation strategy
	private Long id;

	@Column(name = "ordered_date") // Maps this field to the 'ordered_date' column in the database
	@NotNull // Ensures this field is not null
	private LocalDate orderedDate;

	@Column(name = "status") // Maps this field to the 'status' column in the database
	@NotNull // Ensures this field is not null
	private String status;

	@Column(name = "total") // Maps this field to the 'total' column in the database
	private BigDecimal total;

	@ManyToMany(cascade = CascadeType.ALL) // Defines a many-to-many relationship with the Item entity
	@JoinTable(
			name = "cart", // Specifies the join table name
			joinColumns = @JoinColumn(name = "order_id"), // Specifies the join column for the order
			inverseJoinColumns = @JoinColumn(name = "item_id") // Specifies the join column for the item
	)
	private List<Item> items;

	@ManyToOne(cascade = CascadeType.ALL) // Defines a many-to-one relationship with the User entity
	@JoinColumn(name = "user_id") // Specifies the foreign key column
	private User user;

	// Default constructor
	public Order() {
	}

	// Getter and setter for id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Getter and setter for orderedDate
	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = orderedDate;
	}

	// Getter and setter for status
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Getter and setter for total
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	// Getter and setter for items
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	// Getter and setter for user
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
