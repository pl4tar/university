package voids;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static voids.OpenData.logFile;

public class DataSave extends JFrame {
    public DataSave(String str, DefaultTableModel table) {
        FileDialog sava = new FileDialog(this, str, FileDialog.SAVE);
        sava.setFile("*.txt");// Установка начального каталога
        sava.setVisible(true);
        //Определяем имя каталога или файла
        String fileNameSave = sava.getDirectory() + sava.getFile();
        if (sava.getFile() == null) return; // Пользователь нажал отмена
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameSave));
            for (int i = 0; i < table.getRowCount(); i++) {
                writer.write("\r\n");
                for (int j = 0; j < table.getColumnCount(); j++) {
                    writer.write((String) table.getValueAt(i, j));
                    if (j < 4) writer.write(";");
                }
            }
            writer.close();
            logFile.info("file save to:" + fileNameSave);
            logFile.debug("File save successfully");
        } catch (IOException e) {
            e.printStackTrace();
            logFile.error("File Not Found", e);
        }
        if (sava.getFile() != null) {
            try {

                String comand = "cmd.exe /c start explorer.exe /select," + sava.getDirectory() + sava.getFile();
                Runtime.getRuntime().exec(comand);

            } catch (Exception ex) {
                System.out.println("Error - " + ex);
            }
        }
    }
}