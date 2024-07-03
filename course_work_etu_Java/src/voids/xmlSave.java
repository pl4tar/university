package voids;

import java.awt.FileDialog;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import static voids.OpenData.logFile;


public class xmlSave extends JFrame {

    public xmlSave(String str, DefaultTableModel tableModel) throws Exception {

        FileDialog savaXML = new FileDialog(this, str, FileDialog.SAVE);
        savaXML.setFile("*.xml");// Установка начального каталога
        savaXML.setVisible(true);
        //Определяем имя каталога или файла
        String fileNameSave = savaXML.getDirectory() + savaXML.getFile();
        if (fileNameSave == null) return; // Пользователь нажал отмена

        Document doc = getDocument();
        // Создаём корневой элемент booklist и добавляем его в документ
        Node booklist = doc.createElement("police");// создать элемент
        doc.appendChild(booklist);// добавляем ребёнка
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Element book = doc.createElement("database");
            booklist.appendChild(book);
            book.setAttribute("car_holder", (String) tableModel.getValueAt(i, 0));
            book.setAttribute("car", (String) tableModel.getValueAt(i, 1));
            book.setAttribute("service_date", (String) tableModel.getValueAt(i, 2));
            book.setAttribute("plate", (String) tableModel.getValueAt(i, 3));
        }
        try {
            // Создание преобразование документа
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");// хз
            trans.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileNameSave)));
            logFile.info("file save to:" + fileNameSave);
            logFile.debug("File save successfully");
            // Ошибка создания XML преобразователя
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            // Ошибка работы XML преобразователя
        } catch (TransformerException e) {
            e.printStackTrace();
            // Ошибка ввода - вывода
        } catch (IOException e) {
            e.printStackTrace();
            logFile.error("File Not Found", e);
        }
        if (savaXML.getFile() != null) {
            try {

                String comand = "cmd.exe /c start explorer.exe /select," + savaXML.getDirectory() + savaXML.getFile();
                Runtime.getRuntime().exec(comand);

            } catch (Exception ex) {
                System.out.println("Error - " + ex);
            }
        }
    }

    private static Document getDocument() throws Exception {
        try {
            // Получаем парсер, порождающий дерево объектов XML - документов
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// Новый экземпляр
            // Создает пустой документ
            DocumentBuilder builder = f.newDocumentBuilder();
            // Разбирает ( получает ) данные по пути
            //return builder.parse(new File("test.xml"));
            return builder.newDocument();

        } catch (Exception exception) {
            throw new Exception("XML parsing error!");
        }
    }
}
