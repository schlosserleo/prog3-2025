package domainLogic;

import java.util.HashSet;
import verwaltung.Hersteller;

public class HerstellerVerwaltung {

  private final HashSet<Hersteller> herstellerListe;

  public HerstellerVerwaltung() {
    this.herstellerListe = new HashSet<>();
  }

  public boolean addHersteller(Hersteller hersteller) {
    if (containsHersteller(hersteller)) {
      return false;
    }
    this.herstellerListe.add(hersteller);
    return true;
  }

  public Hersteller getHersteller(String name) {
    Hersteller currentHersteller = new HerstellerImpl(name);
    if (this.containsHersteller(new HerstellerImpl(name))) {
      return new HerstellerImpl(name);
    }
    return null;
  }

  public boolean deleteHersteller(Hersteller hersteller){
    if (containsHersteller(hersteller)) {
      return false;
    }
    this.herstellerListe.remove(hersteller);
    return true;
  }

  public boolean containsHersteller(Hersteller hersteller) {
    return herstellerListe.contains(hersteller);
  }

  public HashSet<Hersteller> getHerstellerListe() {
    return new HashSet<>(this.herstellerListe);
  }
}
