package com.example.demo;

import controller.RegistrationSystem;
import entities.CustomException;
import entities.Student;
import entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StudentLogInCtrl {
    @FXML
    public Button log_in_button = null;
    public TextField log_in_placer = null;
    public TextField log_in_placer1 = null;
    public Label log_in_label = null;
    public Label log_in_label1 = null;
    public Label greetings_label = null;
    public Label error_label = null;

    public Label sign_up1 = null;
    public Label sign_up2 = null;
    public Label sign_up3 = null;
    public TextField sign_up_name_txt = null;
    public TextField sign_up_vorname_txt = null;
    public TextField sign_up_id = null;
    public Button sign_up_button = null;

    public Student entity;
    public RegistrationSystem ctrl=new RegistrationSystem();

    /***
     * diese Funktion uberpruft ob die Strings aus der
     * Vorname und Nachname Textfields zu eine Person
     * anpassen. Wenn ja, geht man zum StudentMenu und ist
     * logged in
     * @param actionEvent
     * @throws IOException
     */
    public void logIn(ActionEvent actionEvent) throws IOException {
        try {
            entity = ctrl.findStudent(new Student(log_in_placer.getText(), log_in_placer1.getText(), 0));
            if(entity!=null){
                StudentLogIn aux=new StudentLogIn();
                aux.changeScene("StudentMenu.fxml", "Student Menu", entity);
            }
        }catch (Exception e){
            error_label.setText(e.getMessage());
            System.out.println(e);
        }
    }

    /***
     * dieser Funktion erlaubt der user sich als Student
     * zu erwerben id der Database
     * @param actionEvent
     */
    public void signUp(ActionEvent actionEvent){
        try {
            ctrl.addStudent(new Student(sign_up_name_txt.getText(),sign_up_vorname_txt.getText(),Integer.parseInt(sign_up_id.getText())));
            greetings_label.setText("Sign up done");
            sign_up_name_txt.clear();
            sign_up_vorname_txt.clear();
            sign_up_id.clear();
        } catch (CustomException e){
            error_label.setText(e.getMessage());
        }
    }
}
