package rice.events.listeners;

import rice.events.Event;

public class EventYawHead extends Event<EventYawHead> {
	public float yaw;
	
	public EventYawHead(float yaw) {
		this.yaw = yaw;
	}
}
