package view;

import controller.RegistrationSystem;
import entities.Course;
import entities.CustomException;
import entities.Student;
import entities.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class KonsoleView {
    protected  RegistrationSystem ctrl;

    public KonsoleView(){
        ctrl=new RegistrationSystem();
    }

    public void start(){
        System.out.println("Menu\n" +
                "1. addStudent\n" +
                "2. addTeacher\n" +
                "3. addCourse\n" +
                "4. deleteStudent\n" +
                "5. deleteTeacher\n"+
                "6. deleteCourse\n" +
                "7. updateStudent\n" +
                "8. updateTeacher\n" +
                "9. updateCourse\n" +
                "10. register\n" +
                "11. retrieveCoursesWithFreePlaces\n" +
                "12. StudentsEnrolledForACourse\n" +
                "13. getAllCourses\n" +
                "14. sortStudents\n" +
                "15. sortCourses\n" +
                "16. filterStudents\n" +
                "17. filterCourses\n" +
                "18. exit");

        Scanner myInput = new Scanner( System.in );
        int option= myInput.nextInt();
        switch (option){
            case 1: addStudent(myInput); break;
            case 2: addTeacher(myInput); break;
            case 3: addCourse(myInput); break;
            case 4: deleteStudent(myInput); break;
            case 5: deleteTeacher(myInput); break;
            case 6: deleteCourse(myInput); break;
            case 7: updateStudent(myInput); break;
            case 8: updateTeacher(myInput); break;
            case 9: updateCourse(myInput); break;
            case 10: register(myInput); break;
            case 11: retrieveCoursesWithFreePlaces(); break;
            case 12: StudentsEnrolledForACourse(myInput); break;
            case 13: getAllCourses(); break;
            case 14: sortStudents(myInput); break;
            case 15: sortCourses(myInput); break;
            case 16: filterStudents(myInput); break;
            case 17: filterCourses(myInput); break;
            case 18: exit(myInput); break;
            default:
                System.out.println("not a valid option, try again");
                start();
                break;
        }
    }

    void addStudent(Scanner myInput) {
        String firstName,lastName;
        int studentId;
        System.out.println("Adding Student. Please enter details:\n" +
                "firstName:");
        myInput.nextLine();
        firstName=myInput.nextLine();
        System.out.println("lastName:");
        lastName=myInput.nextLine();
        System.out.println("studentId:");
        studentId=myInput.nextInt();
        try {
            ctrl.addStudent(new Student(firstName, lastName, studentId));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void addTeacher(Scanner myInput){
        String firstName,lastName;
        System.out.println("Adding Teacher. Pelase enter details:\n" +
                "firstName:");
        myInput.nextLine();
        firstName=myInput.nextLine();
        System.out.println("lastName:");
        lastName=myInput.nextLine();
        try {
            ctrl.addTeacher(new Teacher(firstName,lastName));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void addCourse(Scanner myInput){
        String name,firstName, lastName;
        int maxEnrolled, credits;
        System.out.println("Adding Course. Please enter details:\n" +
                "name:");
        myInput.nextLine();
        name=myInput.nextLine();
        System.out.println("Teacher's firstName:");
        firstName=myInput.nextLine();
        System.out.println("Teacher's lastName:");
        lastName=myInput.nextLine();
        System.out.println("maxEnrolled:");
        maxEnrolled=myInput.nextInt();
        System.out.println("credits:");
        credits=myInput.nextInt();
        try {
            ctrl.addCourse(new Course(name, 0, maxEnrolled, credits), new Teacher(firstName, lastName));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void deleteStudent(Scanner myInput){
        int studentId=0;
        String firstName="-", lastName="-";
        System.out.println("Deleting Student. Please enter details[studentId/lastName+firstName]:\n" +
                "studentId:");
        myInput.nextLine();
        studentId=myInput.nextInt();
        System.out.println("firstName");
        firstName=myInput.nextLine();
        System.out.println("lastName:");
        lastName=myInput.nextLine();
        try {
            ctrl.deleteStudent(new Student(firstName,lastName,studentId));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void deleteTeacher(Scanner myInput){
        String firstName,lastName;
        System.out.println("Deleting Teacher. Please enter details:\n" +
                "firstName:");
        myInput.nextLine();
        firstName=myInput.nextLine();
        System.out.println("lastName:\n");
        lastName=myInput.nextLine();

        try {
            ctrl.deleteTeacher(new Teacher(firstName,lastName));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void deleteCourse(Scanner myInput){
        String name;
        System.out.println("Deleting Course. Pelase enter details:\n" +
                "name:");
        myInput.nextLine();
        name=myInput.nextLine();

        try {
            ctrl.deleteCourse(new Course(name, 0,1,1));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void updateStudent(Scanner myInput){
        int id;
        String firstName="-", lastName="-",new_firstName="-", new_lastName="-";
        System.out.println("Updating Student. Please enter details[firstName+lastName/studentId]:\n" +
                "id:");
        myInput.nextLine();
        id=myInput.nextInt();
        System.out.println("firstName:");
        firstName=myInput.nextLine();
        System.out.println("lastName:");
        lastName=myInput.nextLine();

        System.out.println("Update firstName?[yes/no]");
        String input;
        input=myInput.nextLine();
        if(input.compareTo("yes")==0){
            System.out.println("new firstName:");
            new_firstName=myInput.nextLine();
        }
        System.out.println("Update lastName?[yes/no]");
        input=myInput.nextLine();
        if(input.compareTo("yes")==0){
            System.out.println("new lastName:");
            new_lastName=myInput.nextLine();
        }

        try {
            ctrl.updateStudent(new Student(firstName,lastName,id),new Student(new_firstName,new_lastName,0));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void updateTeacher(Scanner myInput){
        List<Course> courses = new ArrayList<>();
        String firstName="-", lastName="-", new_firstName="-", new_lastName="-";
        System.out.println("Updating Teacher. Please enter details:\n" +
                "firstName:");
        myInput.nextLine();
        firstName=myInput.nextLine();
        System.out.println("lastName:");
        lastName=myInput.nextLine();

        System.out.println("Update firstName?[yes/no]");
        String input;
        input=myInput.nextLine();
        if(input.compareTo("yes")==0){
            System.out.println("new firstName:");
            new_firstName=myInput.nextLine();
        }
        System.out.println("Update lastName?[yes/no]");
        input=myInput.nextLine();
        if(input.compareTo("yes")==0){
            System.out.println("new lastName:");
            new_lastName=myInput.nextLine();
        }

        try {
            ctrl.updateTeacher(new Teacher(firstName,lastName),new Teacher(new_firstName,new_lastName));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void updateCourse(Scanner myInput){
        String name;
        try{
            int maxEnrollment=0, credits=0;
            System.out.println("Updating Course. Please enter details:\n" +
                    "name:");
            myInput.nextLine();
            name=myInput.nextLine();


            System.out.println("Want to update maxEnrolled?[yes/no]");
            String input=myInput.nextLine();
            if(input.compareTo("yes")==0)
            {
                System.out.println("new value for maxEnrolled:");
                maxEnrollment=myInput.nextInt();
            }

            System.out.println("Want to update credits?[yes/no]");
            input=myInput.nextLine();
            if(input.compareTo("yes")==0)
            {
                System.out.println("new value for credits:");
                credits=myInput.nextInt();
            }

            ctrl.updateCourse(new Course(name,0,maxEnrollment,credits));
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        start();
    }

    void register(Scanner myInput){
        String name;
        int id;
        System.out.println("Registering student in course. Please enter details:\n");
        System.out.println("course name:");
        myInput.nextLine();
        name=myInput.nextLine();

        System.out.println("student id:");
        id=myInput.nextInt();

        try{
            boolean ok=ctrl.register(new Course(name,0,0,0),new Student("-","-",id));
            if(!ok){
                System.out.println("Upsie, that didn't work. want to ty again?[yes/no]");
                String input=myInput.nextLine();
                if(input.compareTo("yes")==0)
                    register(myInput);
            }
        }catch(CustomException e){
            System.out.println(e.getMessage());
        }
    }

    void retrieveCoursesWithFreePlaces(){
        System.out.println("Courses with free places:\n");
        System.out.println(Arrays.toString(ctrl.retrieveCoursesWithFreePlaces().toArray()));
        start();
    }

    void StudentsEnrolledForACourse(Scanner myInput){
        String name;
        System.out.println("Name of course:");
        myInput.nextLine();
        name=myInput.nextLine();
        System.out.println("Students from "+ name +" :");
        System.out.println(Arrays.toString(ctrl.StudentsEnrolledForACourse(new Course()).toArray()));
        start();
    }

    void getAllCourses(){
        System.out.println("All courses:\n");
        System.out.println(Arrays.toString(ctrl.getAllCourses().toArray()));
        start();
    }

    void sortStudents(Scanner myInput){
        System.out.println("Sorting students. Please enter details:\n");
        System.out.println("Which attribute do you want to sort by?[1,2,3]:\n" +
                "1- Anzahl von courses,\n" +
                "2- studentId,\n" +
                "3- totalCredits\n" +
                "4- firstName\n" +
                "5- lastName");
        myInput.nextLine();
        int i=myInput.nextInt();
        System.out.println("Want it ascending or descending?[asc/desc]");
        myInput.nextLine();
        String input=myInput.nextLine();
        boolean order= input.compareTo("asc") == 0;
        System.out.println("Sorted student list:");
        System.out.println(Arrays.toString(ctrl.sortStudents(i,order).toArray()));
        start();
    }

    void sortCourses(Scanner myInput){
        System.out.println("Sorting courses. Please enter details:\n");
        System.out.println("Which attribute do you want to sort by?[1,2,3,4,5]:\n" +
                "1- Name der Kurs\n"+
                "2- maxEnrollment,\n"+
                "3- Anzahl von studentsEnrolled,\n"+
                "4- credits");
        myInput.nextLine();
        int i=myInput.nextInt();
        System.out.println("want it ascending or descending?[asc/desc]");
        String input=myInput.nextLine();
        boolean order= input.compareTo("asc") == 0;
        System.out.println("Sorted student list:");
        System.out.println(Arrays.toString(ctrl.sortCourses(i,order).toArray()));
        start();
    }

    void filterStudents(Scanner myInput) {
        try {
            Student s = new Student("-", "-", 0);
            System.out.println("Filtering students. Please enter details:\n");
            System.out.println("Which attribute do you want to filter by?[1,2,3]:\n" +
                    "1- Anzahl von courses,\n" +
                    "2- studentId,\n" +
                    "3- totalCredits\n" +
                    "4- firstName\n" +
                    "5- lastName");
            myInput.nextLine();
            int i = myInput.nextInt();

            switch (i){
                case 1:
                    System.out.println("value of anzahl von courses:");
                    int x=myInput.nextInt();
                    while(x>0){
                        s.addCourse(new Course("abc",0,0,0));
                        x--;
                    }
                    break;
                case 2:
                    System.out.println("value of studentId:");
                    int y=myInput.nextInt();
                    s.setStudentId(y);
                    break;
                case 3:
                    System.out.println("value of totalCredits:");
                    int z=myInput.nextInt();
                    s.setTotalCredits(z);
                    break;
                case 4:
                    System.out.println("value of firstName:");
                    String firstName=myInput.nextLine();
                    s.setFirstName(firstName);
                    break;
                case 5:
                    System.out.println("value of lastName:");
                    String lastName=myInput.nextLine();
                    s.setLastName(lastName);
                    break;
                default:
                    break;
            }

            System.out.println("What relationship to use?[1,2,3]:\n" +
                    "1- all lower than value\n" +
                    "2- all higher than value,\n" +
                    "3- all equal to value");
            short type = (short)myInput.nextInt();

            System.out.println("Filtered student list:");
            System.out.println(Arrays.toString(ctrl.filterStudents(i, s, type).toArray()));
            start();
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }

    void filterCourses(Scanner myInput){
        try {
            Course c = new Course("abc",0,0,0);
            System.out.println("Filtering courses. Please enter details:\n");
            System.out.println("Which attribute do you want to filter by?[1,2,3,4,5]:\n" +
                    "1- name der Kurse,\n" +
                    "2- maxEnrolled\n" +
                    "3- Anzahl von studentsEnrolled\n" +
                    "4- credits");
            myInput.nextLine();
            int i = myInput.nextInt();

            switch (i){
                case 1:
                    System.out.println("name der Kurse:");
                    String in=myInput.nextLine();
                    c.setName(in);
                    break;
                case 2:
                    System.out.println("maxEnrolled:");
                    int max=myInput.nextInt();
                    c.setMaxEnrollment(max);
                    break;
                case 3:
                    System.out.println("anzahl von studenten enrolled:");
                    int nr=myInput.nextInt();
                    while(nr>0){
                        c.addStudent(new Student("john","doe",0));
                        nr--;
                    }
                    break;
                case 4:
                    System.out.println("credits:");
                    int credits=myInput.nextInt();
                    c.setCredits(credits);
                    break;
                default:
                    break;
            }

            System.out.println("What relationship to use?[1,2,3]:\n" +
                    "1- all lower than value\n" +
                    "2- all higher than value,\n" +
                    "3- all equal to value");
            short type = (short)myInput.nextInt();

            System.out.println("Filtered course list:");
            System.out.println(Arrays.toString(ctrl.filterCourses(i, c, type).toArray()));
            start();
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }

    void exit(Scanner myInput){
        System.out.println("Are you sure?[yes/no]");
        myInput.nextLine();
        String input=myInput.nextLine();
        if(input.compareTo("yes")==0)
            return;
        start();
    }
}
