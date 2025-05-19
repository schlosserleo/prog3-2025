import cli.CLI;
import domainLogic.HerstellerVerwaltung;
import domainLogic.KuchenAutomat;
import listeners.CreateCakeEventListener;
import listeners.InspectCakeEventListener;
import listeners.ReadAllCakesEventListener;
import listeners.ReadCakeTypeEventListener;

public class MainCLI {

  public static void main(String[] args) throws ClassNotFoundException {
    HerstellerVerwaltung hv = new HerstellerVerwaltung();
    KuchenAutomat ka = new KuchenAutomat(20, hv);
    CreateCakeEventListener createCakeEventListener = new CreateCakeEventListener(ka);
    ReadCakeTypeEventListener readCakeTypeEventListener = new ReadCakeTypeEventListener(ka);
    ReadAllCakesEventListener readAllCakesEventListener = new ReadAllCakesEventListener(ka);
    InspectCakeEventListener inspectCakeEventListener = new InspectCakeEventListener(ka);
    CLI cli = new CLI(hv, createCakeEventListener, readCakeTypeEventListener,
        readAllCakesEventListener, inspectCakeEventListener, new String[]{});
    cli.run();

    /* Implemented:
    create Hersteller
      :c
      <HerstellerName>
    create Cake
      :c
      <As described in pdf>
    read
      Cakes by Type
        :r
        kuchen <CakeType>(Kremkuchen, Obstkuchen, Obsttorte)
      All Cakes
        :r
        kuchen
     quit
      :q
     */
  }
}
