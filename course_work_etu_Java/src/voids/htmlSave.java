package voids;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.table.DefaultTableModel;


public class htmlSave {

    public htmlSave(DefaultTableModel tableModel) {

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("htmldatalibrary.html"));        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.println("<TABLE BORDER><TR><TH>фио владельца<TH>марка тс<TH>последнее то<TH>гос. номер тс</TR>");
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int square = i * i;
            pw.println("<TR><TD>" + (String) tableModel.getValueAt(i, 0)
                    + "<TD>" + (String) tableModel.getValueAt(i, 1)
                    + "<TD>" + (String) tableModel.getValueAt(i, 2)
                    + "<TD>" + (String) tableModel.getValueAt(i, 3));
        }
        pw.println("</TABLE>");
        pw.close();
    }
}

