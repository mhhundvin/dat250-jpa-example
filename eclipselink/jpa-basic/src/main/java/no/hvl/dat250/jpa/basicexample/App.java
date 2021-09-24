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

        get("/hello", (req, res) -> "Hello World!");

        get("/todos", (req, res) -> todos.getAll());

        get("/todos/:id", (req, res) -> todos.getID(Long.parseLong(req.params(":id"))));

        put("/todos/:id", (req,res) -> {
            Long id = Long.parseLong(req.params(":id"));

            Gson gson = new Gson();

            Todo todo = gson.fromJson(req.body(), Todo.class);

            return todos.updateID(id, todo);

        });

        post("/todos/add", (req,res) -> {
            Gson gson = new Gson();

            Todo todo = gson.fromJson(req.body(), Todo.class);

            return todos.add(todo);

        });

        //put("/todos/:id", (req, res) -> todos.getID(Long.parseLong(req.params(":id"))));

        /*put("/todos", (req,res) -> {

            Gson gson = new Gson();

            todos = gson.fromJson(req.body(), Todo.class);

            return todos.toJson();

        });*/
    }

}
