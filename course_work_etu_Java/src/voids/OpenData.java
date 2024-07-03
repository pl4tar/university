package voids;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenData extends JFrame {
    public static final Logger logFile = Logger.getLogger(OpenData.class);

        public OpenData(String str, DefaultTableModel table, int mode) {
            if(mode == 1) {
                try {
                    FileDialog open = new FileDialog(this, str, FileDialog.LOAD);
                    open.setFile("*.txt");// Установка начального каталога
                    open.setVisible(true);
                    //Определяем имя каталога или файла
                    String fileNameOpen = open.getDirectory() + open.getFile();
                    if (open.getFile() == null) return;
                    // Считываем строку и заносим в буфер
                    BufferedReader reader = new BufferedReader(new FileReader(fileNameOpen));
                    int rows = table.getRowCount();
                    for (int i = 0; i < rows; i++)
                        table.removeRow(0);
                    String line;
//                    int pos_table = 0;
                    do {
                        line = reader.readLine();
                        if (line != null) {
//                            pos_table+=1;
//                            String str_pos = String.valueOf(pos_table);
                            String[] str1 = line.split("\\;");
                            if (str1.length == 4)
                                table.addRow(new String[]{str1[0], str1[1], str1[2], str1[3]});
                        }
                    } while (line != null);
                    reader.close();
                    logFile.info("file open from:" + fileNameOpen);
                    logFile.debug("File open successfully");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    logFile.error("File Not Found", e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(mode == 2){
                try {
                    FileDialog open = new FileDialog(this, str, FileDialog.LOAD);
//                    open.setFile("*.txt");// Установка начального каталога
//                    open.setVisible(true);
                    //Определяем имя каталога или файла
                    String fileNameOpen = "C:\\Users\\tat-p\\IdeaProjects\\lab9\\fine_base.txt";
                    if (open.getFile() == null) return;
                    // Считываем строку и заносим в буфер
                    BufferedReader reader = new BufferedReader(new FileReader(fileNameOpen));
                    int rows = table.getRowCount();
                    for (int i = 0; i < rows; i++)
                        table.removeRow(0);
                    String line;
                    do {
                        line = reader.readLine();
                        if (line != null) {
                            String[] str1 = line.split("\\;");
                            if (str1.length == 2)
                                table.addRow(new String[]{str1[0], str1[1]});
                        }
                    } while (line != null);
                    reader.close();
                    logFile.debug("no errors");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    logFile.error("File Not Found", e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}