import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class Parameters implements ActionListener, MouseListener{
	private JFrame paramsFrame = new JFrame("Параметри");
	private JPanel levelsPanel = new JPanel();
	private JPanel confirmPanel = new JPanel();
	private GroupLayout groupLayout = new GroupLayout(levelsPanel);
	private GridBagLayout grBagLayout = new GridBagLayout();
	private GridBagConstraints grBagConstraints = new GridBagConstraints();
	private JRadioButton simpleModeButton = new JRadioButton("9x9");
	private JRadioButton mediumModeButton = new JRadioButton("16x16");
	private JRadioButton hardModeButton = new JRadioButton("16x30");
	private JRadioButton customModeButton = new JRadioButton("Своя гра");
	private JLabel heightLabel = new JLabel("Заввишки (9-24)");
	private JLabel widthLabel = new JLabel("Завширшки (9-30)");
	private JLabel bombLabel = new JLabel("Бомби (10-668)");
	private HeightField heightField = new HeightField();
	private WidthField widthField = new WidthField();
	private BombField bombField = new BombField(heightField,widthField);
	private ButtonGroup levelsGroup = new ButtonGroup();
	private JFrame mainFrame;
	private GameMode newlySelectedMode = null;
	private GameMode currentGameMode = null;
	private Game game;
	
	JCheckBox soundButton = new JCheckBox("Звук увімкнено");
	JButton okButton = new JButton("Гаразд");
	
	Parameters(Game game){
		this.game = game;
		currentGameMode = game.getCurrentMode();
		mainFrame = game.getMainFrame();
		paramsFrame.getContentPane().setLayout(new BorderLayout());
		paramsFrame.getContentPane().add(getLevelsPanel(), BorderLayout.NORTH);
		paramsFrame.getContentPane().add(getConfirmPanel());
		addButtonsFunctions();
		addMouseListenerToAll();
		paramsFrame.setUndecorated(true);  
		paramsFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		paramsFrame.setResizable(false);
		paramsFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){		
				paramsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				mainFrame.setEnabled(true);
			}
		});
		paramsFrame.setPreferredSize(new Dimension(300, 300));
		paramsFrame.pack();
		paramsFrame.setLocationRelativeTo(null);
	}
	
	private JPanel getLevelsPanel(){
		levelsGroup.add(simpleModeButton);
		levelsGroup.add(mediumModeButton);
		levelsGroup.add(hardModeButton);
		levelsGroup.add(customModeButton);
		
		levelsPanel.setLayout(groupLayout);
		Border border = BorderFactory.createTitledBorder("Обирай своє");
		levelsPanel.setBorder(border);
		
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		GroupLayout.SequentialGroup leftToRight = groupLayout.createSequentialGroup();
		GroupLayout.ParallelGroup firstClmn = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup secClmn = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup thrdClmn = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		leftToRight.addGroup(firstClmn);
		leftToRight.addGroup(secClmn);
		leftToRight.addGroup(thrdClmn);
		firstClmn.addComponent(simpleModeButton);
		firstClmn.addComponent(mediumModeButton);
		firstClmn.addComponent(hardModeButton);
		secClmn.addComponent(customModeButton);
		GroupLayout.SequentialGroup hLab = groupLayout.createSequentialGroup();
		hLab.addGap(20);
		hLab.addComponent(heightLabel);
		secClmn.addGroup(hLab);
		GroupLayout.SequentialGroup wLab = groupLayout.createSequentialGroup();
		wLab.addGap(20);
		wLab.addComponent(widthLabel);
		secClmn.addGroup(wLab);
		GroupLayout.SequentialGroup bLab = groupLayout.createSequentialGroup();
		bLab.addGap(20);
		bLab.addComponent(bombLabel);
		secClmn.addGroup(bLab);
		thrdClmn.addComponent(heightField);
		thrdClmn.addComponent(widthField);
		thrdClmn.addComponent(bombField);
		
		GroupLayout.SequentialGroup topToBottom = groupLayout.createSequentialGroup();
		GroupLayout.ParallelGroup frstRow = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		GroupLayout.ParallelGroup scndRow = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		GroupLayout.ParallelGroup thrdRow = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		GroupLayout.ParallelGroup frthRow = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);

		topToBottom.addGroup(frstRow);
		topToBottom.addGroup(scndRow);
		topToBottom.addGroup(thrdRow);
		topToBottom.addGroup(frthRow);
		frstRow.addComponent(simpleModeButton);
		frstRow.addComponent(customModeButton);
		scndRow.addComponent(mediumModeButton);
		scndRow.addComponent(heightLabel);
		scndRow.addComponent(heightField);
		
		thrdClmn.addComponent(heightField);
		thrdClmn.addComponent(widthField);
		thrdClmn.addComponent(bombField);

		thrdRow.addComponent(hardModeButton);
		thrdRow.addComponent(widthLabel);
		thrdRow.addComponent(widthField);
		frthRow.addComponent(bombLabel);
		frthRow.addComponent(bombField);
		
		groupLayout.setHorizontalGroup(leftToRight);
		groupLayout.setVerticalGroup(topToBottom);
		groupLayout.linkSize(SwingConstants.VERTICAL, heightField, widthField, bombField);
		groupLayout.linkSize(SwingConstants.VERTICAL, simpleModeButton, mediumModeButton, 
				hardModeButton, customModeButton, heightLabel, widthLabel, bombLabel);
		return levelsPanel;
	}
	
	private void addButtonsFunctions(){
		simpleModeButton.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				switchFieldsAccess();
				newlySelectedMode = new SimpleMode();
			}
		});
		
		mediumModeButton.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				switchFieldsAccess();
				newlySelectedMode = new MediumMode();
			}
		});
		
		hardModeButton.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				switchFieldsAccess();
				newlySelectedMode = new HardMode();
			}
		});
		
		customModeButton.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				switchFieldsAccess();
				newlySelectedMode = new CustomMode();
			}
		});
		
		soundButton.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				game.switchSound();
			}
		});
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				if(paramsWereChanged()){
					if(fieldsAreCorrected()) return;
					if(game.inProgress()){
						ExitMessage message = new ExitMessage(mainFrame);
						if(message.getAnswer() == JOptionPane.YES_OPTION){
							currentGameMode = newlySelectedMode;
							paramsFrame.dispose();
							game.end();
							game.start(newlySelectedMode);
							mainFrame.setEnabled(true);
							newlySelectedMode = null;
						}
					}
					else{
						currentGameMode = newlySelectedMode;
						paramsFrame.dispose();
						game.end();
						game.start(newlySelectedMode);
						mainFrame.setEnabled(true);
						newlySelectedMode = null;
					}
				}
				else{
					paramsFrame.dispose();
					mainFrame.toFront();
					mainFrame.setEnabled(true);
					newlySelectedMode = null;
				}
			}
		});
	}
	
	private JPanel getConfirmPanel(){
		soundButton.setSelected(true);
		confirmPanel.setLayout(grBagLayout);
		grBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		grBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		grBagConstraints.weightx = 0.1;
		grBagConstraints.weighty = 0.1;
		grBagConstraints.gridx = 0;
		grBagConstraints.gridy = 0;
		confirmPanel.add(soundButton, grBagConstraints);
		
		grBagConstraints.fill = GridBagConstraints.NONE;
		grBagConstraints.anchor = GridBagConstraints.CENTER;
		grBagConstraints.gridwidth = 1;
		grBagConstraints.weightx = 0.1;
		grBagConstraints.weighty = 0.1;
		grBagConstraints.gridx = 1;
		grBagConstraints.gridy = 2;
		confirmPanel.add(okButton, grBagConstraints);
		return confirmPanel;
	}
	
	private void setModeSelected(){
		for(int i = 0; i < levelsGroup.getButtonCount(); i++){
			if(currentGameMode instanceof SimpleMode) simpleModeButton.setSelected(true);
			if(currentGameMode instanceof MediumMode) mediumModeButton.setSelected(true);
			if(currentGameMode instanceof HardMode) hardModeButton.setSelected(true);
			if(currentGameMode instanceof CustomMode) customModeButton.setSelected(true);
		}
	}
	
	private void switchFieldsAccess(){
		if (customModeButton.isSelected()){
			widthField.setEnabled(true);
			heightField.setEnabled(true);
			bombField.setEnabled(true);
		}
		else {
			widthField.setEnabled(false);
			heightField.setEnabled(false);
			bombField.setEnabled(false);
		}
	}
	
	private boolean fieldsAreCorrected(){
		return heightField.valueIsCorrected() || widthField.valueIsCorrected() || bombField.valueIsCorrected();
	}
	
	private void addMouseListenerToAll(){
		paramsFrame.getContentPane().addMouseListener(this);
		for(Component component: levelsPanel.getComponents()){
			component.addMouseListener(this);
			}
		
		for(Component component: confirmPanel.getComponents()){
			if(component instanceof JButton){
				continue;
			}
			component.addMouseListener(this);
			}
	}
	
	private boolean paramsWereChanged(){
		if((newlySelectedMode == null && currentGameMode.getModeName().equals("Custom"))
			|| (newlySelectedMode != null && newlySelectedMode.getModeName().equals("Custom"))){
			int rows = Integer.parseInt(heightField.getText());
			int columns = Integer.parseInt(widthField.getText());
			int bombs = Integer.parseInt(bombField.getText());
			if(newlySelectedMode == null) newlySelectedMode = new CustomMode();
			((CustomMode) newlySelectedMode).setModeParams(rows, columns, bombs);
			if(currentGameMode.getColumns() == newlySelectedMode.getColumns() &&
					currentGameMode.getRows() == newlySelectedMode.getRows() &&
					currentGameMode.getBombQuantity() == newlySelectedMode.getBombQuantity()){
				return false;
			}
			return true;
		}
		else if(newlySelectedMode == null || 
				currentGameMode.getModeName().equals(newlySelectedMode.getModeName())){
			return false;
		}
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		mainFrame.setEnabled(false);
		setModeSelected();
		switchFieldsAccess();
		paramsFrame.setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if(customModeButton.isSelected()){
			fieldsAreCorrected();
		}
	}

	@Override
	public void mouseEntered(MouseEvent me) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
