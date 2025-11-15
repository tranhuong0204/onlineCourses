package com.example.onlineCourses.model;

import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") //xóa user theo id
    private User user;

    @ManyToOne
    private Course course;

    private int quantity = 1;

    public long getPrice() {
        if (course != null) {
            return course.getPrice(); // vì quantity = 1
        }
        return 0L;
    }

    // Constructors
    public CartItem() {}

    public CartItem(User user, Course course) {
        this.user = user;
        this.course = course;
        this.quantity = 1;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
