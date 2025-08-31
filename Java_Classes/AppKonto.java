public class AppKonto {
    public static void main(String[] args) {
        Konto k1 = new Konto();
        System.out.println("== Konto 1 (Start) ==");
        System.out.println(k1);

        k1.einzahlen(200.0);
        System.out.println("Nach Einzahlung 200 €: " + k1.getKontostand() + " €");

        k1.auszahlen(50.0);
        System.out.println("Nach Auszahlung 50 €: " + k1.getKontostand() + " €");

        System.out.println();

        Konto k2 = new Konto("Anna", "Muster", "DE1234567890", 500.0, -200.0);
        System.out.println("== Konto 2 (Start) ==");
        System.out.println(k2);

        k2.auszahlen(600.0); // möglich, da Limit = -200 €
        System.out.println("Nach Auszahlung 600 €: " + k2.getKontostand() + " €");

        boolean ok = k2.auszahlen(200.0); // nicht möglich, würde Limit überschreiten
        System.out.println("Versuch 200 € abzuheben erfolgreich? " + ok);
        System.out.println("Kontostand bleibt: " + k2.getKontostand() + " €");
    }
}

class Konto {
    private String vorname;
    private String nachname;
    private String kontonummer;
    private double kontostand;
    private double limit;

    public Konto() {
        this.vorname = "";
        this.nachname = "";
        this.kontonummer = "";
        this.kontostand = 0.0;
        this.limit = 0.0;
    }

    public Konto(String vorname, String nachname, String kontonummer,
                 double startstand, double limit) {
        this.vorname = (vorname == null) ? "" : vorname.trim();
        this.nachname = (nachname == null) ? "" : nachname.trim();
        this.kontonummer = (kontonummer == null) ? "" : kontonummer.trim();
        this.kontostand = startstand;
        this.limit = limit;
    }

    public void einzahlen(double betrag) {
        if (betrag > 0) {
            kontostand += betrag;
        }
    }

    public boolean auszahlen(double betrag) {
        if (betrag <= 0) return false;
        double neuerStand = kontostand - betrag;
        if (neuerStand >= limit) {
            kontostand = neuerStand;
            return true;
        } else {
            return false;
        }
    }

    public double getKontostand() {
        return kontostand;
    }

    @Override
    public String toString() {
        return "Konto{" +
                "Inhaber='" + vorname + " " + nachname + '\'' +
                ", Kontonummer='" + kontonummer + '\'' +
                ", Kontostand=" + kontostand +
                " €, Limit=" + limit + " €}";
    }
}