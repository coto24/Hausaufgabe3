package repository;

import entities.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRowMapper implements RowMapper<Course>{

    @Override
    public Course mapRow(ResultSet resultSet) throws SQLException {
        Course course=new Course();
        course.setCourseId(resultSet.getInt("id"));
        course.setName(resultSet.getString("name"));
        course.setTeacher(resultSet.getInt("id_teacher"));
        course.setMaxEnrollment(resultSet.getInt("maxEnrollment"));
        course.setCredits(resultSet.getInt("credits"));
        try{
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/java","eu","parola");
            Statement statement=connection.createStatement();
            ResultSet aux=statement.executeQuery("select * from students_courses where id_course="+course.getCourseId());
            List<Integer> students=new ArrayList<>();
            while(aux.next())
                students.add(aux.getInt("id_student"));

            course.setStudentsEnrolled(students);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return course;
    }
}
