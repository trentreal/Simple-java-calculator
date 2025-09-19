import javax.swing.*;
import java.awt.*;

public class CalcolatriceGui {
    public static void main(String[] args) {
        CalcolatriceLogica logica = new CalcolatriceLogica();

        JFrame frame = new JFrame("Calcolatrice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);
        frame.setLayout(new BorderLayout());

        JTextField display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        frame.add(display, BorderLayout.NORTH);

        JPanel tastiPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        JButton[][] tasto = new JButton[5][4];

        String[][] labels = {
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"0", ".", "=", "+"},
                {"C", "C", "←", "←"}
        };

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                String label = labels[i][j];
                if (!label.equals("")) {
                    tasto[i][j] = new JButton(label);
                    tasto[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                    tastiPanel.add(tasto[i][j]);

                    tasto[i][j].addActionListener(e -> {
                        String text = ((JButton) e.getSource()).getText();
                        if (text.equals("C")) {
                            logica.clear();
                        } else if (text.equals("←")) {
                            logica.backspace();
                        } else if (text.equals("=")) {
                            logica.calcola();
                        } else {
                            if ("+-*/".contains(text)) {
                                logica.aggiungiOperatore(text);
                            } else {
                                logica.aggiungiNumero(text);
                            }
                        }
                        display.setText(logica.getDisplay());
                    });
                } else {
                    tastiPanel.add(new JLabel(""));
                }
            }
        }

        frame.add(tastiPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
