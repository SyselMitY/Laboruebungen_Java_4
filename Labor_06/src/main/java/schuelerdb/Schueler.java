package schuelerdb;

public class Schueler {
    private String familienname;
    private String vorname;
    private char geschlecht;
    private int katalognummer;
    private String klasse;

    public Schueler(String familienname, String vorname, char geschlecht, int katalognummer, String klasse) {
        this.familienname = familienname;
        this.vorname = vorname;
        this.geschlecht = geschlecht;
        this.katalognummer = katalognummer;
        this.klasse = klasse;
    }


    public static Schueler of(String s) {
        String[] splitted = s.split(";");

        if (splitted.length != 5)
            throw new IllegalArgumentException("Invalid number of parameters in String");
        if (splitted[2].length() != 1)
            throw new IllegalArgumentException("Geschlecht should have length 1, is "+splitted[2]);

        String newFamilienname = splitted[0];
        String newVorname = splitted[1];
        char newGeschlecht = splitted[2].charAt(0);
        int newKatalognummer = Integer.parseInt(splitted[3]);
        String newKlasse = splitted[4];
        return new Schueler(newFamilienname, newVorname, newGeschlecht, newKatalognummer, newKlasse);
    }

    public static boolean isValidString(String s) {
        try {
            Schueler.of(s);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getFamilienname() {
        return familienname;
    }

    public String getVorname() {
        return vorname;
    }

    public char getGeschlecht() {
        return geschlecht;
    }

    public int getKatalognummer() {
        return katalognummer;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setFamilienname(String familienname) {
        this.familienname = familienname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setGeschlecht(char geschlecht) {
        this.geschlecht = geschlecht;
    }

    public void setKatalognummer(int katalognummer) {
        this.katalognummer = katalognummer;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    @Override
    public String toString() {
        return String.format("%s %d: %s %s (%c)",klasse,katalognummer,vorname,familienname,geschlecht);
    }
}
