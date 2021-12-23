package com.example.demo;

import controller.RegistrationSystem;
import entities.Course;
import entities.CustomException;
import entities.Student;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentCtrl implements Initializable {
    @FXML
    public Label credits_label = null;
    public Button register_button = null;
    public ComboBox comb = null;
    public Label greetings_label = null;
    public Label error_label = null;
    public RegistrationSystem ctrl = new RegistrationSystem();
    public Student entity;

    public void setEntity(Student s){
        entity=s;
    }

    /***
     * hier initializieren wir alles:
     * -wir setzen alle Kursen im ComboBox
     * -wir setzen die gemeinsame Anzahl von Kredite fest
     * -wir sagen ein kleines "Hallo"
     */

    @Override
    public void initialize(URL ur, ResourceBundle rb){
        ObservableList<String> list= FXCollections.observableArrayList(
                ctrl.getAllCourses().stream()
                        .sorted((c1, c2) -> c1.getName().compareTo(c2.getName()))
                        .map(p -> p.getName() + "/" + p.getCredits()+" credits").collect(Collectors.toList())
        );
        comb.setItems(list);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), ev -> {
            setCredits();
            greetings_label.setText("Hallo "+entity.getFirstName()+"!");
        }));
        timeline.play();
    }

    /***
     * diese Funktion ist von der Button register getriegert
     * sie nimmt die Kurs von der ComboBox und aus der LogIn
     * haben wir schon das Student und wir registrieren es bei
     * der Kurs
     * @param actionEvent
     */

    @FXML
    public void register(ActionEvent actionEvent){
        String s=comb.getSelectionModel().getSelectedItem().toString();
        String[] arrOfStr = s.split("/", 2);
        System.out.println(arrOfStr[0]);
        try {
            Course aux=ctrl.findCourse(new Course(arrOfStr[0],0,0,0));
            ctrl.register(aux,entity);
            greetings_label.setText("successful register");
            setCredits();
        }catch (CustomException e){
            error_label.setText(e.getMessage());
            greetings_label.setText("unsuccessful register");
        }
    }

    /***
     * diese Funktion schreibt auf credits_label
     * der Anzahl von Kredite
     */
    @FXML
    public void setCredits(){
        entity=ctrl.findStudent(entity);
        credits_label.setText("Total credits: "+entity.getTotalCredits());
    }

    /***
     * diese Funktion geht zuruck zum Log In Menu
     * ohne eine  neue Fenster zu offnen
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void logOut(ActionEvent actionEvent) throws IOException {
        StudentLogIn aux=new StudentLogIn();
        aux.changeScene("StudentLogIn.fxml","Student Log In",null);
    }
}
