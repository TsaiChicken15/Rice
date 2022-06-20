package rice.events.listeners;

import net.minecraft.entity.item.EntityItem;
import rice.events.Event;

public class EventRenderItem extends Event<EventRenderItem> 
{
	public EntityItem item;
	  
	public EventRenderItem(EntityItem item) 
	{
	    this.item = item;
	}
}