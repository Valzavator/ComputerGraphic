package com.gmail.max.airplane.main;

import com.gmail.max.airplane.model.Airplane;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Airplane airplane = new Airplane();
            airplane.setVisible(true);
        });
    }
}


