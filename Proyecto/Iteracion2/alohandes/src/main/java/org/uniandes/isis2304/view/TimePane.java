package org.uniandes.isis2304.view;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.stream.IntStream;

final class TimePicker extends JPanel {
    private final JComboBox<String> hour = new JComboBox<>(
            IntStream.range(0, 24).mapToObj(i -> String.format("%02d", i)).toArray(String[]::new));
    private final JComboBox<String> minute = new JComboBox<>(
            IntStream.range(0, 4).map(i -> i * 15).mapToObj(i -> String.format("%02d", i)).toArray(String[]::new));

    {
        setLayout(new GridLayout(2, 2, 4, 2));
        add(new JLabel("HH:"));
        add(hour);
        add(new JLabel("mm:"));
        add(minute);
    }

    @Override public String toString() {return hour.getSelectedItem() + ":" + minute.getSelectedItem();}
}

final class DatePicker extends JPanel {
    private static final String PATTERN = "yyyy-MM-dd";
    private final JDateChooser date; //Let's not re-create the wheel

    {
        JTextFieldDateEditor field = new JTextFieldDateEditor(true, PATTERN, "####-##-##", '_');
        date = new JDateChooser(null, PATTERN, field);
        date.setPreferredSize(new Dimension(100, 20));

        add(new JLabel(PATTERN));
        add(date);
    }

    @Override public String toString() {return new SimpleDateFormat(PATTERN).format(date.getDate());}
}
