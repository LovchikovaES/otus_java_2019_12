package ru.catn.jdbc;

import ru.catn.core.model.Id;
import ru.catn.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;
    private Class<T> clazz;
    private Field idField;
    private List<Field> allFields;
    private Constructor<T> constructor;
    private String allFieldNames;
    private String fieldsWithValues;
    private String createSQLquery;
    private String updateSQLquery;
    private String loadSQLquery;
    private Map<Class<?>, Class<?>> primitiveWrappers = new HashMap<>();

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.clazz = clazz;
        this.allFields = getFields(this.clazz);
        this.idField = getIdField(this.allFields);
        this.allFieldNames = getAllFieldNames();
        this.fieldsWithValues = getFieldWithValues();
        this.constructor = getConstructor();
        setPrimitiveWrappers();
    }

    @Override
    public void create(T objectData) {
        List<Object> values = new LinkedList<>();
        for (var field : allFields) {
            try {
                values.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                throw new JdbcMapperException(e);
            }
        }
        try {
            dbExecutor.insertRecord(getConnection(), getCreateSQLQuery(), values);
        } catch (Exception e) {
            throw new JdbcMapperException(e);
        }
    }

    @Override
    public void update(T objectData) {
        List<Object> values = new LinkedList<>();
        try {
            for (var field : allFields)
                if (!field.equals(idField))
                    values.add(field.get(objectData));
        } catch (IllegalAccessException e) {
            throw new JdbcMapperException(e);
        }

        try {
            dbExecutor.updateRecord(getConnection(), getUpdateSQLQuery(objectData), values);
        } catch (SQLException e) {
            throw new JdbcMapperException(e);
        }
    }

    @Override
    public Optional<T> load(long id) {
        try {
            return dbExecutor.selectRecord(getConnection(), getLoadSQLQuery(), id, resultSet -> {
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
            return this.constructor.newInstance(values.toArray());
        } catch (Exception e) {
            throw new JdbcMapperException(e);
        }
    }

    private List<Object> getValues(ResultSet resultSet) throws SQLException {
        List<Object> values = new LinkedList<>();
        for (var field : allFields) {
            values.add(resultSet.getObject(field.getName(), getFieldType(field)));
        }
        return values;
    }

    private String getAllFieldNames() {
        List<String> fieldNames = new LinkedList<>();
        for (var field : allFields)
            fieldNames.add(field.getName());
        return String.join(", ", fieldNames);
    }

    private String getFieldWithValues() {
        List<String> fieldNamesWithValues = new LinkedList<>();
        for (var field : allFields)
            if (!field.equals(idField))
                fieldNamesWithValues.add(field.getName() + " = ?");
        return String.join(", ", fieldNamesWithValues);
    }

    private String getCreateSQLQuery() {
        if (this.createSQLquery == null) {
            String insertValues = ", ?".repeat(allFields.size()).replaceFirst(", ", "");
            this.createSQLquery = String.format("insert into %s (%s) values(%s)",
                    clazz.getSimpleName(), this.allFieldNames, insertValues);
        }
        return this.createSQLquery;
    }

    private String getUpdateSQLQuery(T objectData) {
        if (this.updateSQLquery == null) {
            try {
                this.updateSQLquery = String.format("update %s set %s where %s = %s",
                        clazz.getSimpleName(), fieldsWithValues, idField.getName(), idField.get(objectData).toString());
            } catch (IllegalAccessException e) {
                throw new JdbcMapperException(e);
            }
        }
        return this.updateSQLquery;
    }

    private String getLoadSQLQuery() {
        if (this.loadSQLquery == null) {
            this.loadSQLquery = String.format("select %s from %s where %s = ?",
                    this.allFieldNames, clazz.getSimpleName(), idField.getName());
        }
        return this.loadSQLquery;
    }

    private Constructor<T> getConstructor() {
        List<Class<?>> fieldTypes = getFieldTypes(this.allFields);
        try {
            return clazz.getConstructor(fieldTypes.toArray(Class[]::new));
        } catch (NoSuchMethodException e) {
            throw new JdbcMapperException(e);
        }
    }

    private void setPrimitiveWrappers() {
        this.primitiveWrappers.put(int.class, Integer.class);
        this.primitiveWrappers.put(long.class, Long.class);
        this.primitiveWrappers.put(char.class, Character.class);
        this.primitiveWrappers.put(byte.class, Byte.class);
        this.primitiveWrappers.put(boolean.class, Boolean.class);
        this.primitiveWrappers.put(float.class, Float.class);
        this.primitiveWrappers.put(short.class, Short.class);
        this.primitiveWrappers.put(double.class, Double.class);
    }

    private Class<?> getFieldType(Field field) {
        if (!field.getType().isPrimitive())
            return field.getType();
        return primitiveWrappers.get(field.getType());
    }
}