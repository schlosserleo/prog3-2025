package domainLogic;

import domainLogic.cake.CakeProduct;
import domainLogic.cake.CakeProductMutable;
import domainLogic.cake.KremkuchenImpl;
import domainLogic.cake.ObstkuchenImpl;
import domainLogic.cake.ObsttorteImpl;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import kuchen.Allergen;
import verwaltung.Hersteller;
import verwaltung.Verkaufsobjekt;

public class KuchenAutomat {

  public final HashMap<Verkaufsobjekt, Integer> indexMap;
  private final HashMap<Integer, CakeProductMutable> kuchenList;
  private final HerstellerVerwaltung herstellerVerwaltung;
  private final int maxCakes;
  private int nextFreeSlot;

  public KuchenAutomat(int maxCakes, HerstellerVerwaltung herstellerVerwaltung) {
    this.kuchenList = new HashMap<>();
    this.indexMap = new HashMap<>();
    this.herstellerVerwaltung = herstellerVerwaltung;
    this.maxCakes = maxCakes;
    this.nextFreeSlot = 0;
  }


  public boolean create(Hersteller hersteller, BigDecimal preis, int naehrwert,
      Collection<Allergen> allergene, Duration haltbarkeit, Krem krem) {
    return insert(hersteller, new KremkuchenImpl(preis, naehrwert, allergene, haltbarkeit, krem));
  }

  public boolean create(Hersteller hersteller, BigDecimal preis, int naehrwert,
      Collection<Allergen> allergene, Duration haltbarkeit, Obst obst) {
    return insert(hersteller, new ObstkuchenImpl(preis, naehrwert, allergene, haltbarkeit, obst));
  }

  public boolean create(Hersteller hersteller, BigDecimal preis, int naehrwert,
      Collection<Allergen> allergene, Duration haltbarkeit, Krem krem, Obst obst) {
    return insert(hersteller,
        new ObsttorteImpl(preis, naehrwert, allergene, haltbarkeit, krem, obst));
  }


  public ArrayList<CakeProduct> read() {
    return new ArrayList<>(this.kuchenList.values());
  }

  public CakeProduct read(int fachnummer) {
    return this.kuchenList.get(fachnummer);
  }


  public ArrayList<CakeProduct> read(Class<? extends CakeProduct> kuchenSorte) {
    ArrayList<CakeProduct> result = new ArrayList<>();
    for (Map.Entry<Integer, CakeProductMutable> entry : this.kuchenList.entrySet()) {
      if (kuchenSorte.isInstance(entry.getValue())) {
        result.add(entry.getValue());
      }
    }
    return result;
  }

  public Integer getIndexOfCake(Verkaufsobjekt cake) {
    return this.indexMap.get(cake);
  }

  public boolean update(int fachnummer) {
    if (isFachnummerValid(fachnummer)) {
      this.kuchenList.get(fachnummer).updateInspektionsdatum();
      return true;
    }
    return false;
  }

  public boolean delete(int fachnummer) {
    if (isFachnummerValid(fachnummer)) {
      this.indexMap.remove(this.kuchenList.get(fachnummer));
      this.kuchenList.remove(fachnummer);
      return true;
    }
    return false;
  }

  public int getCapacity() {
    return maxCakes;
  }

  private boolean insert(Hersteller hersteller, CakeProductMutable kuchen) {
    if (!(this.herstellerVerwaltung.containsHersteller(hersteller))) {
      return false;
    }
    if (!(findNextFreeSlot())) {
      return false;
    }

    kuchen.setKuchenautomat(this);
    kuchen.setHersteller(hersteller);
    kuchen.updateInspektionsdatum();
    this.kuchenList.put(nextFreeSlot, kuchen);
    this.indexMap.put(kuchen, nextFreeSlot);
    return true;
  }

  private boolean isFachnummerValid(int fachnummer) {
    return this.kuchenList.containsKey(fachnummer);
  }

  private boolean findNextFreeSlot() {
    int previousLocation = 0;

    for (int currentLocation : this.kuchenList.keySet()) {
      if (currentLocation - previousLocation > 1) {
        nextFreeSlot = previousLocation + 1;
        return true;
      }
      previousLocation = currentLocation;
    }

    if (previousLocation < maxCakes) {
      nextFreeSlot = previousLocation + 1;
      return true;
    }

    return false;
  }


}