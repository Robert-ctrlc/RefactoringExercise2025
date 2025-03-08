import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileManager {
    private File file;
    private RandomFile application = new RandomFile();

    public FileManager(File file) {
        this.file = file;
    }

    public void openFile(EmployeeDetails parentFrame) {
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open");
        fc.setFileFilter(new FileNameExtensionFilter("dat files (*.dat)", "dat"));

        int returnVal = fc.showOpenDialog(parentFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File newFile = fc.getSelectedFile();
            file = newFile;
            application.openReadFile(file.getAbsolutePath());
            parentFrame.loadFirstRecord();
            application.closeReadFile();
        }
    }

    public void saveFile(EmployeeDetails parentFrame, boolean changesMade) {
        if (file.getName().equals(parentFrame.getGeneratedFileName()))
            saveFileAs(parentFrame);
        else {
            if (changesMade) {
                int returnVal = JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Save",
                        JOptionPane.YES_NO_OPTION);
                if (returnVal == JOptionPane.YES_OPTION) {
                    application.openWriteFile(file.getAbsolutePath());
                    application.changeRecords(parentFrame.getCurrentEmployee(), parentFrame.getCurrentByteStart());
                    application.closeWriteFile();
                }
            }
        }
    }

    public void saveFileAs(EmployeeDetails parentFrame) {
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save As");
        fc.setFileFilter(new FileNameExtensionFilter("dat files (*.dat)", "dat"));

        int returnVal = fc.showSaveDialog(parentFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File newFile = fc.getSelectedFile();
            newFile = new File(newFile.getAbsolutePath() + ".dat");
            try {
                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                file = newFile;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file.");
            }
        }
    }

    public File getFile() {
        return file;
    }
}
