package ru.catn.jdbc;

import ru.catn.core.model.Id;
import ru.catn.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;
    private Field idField;
    private List<Field> fields;
    private Class<?> clazz;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor, Class<?> clazz) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;

        this.clazz = clazz;
        this.fields = getFields(this.clazz);
        this.idField = getIdField(this.fields);
    }

    @Override
    public void create(T objectData) {

        StringBuilder sql = new StringBuilder();
        List<String> values = new LinkedList<>();

        sql.append("insert into ")
                .append(clazz.getSimpleName())
                .append('(');
        for (var field : fields) {
            sql.append(field.getName())
                    .append(',');
        }
        if (fields.size() > 0) {
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(')')
                .append(" values (")
                .append("?,".repeat(fields.size()));

        if (fields.size() > 0) {
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(')');

        for (var field : fields) {
            try {
                values.add(field.get(objectData).toString());
            } catch (IllegalAccessException e) {
                throw new JdbcMapperException(e);
            }
        }

        try {
            dbExecutor.insertRecord(getConnection(), sql.toString(), values);
        } catch (Exception e) {
            throw new JdbcMapperException(e);
        }
    }

    @Override
    public void update(T objectData) {
        StringBuilder sql = new StringBuilder();
        List<String> values = new LinkedList<>();

        try {
            sql.append("update ")
                    .append(clazz.getSimpleName())
                    .append(" set");
            for (var field : fields) {
                if (field == idField)
                    continue;
                sql.append(' ')
                        .append(field.getName())
                        .append(" = ?,");

                values.add(field.get(objectData).toString());
            }
            if (fields.size() > 0) {
                sql.deleteCharAt(sql.length() - 1);
            }
            sql.append(" where ")
                    .append(idField.getName())
                    .append(" = ")
                    .append(idField.get(objectData).toString());
        } catch (IllegalAccessException e) {
            throw new JdbcMapperException(e);
        }

        try {
            dbExecutor.updateRecord(getConnection(), sql.toString(), values);
        } catch (SQLException e) {
            throw new JdbcMapperException(e);
        }
    }

    @Override
    public Optional<T> load(long id) {
        StringBuilder sql = new StringBuilder();

        sql.append("select");

        for (var field : fields) {
            sql.append(' ')
                    .append(field.getName())
                    .append(',');
        }
        if (fields.size() > 0) {
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(" from ")
                .append(clazz.getSimpleName())
                .append(" where ")
                .append(idField.getName())
                .append(" = ?");

        try {
            return dbExecutor.selectRecord(getConnection(), sql.toString(), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        List<Object> values = getValues(resultSet);
                        return createObject(values);
                    }
                } catch (SQLException e) {
                    throw new JdbcMapperException(e);
                }
                return null;
            });
        } catch (Exception e) {
            throw new JdbcMapperException(e);
        }
    }

    public long getId(T entity) {
        try {
            return idField.getLong(entity);
        } catch (IllegalAccessException e) {
            throw new JdbcMapperException(e);
        }
    }

    private List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new LinkedList<>();
        for (var field : clazz.getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers()))
                continue;
            if (field.trySetAccessible())
                field.setAccessible(true);
            else
                continue;
            fields.add(field);
        }
        return fields;
    }

    private Field getIdField(List<Field> fields) {
        for (var field : fields) {
            if (field.getAnnotation(Id.class) != null)
                return field;
        }
        return null;
    }

    private List<Class<?>> getFieldTypes(List<Field> fields) {
        List<Class<?>> fieldTypes = new LinkedList<>();
        for (var field : fields) {
            fieldTypes.add(field.getType());
        }
        return fieldTypes;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    private T createObject(List<Object> values) {
        try {
            List<Class<?>> fieldTypes = getFieldTypes(fields);
            Constructor<?> constructor = clazz.getConstructor(fieldTypes.toArray(Class[]::new));
            return (T) constructor.newInstance(values.toArray());
        } catch (Exception e) {
            throw new JdbcMapperException(e);
        }
    }

    private List<Object> getValues(ResultSet resultSet) throws SQLException {
        List<Object> values = new LinkedList<>();
        for (var field : fields) {
            if (field.getType().isPrimitive()) {
                switch (field.getType().getSimpleName()) {
                    case "long":
                        values.add(resultSet.getLong(field.getName()));
                        break;
                    case "int":
                        values.add(resultSet.getInt(field.getName()));
                        break;
                    case "byte":
                        values.add(resultSet.getByte(field.getName()));
                        break;
                    case "short":
                        values.add(resultSet.getShort(field.getName()));
                        break;
                    case "boolean":
                        values.add(resultSet.getBoolean(field.getName()));
                        break;
                    case "float":
                        values.add(resultSet.getFloat(field.getName()));
                        break;
                    case "double":
                        values.add(resultSet.getDouble(field.getName()));
                        break;
                    case "char":
                        values.add(resultSet.getString(field.getName()));
                        break;
                }
            } else {
                values.add(resultSet.getObject(field.getName(), field.getType()));
            }
        }
        return values;
    }
}