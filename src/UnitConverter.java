import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

public class UnitConverter extends JFrame implements ActionListener {
    JPanel panel;
    JButton convertButton;
    JTextField leftField, rightField;
    JLabel errorMessage;
    JComboBox conversionTypeSelection;
    JComboBox leftUnitSelection, rightUnitSelection;

    GridBagConstraints c;

    static final Font TIMES_NEW_ROMAN = new Font("Times New Roman", Font.PLAIN, 30);
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

    HashMap<String, Double> unitConversionMapping = new HashMap<>();

    public UnitConverter() {
        super("Unit Converter");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        fillUnitConversionMappings();

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
            String inputString = leftField.getText();

            if (!(inputString.matches("^\\d*\\.\\d+|\\d+\\.\\d*$") || inputString.matches("^\\d+$"))) {
                errorMessage.setText("Enter a valid number!");
            } else {
                String leftUnit = (String) leftUnitSelection.getSelectedItem();
                String rightUnit = (String) rightUnitSelection.getSelectedItem();

                double input = Double.parseDouble(inputString);

                double output = 0;

                if(conversionTypeSelection.getSelectedItem().equals("Temperature")) {
                    //"Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)"
                    if(leftUnit.equals("Kelvin (K)")) {
                        if(rightUnit.equals("Fahrenheit (°F)")) {
                            output = (input - 273.15) * 9 / 5 + 32;
                        } else if(rightUnit.equals("Celsius (°C)")) {
                            output = input - 273.15;
                        } else {
                            output = input;
                        }
                    } else if(leftUnit.equals("Celsius (°C)")) {
                        if(rightUnit.equals("Fahrenheit (°F)")) {
                            output = input * 9 / 5 + 32;
                        } else if(rightUnit.equals("Kelvin (K)")) {
                            output = input + 273.15;
                        } else {
                            output = input;
                        }
                    } else {
                        if(rightUnit.equals("Celsius (°C)")) {
                            output = (input - 32) * 5 / 9;
                        } else if(rightUnit.equals("Kelvin (K)")) {
                            output = (input - 32) * 5 / 9 + 273.15;
                        } else {
                            output = input;
                        }
                    }
                } else {
                    output = input * unitConversionMapping.get(leftUnit) / unitConversionMapping.get(rightUnit);
                }

                BigDecimal bd = new BigDecimal(output);
                bd = bd.round(new MathContext(4));
                double rounded = bd.doubleValue();

                rightField.setText(String.valueOf(rounded));
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




    private void fillUnitConversionMappings() {
        unitConversionMapping.put("Millimeters (mm)", 0.001);
        unitConversionMapping.put("Centimeters (cm)", 0.01);
        unitConversionMapping.put("Meters (m)", 1.);
        unitConversionMapping.put("Kilometers (km)", 1000.);
        unitConversionMapping.put("Inches (in)", 0.0254);
        unitConversionMapping.put("Feet (ft)", 0.3048);
        unitConversionMapping.put("Yards (yd)", 0.9144);
        unitConversionMapping.put("Miles (mi)", 1609.34);
        unitConversionMapping.put("Milligrams (mg)", 0.000001);
        unitConversionMapping.put("Grams (g)", 0.001);
        unitConversionMapping.put("Kilograms (kg)", 1.);
        unitConversionMapping.put("Metric tons (t)", 1000.);
        unitConversionMapping.put("Ounces (oz)", 0.0283495);
        unitConversionMapping.put("Pounds (lb)", 0.453592);
        unitConversionMapping.put("Stones (st)", 6.35029);
        unitConversionMapping.put("Milliliters (ml)", 0.000001);
        unitConversionMapping.put("Liters (l)", 0.001);
        unitConversionMapping.put("Cubic centimeters (cc)", 0.000001);
        unitConversionMapping.put("Cubic meters (m³)", 1.);
        unitConversionMapping.put("Teaspoons (tsp)", 0.000005);
        unitConversionMapping.put("Tablespoons (tbsp)", 0.000015);
        unitConversionMapping.put("Fluid ounces (fl oz)", 0.0000295735);
        unitConversionMapping.put("Cups", 0.00024);
        unitConversionMapping.put("Pints (pt)", 0.000473176);
        unitConversionMapping.put("Quarts (qt)", 0.000946353);
        unitConversionMapping.put("Gallons (gal)", 0.00378541);

        /*
        unitConversionMapping.put("Celsius (°C)", K - 273.15);
        unitConversionMapping.put("Fahrenheit (°F)", (K - 273.15) × 9/5 + 32);
        unitConversionMapping.put("Kelvin (K)", 1.);
        */

        unitConversionMapping.put("Square millimeters (mm²)", 0.000001);
        unitConversionMapping.put("Square centimeters (cm²)", 0.0001);
        unitConversionMapping.put("Square meters (m²)", 1.);
        unitConversionMapping.put("Hectares (ha)", 10000.);
        unitConversionMapping.put("Square kilometers (km²)", 1000000.);
        unitConversionMapping.put("Square inches (in²)", 0.00064516);
        unitConversionMapping.put("Square feet (ft²)", 0.092903);
        unitConversionMapping.put("Square yards (yd²)", 0.836127);
        unitConversionMapping.put("Acres", 4046.86);
        unitConversionMapping.put("Square miles (mi²)", 2589988.);
        unitConversionMapping.put("Meters per second (m/s)", 1.);
        unitConversionMapping.put("Kilometers per hour (km/h)", 0.277778);
        unitConversionMapping.put("Miles per hour (mph)", 0.44704);
        unitConversionMapping.put("Knots", 0.514444);
        unitConversionMapping.put("Seconds (s)", 1.);
        unitConversionMapping.put("Minutes (min)", 60.);
        unitConversionMapping.put("Hours (h)", 3600.);
        unitConversionMapping.put("Days", 86400.);
        unitConversionMapping.put("Weeks", 604800.);
        unitConversionMapping.put("Months", 2629746.);
        unitConversionMapping.put("Years", 31556952.);
        unitConversionMapping.put("Joules (J)", 1.);
        unitConversionMapping.put("Kilojoules (kJ)", 1000.);
        unitConversionMapping.put("Calories (cal)", 4.184);
        unitConversionMapping.put("Kilocalories (kcal)", 4184.);
        unitConversionMapping.put("Watt-hours (Wh)", 3600.);
        unitConversionMapping.put("Kilowatt-hours (kWh)", 3600000.);
        unitConversionMapping.put("Pascals (Pa)", 1.);
        unitConversionMapping.put("Kilopascals (kPa)", 1000.);
        unitConversionMapping.put("Bars", 100000.);
        unitConversionMapping.put("Atmospheres (atm)", 101325.);
        unitConversionMapping.put("Pounds per square inch (psi)", 6894.76);
        unitConversionMapping.put("Watts (W)", 1.);
        unitConversionMapping.put("Kilowatts (kW)", 1000.);
        unitConversionMapping.put("Horsepower (hp)", 745.7);
    }
}