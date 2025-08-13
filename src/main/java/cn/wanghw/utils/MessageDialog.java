package cn.wanghw.utils;

import javax.swing.*;
import java.awt.*;

public class MessageDialog extends JDialog {
    private String message;

    public MessageDialog(String message, String title) {
        this.setTitle(title);
        this.setModal(true);
        this.message = message;
        init();
    }

    public MessageDialog(JDialog dialog, String message, String title) {
        super(dialog, title, true);
        this.message = message;
        init();
    }

    public MessageDialog(Frame main, String message, String title) {
        super(main, title, true);
        this.message = message;
        init();
    }

    public void init() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        this.setResizable(true);
        this.setSize(600, 400);
        this.setLocation((d.width - this.getWidth()) / 2,
                (d.height - this.getHeight()) / 2);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        final JTextPane text = new JTextPane();
        text.setEditable(false);
        text.setContentType("text/html");
        text.setText(message);
        JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);
        this.getContentPane().add(panel);

        SwingUtilities.invokeLater(() -> {
            scroll.getVerticalScrollBar().setValue(0);
            text.setCaretPosition(0);
        });
        setVisible(true);
    }
}