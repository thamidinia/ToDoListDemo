package com.tabbassom.todolist.controllers;

import com.tabbassom.todolist.ToDoRepository;
import com.tabbassom.todolist.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tabbassom on 9/4/2016.
 */

@Controller
public class ToDoRestServiceController {

    @Autowired
    private ToDoRepository repo;


    public ToDoRestServiceController(ToDoRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(path="/create", method= RequestMethod.POST)
    public String createToDo(Todo todo) {
        try {
            todo.setActive(true);
            repo.save(todo);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return e.getMessage();
        }
        return "redirect:/";
    }

    @RequestMapping(path="/deleteAllCompletedTasks", method= RequestMethod.POST)
    public String deleteAllCompletedTodos(Model model) {
        List<Todo> todos = (List<Todo>) repo.findAll();
        try {
            for (Todo todo: todos) {
                if(!todo.isActive()) {
                    repo.delete(todo);
                }
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return e.getMessage();
        }
        return findAllToDos(model);
    }


    @RequestMapping(path="/",method=RequestMethod.GET)
    public String findAllToDos(Model model) {
        List<Todo> todos = (List<Todo>) repo.findAll();
        List<Todo> activeTaskList = new ArrayList<>();
        for (Todo todo:todos) {
            if(todo.isActive()) {
                activeTaskList.add(todo);
            }
        }
        if (todos == null) {
            String errorMst = "no Todo found";
            System.out.print(errorMst);
        } else {
            model.addAttribute("todolist", todos);
            model.addAttribute("numOfActiveTasks",activeTaskList.size());
        }
        return "index";
    }


    @RequestMapping(path="/edit", method= RequestMethod.POST , params="action=update")
    public String updateToDo(@RequestParam("todoId") long todoId,
                             Model model) {
        try {
            Todo existinTodo = repo.getOne(todoId);
            if(existinTodo.isActive())
                existinTodo.setActive(false);
            else
                existinTodo.setActive(true);
            repo.save(existinTodo);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return e.getMessage();
        }
        return findAllToDos(model);
    }

    @RequestMapping(path="/edit", method= RequestMethod.POST,  params="action=delete")
    public String deleteToDos(@RequestParam("todoId") long todoId,
                              Model model) {
        try {
            Todo existinTodo = repo.getOne(todoId);
            repo.delete(existinTodo);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return e.getMessage();
        }
        return findAllToDos(model);
    }

    @RequestMapping(path="/showCompletedTasks",method=RequestMethod.GET)
    public String findAllCompletedTasks(Model model) {
        List<Todo> todos = (List<Todo>) repo.findAll();
        List<Todo> completedTods = new ArrayList<>();
        for (Todo todo:todos) {
            if(!todo.isActive())
                completedTods.add(todo);
        }
        if (todos == null) {
            String errorMst = "no Todo found";
            System.out.print(errorMst);
        } else {
            model.addAttribute("todolist", completedTods);
        }
        return "index";
    }

    @RequestMapping(path="/showActiveTasks",method=RequestMethod.GET)
    public String findAllActiveTasks(Model model) {
        List<Todo> todos = (List<Todo>) repo.findAll();
        List<Todo> activeTods = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.isActive())
                activeTods.add(todo);
        }
        if (todos == null) {
            String errorMst = "no Todo found";
            System.out.print(errorMst);
        } else {
            model.addAttribute("todolist", activeTods);
        }
        return "index";
    }

}
