package rice.ui.clickgui.simple;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import rice.Client;
import rice.modules.Module;
import rice.modules.Module.Category;
import rice.modules.other.ToggleSound;
import rice.settings.BooleanSetting;
import rice.settings.ModeSetting;
import rice.settings.NewLine;
import rice.settings.NumberSetting;
import rice.settings.Setting;
import rice.ui.clickgui.simple.button.BooleanButton;
import rice.ui.clickgui.simple.button.Button;
import rice.ui.clickgui.simple.button.CategoryButton;
import rice.ui.clickgui.simple.button.ModeButton;
import rice.ui.clickgui.simple.button.ModuleButton;
import rice.ui.clickgui.simple.button.NormalButton;
import rice.ui.clickgui.simple.button.NumberButton;
import rice.utils.MCHook;

public class ClickGui extends GuiScreen
{
	public ArrayList<Panel> panels = new ArrayList<Panel>();
	public ArrayList<CategoryButton> categorys = new ArrayList<CategoryButton>();
	public ArrayList<NormalButton> normal = new ArrayList<NormalButton>();
	public ArrayList<ArrayList<ModuleButton>> modules = new ArrayList<ArrayList<ModuleButton>>();
	public ArrayList<ModuleButton> combat = new ArrayList<ModuleButton>();
	public ArrayList<ModuleButton> blatant = new ArrayList<ModuleButton>();
	public ArrayList<ModuleButton> render = new ArrayList<ModuleButton>();
	public ArrayList<ModuleButton> utility = new ArrayList<ModuleButton>();
	public ArrayList<ModuleButton> other = new ArrayList<ModuleButton>();
	public ArrayList<Button> setting = new ArrayList<Button>();
	public int x = 100,y = 100, lastFocus = -1;
	public int lastFocused[] = {-1,-1,-1,-1,-1};
	public static int times = 20;
	public void initGui() 
	{
		super.initGui();
		listClear();
		Button.time = 0;
		times = 50;
		this.modules.add(combat);
		this.modules.add(blatant);
		this.modules.add(render);
		this.modules.add(utility);
		this.modules.add(other);
		this.panels.add(new Panel((this.width - 380) / 2, (this.height - 210) / 2, 380, 210, 0x70000000));
		int index = 0;
		int width = 0;
		for(Category c: Category.values()) 
		{
			width = MCHook._fr.getStringWidth(c.name());
			this.categorys.add(new CategoryButton(c, (this.width - 382) / 2 + index, (this.height - 198) / 2, width + 16, 8));
			index += MCHook._fr.getStringWidth(c.name()) + 12;
		}
		this.normal.add(new NormalButton("??lx", (this.width - 142) / 2 + 241, (this.height - 194) / 2 - 9, 16, 16));
		index = 0;
		for(Module m: Client.getModuleByCategory(Category.COMBAT)) 
		{
			this.combat.add(new ModuleButton(m, (this.width - 382) / 2, (this.height - 198) / 2 + 16 + index * 12, 128, 7));
			index++;
		}
		index = 0;
		for(Module m: Client.getModuleByCategory(Category.BLATANT)) 
		{
			this.blatant.add(new ModuleButton(m, (this.width - 382) / 2, (this.height - 198) / 2 + 16 + index * 12, 128, 7));
			index++;
		}
		index = 0;
		for(Module m: Client.getModuleByCategory(Category.RENDER)) 
		{
			this.render.add(new ModuleButton(m, (this.width - 382) / 2, (this.height - 198) / 2 + 16 + index * 12, 128, 7));
			index++;
		}
		index = 0;
		for(Module m: Client.getModuleByCategory(Category.UTILITY)) 
		{
			this.utility.add(new ModuleButton(m, (this.width - 382) / 2, (this.height - 198) / 2 + 16 + index * 12, 128, 7));
			index++;
		}
		index = 0;
		for(Module m: Client.getModuleByCategory(Category.OTHER))
		{
			this.other.add(new ModuleButton(m, (this.width - 382) / 2, (this.height - 198) / 2 + 16 + index * 12, 128, 7));
			index++;
		}
		if(lastFocus > -1) 
		{
			categorys.get(lastFocus).focused = true;
		}
		else 
		{
			categorys.get(0).focused = true;
		}
		
		for(ArrayList<ModuleButton> mbList: modules)
		{
			if(lastFocused[modules.indexOf(mbList)] > -1) 
			{
				mbList.get(lastFocused[modules.indexOf(mbList)]).focused = true;
			}
		}
		index = 0;
		int count = 0;
		for(Module m: Client.getModule()) 
		{
			if(!m.settings.isEmpty()) 
			{
				for(Setting s: m.settings) 
				{
					if(count<=11)
					{
						if(s instanceof BooleanSetting)
						{
							this.setting.add(new BooleanButton((BooleanSetting) s, (float)((this.width) / 2) - 64, (float)((this.height - 198) / 2 + 16 + index * 12f), 128f, 7f));
							index++;
							count++;
						}
						if(s instanceof rice.settings.Description)
						{
							this.setting.add(new rice.ui.clickgui.simple.button.Description((rice.settings.Description) s, (float)((this.width) / 2) - 64, (float)((this.height - 198) / 2 + 16 + index * 12f), 128f, 7f));
							index++;
							count++;
						}
						if(s instanceof ModeSetting) 
						{
							this.setting.add(new ModeButton((ModeSetting) s, (float)((this.width) / 2) - 64, (float)((this.height - 198) / 2 + 16 + index * 12f), 128f, 7f));
							index++;
							count++;
						}
						if(s instanceof NumberSetting) 
						{
							this.setting.add(new NumberButton((NumberSetting) s, (float)((this.width) / 2) - 64, (float)((this.height - 198) / 2 + 16 + index * 12f), 128f, 7f));
							index++;
							count++;
						}
						if(s instanceof NewLine) 
						{
							index+=12-count;
							count=12;
						}
					}
					else if(count>11)
					{
						if(s instanceof BooleanSetting)
						{
							this.setting.add(new BooleanButton((BooleanSetting) s, (float)((this.width) / 2) + 62, (float)((this.height - 198) / 2 + 16 + (index - 12) * 12f), 128f, 7f));
							index++;
							count++;
						}
						if(s instanceof rice.settings.Description)
						{
							this.setting.add(new rice.ui.clickgui.simple.button.Description((rice.settings.Description) s, (float)((this.width) / 2) + 62, (float)((this.height - 198) / 2 + 16 + (index - 12) * 12f), 128f, 7f));
							index++;
							count++;
						}
						if(s instanceof ModeSetting) 
						{
							this.setting.add(new ModeButton((ModeSetting) s, (float)((this.width) / 2) + 62, (float)((this.height - 198) / 2 + 16 + (index - 12) * 12f), 128f, 7f));
							index++;
							count++;
						}
						if(s instanceof NumberSetting) 
						{
							this.setting.add(new NumberButton((NumberSetting) s, (float)((this.width) / 2) + 62, (float)((this.height - 198) / 2 + 16 + (index - 12) * 12f), 128f, 7f));
							index++;
							count++;
						}
					}
				}
			}
			index=0;
			count=0;
		}
	}
	public void onGuiClosed() 
	{
		for(ArrayList<ModuleButton> a: modules) 
		{
			stopListeningModule(a);
			unHoverModule(a);
		}
		rice.modules.other.ClickGui.disable();
		mc.currentScreen = null;
		mc.setIngameFocus();
	}
    public boolean doesGuiPauseGame()
    {
    	return false;
    }
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		if(Button.time <= times)
		{
			Button.time++;
		}
		for(Panel p: panels) 
		{
			p.draw(mouseX,mouseY);
		}
		for(NormalButton n: normal) 
		{
			n.draw(mouseX,mouseY);
		}
		for(CategoryButton c: categorys) 
		{
			c.draw(mouseX,mouseY);
			if(c.focused) 
			{
				if(c.c == Category.COMBAT) 
				{
					lastFocus = 0;
					for(ModuleButton m: combat) 
					{
						drawModAndSets(m, mouseX, mouseY);
					}
				}
				else if(c.c == Category.BLATANT) 
				{
					lastFocus = 1;
					for(ModuleButton m: blatant) 
					{
						drawModAndSets(m, mouseX, mouseY);
					}
				}
				else if(c.c == Category.RENDER) 
				{
					lastFocus = 2;
					for(ModuleButton m: render) 
					{
						drawModAndSets(m, mouseX, mouseY);
					}
				}
				else if(c.c == Category.UTILITY) 
				{
					lastFocus = 3;
					for(ModuleButton m: utility) 
					{
						drawModAndSets(m, mouseX, mouseY);
					}
				}
				else if(c.c == Category.OTHER)
				{
					lastFocus = 4;
					for(ModuleButton m: other)
					{
						drawModAndSets(m, mouseX, mouseY);
					}
				}
			}
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
    }
	private void drawModAndSets(ModuleButton m, int mouseX, int mouseY) 
	{
		m.draw(mouseX,mouseY);
		drawSettings(m, mouseX, mouseY);
	}
	private void drawSettings(ModuleButton m, int mouseX, int mouseY) 
	{
		if(m.focused) 
		{
			for(Button b: setting) 
			{
				for(Setting s: m.m.settings) 
				{
					if(b instanceof BooleanButton) 
					{
						if(s instanceof BooleanSetting) 
						{
							if(((BooleanButton)b).s == s) 
							{
								b.draw(mouseX, mouseY);
							}
						}
					}
					if(b instanceof ModeButton) 
					{
						if(s instanceof ModeSetting) 
						{
							if(((ModeButton)b).m == s) 
							{
								b.draw(mouseX, mouseY);
							}
						}
					}
					if(b instanceof NumberButton) 
					{
						if(s instanceof NumberSetting) 
						{
							if(((NumberButton)b).m == s)
							{
								b.draw(mouseX, mouseY);
							}
						}
					}
					if(b instanceof rice.ui.clickgui.simple.button.Description) 
					{
						if(s instanceof rice.settings.Description) 
						{
							if(((rice.ui.clickgui.simple.button.Description)b).s == s)
							{
								b.draw(mouseX, mouseY);
							}
						}
					}
				}
			}
		}
	}
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for(CategoryButton c: categorys) 
		{
			if(c.hovered && mouseButton == 0) 
			{
				for(CategoryButton cb: categorys) 
				{
					cb.focused = false;
				}
				ToggleSound.playButtonPressSound();
				c.focused = true;
			}
		}
		for(NormalButton m: normal)
		{
			if(m.hovered && mouseButton == 0) 
			{
				ToggleSound.playButtonPressSound();
				mc.currentScreen.onGuiClosed();
			}
		}
		for(ArrayList<ModuleButton> mbList: modules)
		{
			for(ModuleButton m: mbList) 
			{
				if(m.hovered)
				{
					if(mouseButton == 0) 
					{
						if(m.m.getName() == "Auto" || m.m.getName() == "Alts" || m.m.getName() == "ClickGui" || m.m.getName() == "ToggleSound") continue;
						ToggleSound.playButtonPressSound();
						m.m.toggle();
					}
					else if(m.listening && mouseButton == 1)
					{
						ToggleSound.playButtonPressSound();
						m.m.setKey(0);
						m.listening = false;
					}
						else if(!m.listening && mouseButton == 1) 
					{
						if(!m.m.settings.isEmpty()) 
						{
							ToggleSound.playButtonPressSound();
							if(m.focused) 
							{
								unFocusModule(mbList);
								lastFocused[modules.indexOf(mbList)] = -1;
							}
							else 
							{
								unFocusModule(mbList);
								m.focused = true;
								lastFocused[modules.indexOf(mbList)] = mbList.indexOf(m);
							}
						}
					}
					else if(mouseButton == 2)
					{
						ToggleSound.playButtonPressSound();
						stopListeningModule(mbList);
						m.listening = true;
					}
				}
			}
		}
		for(Button m: setting) 
		{
			if(m instanceof BooleanButton) 
			{
				if(((BooleanButton)m).hovered && mouseButton == 0) 
				{
					ToggleSound.playButtonPressSound();
					((BooleanButton)m).s.toggle();
				}
			}
			if(m instanceof ModeButton)
			{
				if(((ModeButton)m).hovered && mouseButton == 0) 
				{
					ToggleSound.playButtonPressSound();
					((ModeButton)m).m.cycle();
				}
				if(((ModeButton)m).hovered && mouseButton == 1)
				{
					ToggleSound.playButtonPressSound();
					((ModeButton)m).m.reversedCycle();
				}
			}
			if(m instanceof NumberButton)
			{
				if(((NumberButton)m).hovered && mouseButton == 0)
				{
					if(isCtrlKeyDown()) 
					{
						ToggleSound.playButtonPressSound();
						((NumberButton)m).m.setValue(((NumberButton)m).m.value + ((NumberButton)m).m.getIncrement() * 100);
					}
					else if(isShiftKeyDown()) 
					{
						ToggleSound.playButtonPressSound();
						((NumberButton)m).m.setValue(((NumberButton)m).m.value + ((NumberButton)m).m.getIncrement() * 10);
					}
					else 
					{
						ToggleSound.playButtonPressSound();
						((NumberButton)m).m.setValue(((NumberButton)m).m.value + ((NumberButton)m).m.getIncrement());
					}
				}
				if(((NumberButton)m).hovered && mouseButton == 1) 
				{
					if(isCtrlKeyDown()) 
					{
						ToggleSound.playButtonPressSound();
						((NumberButton)m).m.setValue(((NumberButton)m).m.value - ((NumberButton)m).m.getIncrement() * 100);
					}
					else if(isShiftKeyDown())
					{
						ToggleSound.playButtonPressSound();
						((NumberButton)m).m.setValue(((NumberButton)m).m.value - ((NumberButton)m).m.getIncrement() * 10);
					}
					else
					{
						ToggleSound.playButtonPressSound();
						((NumberButton)m).m.setValue(((NumberButton)m).m.value - ((NumberButton)m).m.getIncrement());
					}
				}
			}
		}
    }
	public void unFocusModule(ArrayList<ModuleButton> a) 
	{
		for(ModuleButton m: a) m.focused = false;
	}
	public void unHoverModule(ArrayList<ModuleButton> a) 
	{
		for(ModuleButton m: a) m.hovered = false;
	}
	public void stopListeningModule(ArrayList<ModuleButton> a) 
	{
		for(ModuleButton m: a) m.listening = false;
	}
	private void listClear()
	{
		panels.clear();
		categorys.clear();
		normal.clear();
		modules.clear();
		combat.clear();
		blatant.clear();
		render.clear();
		utility.clear();
		other.clear();
		setting.clear();
	}
}