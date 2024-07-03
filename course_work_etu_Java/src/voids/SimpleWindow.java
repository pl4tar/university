package voids;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class SimpleWindowCreator {
    public void createAndShowWindow(String CarNumber) {
        // Весь код для создания окна, как описано в предыдущем ответе
        JFrame frame = new JFrame("Штрафы");
        // Создаем DefaultTableModel для таблицы
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Номер тс");
        model.addColumn("Нарушение");
        model.addColumn("Дата нарушения");

        // Добавляем данные в таблицу (просто для примера)
        readDataFromFile(model, "fine_base.txt", CarNumber);
        // Создаем JTable и устанавливаем ему модель
        JTable table = new JTable(model);

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Добавляем таблицу на панель
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Создаем кнопку закрытия окна
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Закрываем окно при нажатии кнопки
                frame.dispose();
            }
        });

        // Добавляем кнопку внизу окна
        panel.add(closeButton, BorderLayout.SOUTH);

        // Добавляем панель на окно
        frame.add(panel);

        // Устанавливаем размеры окна
        frame.setSize(500, 500);


        // Устанавливаем операцию по закрытию окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        // Делаем окно видимым
        frame.setVisible(true);
    }
    private static void readDataFromFile(DefaultTableModel model, String filePath, String CarNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Предполагаем, что данные разделены символом табуляции
                String[] data = line.split("\\;");
                if (line.contains(CarNumber)) model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка чтения файла: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}