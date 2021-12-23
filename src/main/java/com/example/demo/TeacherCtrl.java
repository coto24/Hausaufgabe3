package com.example.demo;

import controller.RegistrationSystem;
import entities.Course;
import entities.CustomException;
import entities.Student;
import entities.Teacher;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeacherCtrl implements Initializable {

    public Label greetings_label = null;
    public Label error_label = null;
    public TableView tabel=null;

    private Teacher entity;
    private RegistrationSystem ctrl=new RegistrationSystem();

    /***
     * diese Funktion instanziert das Teacher Menu:
     * -wir machen das Format fur das TableView
     * -wir einfugen die Studenten
     * -wir sagen Hallo
     * -wir machen die letzte 2 sodass sie jede 5
     * sekunden repetieren
     * @param ur
     * @param rb
     */
    @Override
    public void initialize(URL ur, ResourceBundle rb){
        TableColumn<Pair<String,String>, String> nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey()));
        nameColumn.setMinWidth(200);

        TableColumn<Pair<String,String>, String> kursColumn= new TableColumn("Kurs");
        kursColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
        nameColumn.setMinWidth(200);


        tabel.getColumns().addAll(nameColumn, kursColumn);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            setStudents();
            greetings_label.setText("Hallo "+entity.getFirstName()+"!");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /***
     * diese Funktion bringt uns zuruck
     * zum Teacher Log In Menu
     * @param actionEvent
     * @throws IOException
     */
    public void logOut(ActionEvent actionEvent) throws IOException {
        TeacherLogIn aux=new TeacherLogIn();
        aux.changeScene("TeacherLogIn.fxml","Teacher Log In",null);
    }

    /***
     * diese Funktion setzt alle Studenten
     * dieses Lehrers im Tableview
     */
    public void setStudents(){
        try {
            tabel.getItems().clear();
            List<Course> aux=new ArrayList<Course>();
            for(Course i:ctrl.getAllCourses())
                if(i.getTeacher()==entity.getTeacherId())
                    aux.add(i);
            new Course("abc",1,1,1);

            List<Student> students=new ArrayList<Student>();
            for(Course i:aux)
                students= Stream.concat( students.stream(), ctrl.StudentsEnrolledForACourse(i).stream()).toList();


            for(Course i:aux) {
                for(Student j:ctrl.StudentsEnrolledForACourse(i)) {
                    tabel.getItems().add(new Pair<String, String>(j.getFirstName()+" "+j.getLastName(), i.getName()));
                }
            }
        }catch (CustomException e){
            error_label.setText(e.getMessage());
        }
    }

    /***
     * hier gitb man eine Werte fur
     * die Entitat
     * @param entity
     */
    public void setEntity(Teacher entity) {
        this.entity = entity;
    }
}
