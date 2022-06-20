package rice.modules.combat;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.Entity;
import rice.Client;
import rice.events.Event;
import rice.events.listeners.EventGetReach;
import rice.events.listeners.EventUpdate;
import rice.modules.Module;
import rice.settings.BooleanSetting;
import rice.settings.NumberSetting;
import rice.utils.MCHook;
import rice.utils.RandomUtil;

public class Reach extends Module implements MCHook
{
	public Reach() 
	{
		super("Reach", Keyboard.KEY_NONE, Category.COMBAT, "Increases your attack distance");
		this.addSettings(attackRangeValue, buildRangeValue, chanceValue, onlySprintValue, verticalCheckValue, disableInWaterValue);
	}
	public static NumberSetting attackRangeValue = new NumberSetting("AttackRange", 3.0, 0.0, 6.0, 0.01);
	public static NumberSetting buildRangeValue = new NumberSetting("BuildRange", 4.5, 0.0, 6.0, 0.01);
	public static NumberSetting chanceValue = new NumberSetting("Chance", 100, 1, 100, 1);
	public static BooleanSetting onlySprintValue = new BooleanSetting("OnlySpirnt",false);
	public static BooleanSetting verticalCheckValue = new BooleanSetting("NoVertical",false);
	public static BooleanSetting disableInWaterValue = new BooleanSetting("NoWater",false);
	public static Entity entity;
	public void onEvent2(Event e)
	{
		if (e instanceof EventUpdate) 
		{
			this.additionalInfo = String.valueOf(attackRangeValue.get()) + ", " + String.valueOf(buildRangeValue.get());
			if(attackRangeValue.get() > buildRangeValue.get()) 
			{
				double temp = attackRangeValue.get();
				attackRangeValue.setValue(buildRangeValue.get());
				buildRangeValue.setValue(temp);
			}
		}
	}
	public void onEvent(Event e) 
	{
	    if(e instanceof EventGetReach) 
	    {
	    	if((mc.thePlayer.isInWater() && disableInWaterValue.get()) || (!mc.thePlayer.isSprinting() && onlySprintValue.get()) || (RandomUtil.randomInt(0, 100) > chanceValue.get()))
	    	{
	    		return;
	    	}
	    	
	    	if(mc.pointedEntity != null)
	    	{
	    		if(verticalCheckValue.get())
	    		{
	    			if(Math.abs(mc.pointedEntity.posY - mc.thePlayer.posY) > 2.0f)
	    			{
	    				return;
	    			}
	    		}
	    	}
	    	((EventGetReach)e).d = buildRangeValue.get();
	    	e.cancelEvent();
	    }
	}
}