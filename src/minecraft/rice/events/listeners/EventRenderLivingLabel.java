package rice.events.listeners;

import net.minecraft.entity.Entity;
import rice.events.Event;

public class EventRenderLivingLabel extends Event<EventRenderLivingLabel>
{
	public Entity e;
	public String text;
	public double x, y, z;
	public int maxDistance;

	public EventRenderLivingLabel(Entity e,String text, double x, double y, double z, int maxDistance) 
	{
		this.e = e;
		this.text = text;
		this.x = x;
		this.y = y;
		this.z = z;
		this.maxDistance = maxDistance;
		
	}
}