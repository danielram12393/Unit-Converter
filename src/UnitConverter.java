import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnitConverter extends JFrame implements ActionListener {
    JPanel panel;
    JButton convert;


    public static final Font TMRFont = new Font("Times New Roman", 0, 40);

    public UnitConverter() {
        super("Unit Converter");

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(700,500));

        convert = new JButton("Convert");
        convert.setFont(TMRFont);
        convert.addActionListener(this);
        convert.setFocusable(false);

        panel.add(convert);

        add(panel);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(1);
    }
}
