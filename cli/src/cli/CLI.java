package cli;

import domainLogic.HerstellerImpl;
import domainLogic.HerstellerVerwaltung;
import domainLogic.KuchenAutomat;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import eventDispatcher.EventDispatcher;
import events.CreateCakeEvent;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;
import kuchen.Allergen;
import verwaltung.Hersteller;

public class CLI {

  private final String[] launchArgs;
  private final KuchenAutomat kuchenAutomat;
  private final HerstellerVerwaltung herstellerVerwaltung;
  private final Scanner scanner;
  private final EventDispatcher eventDispatcher;

  public CLI(KuchenAutomat kuchenAutomat, HerstellerVerwaltung herstellerVerwaltung,
      String[] launchArgs) {
    this.kuchenAutomat = kuchenAutomat;
    this.launchArgs = launchArgs;
    this.herstellerVerwaltung = herstellerVerwaltung;
    this.scanner = new Scanner(System.in);
    this.eventDispatcher = new EventDispatcher();
  }

  private void createCake(String[] cakeArgs) {
    String cakeType = cakeArgs[0];
    Hersteller hersteller = new HerstellerImpl(cakeArgs[1]);
    BigDecimal preis = BigDecimal.valueOf(Double.parseDouble(cakeArgs[2]));
    int naehrwert = Integer.parseInt(cakeArgs[3]);
    Duration haltbarkeit = Duration.ofDays(Integer.parseInt(cakeArgs[4]));
    HashSet<Allergen> allergene = parseAllergene(cakeArgs[5]);

    CreateCakeEventSetup createCakeEventSetup = new CreateCakeEvent(this, cakeType, preis, allergene,
        hersteller, naehrwert, haltbarkeit);

    switch (cakeType) {
      case "Obstkuchen":
        createCakeEventSetup.setObst(new Obst(cakeArgs[6]));
        break;
      case "Kremkuchen":
        createCakeEventSetup.setKrem(new Krem(cakeArgs[6]));
        break;
      case "Obsttorte":
        createCakeEventSetup.setObst(new Obst(cakeArgs[6]));
        createCakeEventSetup.setKrem(new Krem(cakeArgs[7]));
    }

    CreateCakeEvent createCakeEvent = ;

    this.eventDispatcher.dispatch(createCakeEvent);

  }

  private void createCustomer(String customerName) {
    herstellerVerwaltung.addHersteller(new HerstellerImpl(customerName));
  }

  private HashSet<Allergen> parseAllergene(String allergeneUnparsed) {
    if (allergeneUnparsed.equals(",")) {
      return null;
    }
    return Arrays.stream(allergeneUnparsed.split(",")).map(Allergen::valueOf)
        .collect(Collectors.toCollection(HashSet::new));
  }

  private String[] getSplittedArgs() {
    return this.scanner.nextLine().split(" ");
  }
}