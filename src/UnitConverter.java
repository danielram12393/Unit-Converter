import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnitConverter extends JFrame implements ActionListener {
    JPanel panel;
    JButton convert;
    JTextField leftField, rightField;
    JLabel errorMessage;

    GridBagConstraints c;

     static final Font TIMES_NEW_ROMAN = new Font("Times New Roman", Font.PLAIN, 40);
     static final Font ERROR_FONT = new Font("Times New Roman", Font.BOLD, 40);

    public UnitConverter() {
        super("Unit Converter");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanelConfig();

        conversionButtonConfig();

        textFieldConfig();

        errorMessageConfig();

        //Positioning of all components
        {
            c.gridx = 0;
            c.gridy = 1;
            panel.add(leftField, c);

            c.gridx = 1;
            c.gridy = 1;
            panel.add(rightField, c);

            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(convert, c);

            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(errorMessage, c);
        }

        add(panel);
        setVisible(true);
        pack();
    }

    private void mainPanelConfig() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(700,500));
        panel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
    }

    private void conversionButtonConfig() {
        convert = new JButton("Convert");
        convert.setPreferredSize(new Dimension(300, 80));
        convert.setFont(TIMES_NEW_ROMAN);
        convert.addActionListener(this);
        convert.setFocusable(false);
    }

    private void textFieldConfig() {
        leftField = new JTextField();
        rightField = new JTextField();

        leftField.setFont(TIMES_NEW_ROMAN);
        rightField.setFont(TIMES_NEW_ROMAN);

        leftField.setPreferredSize(new Dimension(200, 100));
        rightField.setPreferredSize(new Dimension(200, 100));

        rightField.setEditable(false);
    }

    private void errorMessageConfig() {
        errorMessage = new JLabel();
        errorMessage.setPreferredSize(new Dimension(500,40));
        errorMessage.setFont(ERROR_FONT);

        errorMessage.setForeground(Color.red);
        errorMessage.setBackground(Color.black);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = leftField.getText();

        if(!input.matches("^\\d*\\.\\d+|\\d+\\.\\d*$")) {
            errorMessage.setText("Enter a valid number!");
        } else {
            rightField.setText(input);
        }
    }
}
