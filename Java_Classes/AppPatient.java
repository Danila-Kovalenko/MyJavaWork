import java.time.LocalDate;
import java.time.Period;

public class AppPatient {
    public static void main(String[] args) {
        Patient p1 = new Patient(
                "P-0001",
                "Anna",
                "Muster",
                LocalDate.of(1990, 5, 14),
                "+49 30 1234567",
                "AOK Berlin",
                "AOK-901234"
        );

        Patient p2 = new Patient(
                "P-0002",
                "Jonas",
                "Beispiel",
                LocalDate.of(1985, 11, 2),
                "+49 89 7654321",
                "TK",
                "TK-558877"
        );

        System.out.println("== Patienten ==");
        System.out.println(p1);
        System.out.println("Alter p1: " + p1.berechneAlter() + " Jahre");
        System.out.println(p2);
        System.out.println("Alter p2: " + p2.berechneAlter() + " Jahre");

        p1.setTelefon("+49 30 9999999");
        System.out.println("Neues Telefon p1: " + p1.getTelefon());

        try {
            p2.setGeburtsdatum(LocalDate.now().plusDays(1));
        } catch (IllegalArgumentException ex) {
            System.out.println("Erwarteter Validierungsfehler: " + ex.getMessage());
        }
    }
}

class Patient {

    private final String id;
    private String vorname;
    private String nachname;
    private LocalDate geburtsdatum;
    private String telefon;
    private String krankenkasse;
    private String versicherungsnummer;

    public Patient(
            String id,
            String vorname,
            String nachname,
            LocalDate geburtsdatum,
            String telefon,
            String krankenkasse,
            String versicherungsnummer
    ) {
        this.id = requireNonBlank("id", id);
        this.vorname = requireNonBlank("vorname", vorname);
        this.nachname = requireNonBlank("nachname", nachname);
        this.geburtsdatum = requireValidDate(geburtsdatum);
        this.telefon = requireNonBlank("telefon", telefon);
        this.krankenkasse = requireNonBlank("krankenkasse", krankenkasse);
        this.versicherungsnummer = requireNonBlank("versicherungsnummer", versicherungsnummer);
    }

    public String getId() { return id; }

    public String getVorname() { return vorname; }
    public void setVorname(String vorname) { this.vorname = requireNonBlank("vorname", vorname); }

    public String getNachname() { return nachname; }
    public void setNachname(String nachname) { this.nachname = requireNonBlank("nachname", nachname); }

    public LocalDate getGeburtsdatum() { return geburtsdatum; }
    public void setGeburtsdatum(LocalDate geburtsdatum) { this.geburtsdatum = requireValidDate(geburtsdatum); }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = requireNonBlank("telefon", telefon); }

    public String getKrankenkasse() { return krankenkasse; }
    public void setKrankenkasse(String krankenkasse) { this.krankenkasse = requireNonBlank("krankenkasse", krankenkasse); }

    public String getVersicherungsnummer() { return versicherungsnummer; }
    public void setVersicherungsnummer(String versicherungsnummer) { this.versicherungsnummer = requireNonBlank("versicherungsnummer", versicherungsnummer); }


    public int berechneAlter() {
        return Period.between(this.geburtsdatum, LocalDate.now()).getYears();
    }

    private static String requireNonBlank(String feld, String wert) {
        if (wert == null) {
            throw new IllegalArgumentException("Feld '" + feld + "' darf nicht leer sein.");
        }
        return wert.trim();
    }

    private static LocalDate requireValidDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Geburtsdatum darf nicht null sein.");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Geburtsdatum darf nicht in der Zukunft liegen.");
        }
        return date;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", geburtsdatum=" + geburtsdatum +
                ", telefon='" + telefon + '\'' +
                ", krankenkasse='" + krankenkasse + '\'' +
                ", versicherungsnummer='" + versicherungsnummer + '\'' +
                '}';
    }
}
