package ro.teamnet.zth.api.em;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 7/7/2016.
 */
public class QueryBuilder {
    private Object tableName;
    ArrayList<ColumnInfo> queryColumns;
    QueryType queryType;
    ArrayList<Condition> conditions;

    public String getValueForQuery(Object value) {
        if(value.getClass() == String.class) {
            String myString = (String) value;
            return "'" + myString + "'";
        }
        if(value.getClass() == Date.class) {
            DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
            return "TO_DATE('"+dateFormat.format((Date)value)+"','mm-dd-YYYY'";
        }
        return "";
    }

    public QueryBuilder addCondition(Condition condition) {
        this.conditions.add(condition);
        return this;
    }

    public QueryBuilder setTableName(Object tableName) {
        this.tableName = tableName;
        return this;
    }

    public QueryBuilder addQueryColumns(ArrayList<ColumnInfo> queryColumns ) {
        this.queryColumns.addAll(queryColumns);
        return this;
    }

    public QueryBuilder setQueryType (QueryType queryType) {
        this.queryType = queryType;
        return this;
    }

    public String createSelectQuery(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT ");

        for(ColumnInfo columnInfo : this.queryColumns) {
            stringBuilder.append(columnInfo.getColumnName());
            if(!columnInfo.equals(this.queryColumns))
                stringBuilder.append(",");
        }

        stringBuilder.append(" FROM ");
        stringBuilder.append(this.tableName);

        if(conditions.size() != 0) {
            stringBuilder.append(" WHERE ");

            for (Condition condition : this.conditions) {
                stringBuilder.append(condition.getColumnName());
                stringBuilder.append("=");
                stringBuilder.append(this.getValueForQuery(condition.getValue()));

            }
        }
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    public String createDeleteQuery() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("DELETE ");

        for(ColumnInfo columnInfo : this.queryColumns) {
            stringBuilder.append(columnInfo.getColumnName());
            if(!columnInfo.equals(this.queryColumns))
                stringBuilder.append(",");
        }

        stringBuilder.append(" FROM ");
        stringBuilder.append(this.tableName);
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    public String createUpdateQuery() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("UPDATE ");
        stringBuilder.append(EntityUtils.getTableName(this.tableName.getClass()));
        stringBuilder.append(" SET ");

        for(ColumnInfo columnInfo : this.queryColumns) {
            stringBuilder.append(columnInfo.getColumnName());
            if(!columnInfo.equals(this.queryColumns))
                stringBuilder.append(",");
        }

        if(conditions.size() != 0) {
            stringBuilder.append(" WHERE ");

            for (Condition condition : this.conditions) {
                stringBuilder.append(condition.getColumnName());
                stringBuilder.append("=");
                stringBuilder.append(this.getValueForQuery(condition.getValue()));

            }
        }
        stringBuilder.append(";");
        return stringBuilder.toString();

    }

    public String createInsertQuery() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("INSERT INTO ");
        stringBuilder.append(EntityUtils.getTableName(this.tableName.getClass()));
        stringBuilder.append(" ( ");

        for(ColumnInfo columnInfo : this.queryColumns) {
            stringBuilder.append(columnInfo.getColumnName());
            if(!columnInfo.equals(this.queryColumns))
                stringBuilder.append(",");
        }

        stringBuilder.append(") VALUES");

        if(conditions.size() != 0) {
            stringBuilder.append(" WHERE ");

            for (Condition condition : this.conditions) {
                stringBuilder.append(condition.getColumnName());
                stringBuilder.append("=");
                stringBuilder.append(this.getValueForQuery(condition.getValue()));

            }
        }
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    public String createQuery(){
        if(this.queryType.equals("SELECT"))
            return createSelectQuery();
        if(this.queryType.equals("DELETE"))
            createDeleteQuery();
        if(this.queryType.equals("UPDATE"))
            createUpdateQuery();
        if(this.queryType.equals("INSERT"))
            createInsertQuery();
        return "";
    }
}
