package ro.teamnet.zth.api.em;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 07.07.2016.
 */
public class QueryBuilder {

    private Object tableName;
    private List<ColumnInfo> queryColumns = new LinkedList<>();
    private QueryType queryType;
    private List<Condition> conditions = new LinkedList<>();

    public String getValueForQuery(Object value) {

        if (value.getClass() == Date.class) {

            DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
            return "TO_DATE('" + dateFormat.format((Date) value) + "','dd/mm/YYYY')";
        }
        /*if (value.getClass() == String.class) {

            String string = (String) value;
            return string.substring(string.indexOf("'") + 1, string.indexOf("'"));
        }*/
        return value.toString();
    }

    public QueryBuilder addCondition(Condition condition) {

        this.conditions.add(condition);
        return this;
    }

    public QueryBuilder setTableName(Object tableName) {

        this.tableName = tableName;
        return this;
    }

    public QueryBuilder addQueryColumns(List<ColumnInfo> queryColumns) {

        this.queryColumns.addAll(queryColumns);
        return this;
    }

    public QueryBuilder setQueryType(QueryType queryType) {

        this.queryType = queryType;
        return this;
    }

    private String createSelectQuery() {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("SELECT ");
        for (ColumnInfo columnInfo : this.queryColumns) {
            queryBuilder.append(columnInfo.getDbName() + ",");
        }
        queryBuilder.deleteCharAt(queryBuilder.length() - 1);

        queryBuilder.append(" FROM ");
        queryBuilder.append(EntityUtils.getTableName(this.tableName.getClass()));

        if (this.conditions.isEmpty()) {
            return queryBuilder.toString();
        }

        queryBuilder.append(" WHERE ");
        for (Condition condition : this.conditions) {
            queryBuilder.append(condition.getColumnName() + "=" + getValueForQuery(condition.getValue()) + " AND ");
        }
        queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length() - 1);

        return queryBuilder.toString();
    }

    private String createDeleteQuery() {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("DELETE FROM ");
        queryBuilder.append(EntityUtils.getTableName(this.tableName.getClass()));

        if (this.conditions.isEmpty()) {
            return queryBuilder.toString();
        }

        queryBuilder.append(" WHERE ");
        for (Condition condition : this.conditions) {
            queryBuilder.append(condition.getColumnName() + "=" + getValueForQuery(condition.getValue()) + " AND ");
        }
        queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length() - 1);

        return queryBuilder.toString();
    }

    private String createUpdateQuery() {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("UPDATE ");
        queryBuilder.append(EntityUtils.getTableName(this.tableName.getClass()));
        queryBuilder.append(" SET ");

        for (ColumnInfo columnInfo : this.queryColumns) {
            queryBuilder.append(columnInfo.getDbName() + "=" + getValueForQuery(columnInfo.getValue()) + ",");
        }
        queryBuilder.deleteCharAt(queryBuilder.length() - 1);

        if (this.conditions.isEmpty()) {
            return queryBuilder.toString();
        }

        queryBuilder.append(" WHERE ");
        for (Condition condition : this.conditions) {
            queryBuilder.append(condition.getColumnName() + "=" + getValueForQuery(condition.getValue()) + " AND ");
        }
        queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length() - 1);

        return queryBuilder.toString();
    }

    private String createInsertQuery() {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("INSERT INTO ");
        queryBuilder.append(EntityUtils.getTableName(this.tableName.getClass()));

        queryBuilder.append(" (");
        for (ColumnInfo columnInfo : this.queryColumns) {
            queryBuilder.append(columnInfo.getDbName() + ",");
        }
        queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        queryBuilder.append(") VALUES (");

        for (ColumnInfo columnInfo : this.queryColumns) {
            queryBuilder.append(getValueForQuery(columnInfo.getValue()) + ",");
        }
        queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        queryBuilder.append(")");

        return queryBuilder.toString();
    }

    public String createQuery() {

        switch (this.queryType) {
            case SELECT:
                return createSelectQuery();
            case DELETE:
                return createDeleteQuery();
            case UPDATE:
                return createUpdateQuery();
            case INSERT:
                return createInsertQuery();
            default:
                return "";
        }
    }
}
