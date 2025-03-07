import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SearchDialog extends JDialog implements ActionListener {
    protected JTextField searchField;
    protected JButton searchButton, cancelButton;
    protected EmployeeDetails parent;

    public SearchDialog(EmployeeDetails parent, String title) {
        setTitle(title);
        setModal(true);
        this.parent = parent;
        this.parent.setEnabled(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setupUI();

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        cancelButton = new JButton("Cancel");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter search query:"));
        inputPanel.add(searchField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        searchButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            performSearch();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    protected abstract void performSearch(); 
}
