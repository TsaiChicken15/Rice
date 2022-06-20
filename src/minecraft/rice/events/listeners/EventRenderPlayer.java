package rice.events.listeners;

import net.minecraft.client.entity.AbstractClientPlayer;
import rice.events.Event;

public class EventRenderPlayer extends Event<EventRenderPlayer> {
	public AbstractClientPlayer entity;
	  
	public EventRenderPlayer(AbstractClientPlayer entity) {
	    this.entity = entity;
	}
}