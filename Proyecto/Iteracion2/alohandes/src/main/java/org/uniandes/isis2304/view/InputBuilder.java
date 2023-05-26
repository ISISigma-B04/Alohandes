package org.uniandes.isis2304.view;

import org.uniandes.isis2304.utils.OrderedMap;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.IntStream;

public final class InputBuilder {
    private final OrderedMap<String, Component> map = new OrderedMap<>();

    private InputBuilder() {}

    /** Supplier pattern */
    public static InputBuilder supplier() {return new InputBuilder();}

    /** Basic-level build of input-getter by builder */
    public InputBuilder append(String key, Component value) {
        map.put(key, value);
        return this;
    }

    /** Multiple-level build of input-getter by builder */
    public InputBuilder append(String[] keys, Component[] values) {
        assert keys.length == values.length;
        IntStream.range(0, keys.length).forEach(i -> InputBuilder.this.append(keys[i], values[i]));
        return this;
    }

    /** Get the panel result */
    public JPanel build() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        map.forEach((str, component) -> {
            panel.add(new JLabel(str), gbc);
            gbc.gridx = 1;
            panel.add(component, gbc);
            gbc.gridy++;
            gbc.gridx = 0;
        });
        return panel;
    }

    public void clear() {map.clear();}

    /** Getter of the input */
    public OrderedMap<String, String> getInput()
            throws Exception {
        OrderedMap<String, String> response = new OrderedMap<>();
        for (Map.Entry<String, Component> entry : map) {
            String k = entry.getKey();
            Component v = entry.getValue();
            String nV;
            if (v instanceof JTextField field) nV = field.getText();
            else if (v instanceof JComboBox<?> box) nV = (String) box.getSelectedItem();
            else nV = v.toString();

            if (nV == null || nV.isBlank()) throw new Exception(AlohandesInterface.CANCEL);
            response.put(k, nV);
        }
        return response;
    }
}

