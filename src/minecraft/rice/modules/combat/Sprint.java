package rice.modules.combat;

import org.lwjgl.input.Keyboard;

import rice.events.Event;
import rice.events.listeners.EventRenderGUI;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.utils.KeybindUtil;
import rice.utils.MCHook;
import rice.utils.MovementUtil;
import rice.utils.RenderUtil;

public class Sprint extends Module implements MCHook
{
	public Sprint() 
	{
		super("Sprint", Keyboard.KEY_NONE, Category.COMBAT, "Automatically activates sprinting for you");
		this.toggled = true;
	}
	public void onDisable() 
	{
		_ku.state(_gs.keyBindSprint.getKeyCode(), _ku.isDown(_gs.keyBindSprint.getKeyCode()));
	}
	public void onEvent(Event e) 
	{
		if(e instanceof EventUpdate && e.isPRE()) 
		{
			_ku.state(_gs.keyBindSprint.getKeyCode(), true);
		}
	}
}