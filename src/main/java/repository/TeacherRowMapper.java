package repository;

import entities.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherRowMapper implements RowMapper<Teacher>{

    @Override
    public Teacher mapRow(ResultSet resultSet) throws SQLException {
        Teacher teacher=new Teacher();
        teacher.setTeacherId(resultSet.getInt("id"));
        teacher.setFirstName(resultSet.getString("firstName"));
        teacher.setLastName(resultSet.getString("lastName"));
        try {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/java","eu","parola");
            Statement statement=connection.createStatement();
            ResultSet aux = statement.executeQuery("select * from courses where id_teacher=" + teacher.getTeacherId());
            List<Integer> courses = new ArrayList<>();
            while (aux.next())
                courses.add(aux.getInt("id"));

            teacher.setCourses(courses);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return teacher;
    }
}
