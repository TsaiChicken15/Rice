package rice.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting 
{
	public int index;
	public List<String> modes;
	public ModeSetting(String name, String defaultMode, String... modes) 
	{
		this.name = name;
		this.modes = Arrays.asList(modes);
		index = this.modes.indexOf(defaultMode);
	}
	public String get() 
	{
		return modes.get(this.index);
	}
	public List<String> getModes() 
	{
		return modes;
	}
	public boolean is(String mode) 
	{
		return index == modes.indexOf(mode);
	}
	public int getModeIndex(String mode) 
	{
		return modes.indexOf(mode);
	}
	public void cycle() 
	{
		if(index < modes.size() - 1) 
		{
			index++;
		}
		else 
		{
			index=0;
		}
	}
	public void reversedCycle()
	{
		if(index > 0) 
		{
			index--;
		}
		else 
		{
			index = modes.size() - 1;
		}
	}
}