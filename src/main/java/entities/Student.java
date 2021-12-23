package entities;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Student extends Person{
    private List<Integer> courses;
    private int studentId;
    private int totalCredits;

    public Student(){}

    public Student(String firstName, String lastName, int studentId) throws CustomException {
        super(firstName, lastName);

        if (studentId < 0) {
            throw new CustomException("Invalid ID");
        }

        this.studentId = studentId;
        this.totalCredits = 0;
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course c){
        courses.add(c.getCourseId());
    }

    public void removeCourse(Course c){
        for(int i:courses){
            if(i==c.getCourseId())
                this.totalCredits-=c.getCredits();
        }
        this.courses.remove(c.getCourseId());
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> _courses) { this.courses=_courses; }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId || (this.getFirstName().equals(((Student) o).getFirstName())&&this.getLastName().equals(((Student) o).getLastName()));
    }

    @Override
    public String toString() {
        return "entities.Student{" +
                " firstName=" + getFirstName()+
                ", lastName=" + getLastName()+
                ", studentId=" + studentId +
                ", totalCredits=" + totalCredits +
                ", courses=" + courses +
                '}';
    }
}
