package org.io_poject.first_scenario;

import javax.swing.*;

public class GraphFrame extends JFrame {


    public GraphFrame() {
        super("Graph Dispaly");

        JPanel obrazPanel = new GraphPanel(this);
        obrazPanel.setSize(600,600);
        add(obrazPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        //setLayout(new FlowLayout());





        setVisible(true);
    }
}