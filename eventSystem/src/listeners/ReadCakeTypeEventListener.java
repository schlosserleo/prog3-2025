package listeners;

import domainLogic.KuchenAutomat;
import events.ReadByCakeTypeEvent;

public class ReadCakeTypeEventListener implements GenericListener<ReadByCakeTypeEvent> {

  private final KuchenAutomat kuchenAutomat;

  public ReadCakeTypeEventListener(KuchenAutomat kuchenAutomat) {
    this.kuchenAutomat = kuchenAutomat;
  }

  @Override
  public void onEvent(ReadByCakeTypeEvent event) {
    event.setResponse(this.kuchenAutomat.read(event.getKuchenSorte()).toString());
  }
}