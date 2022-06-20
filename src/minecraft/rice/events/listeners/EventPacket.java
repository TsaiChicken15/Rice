package rice.events.listeners;

import net.minecraft.network.Packet;
import rice.events.Event;

public class EventPacket extends Event<EventPacket>
{
	public Packet packet;
	public EventPacket(Packet packet) 
	{
		this.packet = packet;
	}
	public Packet getPacket() 
	{
		return packet;
	}
}