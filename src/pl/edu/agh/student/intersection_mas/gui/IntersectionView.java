package pl.edu.agh.student.intersection_mas.gui;

import pl.edu.agh.student.intersection_mas.intersection.Intersection;

import javax.swing.*;

/**
 * Created by maciek on 21.05.16.
 */
public class IntersectionView extends JFrame {
    private static final String WINDOW_NAME = "ZURMAS";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private IntersectionPanel intersectionPanel;

    public IntersectionView(Intersection intersection) {
        super(WINDOW_NAME);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.intersectionPanel = new IntersectionPanel(intersection);
        add(intersectionPanel);
    }

    public void updateView() {
        intersectionPanel.repaint();
    }
}
