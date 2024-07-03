package voids;

import com.itextpdf.text.pdf.PdfAction;
import org.apache.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;


/**
 * @author Платто Артём 2306
 * @version 1.00
 * аргументов нет
 */
public class UI extends JFrame {
    /**
     * @author Платто Артём 2306
     */

    public static final Logger logGUI = Logger.getLogger(UI.class);

    //Объявляем массивы
    String[] elements;
    Object[] headers;
    Object[][] data;
    String[] sorting;

    //Объявляем контейнеры
    JMenu fileMenu;
    JMenuItem saveMenuIt, openMenuIt, newMenuIt, extMenuIt;
    JTable table;
    DefaultTableModel tableModel;

    JLabel search_label, sortingLabel;
    JComboBox сomboBox, sortingCombolBox;
    JToolBar toolBar;
    static JTextField search_field;
    JButton button_search;
    JButton button_save, button_open, button_add, button_edit, button_delete, button_profile;
    JTextArea textArea;
    Box box1, box2, box3;

    // Объявляем классы
    Mouse Ms = new Mouse();
    iHandler ihandler = new iHandler();
    Key key = new Key();
    final static public Object shared = new Object();

    /**
     * @author Платто Артём 2306
     */
    public UI() {
        super("База ГАИ");
        logGUI.info("Start app");
        super.setIconImage(new ImageIcon("images//police.png").getImage()); // установка логотипа
        //Размер экрана
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //инициализация массивов
        elements = new String[]{"фио владельца", "марка тс", "дата последнего то", "номер тс"};
        sorting = new String[]{"фио владельца", "марке тс", "дате последнего то", "номеру тс"};
        headers = new Object[]{"фио владельца", "марка тс", "дата последнего то", "гос. номер тс"};
        data = new Object[0][4];

        //инициализация контейнеров
        search_label = new JLabel("Поиск по:");
        сomboBox = new JComboBox(elements);
        toolBar = new JToolBar("инрументальная панель");
        search_field = new JTextField("Введите данные");
        button_search = new JButton("Поиск");
        tableModel = new DefaultTableModel(data, headers);
        table = new JTable(tableModel);
        sortingLabel = new JLabel("Сортировка по");
        sortingCombolBox = new JComboBox(sorting);


        fileMenu = new JMenu("Файл");
        openMenuIt = new JMenuItem("Открыть");
        saveMenuIt = new JMenuItem("Сохранить");
        newMenuIt = new JMenuItem("Новое");
        extMenuIt = new JMenuItem("Выйти");

        //Создаём кнопки и задаём им значки
        button_save = new JButton(new ImageIcon("images/29.png"));
        button_open = new JButton(new ImageIcon("images//46.png"));
        button_add = new JButton(new ImageIcon("images//20.png"));
        button_edit = new JButton(new ImageIcon("images//98.png"));
        button_delete = new JButton(new ImageIcon("images//17.png"));
        button_profile = new JButton(new ImageIcon("images//36.png"));

        //Настройка подсказок для кнопок
        button_save.setToolTipText("Сохранить список");
        button_open.setToolTipText("Открыть список");
        button_add.setToolTipText("Добавить информацию");
        button_edit.setToolTipText("Найти штраф");
        button_delete.setToolTipText("Удалить информацию о машине");
        button_profile.setToolTipText("Отчет о штрафах за заданный промежуток");

        //меняем размер кнопок
        button_save.setPreferredSize(new Dimension(25, 25));
        button_open.setPreferredSize(new Dimension(25, 25));
        button_add.setPreferredSize(new Dimension(25, 25));
        button_edit.setPreferredSize(new Dimension(25, 25));
        button_delete.setPreferredSize(new Dimension(25, 25));
        button_profile.setPreferredSize(new Dimension(25, 25));


        //Создаём меню
        Menu();

        //Создаём меню поиска сортировки
        Bar();

        //Все созданные и расположенные элементы выводим на окно
        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(textArea), BorderLayout.EAST);
        getContentPane().add(new JScrollPane(table));

        //Слушатель

