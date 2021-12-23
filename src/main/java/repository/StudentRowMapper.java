package repository;

import entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRowMapper implements RowMapper<Student>{

    /**
     * {@inheritDoc}
     */
    @Override
    public Student mapRow(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentId(resultSet.getInt("id"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        student.setTotalCredits(resultSet.getInt("totalCredits"));

        try{
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/java","eu","parola");
            Statement statement=connection.createStatement();
            ResultSet aux=statement.executeQuery("select * from students_courses where id_student="+student.getStudentId());
            List<Integer> courses=new ArrayList<>();
            while(aux.next())
               courses.add(aux.getInt("id_course"));

            student.setCourses(courses);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return student;
    }
}
