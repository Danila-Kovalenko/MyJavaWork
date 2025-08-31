import java.util.Scanner;

public class AppRaumbuchung {
    public static void main(String[] args) {
        Firma f = new Firma("Daniel & Co", "Oranienstraße 5-7", 5);

        f.fuegeRaumHinzu(20);   
        f.fuegeRaumHinzu(10);   
        f.fuegeRaumHinzu(30);

        System.out.println("Firma: " + f.getName() + ", Adresse: " + f.getAdresse());
        System.out.println("Max Räume: " + f.getMaxRaeume() + ", aktuell: " + f.getAnzahlRaeume());

        Scanner sc = new Scanner(System.in);
        System.out.println("Bitte gewünschte Anzahl an Sitzplätzen eingeben: ");
        int plaetze = sc.nextInt();

        int raumNummer = f.sucheRaum(plaetze);
        if (raumNummer >= 0) {
            System.out.println("Optimaler Raum gefunden: Nummer = " + raumNummer);
        } else {
            System.out.println("Kein freier Raum mit mindestens " + plaetze + " Plätzen verfügbar.");
        }

        System.out.println("Noch freie Räume: " + f.anzahlFrei());

        sc.close();
    }
}

class Firma {
    private String name;
    private String adresse;
    private int maxRaeume;
    private int anzahlRaeume;

    private int[] raeumePlaetze;     
    private boolean[] raeumeGebucht; 
    private int[] raeumeNummern;

    public Firma(String name, String adresse, int maxRaeume) {
        this.name = name;
        this.adresse = adresse;
        this.maxRaeume = maxRaeume;
        this.anzahlRaeume = 0;
        this.raeumePlaetze = new int[maxRaeume];
        this.raeumeGebucht = new boolean[maxRaeume];
        this.raeumeNummern = new int[maxRaeume];
    }

    public String getName() { return name; }
    public String getAdresse() { return adresse; }
    public int getMaxRaeume() { return maxRaeume; }
    public int getAnzahlRaeume() { return anzahlRaeume; }

    public void fuegeRaumHinzu(int plaetze) {
        if (anzahlRaeume < maxRaeume) {
            raeumePlaetze[anzahlRaeume] = plaetze;
            raeumeGebucht[anzahlRaeume] = false;
            raeumeNummern[anzahlRaeume] = 101 + anzahlRaeume;
            anzahlRaeume++;
        } else {
            System.out.println("Maximale Anzahl Räume erreicht!");
        }
    }

    public int sucheRaum(int plaetze) {
        int bestIndex = -1;
        int bestKap = Integer.MAX_VALUE;
        int bestNummer = Integer.MAX_VALUE;

        for (int i = 0; i < anzahlRaeume; i++) {
            if (!raeumeGebucht[i] && raeumePlaetze[i] >= plaetze) {
                int kap = raeumePlaetze[i];
                int num = raeumeNummern[i];
                if (kap < bestKap || (kap == bestKap && num < bestNummer)) {
                    bestKap = kap;
                    bestNummer = num;
                    bestIndex = i;
                }
            }
        }

        if (bestIndex >= 0) {
            raeumeGebucht[bestIndex] = true;
            return raeumeNummern[bestIndex];
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
