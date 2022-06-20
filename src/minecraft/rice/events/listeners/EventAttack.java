package rice.events.listeners;

import net.minecraft.entity.Entity;
import rice.events.Event;

public class EventAttack extends Event<EventAttack> {
	public Entity entity;
	
	public EventAttack(Entity entity) {
		this.entity = entity;
	}
}
