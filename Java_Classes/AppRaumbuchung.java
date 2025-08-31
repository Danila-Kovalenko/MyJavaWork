public class AppRaumbuchung {
    public static void main(String[] args) {
        Firma f = new Firma("Raum & Co", "Hauptstraße 1", 5);

        // Räume hinzufügen
        f.fuegeRaumHinzu(10);   // Raum 0
        f.fuegeRaumHinzu(20);   // Raum 1
        f.fuegeRaumHinzu(30);   // Raum 2

        System.out.println("Firma: " + f.getName() + ", Adresse: " + f.getAdresse());
        System.out.println("Max Räume: " + f.getMaxRaeume() + ", aktuell: " + f.getAnzahlRaeume());

        int nr = f.sucheRaum(25);
        System.out.println("Suche Raum >= 25 Plätze: Ergebnis = " + nr);

        System.out.println("Freie Räume: " + f.anzahlFrei());
    }
}

class Firma {
    private String name;
    private String adresse;
    private int maxRaeume;
    private int anzahlRaeume;

    private int[] raeumePlaetze;     // Plätze je Raum
    private boolean[] raeumeGebucht; // true = gebucht

    public Firma(String name, String adresse, int maxRaeume) {
        this.name = name;
        this.adresse = adresse;
        this.maxRaeume = maxRaeume;
        this.anzahlRaeume = 0;
        this.raeumePlaetze = new int[maxRaeume];
        this.raeumeGebucht = new boolean[maxRaeume];
    }

    public String getName() { return name; }
    public String getAdresse() { return adresse; }
    public int getMaxRaeume() { return maxRaeume; }
    public int getAnzahlRaeume() { return anzahlRaeume; }

    public void fuegeRaumHinzu(int plaetze) {
        if (anzahlRaeume < maxRaeume) {
            raeumePlaetze[anzahlRaeume] = plaetze;
            raeumeGebucht[anzahlRaeume] = false;
            anzahlRaeume++;
        } else {
            System.out.println("Maximale Anzahl Räume erreicht!");
        }
    }

    public int sucheRaum(int plaetze) {
        for (int i = 0; i < anzahlRaeume; i++) {
            if (!raeumeGebucht[i] && raeumePlaetze[i] >= plaetze) {
                raeumeGebucht[i] = true;
                return i;
            }
        }
        return -1;
    }

    public int anzahlFrei() {
        int frei = 0;
        for (int i = 0; i < anzahlRaeume; i++) {
            if (!raeumeGebucht[i]) frei++;
        }
        return frei;
    }
}
