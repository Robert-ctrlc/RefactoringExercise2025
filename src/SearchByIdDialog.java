import javax.swing.JOptionPane;

public class SearchByIdDialog extends SearchDialog {
    public SearchByIdDialog(EmployeeDetails parent) {
        super(parent, "Search by ID");
    }

    @Override
    protected void performSearch() {
        try {
            int id = Integer.parseInt(searchField.getText().trim());
            parent.searchEmployeeById(Integer.parseInt(searchField.getText().trim()));
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
