package rice.jui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import rice.Client;
import rice.modules.Module;
import rice.modules.Module.Category;
import rice.modules.other.ToggleSound;
import rice.utils.MCHook;

public class JArrayList extends JFrame implements ActionListener, MCHook{

	JLabel label;
	static ArrayList<JLabel> labels = new ArrayList<JLabel>();
	static ArrayList<Module> modsList = new ArrayList<Module>();
	
	JArrayList() {
		int index=0;
		modsList = new ArrayList<Module>();
		for(Module m: Client.getModule()) modsList.add(m);
		modsList.sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(String.valueOf(((Module)m).name + (((Module)m).additionalInfo != "" ? " " + ((Module)m).additionalInfo : "")))).reversed());
		for(Module m: modsList) {
			label = new JLabel();
			if(!m.isToggled()) label.setVisible(false);
			label.setText(m.name);
			label.setBounds(5, 5 + index * 15, 100, 15);
			index++;
			labels.add(label);
		}
		
		this.setTitle("Minecraft");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setBackground(Color.LIGHT_GRAY);
		this.setSize(160, (index + 3) * 15);
		this.setVisible(true);
		
		for(JLabel jl: labels) this.add(jl);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	static void updateArrayList() {
		int index=0;
		for(Module m: modsList) {
			if(!m.isToggled()) labels.get(modsList.indexOf(m)).setVisible(false);
			if(m.isToggled()) labels.get(modsList.indexOf(m)).setVisible(true);
			index++;
		}
	}
}