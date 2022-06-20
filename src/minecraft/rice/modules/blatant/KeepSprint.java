package rice.modules.blatant;

import org.lwjgl.input.Keyboard;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import rice.events.Event;
import rice.events.listeners.EventPacket;
import rice.modules.Module;
import rice.settings.NumberSetting;
import rice.utils.MCHook;

public class KeepSprint extends Module implements MCHook
{
	public KeepSprint() 
	{
		super("KeepSprint", Keyboard.KEY_NONE, Category.BLATANT, "¡±rPrevents you from losing sprint while attacking");
	}
	public void onEvent(Event e) 
	{
	    if(e instanceof EventPacket) 
	    {
	    	if(((EventPacket)e).packet instanceof C0BPacketEntityAction) 
	    	{
	    		if(((C0BPacketEntityAction)(((EventPacket)e).packet)).func_180764_b() == C0BPacketEntityAction.Action.STOP_SPRINTING)
	    		{
	    			e.cancelEvent();
	    		}
	    	}
	    }
	}
}