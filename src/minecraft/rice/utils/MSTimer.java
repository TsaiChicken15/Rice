package rice.utils;

import java.awt.Color;

public class MSTimer
{
	private long time = -1L;
    public boolean hasTimePassed(long MS)
    {
        return System.currentTimeMillis() >= time + MS ? true : false;
    }
    public long hasTimeLeft(long MS) 
    {
        return MS + time - System.currentTimeMillis();
    }
    public long timePassed() 
    {
        return System.currentTimeMillis() - time;
    }
    public void reset()
    {
        time = System.currentTimeMillis();
    }
}