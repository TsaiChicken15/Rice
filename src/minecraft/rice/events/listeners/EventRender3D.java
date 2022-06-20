package rice.events.listeners;

import rice.events.Event;

public class EventRender3D extends Event<EventRender3D> 
{
	public float tick;
	public EventRender3D(float tick) 
	{
		this.tick = tick;
	}
	public float getTick() 
	{
		return tick;
	}
	public void setTick(int tick) 
	{
		this.tick = tick;
	}
}