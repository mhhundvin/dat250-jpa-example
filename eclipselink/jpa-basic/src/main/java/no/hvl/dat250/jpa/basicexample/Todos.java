package no.hvl.dat250.jpa.basicexample;

import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Todos {
    private static final String PERSISTENCE_UNIT_NAME = "todos";
    private static EntityManagerFactory factory;
    //private HashMap todos = new HashMap();
    private ArrayList todos = new ArrayList();
    //HashMap elem = new HashMap();

    public String getAll(){
        todos.clear();
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        Query q = em.createQuery("select t from Todo t");
        List<Todo> todoList = q.getResultList();
        for (Todo todo : todoList) {
            /*elem.put("Description", todo.getDescription());
            elem.put("Summary", todo.getSummary());
            todos.put(todo.getId(), todo);*/

            todos.add(todo);
        }
        Gson gson = new Gson();

        String jsonInString = gson.toJson(todos);

        return jsonInString;
    }

    public String getID(Long id){
        todos.clear();
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("SELECT t FROM Todo t WHERE t.id = " + id);
        List<Todo> todoList =  q.getResultList();
        /*elem.put("Description", todoList.get(0).getDescription());
        elem.put("Summary", todoList.get(0).getSummary());*/
        Gson gson = new Gson();
        todos.add(todoList.get(0));
        String jsonInString = gson.toJson(todos);

        return jsonInString;
    }

    public String updateID(Long id, Todo todo){
        todos.clear();
        String description = todo.getDescription();
        String summary = todo.getSummary();
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        Query q2 = em.createQuery("update Todo t set t.description = '" + description +"', t.summary = '"+ summary +"' where t.id = " + id);
        q2.executeUpdate();
        em.getTransaction().commit();
        Query q = em.createQuery("SELECT t FROM Todo t WHERE t.id = " + id);
        List<Todo> todoList = q.getResultList();
        em.close();
        todos.add(todoList.get(0));
        Gson gson = new Gson();
        String jsonInString = gson.toJson(todos);
        return jsonInString;
    }

    public String add(Todo todo){
        todos.clear();
        String description = todo.getDescription();
        String summary = todo.getSummary();

        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        Todo t = new Todo();
        t.setDescription(description);
        t.setSummary(summary);
        em.persist(t);
        em.getTransaction().commit();
        em.close();

        return t.toJson();
    }


}
