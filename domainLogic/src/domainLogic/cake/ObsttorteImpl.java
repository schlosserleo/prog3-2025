package domainLogic.cake;

import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import kuchen.Allergen;
import kuchen.Obsttorte;

public class ObsttorteImpl extends CakeProductImpl implements Obsttorte {
  private final Krem krem;
  private final Obst obst;

  public ObsttorteImpl(BigDecimal preis, int naehrwert,
      Collection<Allergen> allergene, Krem krem, Obst obst) {
    super(preis, naehrwert, allergene);
    this.krem = krem;
    this.obst = obst;
  }

  @Override
  public String getKremsorte() {
    return krem.kremsorte();
  }

  @Override
  public String getObstsorte() {
    return obst.obstsorte();
  }
}
