import java.util.Scanner;

public class AppPunkt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Eingabe Punkt p1
        System.out.print("Gib x-Koordinate von p1 ein: ");
        double x1 = sc.nextDouble();
        System.out.print("Gib y-Koordinate von p1 ein: ");
        double y1 = sc.nextDouble();
        Punkt p1 = new Punkt(x1, y1);

        System.out.println("Punkt p1: " + p1);
        System.out.println("Abstand p1 zum Ursprung: " + p1.abstandZumUrsprung());

        Punkt p1x = p1.spiegelungAnX();
        Punkt p1y = p1.spiegelungAnY();
        Punkt p1o = p1.spiegelungAmUrsprung();
        System.out.println("Spiegelung an X-Achse: " + p1x);
        System.out.println("Spiegelung an Y-Achse: " + p1y);
        System.out.println("Spiegelung am Ursprung: " + p1o);

        // Eingabe Punkt p2
        System.out.print("\nGib x-Koordinate von p2 ein: ");
        double x2 = sc.nextDouble();
        System.out.print("Gib y-Koordinate von p2 ein: ");
        double y2 = sc.nextDouble();
        Punkt p2 = new Punkt(x2, y2);

        System.out.println("Punkt p2: " + p2);
        System.out.println("Abstand zwischen p1 und p2: " + p1.abstandZu(p2));

        sc.close();
    }
}

class Punkt {
    private double x;
    private double y;

    public Punkt() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Punkt(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double abstandZumUrsprung() {
        return Math.sqrt(x * x + y * y);
    }

    public Punkt spiegelungAnX() {
        return new Punkt(x, -y);
    }

    public Punkt spiegelungAnY() {
        return new Punkt(-x, y);
    }

    public Punkt spiegelungAmUrsprung() {
        return new Punkt(-x, -y);
    }

    public double abstandZu(Punkt p) {
        double dx = this.x - p.x;
        double dy = this.y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
