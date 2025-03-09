import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class TableUtils {
    public static class DecimalFormatRenderer extends DefaultTableCellRenderer {
        private static final DecimalFormat format = new DecimalFormat("\u20ac ###,###,##0.00");

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            ((JLabel) c).setHorizontalAlignment(JLabel.RIGHT);
            if (value instanceof Number) {
                setText(format.format(value));
            }
            return c;
        }
    }
}
