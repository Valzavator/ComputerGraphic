package com.gmail.app.main;

import com.gmail.app.animation.TreeAnimation;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        TreeAnimation animation = new TreeAnimation();

        JFrame frame = new JFrame("Lab2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(animation);

        frame.setVisible(true);

        Dimension  size = frame.getSize();
        Insets insets = frame.getInsets();
        animation.setMaxWidth(size.width - insets.left - insets.right);
        animation.setMaxHeight(size.height - insets.top - insets.bottom);
    }
}