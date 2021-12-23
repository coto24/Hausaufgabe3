package com.example.demo;

import entities.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentLogIn extends Application {

    private static Stage stg;

    /***
     * diese Funktion schaft ein neues Student Log In
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        stg=stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherLogIn.class.getResource("StudentLogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Student Log In");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * diese Funktion verandert die Szene aus dem Fenster mit
     * dem gegebenen FXML, mit dem gegebenen Titel und bekommt
     * auch eine Entitat, die nutzlich ist, um sie zum Student
     * Menu weiterzugeben, durch setEntity
     * @param fxml
     * @param title
     * @param entity
     * @throws IOException
     */
    public void changeScene(String fxml, String title, Student entity) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource(fxml));
        Parent pane= loader.load();
        if(entity!=null) {
            StudentCtrl aux = loader.getController();
            aux.setEntity(entity);
        }
        stg.setTitle(title);
        stg.getScene().setRoot(pane);
    }
}
