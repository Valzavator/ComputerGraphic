package com.gmail.max.main;

import com.gmail.max.spaceship.Spaceship;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Spaceship window = new Spaceship();

            window.setVisible(true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
