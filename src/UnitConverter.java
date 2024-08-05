import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UnitConverter extends JFrame implements ActionListener {
    JPanel panel;
    JButton convertButton;
    JTextField leftField, rightField;
    JLabel errorMessage;
    JComboBox conversionTypeSelection;
    JComboBox leftUnitSelection, rightUnitSelection;

    GridBagConstraints c;

    static final Font TIMES_NEW_ROMAN = new Font("Times New Roman", Font.PLAIN, 40);
    static final Font ERROR_FONT = new Font("Times New Roman", Font.BOLD, 30);

    final String[] measurementCategories = {"Length",
            "Weight/Mass",
            "Volume",
            "Temperature",
            "Area",
            "Speed",
            "Time",
            "Energy",
            "Pressure",
            "Power"};
    final String[][] measurements = {{"Millimeters (mm)", "Centimeters (cm)", "Meters (m)", "Kilometers (km)", "Inches (in)", "Feet (ft)", "Yards (yd)", "Miles (mi)"},
            {"Milligrams (mg)", "Grams (g)", "Kilograms (kg)", "Metric tons (t)", "Ounces (oz)", "Pounds (lb)", "Stones (st)"},
            {"Milliliters (ml)", "Liters (l)", "Cubic centimeters (cc)", "Cubic meters (m³)", "Teaspoons (tsp)", "Tablespoons (tbsp)", "Fluid ounces (fl oz)", "Cups", "Pints (pt)", "Quarts (qt)", "Gallons (gal)"},
            {"Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)"},
            {"Square millimeters (mm²)", "Square centimeters (cm²)", "Square meters (m²)", "Hectares (ha)", "Square kilometers (km²)", "Square inches (in²)", "Square feet (ft²)", "Square yards (yd²)", "Acres", "Square miles (mi²)"},
            {"Meters per second (m/s)", "Kilometers per hour (km/h)", "Miles per hour (mph)", "Knots (nautical miles per hour)"},
            {"Seconds (s)", "Minutes (min)", "Hours (h)", "Days", "Weeks", "Months", "Years"},
            {"Joules (J)", "Kilojoules (kJ)", "Calories (cal)", "Kilocalories (kcal)", "Watt-hours (Wh)", "Kilowatt-hours (kWh)"},
            {"Pascals (Pa)", "Kilopascals (kPa)", "Bars", "Atmospheres (atm)", "Pounds per square inch (psi)"},
            {"Watts (W)", "Kilowatts (kW)", "Horsepower (hp)"}};

    public UnitConverter() {
        super("Unit Converter");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanelConfig();

        conversionButtonConfig();

        textFieldConfig();

        errorMessageConfig();

        unitSelectionConfig();

        conversionTypeConfig();


        {
            c.gridx = 0;
            c.gridy = 0;
            panel.add(leftUnitSelection, c);

            c.gridx = 2;
            c.gridy = 0;
            panel.add(rightUnitSelection, c);

            c.gridx = 0;
            c.gridy = 1;
            panel.add(leftField, c);

            c.gridx = 1;
            c.gridy = 1;
            panel.add(conversionTypeSelection, c);

            c.gridx = 2;
            c.gridy = 1;
            panel.add(rightField, c);

            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(convertButton, c);



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
        convertButton = new JButton("Convert");
        convertButton.setPreferredSize(new Dimension(300, 80));
        convertButton.setFont(TIMES_NEW_ROMAN);
        convertButton.addActionListener(this);
        convertButton.setFocusable(false);
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
        errorMessage.setOpaque(true);
        errorMessage.setForeground(Color.red);
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void conversionTypeConfig() {
        conversionTypeSelection = new JComboBox(measurementCategories);
        conversionTypeSelection.addActionListener(this);
        conversionTypeSelection.setSelectedIndex(0);
        conversionTypeSelection.setEditable(false);
    }
    
    private void unitSelectionConfig() {
        leftUnitSelection = new JComboBox(measurements[0]);
        rightUnitSelection = new JComboBox(measurements[0]);
        
        leftUnitSelection.addActionListener(this);
        rightUnitSelection.addActionListener(this);

        leftUnitSelection.setSelectedIndex(0);
        rightUnitSelection.setSelectedIndex(0);

        leftUnitSelection.setEditable(false);
        rightUnitSelection.setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == convertButton) {
            String input = leftField.getText();

            if (!input.matches("^\\d*\\.\\d+|\\d+\\.\\d*$")) {
                errorMessage.setText("Enter a valid number!");
            } else {
                rightField.setText(input);
            }
        }

        if(e.getSource() == conversionTypeSelection) {

            leftUnitSelection.removeAllItems();
            rightUnitSelection.removeAllItems();

            for(String unit : measurements[conversionTypeSelection.getSelectedIndex()]) {
                leftUnitSelection.addItem(unit);
                rightUnitSelection.addItem(unit);
            }
        }
    }
}
