package com.gmail.app.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;

public class TreeAnimation extends JPanel implements ActionListener {
    private int maxWidth;
    private int maxHeight;

    private double angle = 0;
    private double angleDelta = 0.01;
    private double scale = 1;
    private double scaleDelta = 0.01;

    private Timer timer;
    private final static int DELAY_DIFFERENCE = 10;
    private final static int BORDER_OFFSET = 40;

    public TreeAnimation() {
        timer = new Timer(10, this);
        timer.start();
        addMouseWheelListener(e -> {
                    if (e.getWheelRotation() > 0 && angleDelta > 0.01) {
                        angleDelta -= 0.01;

                    } else if (e.getWheelRotation() < 0 && angleDelta < 0.1) {
                        angleDelta += 0.01;
                    }
                }
        );
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && Math.abs(scaleDelta) > 0.001) {
                    scaleDelta = Math.signum(scaleDelta) * (Math.abs(scaleDelta) - 0.001);
                } else if (SwingUtilities.isLeftMouseButton(e) && Math.abs(scaleDelta) < 0.01) {
                    scaleDelta = Math.signum(scaleDelta) * (Math.abs(scaleDelta) + 0.001);
                }
            }
        });
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

        double[][] starPoints = {
                {-30, -20}, {-10, -23}, {0, -50}, {10, -23},
                {30, -20}, {15, -10}, {18, 13}, {0, 0},
                {-18, 13}, {-15, -10}, {-30, -20}
        };

        GeneralPath star = drawPathByPoints(starPoints);

        double[][] coverOfTreePoints = {
                {0, 0},
                {63, 86},
                {39, 86},
                {81, 153},
                {47, 153},
                {86, 212},
                {-57, 212},
                {-20, 153},
                {-51, 153},
                {-18, 78},
                {-41, 78}
        };

        GeneralPath coverOfTree = drawPathByPoints(coverOfTreePoints);

        g2d.rotate(angle);
        g2d.scale(scale, scale);

        g2d.setColor(new Color(0, 128, 0));
        g2d.fill(coverOfTree);

        g2d.setColor(new Color(255, 100, 14));
        g2d.fill(star);

        g2d.setColor(new Color(128, 64, 0));
        g2d.fillRect(-9, 212, 40, 100);

        GradientPaint gp = new GradientPaint(21, 115,
                new Color(255, 8, 0),
                26, 120,
                new Color(251, 255, 245),
                true);
        g2d.setPaint(gp);

        g2d.fillRect(21, 107, 18, 19);
        g2d.fillRect(-12, 118, 18, 19);
        g2d.fillRect(17, 162, 18, 19);
    }

    private GeneralPath drawPathByPoints(double[][] points) {
        GeneralPath path = new GeneralPath();
        path.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++) {
            path.lineTo(points[k][0], points[k][1]);
        }
        path.closePath();
        return path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (scale < 0.1 || scale > 0.99) {
            scaleDelta = -scaleDelta;
        }

        scale += scaleDelta;
        angle -= angleDelta;

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
