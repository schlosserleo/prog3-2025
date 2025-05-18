package domainLogic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import verwaltung.Hersteller;

class HerstellerVerwaltungTest {

  @org.junit.jupiter.api.Test
  void addHersteller() {
    HashSet<Hersteller> expectedHerstellerListe = new HashSet<>();
    expectedHerstellerListe.add(new HerstellerImpl("Kuchenmeister"));
    expectedHerstellerListe.add(new HerstellerImpl("doktorSchlecker"));

    Hersteller customerOne = new HerstellerImpl("Kuchenmeister");
    Hersteller customerTwo = new HerstellerImpl("doktorSchlecker");
    Hersteller customerThree = new HerstellerImpl("Kuchenmeister");
    HerstellerVerwaltung herstellerVerwaltung = new HerstellerVerwaltung();
    herstellerVerwaltung.addHersteller(customerOne);
    herstellerVerwaltung.addHersteller(customerTwo);
    herstellerVerwaltung.addHersteller(customerThree);
    assertEquals(expectedHerstellerListe, herstellerVerwaltung.getHerstellerListe());
  }
}