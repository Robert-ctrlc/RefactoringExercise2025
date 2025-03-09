import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class AddRecordDialog extends JDialog {
    private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
    private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    private JButton save, cancel;
    private EmployeeDetails parent;

    public AddRecordDialog(EmployeeDetails parent) {
        setTitle("Add Record");
        setModal(true);
        this.parent = parent;
        this.parent.setEnabled(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize UI
        JScrollPane scrollPane = new JScrollPane(createDialogPane());
        setContentPane(scrollPane);

        getRootPane().setDefaultButton(save);
        setSize(500, 370);
        setLocationRelativeTo(null); // Centers the dialog
        setVisible(true);
    }

    // Creates the main dialog panel
    private JPanel createDialogPane() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        // Add input fields
        panel.add(new JLabel("ID:"), "growx, pushx");
        panel.add(idField = createDisabledTextField(), "growx, pushx, wrap");

        panel.add(new JLabel("PPS Number:"), "growx, pushx");
        panel.add(ppsField = new JTextField(20), "growx, pushx, wrap");

        panel.add(new JLabel("Surname:"), "growx, pushx");
        panel.add(surnameField = new JTextField(20), "growx, pushx, wrap");

        panel.add(new JLabel("First Name:"), "growx, pushx");
        panel.add(firstNameField = new JTextField(20), "growx, pushx, wrap");

        panel.add(new JLabel("Gender:"), "growx, pushx");
        panel.add(genderCombo = new JComboBox<>(this.parent.gender), "growx, pushx, wrap");

        panel.add(new JLabel("Department:"), "growx, pushx");
        panel.add(departmentCombo = new JComboBox<>(this.parent.department), "growx, pushx, wrap");

        panel.add(new JLabel("Salary:"), "growx, pushx");
        panel.add(salaryField = new JTextField(20), "growx, pushx, wrap");

        panel.add(new JLabel("Full Time:"), "growx, pushx");
        panel.add(fullTimeCombo = new JComboBox<>(this.parent.fullTime), "growx, pushx, wrap");

        // Add buttons
        panel.add(createButtonPanel(), "span 2, growx, pushx, wrap");

        idField.setText(Integer.toString(this.parent.getNextFreeId()));

        return panel;
    }

    // Creates a disabled text field
    private JTextField createDisabledTextField() {
        JTextField field = new JTextField(20);
        field.setEditable(false);
        return field;
    }

    // Creates the button panel
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        save = new JButton("Save");
        save.addActionListener(this::handleSave);
        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> dispose());
        panel.add(save);
        panel.add(cancel);
        return panel;
    }

    // Handles saving a new employee record
    private void handleSave(ActionEvent e) {
        if (parent.checkInput()) {
            addRecord();
            dispose();
            this.parent.changesMade = true;
        } else {
            JOptionPane.showMessageDialog(null, "Wrong values or format! Please check!");
            resetFieldColors();
        }
    }

    // Adds a new employee record
    private void addRecord() {
        boolean fullTime = fullTimeCombo.getSelectedItem().equals("Yes");
        Employee newEmployee = new Employee(
            Integer.parseInt(idField.getText()),
            ppsField.getText().toUpperCase(),
            surnameField.getText().toUpperCase(),
            firstNameField.getText().toUpperCase(),
            genderCombo.getSelectedItem().toString().charAt(0),
            departmentCombo.getSelectedItem().toString(),
            Double.parseDouble(salaryField.getText()),
            fullTime
        );

        this.parent.currentEmployee = newEmployee;
        this.parent.addRecord(newEmployee);
        this.parent.displayRecords(newEmployee);
    }

    // Resets field colors after validation failure
    private void resetFieldColors() {
        resetComponentColor(ppsField, surnameField, firstNameField, salaryField);
        resetComponentColor(genderCombo, departmentCombo, fullTimeCombo);
    }

    // Helper method to reset colors
    private void resetComponentColor(JComponent... components) {
        for (JComponent component : components) {
            component.setBackground(UIManager.getColor("TextField.background"));
        }
    }
}
