package me.marcsteiner.glacier.database;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.MySQLCodec;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database {

    Codec SQL_CODEC = new MySQLCodec(MySQLCodec.Mode.STANDARD);

    void connect() throws HikariPool.PoolInitializationException;
    void disconnect();
    boolean isConnected();

    void setup() throws SQLException;
    void update(String query);
    ResultSet query(String query);

    Connection getConnection();
    HikariDataSource getConnectionPool();

    // Encodes a parameter to be safely passed with MySQL queries and updates (prevents SQL Injection)
    default String encode(String param) {
        return ESAPI.encoder().encodeForSQL(SQL_CODEC, param);
    }

}
