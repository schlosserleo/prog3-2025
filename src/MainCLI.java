import cli.CLI;
import domainLogic.HerstellerVerwaltung;
import domainLogic.KuchenAutomat;

public class MainCLI {
   public static void main(String[] args){
       HerstellerVerwaltung hv = new HerstellerVerwaltung();
       KuchenAutomat ka = new KuchenAutomat(20,hv);
       CLI cli = new CLI(ka,hv,new String[]{});
   }
}
