package voids;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AddData extends JFrame {
    public AddData(String str, DefaultTableModel table) {
        String line1;
        line1 = JOptionPane.showInputDialog(str);
        if (line1 != null) {
            String[] str1 = line1.split("\\;");
            if (str1.length == 4)
                table.addRow(new String[]{str1[0], str1[1], str1[2], str1[3]});
        }
    }
}
