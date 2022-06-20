package rice.modules.other;

import org.lwjgl.input.Keyboard;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventPacket;
import rice.modules.Module;
import rice.modules.Module.Category;
import rice.notification.Notification;
import rice.notification.NotificationManager;
import rice.notification.NotificationType;
import rice.utils.MCHook;

public class NoRotate extends Module implements MCHook
{
	public NoRotate() 
	{
		super("NoRotate", Keyboard.KEY_NONE, Category.OTHER, "");
	}
	public void onEvent(Event e) 
	{
		if(e instanceof EventPacket) 
		{
			if((((EventPacket)e).packet instanceof S08PacketPlayerPosLook))
			{
				Packet packet = ((EventPacket)e).packet;
	        	S08PacketPlayerPosLook p = (S08PacketPlayerPosLook) packet;
	            p.field_148936_d = mc.thePlayer.rotationYaw;
	            p.field_148937_e = mc.thePlayer.rotationPitch;
				NotificationManager.show(new Notification(NotificationType.INFO, "¡±l" + "Packet edited", 1));
			}
		}
	}
}