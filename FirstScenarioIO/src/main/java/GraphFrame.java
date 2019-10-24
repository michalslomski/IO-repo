import javax.swing.*;


public class GraphFrame extends JFrame{

    public GraphFrame(){
        super("Graph Viever");

        JPanel display = new GraphPanel(this);
        display.setSize(600,600);
        add(display);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setVisible(true);

    }
}
