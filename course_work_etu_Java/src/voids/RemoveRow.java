package voids;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class RemoveRow extends JFrame {
    public RemoveRow(DefaultTableModel table) {
        String searchString = JOptionPane.showInputDialog("Введите номер ТС, которую нужно удалить");

        for (int row = 0; row < table.getRowCount(); row++) {
            Object valueAtColumn4 = table.getValueAt(row, 3); // 4 столбец (индекс 3)

            if (valueAtColumn4 != null && valueAtColumn4.toString().equals(searchString)) {
                table.removeRow(row);
                JOptionPane.showMessageDialog(this, "Строка успешно удалена");
                return; // Выход из цикла после удаления строки
            }
        }

        JOptionPane.showMessageDialog(this, "Строка не найдена");
    }
}