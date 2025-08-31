import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AppAuto {
    public static void main(String[] args) {
        Auto a1 = new Auto();
        System.out.println("== Auto 1 (Start) ==");
        printStatus(a1);

        a1.tanken(30.0);      // 30 Liter tanken
        a1.fahren(120.0);     // 120 km fahren
        System.out.println("== Auto 1 (nach Tanken+Fahren) ==");
        printStatus(a1);

        // Auto 2: mit Startwerten
        Auto a2 = new Auto("B-AB 1234", 50000.0, 55.0, 6.0, 8.0);
        System.out.println("== Auto 2 (Start) ==");
        printStatus(a2);

        a2.fahren(300.0); // versucht 300 km zu fahren -> fährt bis Tank leer
        System.out.println("== Auto 2 (nach Fahren) ==");
        printStatus(a2);

        a2.tanken(40.0); // nachtanken (bis max. 55 L)
        System.out.println("== Auto 2 (nach Tanken) ==");
        printStatus(a2);
    }

    private static void printStatus(Auto a) {
        System.out.printf(
            "Kennzeichen: %s | Kilometerstand: %.1f km | Tankvolumen: %.1f L | Kraftstoff: %.1f L%n",
            a.getKfzKennzeichen(), a.getKilometerstand(), a.getTankvolumen(), a.getKraftstoffmenge()
        );
    }
}

class Auto {
    private String kfzKennzeichen;
    private double kilometerstand;
    private double tankvolumen;
    private double kraftstoffverbrauch;
    private double kraftstoffmenge;

    public Auto() {
        this.kfzKennzeichen = "";
        this.kilometerstand = 0.0;
        this.tankvolumen = 50.0;
        this.kraftstoffverbrauch = 6.5;
        this.kraftstoffmenge = 0.0;
    }

    public Auto(String kfzKennzeichen,
                double kilometerstand,
                double tankvolumen,
                double kraftstoffverbrauch,
                double kraftstoffmenge) {

        this.kfzKennzeichen = (kfzKennzeichen == null) ? "" : kfzKennzeichen.trim();
        this.kilometerstand = Math.max(0.0, kilometerstand);
        this.tankvolumen = (tankvolumen > 0.0) ? tankvolumen : 50.0;
        this.kraftstoffverbrauch = (kraftstoffverbrauch > 0.0) ? kraftstoffverbrauch : 6.5;

        if (kraftstoffmenge < 0.0) kraftstoffmenge = 0.0;
        if (kraftstoffmenge > this.tankvolumen) kraftstoffmenge = this.tankvolumen;
        this.kraftstoffmenge = kraftstoffmenge;
    }

    public void tanken(double menge) {
        if (menge <= 0.0) return;
        double freierPlatz = tankvolumen - kraftstoffmenge;
        double tatsaechlich = Math.min(menge, freierPlatz);
        kraftstoffmenge += tatsaechlich;
    }

    public void fahren(double strecke) {
        if (strecke <= 0.0) return;
        double literProKm = kraftstoffverbrauch / 100.0;
        double benoetigt = strecke * literProKm;

        if (benoetigt <= kraftstoffmenge) {
            kraftstoffmenge -= benoetigt;
            kilometerstand += strecke;
        } else if (literProKm > 0.0) {
            double moeglicheKm = kraftstoffmenge / literProKm;
            kilometerstand += moeglicheKm;
            kraftstoffmenge = 0.0;
        }
    }

    public String getKfzKennzeichen() { return kfzKennzeichen; }
    public double getKilometerstand() { return kilometerstand; }
    public double getTankvolumen() { return tankvolumen; }
    public double getKraftstoffmenge() { return kraftstoffmenge; }
}

class AutoGUI extends JFrame {
    private Auto auto;
    private JTextField tfTanken;
    private JTextField tfFahren;
    private JTextArea taStatus;

    public AutoGUI() {
        super("Auto GUI (Zusatz)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        auto = new Auto("B-XY 999", 10000, 50, 6.0, 10.0);

        JPanel pInput = new JPanel(new FlowLayout());
        tfTanken = new JTextField("10.0", 5);
        JButton btTanken = new JButton("Tanken");
        btTanken.addActionListener(this::onTanken);

        tfFahren = new JTextField("100.0", 5);
        JButton btFahren = new JButton("Fahren");
        btFahren.addActionListener(this::onFahren);

        pInput.add(new JLabel("Menge (L):"));
        pInput.add(tfTanken);
        pInput.add(btTanken);
        pInput.add(new JLabel("Strecke (km):"));
        pInput.add(tfFahren);
        pInput.add(btFahren);

        taStatus = new JTextArea(8, 40);
        taStatus.setEditable(false);
        JScrollPane sp = new JScrollPane(taStatus);

        add(pInput, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        updateStatus();
    }

    private void onTanken(ActionEvent e) {
        try {
            double menge = Double.parseDouble(tfTanken.getText());
            auto.tanken(menge);
            updateStatus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ungültige Zahl!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onFahren(ActionEvent e) {
        try {
            double strecke = Double.parseDouble(tfFahren.getText());
            auto.fahren(strecke);
            updateStatus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ungültige Zahl!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatus() {
        taStatus.setText("");
        taStatus.append("Kennzeichen: " + auto.getKfzKennzeichen() + "\n");
        taStatus.append(String.format("Kilometerstand: %.1f km%n", auto.getKilometerstand()));
        taStatus.append(String.format("Tankvolumen: %.1f L%n", auto.getTankvolumen()));
        taStatus.append(String.format("Kraftstoff: %.1f L%n", auto.getKraftstoffmenge()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoGUI().setVisible(true));
    }
}
