package rice.jui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

import net.minecraft.util.ResourceLocation;

public class JMain {
	public JMain() {
		if(JClickGUI.countOfFrames == 0)
			new JClickGUI();
		else if(JClickGUI.countOfFrames != 0)
			System.out.println("ExternalGui is already opened.");
		//new JArrayList();
	}
}
