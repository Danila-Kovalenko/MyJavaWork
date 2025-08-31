import java.util.Scanner;

public class AppRadio {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Eingabe Startwerte für r1
        System.out.print("Starteinstellungen für Radio r1\n");
        System.out.print("Lautstärke (0..10): ");
        int laut = sc.nextInt();
        System.out.print("Frequenz (85.0..110.0): ");
        double freq = sc.nextDouble();

        Radio r1 = new Radio(false, laut, freq);
        System.out.println("Start r1: " + r1);

        r1.an();
        r1.lauter();
        r1.waehleSender(101.2);
        System.out.println("r1: " + r1);

        r1.leiser();
        r1.aus();
        System.out.println("r1: " + r1);

        System.out.println();

        // Eingabe Startwerte für r2
        System.out.print("Starteinstellungen für Radio r2\n");
        System.out.print("Lautstärke (0..10): ");
        int laut2 = sc.nextInt();
        System.out.print("Frequenz (85.0..110.0): ");
        double freq2 = sc.nextDouble();

        Radio r2 = new Radio(true, laut2, freq2);
        System.out.println("Start r2: " + r2);

        r2.lauter();
        r2.lauter();
        r2.waehleSender(95.7);
        System.out.println("r2: " + r2);

        sc.close();
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
