package repository;

import entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class SqlRepository<E> implements ICrudRepository<E>{

    /**
     * macht eine Verbindung an der Database
     * @return die Verbindung
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/java";
        String user = "eu";
        String pass = "parola";

        return DriverManager.getConnection(url, user, pass);
    }

    /**
     * returniert eine Liste, der ist der Ergebniss der als Parameter
     * bekommte Sql Query
     * @param sql das sql query
     * @param mapper mappiert jeder Reie der Ergebnis
     * @return eine Liste von E
     * @throws SQLException
     */
    public List<E> getRows(String sql, RowMapper<E> mapper) throws SQLException {
        List<E> rows = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();


        while (resultSet.next()) {
            rows.add(mapper.mapRow(resultSet));
        }
        connection.close();
        return rows;
    }

    /**
     * returniert der einzige Element, der man durch das Sql query bekommt
     * z.B. wenn man beim Find
     * @param sql das sql query
     * @param mapper mappiert jeder Reie der Ergebnis
     * @return der einzige Element, der man durch das Sql query bekommt
     */
    public E getEntity(String sql, RowMapper<E> mapper) {
        E row = null;
        try {
            Connection connection = getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                row = mapper.mapRow(resultSet);
            }
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return row;
    }

    /**
     * verandert die tabelle sobald eine Veranderung im Kontroller
     * gerufen ist
     * @param sql das sql query
     * @throws SQLException
     */
    public void updateTable(String sql) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        connection.close();
    }
}
