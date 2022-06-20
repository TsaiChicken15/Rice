package rice.modules.other;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.ModeSetting;

public class ToggleSound extends Module
{
	public ToggleSound()
	{
		super("ToggleSound", Keyboard.KEY_NONE, Category.OTHER, "");
		this.addSettings(modeValue);
		this.toggled = true;
	}
	public static ModeSetting modeValue = new ModeSetting("Sound","Button","None","Button","PickUp","Harp","Pling");
	public void onEvent2(Event e)
	{
		if (e instanceof EventUpdate) 
		{
			this.additionalInfo = modeValue.get();
		}
	}
	public static void playButtonPressSound() 
	{
		if(modeValue.is("Button"))
			mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
		else if(modeValue.is("Harp"))
			mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("note.harp"), 1.0F));
		else if(modeValue.is("Pling"))
			mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("note.pling"), 1.0F));
		else if(modeValue.is("PickUp"))
			mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("random.pop"), 1.0F));
	}
}