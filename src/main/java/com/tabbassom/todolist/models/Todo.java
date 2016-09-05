package com.tabbassom.todolist.models;

import javax.persistence.*;

/**
 * Created by Tabbassom on 9/4/2016.
 */

@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private boolean active;

    public Todo(){}

    public Todo(String description, boolean active) {
        this.description = description;
        this.active = active;
    }

    public Todo(String description){
        this.description=description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("Todo[id=%d, description='%s']", id, description);
    }
}
