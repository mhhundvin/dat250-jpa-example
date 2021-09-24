package no.hvl.dat250.jpa.basicexample;


import com.google.gson.Gson;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {

    static Todos todos = null;

    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        todos = new Todos();

        after((req, res) -> {
            res.type("application/json");
        });

        //To test that it is running, you should get Hello World!
        get("/hello", (req, res) -> "Hello World!");

        //Get all todos
        get("/todos", (req, res) -> todos.getAll());

        //Get todo with id = :id
        get("/todos/:id", (req, res) -> todos.getID(Long.parseLong(req.params(":id"))));

        //Update todo with id = :id
        put("/todos/:id", (req,res) -> {
            Long id = Long.parseLong(req.params(":id"));

            Gson gson = new Gson();

            Todo todo = gson.fromJson(req.body(), Todo.class);

            return todos.updateID(id, todo);

        });

        //Add new todo
        post("/todos/add", (req,res) -> {
            Gson gson = new Gson();

            Todo todo = gson.fromJson(req.body(), Todo.class);

            return todos.add(todo);

        });


    }

}
