package rice.events;

public class Event<T>
{
	public boolean cancelled; 
	public EventType eventType;
	public EventDirection eventDirection;
	public boolean isCancelled()
	{
		return this.cancelled;
	}
	public void setCancelled(boolean canceled) 
	{
		this.cancelled = canceled;
	}
	public void cancelEvent() 
	{
		this.cancelled = true;
	}
	public EventType getType() 
	{
		return this.eventType;
	}
	public void setType(EventType type) 
	{
		this.eventType = type;
	}
	public EventDirection getDirection() 
	{
		return this.eventDirection;
	}
	public void setDirection(EventDirection direction) 
	{
		this.eventDirection = direction;
	}
	public boolean isPRE() 
	{
		if (this.eventType == null) 
		{
			return false; 
		}
		return (this.eventType == EventType.PRE);
	}	
	public boolean isPOST() 
	{
		if (this.eventType == null) 
		{
			return false; 
		}
		return (this.eventType == EventType.POST);
	}
	public boolean isINCOMING() 
	{
		if (this.eventDirection == null)
		{
			return false; 
		}
		return (this.eventDirection == EventDirection.INCOMING);
	}
	public boolean isOUTGOING() 
	{
		if (this.eventDirection == null) 
		{
			return false; 
		}
		return (this.eventDirection == EventDirection.OUTGOING);
	}
}