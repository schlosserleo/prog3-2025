package events;

import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import kuchen.Allergen;
import verwaltung.Hersteller;

public class CreateCakeEvent extends ResponseEvent implements CreateCakeEventSetup {

  private final String cakeType;
  private final BigDecimal preis;
  private final HashSet<Allergen> allergene;
  private final Hersteller hersteller;
  private final Duration haltbarkeit;
  private final Integer naehrwert;

  private Krem krem = null;
  private Obst obst = null;

  public CreateCakeEvent(Object source, String cakeType, BigDecimal preis,
      HashSet<Allergen> allergene, Hersteller hersteller, Integer naehrwert, Duration haltbarkeit) {
    super(source);
    this.cakeType = cakeType;
    this.preis = preis;
    this.allergene = allergene;
    this.hersteller = hersteller;
    this.naehrwert = naehrwert;
    this.haltbarkeit = haltbarkeit;
  }

  public String getCakeType() {
    return this.cakeType;
  }

  public BigDecimal getPreis() {
    return this.preis;
  }

  public int getNaehrwert() {
    return this.naehrwert;
  }

  public HashSet<Allergen> getAllergene() {
    return this.allergene;
  }

  public Hersteller getHersteller() {
    return this.hersteller;
  }

  public Duration getHaltbarkeit() {
    return this.haltbarkeit;
  }

  public Obst getObst() {
    return this.obst;
  }

  public void setObst(Obst obst) {
    this.obst = obst;
  }

  public Krem getKrem() {
    return this.krem;
  }

  public void setKrem(Krem krem) {
    this.krem = krem;
  }
}
