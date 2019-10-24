import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphDisplay extends JPanel implements ActionListener {

    private BufferedImage image;
    private JButton button;
    private JLabel imageLabel;
    private JFrame frame;
    private JTextArea viewingInfo;
    private GridBagLayout layout;

    public GraphDisplay(JFrame frame) {
        super();
        this.frame = frame;
        layout = new GridBagLayout();
        setLayout(layout);

        button=new JButton("Graph 1");
        button.addActionListener(this);

        viewingInfo = new JTextArea("Displaying Graph 1");
        viewingInfo.setEditable(false);

        imageLabel = new JLabel();

        GridBagConstraints button1Constraints = new GridBagConstraints();
        button1Constraints.gridx = 1;
        button1Constraints.gridy = 0;
        button1Constraints.weightx = 1.0;
        button1Constraints.weighty = 1.0;


        GridBagConstraints viewingInfoConstraints = (GridBagConstraints) button1Constraints.clone();
        button1Constraints.gridx = 0;

        GridBagConstraints photoConstraints = new GridBagConstraints();
        photoConstraints.gridx = 0;
        photoConstraints.gridy = 1;
        photoConstraints.gridwidth = 3;
        photoConstraints.gridheight = 1;
        photoConstraints.weightx = 1.0;
        photoConstraints.weighty = 1.0;


        add(viewingInfo, viewingInfoConstraints);
        add(button, button1Constraints);
        add(imageLabel, photoConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        ImageIcon icon = null;
        if(action.getSource()==button)
        {
            File imageFile = new File("E:\\GIT\\IO-repo\\FirstScenarioIO\\src\\main\\java\\graf.png"); // direct path to graph
            viewingInfo.setText("Displaying Graph 1");
            try {
                image = ImageIO.read(imageFile);
                icon = new ImageIcon( image );
                icon = new ImageIcon(icon.getImage().getScaledInstance(700,700, Image.SCALE_SMOOTH));

            } catch (IOException e) {
                System.err.println("Error in displaying graph!");
            }
        }

        if(icon != null) {
            imageLabel.setIcon(icon);
        }

        this.frame.pack();
    }
}