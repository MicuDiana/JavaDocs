package ro.teamnet.zth.api.em;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 08.07.2016.
 */
public interface EntityManager {

    <T> T findById(Class<T> entityClass, Long id);

    Long getNextIdVal(String tableName, String columnIdName);

    <T> Object insert(T entity);

    <T> List<T> findAll(Class<T> entityClass);

    public <T> void delete(T entity);

    public <T> T update(T entity);

    public <T> List<T> findByParams(Class<T> entityClass, Map<String, Object> params);

}
