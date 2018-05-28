package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TestServiceJPA {
    @PersistenceContext
    private EntityManager entityManager;
    public void test(){
        entityManager.persist(new Student("Jano", 200));
        }

}
