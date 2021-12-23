package com.example.demo;

import controller.RegistrationSystem;
import entities.CustomException;
import view.KonsoleView;

public class Main {

    //hier man kann noch die Konsole verwenden
    public static void main(String[] args) throws CustomException {
        KonsoleView consola=new KonsoleView();
        consola.start();
    }
}
