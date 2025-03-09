import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

import net.miginfocom.swing.MigLayout;

public class EmployeeSummaryDialog extends JDialog {
    private static final String[] HEADER_NAMES = { 
        "ID", "PPS Number", "Surname", "First Name", "Gender", "Department", "Salary", "Full Time" 
    };
    private static final int[] COLUMN_WIDTHS = { 15, 100, 120, 120, 50, 120, 80, 80 };

    private JButton back;
    private JTable employeeTable;

    public EmployeeSummaryDialog(Vector<Object> allEmployees) {
        setTitle("Employee Summary");
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        JScrollPane scrollPane = new JScrollPane(createEmployeeTable(allEmployees));
        JPanel buttonPanel = createButtonPanel();

        // Layout
        JPanel summaryDialog = new JPanel(new MigLayout());
        summaryDialog.add(buttonPanel, "growx, pushx, wrap");
        summaryDialog.add(scrollPane, "growx, pushx, wrap");
        scrollPane.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        setContentPane(summaryDialog);
        setSize(850, 500);
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    // Creates the employee table
    private JScrollPane createEmployeeTable(Vector<Object> allEmployees) {
        DefaultTableModel tableModel = new DefaultTableModel(HEADER_NAMES, 0) {
            public Class<?> getColumnClass(int column) {
                return switch (column) {
                    case 0 -> Integer.class;
                    case 4 -> Character.class;
                    case 6 -> Double.class;
                    case 7 -> Boolean.class;
                    default -> String.class;
                };
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setEnabled(false);
        employeeTable.setAutoCreateRowSorter(true);
        employeeTable.setPreferredScrollableViewportSize(new Dimension(800, 15 * allEmployees.size() + 15));

        setupTableColumns(employeeTable);
        fillTableWithData(tableModel, allEmployees);

        return new JScrollPane(employeeTable);
    }

    // Configures table column widths & alignment
    private void setupTableColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setMinWidth(COLUMN_WIDTHS[i]);
            if (i == 0) column.setCellRenderer(leftRenderer);
            if (i == 4) column.setCellRenderer(centerRenderer);
            if (i == 6) column.setCellRenderer(new TableUtils.DecimalFormatRenderer());
        }
    }

    // Fills the table with employee data
    private void fillTableWithData(DefaultTableModel model, Vector<Object> employees) {
        for (Object empData : employees) {
            model.addRow((Vector<?>) empData);
        }
    }

    // Creates the button panel
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        back = new JButton("Back");
        back.setToolTipText("Return to main screen");
        back.addActionListener(e -> dispose());
        panel.add(back);
        return panel;
    }
}
