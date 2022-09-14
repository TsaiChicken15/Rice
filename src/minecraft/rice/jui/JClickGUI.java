package rice.jui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import rice.Client;
import rice.modules.Module;
import rice.modules.Module.Category;
import rice.settings.BooleanSetting;
import rice.settings.ModeSetting;
import rice.settings.NumberSetting;
import rice.settings.Setting;
import rice.ui.clickgui.simple.ClickGui;

public class JClickGUI extends JFrame implements ActionListener{

	public static int countOfFrames = 0;
	
	JButton button;
	JLabel label;
	JCheckBox checkbox;
	JComboBox<String> combobox;
	JSlider slider;
	//JPanel panel;
	//JTextArea textArea; 
	ArrayList<JButton> categorys = new ArrayList<JButton>();
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	ArrayList<JButton> arrows = new ArrayList<JButton>();
	ArrayList<JCheckBox> checkBoxs = new ArrayList<JCheckBox>();
	ArrayList<JComboBox> comboBoxs = new ArrayList<JComboBox>();
	//ArrayList<JTextArea> texts = new ArrayList<JTextArea>();
	ArrayList<JButton> texts = new ArrayList<JButton>();
	public static ArrayList<JSlider> sliders = new ArrayList<JSlider>();
	public static Category currentCategory = Category.COMBAT;
	public static int lastFocused[] = {-1,-1,-1,-1,-1};
	int defaultFocused[] = {-1,-1,-1,-1,-1};
	JClickGUI() {
		categorys.clear();
		buttons.clear();
		arrows.clear();
		checkBoxs.clear();
		comboBoxs.clear();
		texts.clear();
		sliders.clear();
		//currentCategory = ClickGui.lastFocus == -1 ? Category.values()[0] : Category.values().clone()[ClickGui.lastFocus];
		lastFocused = ClickGui.lastFocused;
		int index=0, subindex=0;
		for(Category c: Module.Category.values()) {
			button = new JButton();
			button.setBounds(5, 5 + index * 25, 100, 25);
			button.setFocusable(true);
			button.setText(c.name);
			button.addActionListener(e -> changeCategory(c));
			button.setBackground(Color.LIGHT_GRAY);
			button.setBorder(BorderFactory.createEtchedBorder());
			categorys.add(button);
			index++;
		}
		for(Category c: Module.Category.values()) {
			index=0;
			for(Module m: Client.getModuleByCategory(c)) {
				button = new JButton();
				button.setBounds(105, 5 + index * 25, 100, 25);
				button.setFocusable(true);
				button.setText(m.getName());
				button.addActionListener(e -> toggleModule(m));
				button.setForeground(m.isToggled() ? Color.GREEN : Color.RED);
				button.setBackground(Color.LIGHT_GRAY);
				button.setBorder(BorderFactory.createEtchedBorder());
				button.setVisible(false);
				buttons.add(button);
				
				button = new JButton();
				button.setBounds(205, 5 + index * 25, 15, 25);
				button.setFocusable(true);
				button.setName(m.getName());
				button.setText(">");
				button.addActionListener(e -> expandSetting(m));
				button.setBackground(Color.LIGHT_GRAY);
				button.setBorder(BorderFactory.createEtchedBorder());
				button.setVisible(false);
				
				if(m.settings.size() <= 1) button.setEnabled(false); 
				
				arrows.add(button);
				
				if(m.settings.size() > 1) {
					subindex = 0;
					for(Setting s: m.settings) {
						if(s instanceof ModeSetting) {
							button = new JButton();
							button.setBounds(220, 5 + subindex * 15, 150, 15);
							button.setFocusable(false);
							button.setBorderPainted(false);
							button.setName(m.getName() + " " + ((ModeSetting)s).name);
							button.setText(((ModeSetting)s).name);
							button.setBackground(Color.LIGHT_GRAY);
							button.setVisible(false);
							texts.add(button);
							
							combobox = new JComboBox<String>(((ModeSetting)s).getModesStrings());
							combobox.setSelectedItem(((ModeSetting)s).get());
							combobox.setBounds(370, 5 + subindex * 15, 280, 15);
							combobox.setName(m.getName() + " " + ((ModeSetting)s).name);
							combobox.setFocusable(false);
							combobox.setBackground(Color.LIGHT_GRAY);
							combobox.addActionListener(e -> comboboxIsChanged(s, e));
							combobox.setVisible(false);
							comboBoxs.add(combobox);
							subindex++;
						}
						else if(s instanceof NumberSetting) {
							/*textArea = new JTextArea();
							textArea.setBounds(230, 5 + subindex * 15, 100, 15);
							textArea.setFocusable(false);
							textArea.setName(m.getName() + " " + ((NumberSetting)s).name);
							textArea.setText(((NumberSetting)s).name);
							textArea.setBackground(Color.LIGHT_GRAY);
							textArea.setVisible(false);
							texts.add(textArea);*/
							
							button = new JButton();
							button.setBounds(220, 5 + subindex * 15, 150, 15);
							button.setFocusable(false);
							button.setBorderPainted(false);
							button.setName(m.getName() + " " + ((NumberSetting)s).name);
							button.setText(((NumberSetting)s).name);
							button.setBackground(Color.LIGHT_GRAY);
							button.setVisible(false);
							texts.add(button);
							
							button = new JButton();
							button.setBounds(550, 5 + subindex * 15, 100, 15);
							button.setFocusable(false);
							button.setBorderPainted(false);
							button.setName(m.getName() + " " + ((NumberSetting)s).name + " Number");
							button.setText(String.valueOf(((NumberSetting)s).get()));
							button.setBackground(Color.LIGHT_GRAY);
							button.setVisible(false);
							texts.add(button);
							
							slider = new JSlider((int)(((NumberSetting)s).getMinimum() * 100), (int)(((NumberSetting)s).getMaximum() * 100), (int)(((NumberSetting)s).get()) * 100);
							slider.setValue((int) (((NumberSetting)s).get() * 100));
							slider.setBounds(370, 5 + subindex * 15, 180, 15);
							slider.setFocusable(true);
							slider.setName(m.getName() + " " + ((NumberSetting)s).name);
							slider.addChangeListener(e -> sliderIsChanged(s, e));
							slider.setBackground(Color.LIGHT_GRAY);
							slider.setVisible(false);
							sliders.add(slider);
							subindex++;
						}
						else if(s instanceof BooleanSetting) {
							checkbox = new JCheckBox(((BooleanSetting)s).name);
							checkbox.setSelected(((BooleanSetting)s).get());
							checkbox.setBounds(220, 5 + subindex * 15, 430, 15);
							checkbox.setFocusable(true);
							checkbox.setName(m.getName() + " " + ((BooleanSetting)s).name);
							checkbox.addActionListener(e -> checkboxIsSelected(s));
							checkbox.setBackground(Color.LIGHT_GRAY);
							checkbox.setBorder(BorderFactory.createEtchedBorder());
							checkbox.setVisible(false);
							checkBoxs.add(checkbox);
							subindex++;
						}
						
					}
				}
				index++;
			}
		}
		
		countOfFrames = 1;
		
		this.setTitle("");
		this.setIconImage(null);
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        countOfFrames = 0;
		    }
		});
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.setSize(670, 318);
		this.setVisible(true);
		this.changeCategory(currentCategory);
		
		for(JButton jb: categorys) this.add(jb);
		for(JButton jb: buttons) this.add(jb);
		for(JButton jb: arrows) this.add(jb);
		for(JCheckBox jc: checkBoxs) this.add(jc);
		for(JComboBox jc: comboBoxs) this.add(jc);
		for(JSlider js: sliders) this.add(js);
		//for(JTextArea jt: texts) this.add(jt);
		for(JButton jt: texts) this.add(jt);
		
		if(lastFocused[Module.getIndex(currentCategory)] != -1)
			this.expandSetting(Client.getModuleByCategory(currentCategory).get(lastFocused[Module.getIndex(currentCategory)]));
	}
	
	void changeCategory(Category c) {
		currentCategory = c;
		//ToggleSound.playButtonPressSound();
		
		for(JSlider js: sliders) js.setVisible(false);
		if(lastFocused[Module.getIndex(c)] != -1) for(JSlider js: sliders) //for(Module m: Client.getModuleByCategory(c)) 
				if(js.getName().contains(Client.getModuleByCategory(c).get( lastFocused[Module.getIndex(c)] ).getName())) js.setVisible(true);
		
		/*for(JTextArea jb: texts) jb.setVisible(false);
		for(JTextArea jb: texts) for(Module m: Client.getModuleByCategory(c)) 
				if(jb.getName().contains(m.getName())) jb.setVisible(true);*/
		
		for(JButton jb: texts) jb.setVisible(false);
		if(lastFocused[Module.getIndex(c)] != -1) for(JButton jb: texts) //for(Module m: Client.getModuleByCategory(c)) 
				if(jb.getName().contains(Client.getModuleByCategory(c).get( lastFocused[Module.getIndex(c)] ).getName())) jb.setVisible(true);
		
		for(JCheckBox jc: checkBoxs) jc.setVisible(false);
		if(lastFocused[Module.getIndex(c)] != -1) for(JCheckBox jc: checkBoxs) for(Module m: Client.getModuleByCategory(c)) 
				if(jc.getName().contains(Client.getModuleByCategory(c).get( lastFocused[Module.getIndex(c)] ).getName())) jc.setVisible(true);
		
		for(JComboBox js: comboBoxs) js.setVisible(false);
		if(lastFocused[Module.getIndex(c)] != -1) for(JComboBox jc: comboBoxs) //for(Module m: Client.getModuleByCategory(c)) 
				if(jc.getName().contains(Client.getModuleByCategory(c).get( lastFocused[Module.getIndex(c)] ).getName())) jc.setVisible(true);
		
		for(JButton jb: arrows) jb.setVisible(false);
		for(JButton jb: arrows) for(Module m: Client.getModuleByCategory(c))
				if(jb.getName() == m.getName()) jb.setVisible(true);
		
		for(JButton jb: buttons) jb.setVisible(false);
		for(JButton jb: buttons) for(Module m: Client.getModuleByCategory(c)) 
				if(jb.getText() == m.getName()) jb.setVisible(true);
	}
	
	void expandSetting(Module m) {
		lastFocused[Module.getIndex(m.getCategory())] = Client.getModuleByCategory(m.getCategory()).indexOf(m);
		//ToggleSound.playButtonPressSound();
		for(Module mod: Client.getModule()) mod.expanded = false;
		m.expanded = true;
		
		for(JSlider js: sliders) js.setVisible(false);
		for(JSlider js: sliders) 
			if(js.getName().contains(m.getName())) js.setVisible(true);
		
		/*for(JTextArea jb: texts) jb.setVisible(false);
		for(JTextArea jb: texts) 
			if(jb.getName().contains(m.getName())) jb.setVisible(true);*/
		
		for(JButton jb: texts) jb.setVisible(false);
		for(JButton jb: texts) 
			if(jb.getName().contains(m.getName())) jb.setVisible(true);
		
		for(JCheckBox jc: checkBoxs) jc.setVisible(false);
		for(JCheckBox jc: checkBoxs) 
			if(jc.getName().contains(m.getName())) jc.setVisible(true);
		
		for(JComboBox jc: comboBoxs) jc.setVisible(false);
		for(JComboBox jc: comboBoxs) 
			if(jc.getName().contains(m.getName())) jc.setVisible(true);
	}
	
	void toggleModule(Module m) {
		if(Client.isEnabled("AntiBlatant") != null && m.getCategory() == Category.BLATANT) return;
		Client.getModuleByName(m.getName()).silentToggle();
		for(JButton jb: buttons) 
			if(Client.getModuleByName(jb.getText()).isToggled()) jb.setForeground(Color.GREEN);
			else jb.setForeground(Color.RED);
		//JArrayList.updateArrayList();
	}
	
	void checkboxIsSelected(Setting s) {
		for(Module mod: Client.getModule()) for(Setting set: mod.settings) {
				if(!(set instanceof BooleanSetting)) continue;
				if(((BooleanSetting)set).name == s.name) for(JCheckBox jc: checkBoxs) 
						if(jc.getName().contains(((BooleanSetting)set).name)) ((BooleanSetting)set).setEnabled(jc.isSelected());
			}
	}
	
	void comboboxIsChanged(Setting s, ActionEvent e) {
		int index = 0;
		for(JComboBox<String> jc: comboBoxs) 
			if(jc.getName().contains(((ModeSetting)s).name)) index = ((ModeSetting)s).getModeIndex((String) jc.getSelectedItem());
				//System.out.println(jc.getSelectedItem());
		for(Module mod: Client.getModule()) for(Setting set: mod.settings) {
				if(!(set instanceof ModeSetting)) continue;
				if(((ModeSetting)set).name == s.name) ((ModeSetting)set).index = index;
			}
	}
	
	void sliderIsChanged(Setting s, ChangeEvent e) {
		//System.out.println(((JSlider)e.getSource()).getValue());
		double d = ((JSlider)e.getSource()).getValue();
		((NumberSetting)s).setValue(d / 100);
		for(Module m: Client.getModule()) 
			for(Setting set: m.settings) 
				if(set instanceof NumberSetting) 
					if(((NumberSetting)set).name == ((NumberSetting)s).name)
						for(JButton jb: texts) 
							if(jb.getName().contains(((NumberSetting)s).name) && jb.getName().contains("Number")) 
								jb.setText(String.valueOf(((NumberSetting)s).get()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
}
