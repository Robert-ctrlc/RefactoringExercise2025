public class SearchBySurnameDialog extends SearchDialog {
    public SearchBySurnameDialog(EmployeeDetails parent) {
        super(parent, "Search by Surname");
    }

    @Override
    protected void performSearch() {
	String surname = searchField.getText().trim();
        parent.searchEmployeeBySurname(searchField.getText().trim());
        dispose();
    }
}
