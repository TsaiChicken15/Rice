package rice.events.listeners;

import rice.events.Event;

public class EventRender3D extends Event<EventRender3D> 
{
	public float partialTicks;
	public EventRender3D(float partialTicks) 
	{
		this.partialTicks = partialTicks;
	}
	public float getPartialTicks() 
	{
		return partialTicks;
	}
	public void setPartialTicks(int partialTicks) 
	{
		this.partialTicks = partialTicks;
	}
}