        сomboBox.addActionListener(ihandler);
        extMenuIt.addActionListener(ihandler);
        button_open.addActionListener(ihandler);
        button_add.addActionListener(ihandler);
        button_save.addActionListener(ihandler);
        button_profile.addActionListener(ihandler);
        button_delete.addActionListener(ihandler);
        sortingCombolBox.addActionListener(ihandler);
        button_edit.addActionListener(ihandler);
        search_field.addMouseListener(Ms);
        search_field.addKeyListener(key);
        table.setAutoCreateRowSorter(true);
        logGUI.info("Finish app");
    }

    private void Menu() {
        //Создаём меню
        fileMenu.add(newMenuIt);
        fileMenu.add(openMenuIt);
        fileMenu.add(saveMenuIt);
        fileMenu.addSeparator();
        fileMenu.add(extMenuIt);

    }


    private void Bar() {
        //Вставляем кнопки в панель инструментов
        box1 = Box.createHorizontalBox();
        box1.add(button_save);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(button_open);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(button_profile);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(new JSeparator(SwingConstants.VERTICAL));
        box1.add(Box.createHorizontalStrut(3));
        box1.add(button_add);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(button_delete);
        box1.add(Box.createHorizontalStrut(3));
        box1.add(button_edit);

        //Создаём поисковую строку
        box2 = Box.createHorizontalBox();
        box2.add(search_label);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(сomboBox);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(search_field);

        //Создаём строку для сортировки
        box3 = Box.createHorizontalBox();
        box3.add(sortingLabel);
        box3.add(Box.createHorizontalStrut(6));
        box3.add(sortingCombolBox);

        //Объединяем все три строки
        toolBar.add(box1, BorderLayout.WEST);
        toolBar.add(Box.createHorizontalStrut(12));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(12));
        toolBar.add(box2);
        toolBar.add(Box.createHorizontalStrut(12));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(12));

        toolBar.add(box3, BorderLayout.EAST);

//        Лишаем возможность перемещать панель
        toolBar.setFloatable(false);
    }

    // Слушатель кнопки
    public class iHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == сomboBox) {
                search_field.setText("Введите данные");
            }
            if(e.getSource() == search_field){
                search_field.setText("");
            }
            if(e.getSource() == button_edit){
                String line1;
                line1 = JOptionPane.showInputDialog("Введите номер тс (нажмите ОК для просмотра всей базы нарушений)");
                SimpleWindowCreator windowCreator = new SimpleWindowCreator();
                windowCreator.createAndShowWindow(line1);

            }

            if (e.getSource() == button_delete) {
                new RemoveRow(tableModel);
            }

            if (e.getSource() == button_add) {
                new AddData("Добавить Строку", tableModel);
            }

            if (e.getSource() == button_profile) {
                try {
                    new pdfSave(tableModel);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(e.getSource() == sortingCombolBox){

                switch(sortingCombolBox.getSelectedIndex())
                {
                    case 0 : table.getRowSorter().toggleSortOrder(0); break;
                    case 1 : table.getRowSorter().toggleSortOrder(1); break;
                    case 2 : table.getRowSorter().toggleSortOrder(2); break;
                    case 3 : table.getRowSorter().toggleSortOrder(3); break;
                    default : return;
                }
            }
            if (e.getSource() == button_open) {

                String[] options_open = {"Открытие из txt", "Открытие из xml"};
                int choice_open = JOptionPane.showOptionDialog(null, "Выберите формат открытия", "открытие файла",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options_open, options_open[0]);

                if (choice_open == 0) {
                    // Логика сохранения в txt
                    try {
                        OpenData file = new OpenData("Открытие данных из txt", tableModel, 1);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (choice_open == 1) {
                    // Логика сохранения в xml

                    try {
                        xmlOpen file = new xmlOpen("Открытие данных из XML", tableModel, table);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new RuntimeException(ex);
                    }

                }
            }

            if (e.getSource() == button_save) {
                String[] options_save = {"Сохранить в txt", "Сохранить в xml"};
                int choice_save = JOptionPane.showOptionDialog(null, "Выберите формат сохранения", "Сохранение файла",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options_save, options_save[0]);

                if (choice_save == 0) {
                    // Логика сохранения в txt
                    DataSave file = new DataSave("Сохранение данных txt", tableModel);
                } else if (choice_save == 1) {
                    // Логика сохранения в xml
                    try {
                        xmlSave file = new xmlSave("Сохранение данных xml", tableModel);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    //Слушатель мыши
    public class Mouse implements MouseListener {

        public void mouseClicked(MouseEvent e) {

            // Если строка в строке search_field, есть первичная запись из elements, то она удаляется
            if ((search_field.getText().contains("Введите данные"))) {
                search_field.setText("");
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

    }

    public class Key implements KeyListener {


        public void keyPressed(KeyEvent e) {

            String str_Poisk = search_field.getText();

            if (Character.isLetter(e.getKeyChar()) || Character.isDigit(e.getKeyChar())) {
                str_Poisk = search_field.getText();
                str_Poisk += e.getKeyChar();
            } else {
                str_Poisk = search_field.getText();
            }

            int kolstr = table.getModel().getRowCount();
            table.clearSelection();

            for (int stroka = 0; stroka < kolstr; stroka++) {

                String str_jtab = table.getValueAt(stroka, сomboBox.getSelectedIndex()).toString();

                // ������� ������

                if (str_jtab.regionMatches(true, 0, str_Poisk, 0, str_Poisk.length())) {

                    table.setRowSelectionInterval(stroka, stroka); // �������� �������� ������
                    table.scrollRectToVisible(table.getCellRect(stroka, 0, true));
                    return;
                }
            }
        }
        public void keyReleased(KeyEvent e) { }

        public void keyTyped(KeyEvent e) { }

    }
}