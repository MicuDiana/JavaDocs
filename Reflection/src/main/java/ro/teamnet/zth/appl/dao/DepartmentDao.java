package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Department;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 7/11/2016.
 */
public class DepartmentDao {


    EntityManager entityManager = new EntityManagerImpl();

    public Department findById(Long id){
        return (Department) entityManager.findById(Department.class, id);
    }

    public List<Department> findAll(){
        return entityManager.findAll(Department.class);
    }

    public List<Department> findByParams( Map<String, Object> params){
        return entityManager.findByParams(Department.class, params);
    }

    public Department insert(Department department){
        return (Department) entityManager.insert(department);
    }

    public void delete(Department department){
        entityManager.delete(department);
    }

    public Department update(Department department){
        return entityManager.update(department);
    }
}
