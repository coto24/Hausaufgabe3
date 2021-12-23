package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Teacher extends Person{
    private int teacherId;
    private List<Integer> courses;

    public Teacher(){
        super();
    }

    public Teacher(String firstName, String lastName) throws CustomException {
        super(firstName,lastName);
        this.courses = new ArrayList<>();
        int teacherId=0;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void addCourse(Course c){
        this.courses.add(c.getCourseId());
    }

    public void removeCourse(Course c){this.courses.remove(c.getCourseId());}

    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return (this.getFirstName().equals(((Teacher) o).getFirstName()) && this.getLastName().equals(((Teacher) o).getLastName()))||this.teacherId==((Teacher) o).teacherId;
    }

    @Override
    public String toString() {
        return "entities.Teacher{" +
                "id= "+ teacherId+
                ", courses=" + courses +
                "} " + super.toString();
    }
}
