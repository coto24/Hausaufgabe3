package controller;

import entities.Course;
import entities.CustomException;
import entities.Student;
import entities.Teacher;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.CourseJdbcRepo;
import repository.StudentJdbcRepo;
import repository.TeacherJdbcRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


class RegistrationSystemTest {

    private RegistrationSystem registrationSystem;

    @BeforeEach
    void setup() throws SQLException {
        CourseJdbcRepo courseRepository = Mockito.mock(CourseJdbcRepo.class, Mockito.RETURNS_DEEP_STUBS);
        StudentJdbcRepo studentRepository = Mockito.mock(StudentJdbcRepo.class, Mockito.RETURNS_DEEP_STUBS);
        TeacherJdbcRepo teacherRepository = Mockito.mock(TeacherJdbcRepo.class, Mockito.RETURNS_DEEP_STUBS);
        registrationSystem=new RegistrationSystem(studentRepository, teacherRepository, courseRepository);

        try {
            Student student = new Student("Maria", "Pop", 100);
            Student student1 = new Student("Vlad", "Popa", 101);
            Student student2 = new Student("Marian", "Popa", 102);
            Student student3 = new Student("Vlad", "Apopei", 104);
            Student student4 = new Student("Jane", "Doe", 105);

            student.setTotalCredits(29);
            student.setCourses(Arrays.asList(3));

            List<Student> students = new ArrayList<>(Arrays.asList(student, student1, student2, student3, student4));
            when(studentRepository.getAll()).thenReturn(students);
            when(studentRepository.find(argThat(s -> (s!=null) ? s.equals(student):false))).thenReturn(student);
            when(studentRepository.find(argThat(s -> (s!=null) ? s.equals(student1):false))).thenReturn(student1);
            when(studentRepository.find(argThat(s -> (s!=null) ? s.equals(student2):false))).thenReturn(student2);
            when(studentRepository.find(argThat(s -> (s!=null) ? s.equals(student3):false))).thenReturn(student3);
            when(studentRepository.find(argThat(s -> (s!=null) ? s.equals(student4):false))).thenReturn(student4);
            when(studentRepository.find(argThat(s -> (s!=null&&s.equals(student1)==false&&s.equals(student2)==false&&s.equals(student3)==false&&s.equals(student4)==false) ? true:false))).thenReturn(null);


            when(studentRepository.find(new Student("Maria", "Pop", 100))).thenReturn(student);
            when(studentRepository.find(new Student("Vlad", "Popa", 101))).thenReturn(student1);
            when(studentRepository.find(new Student("Marian", "Popa", 102))).thenReturn(student2);
            when(studentRepository.find(new Student("Vlad", "Apopei", 104))).thenReturn(student3);
            when(studentRepository.find(new Student("Jane", "Doe", 105))).thenReturn(student4);
            when(studentRepository.find(new Student("not_in", "database", 106))).thenReturn(null);

            Teacher teacher1 = new Teacher("Jan","Bartos");
            Teacher teacher2 = new Teacher("Bob","Stan");

            teacher1.setTeacherId(1);
            teacher2.setTeacherId(2);

            teacher1.setCourses(Arrays.asList(1,4));
            teacher2.setCourses(Arrays.asList(2,3));

            List<Teacher> teachers = new ArrayList<>(Arrays.asList(teacher1, teacher2));
            when(teacherRepository.getAll()).thenReturn(teachers);
            when(teacherRepository.find(argThat(t -> (t!=null) ? t.equals(teacher1):false))).thenReturn(teacher1);
            when(teacherRepository.find(argThat(t -> (t!=null) ? t.equals(teacher2):false))).thenReturn(teacher2);
            when(teacherRepository.find(argThat(t -> (t!=null&&t.equals(teacher1)==false&&t.equals(teacher2)==false) ? true:false))).thenReturn(null);

            Course databases = new Course("DB", 1, 80, 50);
            Course oop = new Course("OOP", 2, 0, 6);
            Course map = new Course("MAP", 2, 50, 29);
            Course algebra = new Course("Algebra", 1, 60,5);

            map.setStudentsEnrolled(Arrays.asList(100));
            databases.setCourseId(1);
            oop.setCourseId(2);
            map.setCourseId(3);
            algebra.setCourseId(4);

            List<Course> courses = new ArrayList<>(Arrays.asList(databases, oop, map, algebra));
            when(courseRepository.getAll()).thenReturn(courses);


            when(courseRepository.find(argThat(c -> (c!=null) ? c.equals(databases):false))).thenReturn(databases);
            when(courseRepository.find(argThat(c -> (c!=null) ? c.equals(oop):false))).thenReturn(oop);
            when(courseRepository.find(argThat(c -> (c!=null) ? c.equals(map):false))).thenReturn(map);
            when(courseRepository.find(argThat(c -> (c!=null) ? c.equals(algebra):false))).thenReturn(algebra);
            when(courseRepository.find(argThat(c -> (c!=null&&c.equals(databases)==false&&c.equals(oop)==false&&c.equals(map)==false&&c.equals(algebra)==false) ? true:false))).thenReturn(null);

            when(courseRepository.getRows(anyString(),any())).thenCallRealMethod();
            when(registrationSystem.retrieveCoursesWithFreePlaces()).thenCallRealMethod();

        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Description("man uberpruft ob es eine Exception gibt, wenn die Kurse in der Datenbank nicht existiert")
    void registerNoSuchCourse() {
        try{
            registrationSystem.register(new Course("not_in_database",0,11,10), new Student("Jane", "Doe", 105));
            assert(false);
        }catch (CustomException e){
            if(e.getMessage().compareTo("No such course")==0)
                assert(true);
        }
    }

    @Test
    @Description("man uberpruft ob es eine Exception gibt, wenn das Student in der Datenbank nicht existiert")
    void registerNoSuchStudent() {
        try{
            registrationSystem.register(new Course("DB", 1, 80, 4), new Student("not_in", "database", 106));
            assert(false);
        }catch (CustomException e){
            if(e.getMessage().compareTo("No such student")==0)
                assert(true);
        }
    }

    @Test
    @Description("die Funktion returniert eine Exceptio, weil es kein mehr Platz bei dieser Kurs")
    void registerNoPlace() {
        try{
            assertFalse(registrationSystem.register(new Course("OOP", 2, 0, 6),new Student("Vlad", "Popa", 101)));
        }catch (CustomException e){
            System.out.println(e.getMessage());
            assert(true);
        }
    }

    @Test
    @Description("die Funktion returniert ein Exception, weil der Student zu viele Kredite hat")
    void registerTooManyCredits() {
        try{
            assertFalse(registrationSystem.register(new Course("DB", 1, 80, 50),new Student("Maria", "Pop", 100)));
        }catch (CustomException e){
            System.out.println(e.getMessage());
            assert(true);
        }
    }

    @Test
    @Description("man bekommt zuruck alle Kursen, ohne oop wo es kein mehr Platz gibt")
    void retrieveCoursesWithFreePlaces(){
        List<Course> aux=registrationSystem.retrieveCoursesWithFreePlaces();
        assert(aux.get(0).equals(registrationSystem.getAllCourses().get(0)));
        assert(aux.get(1).equals(registrationSystem.getAllCourses().get(2)));
        assert(aux.get(2).equals(registrationSystem.getAllCourses().get(3)));
    }

    @Test
    @Description("man bekommt zuruck alle Kursen, ohne oop wo es kein mehr Platz gibt")
    void StudentsEnrolledForACourse(){
        try {
            List<Student> aux=(((new RegistrationSystem().StudentsEnrolledForACourse(new Course("MAP", 2, 50, 29)))));
            assert(aux.size()==1);
            assert(aux.get(0).equals(new Student("Maria","Pop",100)));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Description("returniert alle Kursen")
    void getAllCourses(){
        try {
            new Course("abc",1,1,1);
            List<Course> aux=new RegistrationSystem().getAllCourses();
            assert(aux.size()==registrationSystem.getAllCourses().size());
            assert(aux.get(0).equals(registrationSystem.getAllCourses().get(0)));
            assert(aux.get(1).equals(registrationSystem.getAllCourses().get(1)));
            assert(aux.get(2).equals(registrationSystem.getAllCourses().get(2)));
            assert(aux.get(3).equals(registrationSystem.getAllCourses().get(3)));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Description("wir sortierene zB nach firstName alle Studenten")
    void sortStudents(){
        List<String> aux=new RegistrationSystem().sortStudents(4,true).stream()
                    .collect(Collectors.mapping(
                            p -> p.getFirstName(),
                            Collectors.toList()
                    ));
        assert (aux.get(0).compareTo("Jane")==0);
        assert (aux.get(1).compareTo("Maria")==0);
        assert (aux.get(2).compareTo("Marian")==0);
        assert (aux.get(3).compareTo("Vlad")==0);
        assert (aux.get(4).compareTo("Vlad")==0);
    }

    @Test
    @Description("wir sortierene zB nach credits alle Kursen")
    void sortCourses(){
        List<Integer> aux=new RegistrationSystem().sortCourses(4,true).stream()
                .collect(Collectors.mapping(
                        p -> p.getCredits(),
                        Collectors.toList()
                ));

        assert (aux.get(0)==5);
        assert (aux.get(1)==6);
        assert (aux.get(2)==29);
        assert (aux.get(3)==50);
    }

    @Test
    @Description("wir filtern alle Studenten z.b. nach studentId")
    void filterStudents(){
        try {
            List<Integer> aux = new RegistrationSystem().filterStudents(2, new Student("-", "-", 103), (short)1).stream()
                    .collect(Collectors.mapping(
                            p -> p.getStudentId(),
                            Collectors.toList()
                    ));

            //alle Studenten mit StudentId <103
            assert (aux.get(0)==100);
            assert (aux.get(1)==101);
            assert (aux.get(2)==102);
        }catch (CustomException e){
            System.out.println(e.getMessage());
            assert (false);
        }
    }

    @Test
    @Description("wir filtern alle Kursen z.b. nach Name")
    void filterCourses(){
        try {
            List<String> aux = new RegistrationSystem().filterCourses(1, new Course("Mb",1,1,1), (short)1).stream()
                    .collect(Collectors.mapping(
                            p -> p.getName(),
                            Collectors.toList()
                    ));

            //alle Kursen mit Name <"Mb"
            assert (aux.get(0).compareTo("DB")==0);
            assert (aux.get(1).compareTo("MAP")==0);
            assert (aux.get(2).compareTo("Algebra")==0);
        }catch (CustomException e){
            System.out.println(e.getMessage());
            assert (false);
        }
    }
}