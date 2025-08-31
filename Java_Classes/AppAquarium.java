public class AppAquarium {
    public static void main(String[] args) {
        Steuerung s = new Steuerung(21.0);

        s.einschalten();           // "Status: Eingeschaltet. Soll: 21.0°C. Überwachung läuft."
        s.messeUndRegle(19.1);     // "Status: Soll: 21.0°C. Ist: 19.1°C." + Warnung kalt + Heizung an
        s.lampeAn();               // "Lampe an."
        s.messeUndRegle(20.9);     // innerhalb ±5% → Heizung aus
        s.messeUndRegle(22.0);     // innerhalb ±5% → keine Aktion
        s.setSoll(20.5);           // "Neue Solltemperatur: 20.5°C"
        s.lampeAus();              // "Lampe aus."
        s.messeUndRegle(21.9);     // über +5% → Warnung warm
    }
}

class Steuerung {
    private boolean eingeschaltet = false;
    private double soll = 21.0;
    private final double toleranzProzent = 5.0;
    private boolean heizungAn = false;
    private boolean lampeAn = false;

    public Steuerung() { }

    public Steuerung(double soll) {
        this.soll = soll;
    }

    public void einschalten() {
        eingeschaltet = true;
        System.out.printf("Status: Eingeschaltet. Soll: %.1f°C. Überwachung läuft.%n", soll);
    }

    public void ausschalten() {
        eingeschaltet = false;
        heizungAn = false;
        System.out.println("Status: Ausgeschaltet. Überwachung gestoppt.");
    }

    public void setSoll(double neueSoll) {
        this.soll = neueSoll;
        System.out.printf("Neue Solltemperatur: %.1f°C%n", soll);
    }

    public void lampeAn() {
        lampeAn = true;
        System.out.println("Lampe an.");
    }

    public void lampeAus() {
        lampeAn = false;
        System.out.println("Lampe aus.");
    }

    public void messeUndRegle(double ist) {
        if (!eingeschaltet) {
            System.out.println("Status: Steuerung ist aus.");
            return;
        }
        System.out.printf("Status: Soll: %.1f°C. Ist: %.1f°C.%n", soll, ist);

        double unten = untereGrenze();
        double oben  = obereGrenze();

        if (ist < unten) {
            if (!heizungAn) {
                heizungAn = true;
            }
            System.out.println("-> Achtung: zu kalt! Heizung an.");
        } else if (ist > oben) {
            if (heizungAn) {
                heizungAn = false;
            }
            System.out.println("-> Achtung: zu warm!");
        } else {
            if (heizungAn) {
                heizungAn = false;
                System.out.println("-> Heizung aus.");
            }
        }
    }


    private double untereGrenze() {
        return soll * (1.0 - toleranzProzent / 100.0);
    }

    private double obereGrenze() {
        return soll * (1.0 + toleranzProzent / 100.0);
    }
}
