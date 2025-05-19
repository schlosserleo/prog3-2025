package listeners;

import domainLogic.KuchenAutomat;
import events.CreateCakeEvent;

public class CreateCakeEventListener implements GenericListener<CreateCakeEvent> {

  KuchenAutomat kuchenAutomat;

  public CreateCakeEventListener(KuchenAutomat kuchenAutomat) {
    this.kuchenAutomat = kuchenAutomat;
  }

  @Override
  public void onEvent(CreateCakeEvent event) {
    if (kuchenAutomat.create(event.getCakeType(), event.getHersteller(), event.getPreis(),
        event.getNaehrwert(), event.getAllergene(), event.getHaltbarkeit(), event.getKrem(),
        event.getObst())) {
      event.setResponse("SUCCESS");
      return;
    }
    event.setResponse("FAILED");
  }
}

