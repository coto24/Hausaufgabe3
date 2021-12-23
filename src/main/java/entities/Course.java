package entities;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int courseId;
    private String name;
    private int teacher;
    private int maxEnrollment;
    private List<Integer> studentsEnrolled;
    private int credits;

    public Course(){}

    public Course(String name, int teacher, int maxEnrollment, int credits) throws CustomException {
        if (name.isEmpty()) {
            throw new CustomException("Name cannot be null");
        }
        if (maxEnrollment < 0 || credits < 0) {
            throw new CustomException("Value cannot be negative");
        }

        this.courseId= 0;
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = new ArrayList<>();
        this.credits = credits;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacher() {
        return teacher;
    }

    public void setTeacher(int teacher) { this.teacher=teacher; }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public List<Integer> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(List<Integer> _studentEnrolled){ this.studentsEnrolled=_studentEnrolled; }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addStudent(Student s){
        studentsEnrolled.add(s.getStudentId());
    }

    public void removeStudent(Student s){this.studentsEnrolled.remove(s);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name) || this.courseId==((Course) o).courseId;
    }

    @Override
    public String toString() {
        return "entities.Course{" +
                "Name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrollment=" + maxEnrollment +
                ", credits=" + credits +
                ", studentsEnrolled="+ studentsEnrolled +
                '}';
    }
}
