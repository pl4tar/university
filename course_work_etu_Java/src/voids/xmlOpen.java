package voids;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static voids.OpenData.logFile;


public class xmlOpen extends JFrame {

    static String fileNameOpen;

    public xmlOpen(String str, DefaultTableModel tableModel, JTable table) throws Exception {


        //if(tableModel.getRowCount()==0) return;
        FileDialog openXML = new FileDialog(this, str, FileDialog.LOAD);
        openXML.setFile("*.xml");// Установка начального каталога
        openXML.setVisible(true);
        //Определяем имя каталога или файла
        fileNameOpen = openXML.getDirectory() + openXML.getFile();
        if (fileNameOpen == null) return; // Пользователь нажал отмена


        tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);

        try {
            Document doc = getDocument();
            //showDocument(doc, textArea);
            doc.getDocumentElement().normalize();
            // Получение списка элементов book
            NodeList data = doc.getElementsByTagName("database");
            for (int temp = 0; temp < data.getLength(); temp++) {
                Node elem = data.item(temp);
                NamedNodeMap attrs = elem.getAttributes();
                String car_holder = attrs.getNamedItem("car_holder").getNodeValue();
                String car = attrs.getNamedItem("car").getNodeValue();
                String service_date = attrs.getNamedItem("service_date").getNodeValue();
                String pl = attrs.getNamedItem("plate").getNodeValue();
                tableModel.addRow(new String[]{car_holder, car, service_date, pl});
            }
            logFile.info("file open from:" + fileNameOpen);

            // Ошибка при чтение XML файла
        } catch (SAXException e) {
            e.printStackTrace();
            // Ошибка ввода - вывода
        } catch (IOException e) {
            e.printStackTrace();
            logFile.error("File Not Found", e);
        }
    }

    private static Document getDocument() throws Exception {
        try {
            // Получаем парсер, порождающий дерево объектов XML - документов
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// Новый экземпляр
            // Создает пустой документ
            DocumentBuilder builder = f.newDocumentBuilder();
            return builder.parse(new File(fileNameOpen));
        } catch (Exception exception) {
            throw new Exception("XML parsing error!");
        }
    }
}
