package rice.settings;

public class KeybindSetting extends Setting 
{
	public int code;
	public KeybindSetting(int code) 
	{
		this.code = code;
	}
	public int getKeyCode()
	{
		return code;
	}
	public void setKeyCode(int code) 
	{
		this.code = code;
	}
}