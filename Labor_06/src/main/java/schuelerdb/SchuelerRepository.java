package schuelerdb;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SchuelerRepository {
    // persisitiert in einer Transaktion alle in lst gespeicherten Schueler.

    // Löscht die Tabelle schueler falls sie existiert und erzeugt sie neu
    int persistSchueler(List<Schueler> lst) throws SQLException;

    // löscht alle in der DB gespeicherten Schueler und liefert die
    // Anzahl der gelöschten Schüler zurück
    int deleteAll() throws SQLException;

    // Liefert eine Liste aller Schüler der Klasse klasse aufsteigend nach
    // Katalognummern sortiert bzw. eine leere Liste wenn die Klasse
    // unbekannt ist
    List<Schueler> findSchuelerByKlasse(String klasse) throws SQLException;

    // Liefert eine Liste aller Schüler mit Geschlecht geschlecht aufsteigend
    // nach Klasse und Katalognummer sortiert. Wirft eine IlleagalArgument
    // Exception, wenn das Geschlecht nicht ’m’ oder ’w’ ist.
    List<Schueler> findSchuelerByGeschlecht(char geschlecht) throws SQLException;

    // Liefert eine Map, deren Schlüssel die aufsteigenden Klassenzahlen und
    // deren Werte die entsrechenden Schülerzahlen sind.
    Map<String, Integer> getKlassen() throws SQLException;
}