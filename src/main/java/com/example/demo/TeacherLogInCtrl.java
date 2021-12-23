package com.example.demo;

import controller.RegistrationSystem;
import entities.CustomException;
import entities.Student;
import entities.Teacher;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TeacherLogInCtrl {
    //controller
    public TextField log_in_placer=null;
    public TextField log_in_placer1=null;
    public TextField sign_up_name_txt = null;
    public TextField sign_up_vorname_txt = null;
    public Label greetings_label=null;
    public Label error_label=null;
    public Teacher entity = new Teacher();
    public RegistrationSystem ctrl=new RegistrationSystem();

    /***
     * diese Funktion uberpruft ob die Strings aus der
     * Vorname und Nachname Textfields zu eine Person
     * anpassen. Wenn ja, geht man zum TeacherMenu und ist
     * logged in
     * @param actionEvent
     * @throws IOException
     */
    public void logIn(ActionEvent actionEvent) throws IOException {
        try {
            entity = ctrl.findTeacher(new Teacher(log_in_placer.getText(), log_in_placer1.getText()));
            if(entity!=null){
                new TeacherLogIn().changeScene("TeacherMenu.fxml", "Teacher Menu",entity);
            }
        }catch (Exception e){
            error_label.setText(e.getMessage());
        }
    }

    /***
     * dieser Funktion erlaubt der
     * user sich als Lehrer zu
     * erwerben id der Database
     * @param actionEvent
     */
    public void signUp(ActionEvent actionEvent){
        try {
            ctrl.addTeacher(new Teacher(sign_up_name_txt.getText(),sign_up_vorname_txt.getText()));
            greetings_label.setText("Sign up done");
            sign_up_name_txt.clear();
            sign_up_vorname_txt.clear();
        } catch (CustomException e){
            error_label.setText(e.getMessage());
        }
    }
}
