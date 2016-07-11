package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 08.07.2016.
 */
public class EntityManagerImpl implements EntityManager {

    @Override
    public <T> T findById(Class<T> entityClass, Long id) {

        Connection conn = DBManager.getConnection();
        List<ColumnInfo> myColumns = EntityUtils.getColumns(entityClass);

        try (Statement stmt = conn.createStatement()) {

            Condition condition = new Condition();
            for (ColumnInfo columnInfo : myColumns) {
                if (columnInfo.isId()) {
                    condition.setColumnName(columnInfo.getDbName());
                    condition.setValue(id);
                    break;
                }
            }

            QueryBuilder queryBuilder = new QueryBuilder();

            String query = queryBuilder.setTableName(entityClass.newInstance()).addQueryColumns(myColumns).addCondition(condition).setQueryType(QueryType.SELECT).createQuery();
            ResultSet resultSet = stmt.executeQuery(query);
            resultSet.next();

            T instance = entityClass.newInstance();
            for (ColumnInfo columnInfo : myColumns) {

                Field field = instance.getClass().getDeclaredField(columnInfo.getColumnName());
                field.setAccessible(true);

                Object value = resultSet.getObject(columnInfo.getDbName());
                field.set(instance, EntityUtils.castFromSqlType(value, columnInfo.getColumnType()));
            }
            return instance;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getNextIdVal(String tableName, String columnIdName) {

        try (Connection conn = DBManager.getConnection()) {

            try (Statement stmt = conn.createStatement()) {

                StringBuilder string = new StringBuilder();
                string.append("SELECT MAX(");
                string.append(columnIdName);
                string.append(")+1 FROM ");
                string.append(tableName);

                ResultSet res = stmt.executeQuery(string.toString());
                res.next();
                return res.getLong(1);
            } catch (SQLException e) {
                System.out.println("Statement error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public <T> Object insert(T entity) {

        Connection connection = DBManager.getConnection();
        String tableName = EntityUtils.getTableName(entity.getClass());
        LinkedList<ColumnInfo> columnInfoArrayList = EntityUtils.getColumns(entity.getClass());
        Long ID = new Long(0);

        for (ColumnInfo columnInfo : columnInfoArrayList) {
            if (columnInfo.isId()) {
                columnInfo.setValue(getNextIdVal(tableName, columnInfo.getDbName()));
                ID = getNextIdVal(tableName, columnInfo.getDbName());
            } else {
                try {
                    Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    columnInfo.setValue(field.get(entity));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder = queryBuilder.setTableName(entity).addQueryColumns(columnInfoArrayList).setQueryType(QueryType.INSERT);
        String query = queryBuilder.createQuery();
        System.out.println(query);

        try (Statement statement = connection.createStatement()) {

            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {

        Connection DBconn = DBManager.getConnection();
        String tableName = EntityUtils.getTableName(entityClass);
        LinkedList<ColumnInfo> columnInfos = EntityUtils.getColumns(entityClass);
        QueryBuilder queryBuilder = new QueryBuilder();
        ArrayList<T> queryResult = new ArrayList<>();

        try (Statement statement = DBconn.createStatement()) {

            String query = queryBuilder.setTableName(entityClass.newInstance()).addQueryColumns(columnInfos).setQueryType(QueryType.SELECT).createQuery();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                T instance = entityClass.newInstance();
                for (ColumnInfo columnInfo : columnInfos) {

                    Field field = instance.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);

                    Object value = resultSet.getObject(columnInfo.getDbName());
                    if (value != null)
                        field.set(instance, EntityUtils.castFromSqlType(value, columnInfo.getColumnType()));
                }
                queryResult.add(instance);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return queryResult;
    }

    public <T> void delete(T entity) {

        Long id = null;
        Condition condition = new Condition();

        Connection connection = DBManager.getConnection();
        LinkedList<ColumnInfo> columnInfoArrayList = EntityUtils.getColumns(entity.getClass());


        for (ColumnInfo columnInfo : columnInfoArrayList) {
            try {
                Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                field.setAccessible(true);
                columnInfo.setValue(field.get(entity));
                if (columnInfo.isId()) {
                    id = (Long) field.get(entity);
                    condition.setValue(id);
                    condition.setColumnName(columnInfo.getDbName());
                }


            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder = queryBuilder.setTableName(entity).addQueryColumns(columnInfoArrayList).setQueryType(QueryType.DELETE).addCondition(condition);
        String query = queryBuilder.createQuery();

        System.out.println(query);
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T update(T entity) {
        Connection conn = DBManager.getConnection();
        LinkedList<ColumnInfo> columnInfoArrayList = EntityUtils.getColumns(entity.getClass());
        LinkedList<Condition> list = new LinkedList<Condition>();

        Condition condition = new Condition();
        for (ColumnInfo columnInfo : columnInfoArrayList) {
            try {
                Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                field.setAccessible(true);
                columnInfo.setValue(field.get(entity));

                if (columnInfo.isId()) {
                    condition.setColumnName(columnInfo.getDbName());
                    condition.setValue(field.get(entity));

                }
                list.add(condition);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder = queryBuilder.setTableName(entity).addQueryColumns(columnInfoArrayList).setQueryType(QueryType.UPDATE).addCondition(condition);

        String query = queryBuilder.createQuery();
        System.out.println(query);


        try (Statement statement = conn.createStatement()) {

            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public <T> List<T> findByParams(Class<T> entityClass, Map<String, Object> params) {

        Connection DBconn = DBManager.getConnection();
        LinkedList<ColumnInfo> columnInfos = EntityUtils.getColumns(entityClass);
        QueryBuilder queryBuilder = new QueryBuilder();
        ArrayList<T> queryResult = new ArrayList<>();

        for (String param : params.keySet()) {

            Condition condition = new Condition();
            condition.setColumnName(param);
            condition.setValue(params.get(param));
            queryBuilder.addCondition(condition);
        }

        try (Statement statement = DBconn.createStatement()) {

            String query = queryBuilder.setTableName(entityClass.newInstance()).addQueryColumns(columnInfos).setQueryType(QueryType.SELECT).createQuery();
            System.out.println();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                T instance = entityClass.newInstance();
                for (ColumnInfo columnInfo : columnInfos) {

                    Field field = instance.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);

                    Object value = resultSet.getObject(columnInfo.getDbName());
                    if (value != null)
                        field.set(instance, EntityUtils.castFromSqlType(value, columnInfo.getColumnType()));
                }
                queryResult.add(instance);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return queryResult;
    }

}
