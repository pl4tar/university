package voids;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import static voids.OpenData.logFile;


public class pdfSave {

    public pdfSave(DefaultTableModel tableModel) throws ParseException {
        logFile.info("user create report");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        DefaultTableModel model = new DefaultTableModel();
        Object[][] data1 = new Object[0][3];
        Object[] headers1 = new Object[]{"Номер ТС","Название штрафа", "дата нарушения"};
        DefaultTableModel table = new DefaultTableModel(data1, headers1);
        readDataFromFile(table);
        String dataDedline1 = JOptionPane.showInputDialog("Введите от какой даты составить отчет");
        String dataDedline2 = JOptionPane.showInputDialog("Введите до какой даты составить отчет");
        Date startDate = dateFormat.parse(dataDedline1);
        Date endDate = dateFormat.parse(dataDedline2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        PdfPTable t = new PdfPTable(3);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PdfDataLibraryf.pdf"));
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }

        BaseFont bfComic = null;
        try {
            bfComic = BaseFont.createFont("/Windows/Fonts/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e1) {
            e1.printStackTrace();
        }

        Font font1 = new Font(bfComic, 12);

        t.addCell(new PdfPCell(new Phrase("Название штрафа",font1)));
        t.addCell(new PdfPCell(new Phrase("дата нарушения",font1)));
        t.addCell(new PdfPCell(new Phrase("Номер ТС",font1)));


        for(int i = 0; i < table.getRowCount(); i++){
            Date date1 = dateFormat.parse((String) table.getValueAt(i, 2));
            if ((!date1.after(endDate)) && (!date1.before(startDate))) {
                t.addCell(new Phrase((String) table.getValueAt(i, 0), font1));
                t.addCell(new Phrase((String) table.getValueAt(i, 1), font1));
                t.addCell(new Phrase((String) table.getValueAt(i, 2), font1));
            }
        }

        document.open();

        try {
            document.add(t);
            logFile.debug("report done successfully");
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();
    }
    private static void readDataFromFile(DefaultTableModel table) {
        try (BufferedReader reader = new BufferedReader(new FileReader("fine_base.txt"))) {
            String line;
            int rows = table.getRowCount();
            for (int i = 0; i < rows; i++)
                table.removeRow(0);
            while ((line = reader.readLine()) != null) {
                // Предполагаем, что данные разделены символом табуляции
                String[] data = line.split("\\;");
                table.addRow(new String[]{data[0], data[1], data[2]});
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
