package domainLogic;

import verwaltung.Hersteller;

public record HerstellerImpl(String name) implements Hersteller {

  @Override
  public String getName() {
    return this.name;
  }
}
