package com.gmail.max.main;

import com.gmail.max.airplane.Airplane;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Airplane redCube = new Airplane();
            redCube.setVisible(true);
        });
    }
}


