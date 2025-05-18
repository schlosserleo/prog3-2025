package domainLogic.cake;

import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import kuchen.Allergen;
import kuchen.Obstkuchen;

public class ObstkuchenImpl extends CakeProductImpl implements Obstkuchen {
  private final Obst obst;

  public ObstkuchenImpl(BigDecimal preis, int naehrwert, Collection<Allergen> allergene, Obst obst) {
    super(preis, naehrwert, allergene);
    this.obst = obst;
  }

  @Override
  public String getObstsorte() {
    return this.obst.obstsorte();
  }
}
