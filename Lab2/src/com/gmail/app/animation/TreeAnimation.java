package com.gmail.app.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;

public class TreeAnimation extends JPanel implements ActionListener {
    private int maxWidth;
    private int maxHeight;

    private double angle = 0;
    private double scale = 1;
    private double delta = 0.01;

    private Timer timer;
    private final static int DELAY_DIFFERENCE = 10;
    private final static int BORDER_OFFSET = 40;

    public TreeAnimation() {
        timer = new Timer(10, this);
        timer.start();
        addMouseWheelListener(e -> {
                    if (e.getWheelRotation() > 0) {
                        increaseTimerDelay();
                    } else {
                        reduceTimerDelay();
                    }
                }
        );
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );
        g2d.setRenderingHints(rh);

        g2d.setBackground(new Color(0, 128, 255));
        g2d.clearRect(0, 0, maxWidth, maxHeight);

        g2d.translate(maxWidth / 2, maxHeight / 2);

        BasicStroke bs2 = new BasicStroke(16, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(bs2);
        g2d.setColor(Color.YELLOW);
        g2d.drawRect(
                -maxWidth / 2 + BORDER_OFFSET,
                -maxHeight / 2 + BORDER_OFFSET,
                maxWidth - 2 * BORDER_OFFSET,
                maxHeight - 2 * BORDER_OFFSET
        );

        g2d.drawLine(0, 0, 0, 0);

        double[][] coverOfTreePoints = {
                {0, 8.0},
                {63, 94.0},
                {39, 94.0},
                {81, 161.0},
                {47, 161.0},
                {86, 220.0},
                {-57, 220.0},
                {-20, 161.0},
                {-51, 161.0},
                {-18, 86.0},
                {-41, 86.0}
        };

        GeneralPath coverOfTree = new GeneralPath();
        coverOfTree.moveTo(coverOfTreePoints[0][0], coverOfTreePoints[0][1]);
        for (int k = 1; k < coverOfTreePoints.length; k++) {
            coverOfTree.lineTo(coverOfTreePoints[k][0], coverOfTreePoints[k][1]);
        }

        coverOfTree.closePath();

        g2d.rotate(angle);
        g2d.scale(scale, scale);

        g2d.setColor(new Color(0, 128, 0));
        g2d.fill(coverOfTree);

        g2d.setColor(new Color(128, 64, 0));
        g2d.fillRect(-9, 220, 40, 100);

        GradientPaint gp = new GradientPaint(21, 115,
                new Color(255, 8, 0),
                26, 120,
                new Color(251, 255, 245),
                true);
        g2d.setPaint(gp);

        g2d.fillRect(21, 115, 18, 19);
        g2d.fillRect(-12, 126, 18, 19);
        g2d.fillRect(17, 170, 18, 19);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (scale < 0.1 || scale > 0.99) {
            delta = -delta;
        }

        scale += delta;
        angle -= 0.01;

        repaint();
    }

    private void increaseTimerDelay() {
        int newDelay = timer.getDelay() + DELAY_DIFFERENCE;
        if (newDelay <= 100)
            timer.setDelay(newDelay);
    }

    private void reduceTimerDelay() {
        int newDelay = timer.getDelay() - DELAY_DIFFERENCE;
        if (newDelay >= 10)
            timer.setDelay(newDelay);
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
