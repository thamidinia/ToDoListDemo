package com.tabbassom.todolist;

import com.tabbassom.todolist.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Tabbassom on 9/4/2016.
 */
public interface ToDoRepository extends JpaRepository<Todo, Long> {
}
