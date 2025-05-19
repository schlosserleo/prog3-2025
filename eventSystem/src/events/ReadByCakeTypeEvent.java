package events;

import domainLogic.cake.CakeProduct;

public class ReadByCakeTypeEvent extends ResponseEvent{

  private final Class<?> kuchenSorte;
  public ReadByCakeTypeEvent(Object source, Class<?> kuchenSorte) {
    super(source);
    this.kuchenSorte = kuchenSorte;
  }
  public Class<?> getKuchenSorte() {
    return this.kuchenSorte;
  }

}
