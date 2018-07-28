package org.demo.chapter2.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.demo.chapter2.util.CollectionUtil;
import org.demo.chapter2.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_HOLDER=
            new ThreadLocal<Connection>();

    private static final BasicDataSource DATA_SOURCE;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    public static<T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            //closeConnection();
        }
        return entityList;
    }
    public static<T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
            //closeConnection();
        }
        return entity;
    }
    /*
    执行更新语句 update,insert,delete
     */
    public static int executeUpdate(String sql,Object... params) {
        int row = 0;
        try {
            Connection conn = getConnection();
            row = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure", e);
            throw new RuntimeException(e);
        } finally {
            //closeConnection();
        }
        return row;
    }
    /*
    插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity: field is empty");
            return false;
        }
        String sql="INSERT INTO " + entityClass.getSimpleName().toLowerCase();
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for(String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }
    /*
    更新实体 update
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity: field is empty");
            return false;
        }
        String sql = "UPDATE " + entityClass.getSimpleName().toLowerCase() + " SET ";
        StringBuilder columns = new StringBuilder();
        for(String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return executeUpdate(sql, params) == 1;
    }
    /*
    删除实体 delete
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + entityClass.getSimpleName().toLowerCase() + " WHERE id=?";
        return executeUpdate(sql, id) == 1;
    }
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if(conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException E) {
                LOGGER.error("get connection failure", E);
                throw new RuntimeException(E);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }
    /*public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }*/
}
