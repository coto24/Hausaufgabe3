package controller;

import entities.*;
import repository.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class RegistrationSystem {
    private final StudentJdbcRepo studrepo;
    private final TeacherJdbcRepo teachrepo;
    private final CourseJdbcRepo courserepo;

    public RegistrationSystem() {
        this.studrepo = new StudentJdbcRepo();
        this.teachrepo = new TeacherJdbcRepo();
        this.courserepo = new CourseJdbcRepo();
    }

    public RegistrationSystem(StudentJdbcRepo studrepo, TeacherJdbcRepo teachrepo, CourseJdbcRepo courserepo) {
        this.studrepo = studrepo;
        this.teachrepo = teachrepo;
        this.courserepo = courserepo;
    }

    public List<Student> getStudents(){
        return studrepo.getAll();
    }

    public List<Teacher> getTeachers(){
        return teachrepo.getAll();
    }

    public Student addStudent(Student s){
        return studrepo.add(s);
    }

    public Teacher addTeacher(Teacher t){
        return teachrepo.add(t);
    }

    public Course addCourse(Course c, Teacher t){
        Teacher aux = teachrepo.getAll().stream()
                .filter(teacher -> teacher.getFirstName().compareTo(t.getFirstName())==0 &&teacher.getLastName().compareTo(t.getLastName())==0)
                .findFirst()
                .orElseThrow();
        c.setTeacher(aux.getTeacherId());
        if(!aux.getCourses().contains(c.getCourseId())) {
            aux.addCourse(c);
        }
        return courserepo.add(c);
    }

    public Student deleteStudent(Student s){
        Student aux = studrepo.getAll().stream()
                .filter(student -> (student.getFirstName().compareTo(s.getFirstName())==0 &&student.getLastName().compareTo(s.getLastName())==0) || student.getStudentId()==s.getStudentId())
                .findFirst()
                .orElseThrow();

        return studrepo.delete(aux);
    }

    public Teacher deleteTeacher(Teacher t){
        Teacher aux = teachrepo.getAll().stream()
                .filter(teacher -> teacher.getFirstName().compareTo(t.getFirstName())==0 &&teacher.getLastName().compareTo(t.getLastName())==0)
                .findFirst()
                .orElseThrow();

        for(Course i:courserepo.getAll()){
            if(i.getTeacher()==t.getTeacherId())
                this.deleteCourse(i);
        }

        return teachrepo.delete(aux);
    }

    public Course deleteCourse(Course c){
        Course aux = courserepo.getAll().stream()
                .filter(course -> course.getName().compareTo(c.getName())==0 || course.getCourseId()==c.getCourseId())
                .findFirst()
                .orElseThrow();

        for(Student i:studrepo.getAll())
            if(i.getCourses().contains(c.getCourseId())){
                i.removeCourse(c);
                try {
                    this.studrepo.updateTable("update students set totalCredits="+i.getTotalCredits() +"where id="+i.getStudentId());
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }
            }

        return courserepo.delete(aux);
    }

    public Student findStudent(Student s){return studrepo.find(s);}

    public Teacher findTeacher(Teacher t){return teachrepo.find(t);}

    public Course findCourse(Course c){return courserepo.find(c);}

    public Student updateStudent(Student oldS,Student s){
        Student aux = studrepo.getAll().stream()
                .filter(student -> (student.getFirstName().compareTo(oldS.getFirstName())==0 &&student.getLastName().compareTo(oldS.getLastName())==0) || student.getStudentId()==oldS.getStudentId())
                .findFirst()
                .orElseThrow();
        s.setStudentId(aux.getStudentId());
        return studrepo.update(s);
    }

    public Teacher updateTeacher(Teacher oldT, Teacher t){
        Teacher aux = teachrepo.getAll().stream()
                .filter(teacher -> teacher.getFirstName().compareTo(oldT.getFirstName())==0 &&teacher.getLastName().compareTo(oldT.getLastName())==0)
                .findFirst()
                .orElseThrow();
        t.setTeacherId(aux.getTeacherId());
        return teachrepo.update(t);
    }

    public Course updateCourse(Course c){
        Course auxC = courserepo.getAll().stream()
                .filter(course -> course.getName().compareTo(c.getName())==0 || course.getCourseId()==c.getCourseId())
                .findFirst()
                .orElseThrow();
        c.setCourseId(auxC.getCourseId());
        return courserepo.update(c);
    }


    /***
     * diese Function registriert ein Student
     * bei einem Kurse
     * @param course-die Kurse, wo der Student
     *              registrieren will
     * @param student-der Student, der zum
     *               gegebenen Kurse registrieren
     *               will
     * @return boolean- true, wenn die Registration
     *                  erfolgreich ausgefuhrt wurde
     *                  false, wenn:
     *                      -es kein mehr Platz am
     *                      gegebenen Kurs gibt
     *                      -der Student wurde mehr
     *                      als 30 Kreditpunkte haben,
     *                      ob er an diesem Kurs
     *                      registriert
     * @throws CustomException wenn:
     *       -die Kurse ist nicht in der Kurse Liste
     *       -der Student ist nicht in der Studenten Liste
     */
    public boolean register(Course course, Student student) throws CustomException {
        if(course==null) {
            throw new CustomException("Course cannot be null");
        }
        if(student==null)
            throw new CustomException("Student cannot be null");

        boolean checkCourse;
        boolean checkStudent;

        Course indxCourse=courserepo.find(course);
        Student indxStudent=studrepo.find(student);

        if(indxCourse==null) {
            throw new CustomException("No such course");
        }
        if(indxStudent==null)
            throw new CustomException("No such student");

        checkCourse=(indxCourse.getStudentsEnrolled().size()<indxCourse.getMaxEnrollment());
        checkStudent=(indxStudent.getTotalCredits()+indxCourse.getCredits()<=30);

        if(!checkCourse) {
            throw new CustomException("Sorry, no more free positions at this course");
        }

        if(!checkStudent) {
            throw new CustomException("Sorry, too many credits");
        }

        if(indxCourse.getStudentsEnrolled().contains(indxStudent.getStudentId())){
            throw new CustomException("Already enrolled");
        }

       try {
            indxStudent.setTotalCredits(indxStudent.getTotalCredits()+indxCourse.getCredits());
            studrepo.updateTable("insert into students_courses values("+indxStudent.getStudentId()+","+indxCourse.getCourseId()+")");
            studrepo.updateTable("update students set totalCredits="+indxStudent.getTotalCredits()+" where id="+indxStudent.getStudentId());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    /***
     * diese Funktion gibt eine Liste
     * von allen Kurse zuruck, die noch
     * Platz fur Studenten haben
     * @return List&lt;Course&gt;-die Liste
     * von noch freie Kursen
     */
    public List<Course> retrieveCoursesWithFreePlaces() {
        String sql = "select * from java.courses where maxEnrollment>(Select Count(*) from java.students_courses where id_course=id)";
        try {
            return courserepo.getRows(sql, new CourseRowMapper());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /***
     * diese Funktion gibt eine Liste von
     * Studenten zuruck, die bei einem
     * gegebenen Kurs registriert sind
     * @param c-der Kurse, dessen
     *              Studenten wir suchen
     * @return List&lt;Student&gt;-die Liste
     *         von Studenten an der
     *         gegebenen Kurse
     */
    public List<Student> StudentsEnrolledForACourse(Course c){
        Course aux = courserepo.getAll().stream()
                .filter(course -> course.getName().compareTo(c.getName())==0 || course.getCourseId()==c.getCourseId())
                .findFirst()
                .orElseThrow();

        String sql = "Select * from students inner join students_courses on students.id=students_courses.id_student where students_courses.id_course="+aux.getCourseId();
        try {
            return studrepo.getRows(sql, new StudentRowMapper());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /***
     * diese Function gibt eine Liste
     * von allen Kursen zuruck
     * @return List&lt;Course&gt;-die Liste
     *         allen Kursen
     */
    public List<Course> getAllCourses(){
        return courserepo.getAll();
    }


    /***
     * diese Funktion sortiert die Liste
     * von Studenten nach einem bestimmten
     * Attribut des Studenten Objektes
     * @param i- der i. Attribut des Students
     *         wird als Comparator fur die
     *         Sortierung benutzt
     *         (d.h. 1- Anzahl von courses,
     *               2- studentId,
     *               3- totalCredits
     *               4- firstName
     *               5- lastName)
     *         order - true, wenn die Liste ansteigend
     *                       sortiert sein soll
     *                       falsch, anderer falls
     * @return List&lt;Student&gt;-die sortierte
     *                              Liste von
     *                              Studenten
     */
    public List<Student> sortStudents(int i, boolean order){
        List<Student> rez=studrepo.getAll();
        if(i==1){
            Comparator<Student> compareByNumberOfCourses = Comparator.comparingInt((Student student) -> student.getCourses().size());
            if ((order)) {
                rez.sort(compareByNumberOfCourses);
            } else {
                rez.sort(compareByNumberOfCourses.reversed());
            }
        }

        if(i==2){
            Comparator<Student> compareByStudentId = Comparator.comparingInt((Student student) -> student.getStudentId());
            if ((order)) {
                rez.sort(compareByStudentId);
            } else {
                rez.sort(compareByStudentId.reversed());
            }
        }

        if(i==3){
            Comparator<Student> compareByTotalCredits = Comparator.comparingInt((Student student) -> student.getTotalCredits());
            if ((order)) {
                rez.sort(compareByTotalCredits);
            } else {
                rez.sort(compareByTotalCredits.reversed());
            }
        }

        if(i==4){
            Comparator<Student> compareByFirstName = (s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName());
            if ((order)) {
                rez.sort(compareByFirstName);
            } else {
                rez.sort(compareByFirstName.reversed());
            }
        }

        if(i==5){
            Comparator<Student> compareByLastName = (s1, s2) -> s1.getLastName().compareTo(s2.getLastName());
            if ((order)) {
                rez.sort(compareByLastName);
            } else {
                rez.sort(compareByLastName.reversed());
            }
        }
        return rez;
    }

    /***
     * diese Funktion sortiert die Liste
     * von Kursen nach einem bestimmten
     * Attribut des Kursen Objektes
     * @param i- der i. Attribut der Kurse
     *         wird als Comparator fur die
     *         Sortierung benutzt
     *         (d.h. 1- Name der Kurs,
     *               2- maxEnrollment,
     *               3- Anzahl von studentsErolled
     *               4- credits)
     *         order - true, wenn die Liste ansteigend
     *                 sortiert sein soll
     *                 falsch, anderer falls
     * @return List&lt;Course&gt;-die sortierte
     *                              Liste von
     *                              Kursen
     */
    public List<Course> sortCourses(int i, boolean order){
        List<Course> rez=courserepo.getAll();
        if(i==1){
            Comparator<Course> compareByName = (c1, c2) -> c1.getName().compareTo(c2.getName());
            if ((order)) {
                rez.sort(compareByName);
            } else {
                rez.sort(compareByName.reversed());
            }
        }

        if(i==2){
            Comparator<Course> compareByMaxEnrollment = Comparator.comparingInt((Course course) -> course.getMaxEnrollment());
            if ((order)) {
                rez.sort(compareByMaxEnrollment);
            } else {
                rez.sort(compareByMaxEnrollment.reversed());
            }
        }

        if(i==3){
            Comparator<Course> compareByNumberOfStudentsEnrolled = Comparator.comparingInt((Course course) -> course.getStudentsEnrolled().size());
            if ((order)) {
                rez.sort(compareByNumberOfStudentsEnrolled);
            } else {
                rez.sort(compareByNumberOfStudentsEnrolled.reversed());
            }
        }

        if(i==4){
            Comparator<Course> compareByCredits = Comparator.comparingInt((Course c) -> c.getCredits());
            if ((order)) {
                rez.sort(compareByCredits);
            } else {
                rez.sort(compareByCredits.reversed());
            }
        }
        return rez;
    }

    /***
     * diese Funktion filtriert die Liste
     * von Studenten nach einem bestimmten
     * Attribut des Studenten Objektes
     * @param i- der i. Attribut des Students
     *         wird als Comparator fur das
     *         Filter benutzt
     *         (d.h. 1- Anzahl von courses,
     *               2- studentId,
     *               3- totalCredits
     *               4- firstName
     *               5- lastName)
     *         value- das Filter soll anhand
     *                der i. Attribut des value
     *                filtrieren
     *         type - der Typ von Relation benutzt
     *                zum filtrieren
     *                (d.h. 1- &lt; , 2- &gt;, 3- =)
     * @return List&lt;Student&gt;-die sortierte
     *                              Liste von
     *                              Studenten
     */
    public List<Student> filterStudents(int i, Student value, short type)
    {
        List<Student> result= new ArrayList<>();
        if(i==1) {
            if (type == 1) {
                studrepo.getAll().stream().filter(student -> student.getCourses().size() < value.getCourses().size()).forEach(result::add);
                return result;
            }
            if (type == 2) {
                studrepo.getAll().stream().filter(student -> student.getCourses().size() < value.getCourses().size()).forEach(result::add);
                return result;
            }
            if (type == 3) {
                studrepo.getAll().stream().filter(student -> student.getCourses().size() == value.getCourses().size()).forEach(result::add);
                return result;
            }
        }

        if(i==2) {
            if (type == 1) {
                studrepo.getAll().stream().filter(student -> student.getStudentId() < value.getStudentId()).forEach(result::add);
                return result;
            }
            if (type == 2) {
                studrepo.getAll().stream().filter(student -> student.getStudentId() < value.getStudentId()).forEach(result::add);
                return result;
            }
            if (type == 3) {
                studrepo.getAll().stream().filter(student -> student.getStudentId() == value.getStudentId()).forEach(result::add);
                return result;
            }
        }

        if(i==3) {
            if (type == 1) {
                studrepo.getAll().stream().filter(student -> student.getTotalCredits() < value.getTotalCredits()).forEach(result::add);
                return result;
            }
            if (type == 2) {
                studrepo.getAll().stream().filter(student -> student.getTotalCredits() < value.getTotalCredits()).forEach(result::add);
                return result;
            }
            if (type == 3) {
                studrepo.getAll().stream().filter(student -> student.getTotalCredits() == value.getTotalCredits()).forEach(result::add);
                return result;
            }
        }

        if(i==4) {
            if (type == 1) {
                studrepo.getAll().stream().filter(student -> student.getFirstName().compareTo(value.getFirstName())<0).forEach(result::add);
                return result;
            }
            if (type == 2) {
                studrepo.getAll().stream().filter(student -> student.getFirstName().compareTo(value.getFirstName())>0).forEach(result::add);
                return result;
            }
            if (type == 3) {
                studrepo.getAll().stream().filter(student -> student.getFirstName().compareTo(value.getFirstName())==0).forEach(result::add);
                return result;
            }
        }

        if(i==5) {
            if (type == 1) {
                studrepo.getAll().stream().filter(student -> student.getLastName().compareTo(value.getLastName())<0).forEach(result::add);
                return result;
            }
            if (type == 2) {
                studrepo.getAll().stream().filter(student -> student.getLastName().compareTo(value.getLastName())>0).forEach(result::add);
                return result;
            }
            if (type == 3) {
                studrepo.getAll().stream().filter(student -> student.getLastName().compareTo(value.getLastName())==0).forEach(result::add);
                return result;
            }
        }
        return result;
    }

    /***
     * diese Funktion filtriert die Liste
     * von Kursen nach einem bestimmten
     * Attribut des Kurse-Objektes
     * @param i- der i. Attribut der Kurse
     *         wird als Comparator fur das
     *         Filter benutzt
     *         (d.h. 1- Name der Kurs,
     *               2- maxEnrollment,
     *               3- Anzahl von studentsEnrolled
     *               4- credits)
     *         value- das Filter soll anhand
     *                der i. Attribut des value
     *                filtrieren
     *         type - der Typ von Relation benutzt
     *                zum filtrieren
     *                (d.h. 1- &lt; , 2- &gt;, 3- =)
     * @return List&lt;Kursen&gt;-die sortierte
     *                              Liste von
     *                              Kursen
     */
    public List<Course> filterCourses(int i, Course value, short type){
        List<Course> result= new ArrayList<>();
        if(i==1) {
            if (type == 1) {
                courserepo.getAll().stream().filter(course -> course.getName().compareTo(value.getName())<0).forEach(result::add);
                return result;
            }
            if (type == 2) {
                courserepo.getAll().stream().filter(course -> course.getName().compareTo(value.getName())>0).forEach(result::add);
                return result;
            }
            if (type == 3) {
                courserepo.getAll().stream().filter(course -> course.getName().compareTo(value.getName())==0).forEach(result::add);
                return result;
            }
        }

        if(i==3) {
            if (type == 1) {
                courserepo.getAll().stream().filter(course -> course.getMaxEnrollment() < value.getMaxEnrollment()).forEach(result::add);
                return result;
            }
            if (type == 2) {
                courserepo.getAll().stream().filter(course -> course.getMaxEnrollment() > value.getMaxEnrollment()).forEach(result::add);
                return result;
            }
            if (type == 3) {
                courserepo.getAll().stream().filter(course -> course.getMaxEnrollment() == value.getMaxEnrollment()).forEach(result::add);
                return result;
            }
        }

        if(i==4) {
            if (type == 1) {
                courserepo.getAll().stream().filter(course -> course.getStudentsEnrolled().size() < value.getStudentsEnrolled().size()).forEach(result::add);
                return result;
            }
            if (type == 2) {
                courserepo.getAll().stream().filter(course -> course.getStudentsEnrolled().size() > value.getStudentsEnrolled().size()).forEach(result::add);
                return result;
            }
            if (type == 3) {
                courserepo.getAll().stream().filter(course -> course.getStudentsEnrolled().size() == value.getStudentsEnrolled().size()).forEach(result::add);
                return result;
            }
        }

        if(i==5){
            if (type == 1) {
                courserepo.getAll().stream().filter(course -> course.getCredits() < value.getCredits()).forEach(result::add);
                return result;
            }
            if (type == 2) {
                courserepo.getAll().stream().filter(course -> course.getCredits() > value.getCredits()).forEach(result::add);
                return result;
            }
            if (type == 3) {
                courserepo.getAll().stream().filter(course -> course.getCredits() == value.getCredits()).forEach(result::add);
                return result;
            }
        }
        return result;
    }
}
