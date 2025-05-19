package cli;

import domainLogic.HerstellerImpl;
import domainLogic.HerstellerVerwaltung;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import eventDispatcher.EventDispatcher;
import events.CreateCakeEvent;
import events.CreateCakeEvent.cakeEventBuilder;
import events.InspectCakeEvent;
import events.ReadAllCakesEvent;
import events.ReadByCakeTypeEvent;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;
import kuchen.Allergen;
import listeners.CreateCakeEventListener;
import listeners.InspectCakeEventListener;
import listeners.ReadAllCakesEventListener;
import listeners.ReadCakeTypeEventListener;
import verwaltung.Hersteller;

public class CLI {

  private final String[] launchArgs;
  private final HerstellerVerwaltung herstellerVerwaltung;
  private final Scanner scanner;
  private final EventDispatcher eventDispatcher;

  public CLI(HerstellerVerwaltung herstellerVerwaltung,
      CreateCakeEventListener createCakeEventListener,
      ReadCakeTypeEventListener readCakeTypeEventListener,
      ReadAllCakesEventListener readAllCakesEventListener,
      InspectCakeEventListener inspectCakeEventListener,
      String[] launchArgs) {
    this.launchArgs = launchArgs;
    this.herstellerVerwaltung = herstellerVerwaltung;
    this.scanner = new Scanner(System.in);
    this.eventDispatcher = new EventDispatcher();
    this.eventDispatcher.registerListener(CreateCakeEvent.class, createCakeEventListener);
    this.eventDispatcher.registerListener(ReadByCakeTypeEvent.class, readCakeTypeEventListener);
    this.eventDispatcher.registerListener(ReadAllCakesEvent.class, readAllCakesEventListener);
    this.eventDispatcher.registerListener(InspectCakeEvent.class, inspectCakeEventListener);
  }

  private void createCake(String[] cakeArgs) {
    cakeEventBuilder cakeEventBuilder;
    CreateCakeEvent createCakeEvent;

    String cakeType = cakeArgs[0];
    Hersteller hersteller = new HerstellerImpl(cakeArgs[1]);
    BigDecimal preis = BigDecimal.valueOf(Double.parseDouble(cakeArgs[2]));
    int naehrwert = Integer.parseInt(cakeArgs[3]);
    Duration haltbarkeit = Duration.ofDays(Integer.parseInt(cakeArgs[4]));
    HashSet<Allergen> allergene = parseAllergene(cakeArgs[5]);

    cakeEventBuilder = new cakeEventBuilder(this, cakeType, preis, allergene, naehrwert,
        haltbarkeit, hersteller);

    createCakeEvent = switch (cakeType) {
      case "Obstkuchen" -> cakeEventBuilder.obst(new Obst(cakeArgs[6])).build();
      case "Kremkuchen" -> cakeEventBuilder.krem(new Krem(cakeArgs[6])).build();
      case "Obsttorte" ->
          cakeEventBuilder.obst(new Obst(cakeArgs[6])).krem(new Krem(cakeArgs[7])).build();
      default -> throw new IllegalStateException("Unexpected value: " + cakeType);
    };
    this.eventDispatcher.dispatch(createCakeEvent);
    System.out.println(createCakeEvent.getResponse());
  }

  private void createHersteller(String customerName) {
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

  private void createMode() {
    String[] args = getSplittedArgs();
    switch (args.length) {
      case 0 -> System.out.println("Invalid Arguments");
      case 1 -> createHersteller(args[0]);
      default -> createCake(args);
    }
  }

  private void updateMode() {
    String[] args = getSplittedArgs();
    int location = Integer.parseInt(args[0]);
    InspectCakeEvent inspectCakeEvent = new InspectCakeEvent(this,location);
    eventDispatcher.dispatch(inspectCakeEvent);
    System.out.println(inspectCakeEvent.getResponse());
  }

  private void readMode() throws ClassNotFoundException {
    String[] args = scanner.nextLine().split(" ");

    if (args[0].equals("kuchen") && args.length > 1) {
      String className = "domainLogic.cake." + args[1] + "Impl";
      ReadByCakeTypeEvent readByCakeTypeEvent = new ReadByCakeTypeEvent(this,
          Class.forName(className));
      eventDispatcher.dispatch(readByCakeTypeEvent);
      System.out.println(readByCakeTypeEvent.getResponse());
    } else if (args[0].equals("kuchen")) {
      ReadAllCakesEvent readAllCakesEvent = new ReadAllCakesEvent(this);
      eventDispatcher.dispatch(readAllCakesEvent);
      System.out.println(readAllCakesEvent.getResponse());
    }
  }

  public void run() throws ClassNotFoundException {
    while (true) {
      System.out.print("Give Mode ");
      String mode = scanner.nextLine();
      switch (mode) {
        case ":c" -> createMode();
        case ":r" -> readMode();
        case ":u" -> updateMode();
        case ":q" -> {
          return;
        }
      }
    }
  }
}