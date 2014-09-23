package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TitleCreditsPanel extends JPanel implements ActionListener{
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	
	private JLabel textLabel = new JLabel("Enter text");
	private JTextArea textChooser = new JTextArea();
	private JLabel fontLabel = new JLabel("Font specifications");
	private JComboBox<String> sizeChooser = new JComboBox<String>(new String[]{"7","8","9","10","11","12","13","14","15"});
	private JComboBox<String> fontChooser = new JComboBox<String>(new String[]{"Arial","Comic Sans","Lexia","Ultra Violet"});
	private JLabel backgroundLabel = new JLabel("Background Color");
	private JComboBox<String> fontColorChooser = new JComboBox<String>(new String[]{"Black","White","Blue","Red","Green"});
	private JComboBox<String> backgroundColorChooser = new JComboBox<String>(new String[]{"Black","White","Blue","Red","Green"});
	private JComboBox<String> textSideChooser = new JComboBox<String>(new String[]{"Front","Back"});
	private JLabel positionLabel = new JLabel("Choose a position");
	private JButton positionChooser = new JButton("...");
	private JTextField positionX = new JTextField(4);
	private JTextField positionY = new JTextField(4);
	private JLabel timeLabel = new JLabel("Insert Time (Seconds)");
	private JTextField timeTextField = new JTextField(10);
	private JButton addTextButton = new JButton("Add to Project");
	
	private String chosenText;
	private int chosenSize;
	private String chosenFont;
	private String chosenX;
	private String chosenY;
	private String chosenColor;
	private String chosenBackgroundColor;
	private String chosenTime;
	private String chosenEnding;	
	
	public TitleCreditsPanel(){
			setLayout(new BorderLayout());
			add(leftPanel,BorderLayout.LINE_START);
//			rightPanel.setBackground(Color.red);
			add(rightPanel,BorderLayout.CENTER);
			textChooser.setPreferredSize(new Dimension(300,100));
			textChooser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			textChooser.setLineWrap(true);
			
			leftPanel.setLayout(new GridBagLayout());
			leftPanel.setPreferredSize(new Dimension(350,1));
			rightPanel.setLayout(new GridBagLayout());
			
		    GridBagConstraints eC = new GridBagConstraints();
//		    eC.weighty = 1;
		    eC.weightx = 1;
		    eC.gridx = 0;
		    eC.gridy = 0;
		    leftPanel.add(textLabel,eC);
		    eC.gridy = 1;
		    leftPanel.add(textChooser,eC);
		    eC.gridy = 0;
		    rightPanel.add(fontLabel,eC);
		    eC.gridy = 1;
		    
		    JPanel fontPanel = new JPanel();
		    sizeChooser.addActionListener(this);
		    fontPanel.add(sizeChooser);
		    fontColorChooser.addActionListener(this);
		    fontPanel.add(fontColorChooser);
		    fontChooser.addActionListener(this);
		    fontPanel.add(fontChooser);
		    rightPanel.add(fontPanel,eC);
		    eC.gridy = 2;
		    rightPanel.add(backgroundLabel,eC);
		    eC.gridy = 3;
		    rightPanel.add(backgroundColorChooser,eC);
		    eC.gridy = 4;
		    rightPanel.add(positionLabel,eC);
		    eC.gridy = 5;
		    JPanel positionPanel = new JPanel();
		    positionChooser.setPreferredSize(new Dimension(30,22));
		    positionPanel.add(positionChooser);
			positionX.setPreferredSize(new Dimension(50,22));
			positionPanel.add(positionX);
			positionY.setPreferredSize(new Dimension(50,22));
			positionPanel.add(positionY);
		    textSideChooser.setPreferredSize(new Dimension(80,22));
		    positionPanel.add(textSideChooser);
		    rightPanel.add(positionPanel,eC);
		    eC.gridy = 6;
		    rightPanel.add(timeLabel,eC);
		    eC.gridy = 7;
		    rightPanel.add(timeTextField,eC);
		    eC.gridy = 8;
		    addTextButton.addActionListener(this);
		    rightPanel.add(addTextButton,eC);
	}
	public JTextArea getTextChooser() {
		return textChooser;
	}
	public void setTextChooser(JTextArea textChooser) {
		this.textChooser = textChooser;
	}
	public JComboBox<String> getSizeChooser() {
		return sizeChooser;
	}
	public void setSizeChooser(JComboBox<String> sizeChooser) {
		this.sizeChooser = sizeChooser;
	}
	public JComboBox<String> getFontChooser() {
		return fontChooser;
	}
	public void setFontChooser(JComboBox<String> fontChooser) {
		this.fontChooser = fontChooser;
	}
	public JComboBox<String> getFontColorChooser() {
		return fontColorChooser;
	}
	public void setFontColorChooser(JComboBox<String> fontColorChooser) {
		this.fontColorChooser = fontColorChooser;
	}
	public JComboBox<String> getBackgroundColorChooser() {
		return backgroundColorChooser;
	}
	public void setBackgroundColorChooser(JComboBox<String> backgroundColorChooser) {
		this.backgroundColorChooser = backgroundColorChooser;
	}
	public JComboBox<String> getTextSideChooser() {
		return textSideChooser;
	}
	public void setTextSideChooser(JComboBox<String> textSideChooser) {
		this.textSideChooser = textSideChooser;
	}
	public JButton getPositionChooser() {
		return positionChooser;
	}
	public void setPositionChooser(JButton positionChooser) {
		this.positionChooser = positionChooser;
	}
	public JTextField getTimeTextField() {
		return timeTextField;
	}
	public void setTimeTextField(JTextField timeTextField) {
		this.timeTextField = timeTextField;
	}
	public JButton getAddTextButton() {
		return addTextButton;
	}
	public void setAddTextButton(JButton addTextButton) {
		this.addTextButton = addTextButton;
	}
	public JButton getPositionButton(){
		return this.positionChooser;
	}
	public JTextField getPositionTextFieldX(){
		return this.positionX;
	}
	public JTextField getPositionTextFieldY(){
		return this.positionY;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==addTextButton){
			
		}else if(e.getSource()==fontChooser||e.getSource()==sizeChooser){
			chosenFont = this.fontChooser.getSelectedItem().toString();
			chosenSize = Integer.parseInt(this.sizeChooser.getSelectedItem().toString());
			try {
				FileInputStream instanceStream = new FileInputStream(new File("./"+chosenFont+".ttf"));
				textChooser.setFont(Font.createFont(Font.TRUETYPE_FONT, instanceStream).deriveFont(Font.PLAIN, chosenSize+10));
				instanceStream.close();
			} catch (FontFormatException | IOException exp) {
				exp.printStackTrace();
			}
		}
	}
		
}
