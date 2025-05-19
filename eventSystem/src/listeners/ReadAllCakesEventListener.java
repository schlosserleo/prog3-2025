package listeners;

import domainLogic.KuchenAutomat;
import events.ReadAllCakesEvent;

public class ReadAllCakesEventListener implements GenericListener<ReadAllCakesEvent> {
  private final KuchenAutomat kuchenAutomat;
  public ReadAllCakesEventListener(KuchenAutomat kuchenAutomat) {
    this.kuchenAutomat = kuchenAutomat;
  }

  @Override
  public void onEvent(ReadAllCakesEvent event) {
    event.setResponse(this.kuchenAutomat.read().toString());
  }
}
