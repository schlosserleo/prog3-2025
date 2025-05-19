package listeners;

import domainLogic.KuchenAutomat;
import events.InspectCakeEvent;

public class InspectCakeEventListener implements GenericListener<InspectCakeEvent> {
  private final KuchenAutomat kuchenAutomat;

  public InspectCakeEventListener(KuchenAutomat kuchenAutomat) {
    this.kuchenAutomat = kuchenAutomat;
  }

  @Override
  public void onEvent(InspectCakeEvent event) {

   if (this.kuchenAutomat.update(event.getLocation())) {
     event.setResponse("SUCCESS");
     return;
   }
   event.setResponse("FAILED");
  }
}
