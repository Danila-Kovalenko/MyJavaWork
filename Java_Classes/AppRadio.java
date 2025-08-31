public class AppRadio {
    public static void main(String[] args) {
        Radio r1 = new Radio();
        System.out.println("Start r1: " + r1);

        r1.an();
        r1.lauter();
        r1.waehleSender(101.2);
        System.out.println("r1: " + r1);

        r1.leiser();
        r1.aus();
        System.out.println("r1: " + r1);

        System.out.println();

        Radio r2 = new Radio(true, 5, 200.0); // ungültige Frequenz -> 99.9
        System.out.println("Start r2: " + r2);

        r2.lauter();
        r2.lauter();
        r2.waehleSender(95.7);
        System.out.println("r2: " + r2);
    }
}

class Radio {
    private boolean eingeschaltet;
    private int lautstaerke;    // 0..10
    private double frequenz;    // 85.0..110.0

    public Radio() {
        eingeschaltet = false;
        lautstaerke = 0;
        frequenz = 99.9;
        System.out.println("Radio erstellt (Standardwerte).");
    }

    public Radio(boolean istAn, int lautstaerke, double frequenz) {
        eingeschaltet = istAn;
        this.lautstaerke = Math.max(0, Math.min(lautstaerke, 10));
        if (frequenz >= 85.0 && frequenz <= 110.0) {
            this.frequenz = frequenz;
        } else {
            this.frequenz = 99.9;
        }
        System.out.println("Radio erstellt (mit Parametern).");
    }

    public void an() {
        eingeschaltet = true;
        System.out.println("Radio eingeschaltet.");
    }

    public void aus() {
        eingeschaltet = false;
        System.out.println("Radio ausgeschaltet.");
    }

    public void lauter() {
        if (eingeschaltet && lautstaerke < 10) {
            lautstaerke++;
            System.out.println("Lauter gestellt auf " + lautstaerke);
        } else {
            System.out.println("Lauter nicht möglich (Radio aus oder schon max).");
        }
    }

    public void leiser() {
        if (eingeschaltet && lautstaerke > 0) {
            lautstaerke--;
            System.out.println("Leiser gestellt auf " + lautstaerke);
        } else {
            System.out.println("Leiser nicht möglich (Radio aus oder schon min).");
        }
    }

    public void waehleSender(double frequenz) {
        if (frequenz >= 85.0 && frequenz <= 110.0) {
            this.frequenz = frequenz;
            System.out.println("Sender gewählt: " + frequenz);
        } else {
            this.frequenz = 99.9;
            System.out.println("Ungültige Frequenz, auf 99.9 gesetzt.");
        }
    }

    @Override
    public String toString() {
        if (eingeschaltet) {
            return "Radio an: Freq=" + frequenz + ", Laut=" + lautstaerke;
        } else {
            return "Radio aus";
        }
    }
}
