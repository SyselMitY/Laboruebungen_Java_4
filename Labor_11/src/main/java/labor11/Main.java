package labor11;

import labor11.modelreise.Benutzer;
import labor11.modelreise.Reisetyp;
import labor11.modelreise.Reiseveranstaltung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Repository repository = Repository.getINSTANCE()) {
            repository.insertTestData();

            List<Benutzer> benutzerList = new ArrayList<>();
            benutzerList.add(new Benutzer("orangensaft@kuchengenießer.at", "h4ckerp4sswort", true));
            benutzerList.add(new Benutzer("k.meinhardt@gmx.at", "meinekatzeheisstschnurri!", false));
            benutzerList.add(new Benutzer("katharina@selfmade.shop", "vierteraugust1975", true));
            benutzerList.add(new Benutzer("sossi@soisi.cf", "schlawuxxaschlawuxxy", true));
            benutzerList.add(new Benutzer("katzenheferl@gmail.com", "miaufauch", false));

//            benutzerList.add(new Benutzer("falscheEmail", "PasswortLangGenug", true));

            List<Reisetyp> reisetypList = new ArrayList<>();
            reisetypList.add(new Reisetyp("Städtereisen"));
            reisetypList.add(new Reisetyp("Italien"));
            reisetypList.add(new Reisetyp("Last Minute"));

            benutzerList.get(0).addReisetyp(reisetypList.get(0));
            benutzerList.get(0).addReisetyp(reisetypList.get(1));
            benutzerList.get(1).addReisetyp(reisetypList.get(0));
            benutzerList.get(1).addReisetyp(reisetypList.get(2));
            benutzerList.get(2).addReisetyp(reisetypList.get(1));
            benutzerList.get(3).addReisetyp(reisetypList.get(1));
            benutzerList.get(3).addReisetyp(reisetypList.get(2));
            benutzerList.get(4).addReisetyp(reisetypList.get(1));

            List<Reiseveranstaltung> reiseveranstaltungList = new ArrayList<>();
            reiseveranstaltungList.add(new Reiseveranstaltung("Meindlingsreith-Oberzipf",
                    "Ausflug in das schöne Herzen Niederösterreichs",
                    LocalDate.now(),
                    LocalDate.now().plusDays(2),
                    59.95,
                    reisetypList.get(0)));

            reiseveranstaltungList.add(new Reiseveranstaltung("Zwinzing",
                    "Erfahren Sie Ursprung in Oberzwinzing, Entwicklung in Unterzwinzing und die Moderne in Neuzwinzing. Eine Facettenreiche Reise",
                    LocalDate.now().plusDays(7),
                    LocalDate.now().plusDays(9),
                    129.95,
                    reisetypList.get(0)));

            reiseveranstaltungList.add(new Reiseveranstaltung("Verdane-Blanco",
                    "Dieses verlassene Dorf besteht aus 2 Häusern. Machen Sie sich gefasst auf bahnbrechende Langeweile!",
                    LocalDate.now().plusDays(30),
                    LocalDate.now().plusDays(35),
                    599.95,
                    reisetypList.get(1)));

            reiseveranstaltungList.add(new Reiseveranstaltung("Frescalo",
                    "Dieses Italienische etwas besteht nur aus Sand. Es ist eigentlich nur ein Strand. Mehr auch nicht.",
                    LocalDate.now().plusDays(25),
                    LocalDate.now().plusDays(27),
                    329.95,
                    reisetypList.get(1)));

            reiseveranstaltungList.add(new Reiseveranstaltung("Schwarzbach-Grünwimpfling",
                    "Antike Römerstadt im Waldviertel. Warum die Idioten in dem Kaff a Lager gehabt haben? Keiner weiß es.",
                    LocalDate.now().plusDays(3),
                    LocalDate.now().plusDays(4),
                    75.95,
                    reisetypList.get(2)));

            reiseveranstaltungList.add(new Reiseveranstaltung("Grom",
                    "Gehst du in Grom, bist du im Grom. Last minute Sonderpreis!",
                    LocalDate.now().plusDays(2),
                    LocalDate.now().plusDays(2),
                    39.95,
                    reisetypList.get(2)));

            reisetypList.forEach(repository::persist);
            benutzerList.forEach(repository::persist);
            reiseveranstaltungList.forEach(repository::persist);
        }
    }
}
