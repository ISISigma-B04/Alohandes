/**
 * @version 1.1
 * @author Pedro Lobato
 */
package org.uniandes.isis2304.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/** Interface class to simulate a console output with user executions results */
public final class DataPanel extends JPanel {
    private static final TitledBorder title = new TitledBorder("Console for Alohandes");
    private final JTextArea console = new JTextArea("Resultado de las operaciones solicitadas");
    private Color status = Color.BLACK;

    public DataPanel() {
        setBorder(border());
        setLayout(new BorderLayout());
        console.setEditable(false);
        console.setLineWrap(true);
        console.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(new JScrollPane(console), BorderLayout.CENTER);
    }

    private javax.swing.border.Border border() {
        var inner = BorderFactory.createLineBorder(status, 5);
        return BorderFactory.createCompoundBorder(title, inner);
    }

    public void print(String str, Boolean b) {
        Color prev = status;
        if (b == null) status = Color.BLACK;
        else if (b) status = Color.GREEN;
        else status = Color.RED;
        setBorder(border());

        if (prev.equals(status)) {
            console.append("\n--------------------------------------------\n"+str);
            console.setCaretPosition(console.getDocument().getLength());
        } else {
            console.setText(str);
        }
    }
}