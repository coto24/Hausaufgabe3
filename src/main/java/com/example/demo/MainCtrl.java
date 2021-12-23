package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainCtrl {

    /***
     * diese Funktion offnet das Student Log In Menu
     * falls der User das Student Button clickt
     */
    public void isStudent(ActionEvent event) throws IOException {
        StudentLogIn aux=new StudentLogIn();
        aux.start(new Stage());
    }

    /***
     * diese Funktion offnet das Teacher Log In Menu
     * falls der User der Lehrer Button clickt
     */
    public void isTeacher(ActionEvent actionEvent) throws IOException {
        TeacherLogIn aux=new TeacherLogIn();
        aux.start(new Stage());
    }
}
