package tfw.demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import tfw.awt.ecd.ColorECD;
import tfw.component.TriggeredEventChannelCopy;
import tfw.swing.JButtonBB;
import tfw.swing.JPanelBB;
import tfw.swing.JTextFieldModifiableBB;
import tfw.tsm.AWTTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.StringRollbackECD;
import tfw.value.ValueException;

public class TextFieldDemo extends JPanelBB {
	//Define the event channel name space.
	private final static StringECD RED_STRING = new StringECD("redString");

	private final static StringECD RED_STRING_ADJ = new StringECD(
			"redStringAdj");

	private final static RedGreenBlueECD RED_INTEGER = new RedGreenBlueECD(
			"redInteger");

	private final static StringECD GREEN_STRING = new StringECD("greenString");

	private final static StringECD GREEN_STRING_ADJ = new StringECD(
			"greenStringAdj");

	private final static RedGreenBlueECD GREEN_INTEGER = new RedGreenBlueECD(
			"greenInteger");

	private final static StringECD BLUE_STRING = new StringECD("blueString");

	private final static StringECD BLUE_STRING_ADJ = new StringECD(
			"blueStringAdj");

	private final static RedGreenBlueECD BLUE_INTEGER = new RedGreenBlueECD(
			"blueInteger");

	private final static StatelessTriggerECD APPLY_TRIGGER = new StatelessTriggerECD(
			"applyTrigger");

	private final static BooleanECD APPLY_ENABLE = new BooleanECD("applyEnable");

	private final static StatelessTriggerECD RESET_TRIGGER = new StatelessTriggerECD(
			"resetTrigger");

	private final static BooleanECD RESET_ENABLE = new BooleanECD("resetEnable");

	private final static BooleanECD COLOR_BUTTON_ENABLE_NAME = new BooleanECD(
			"colorButtonEnable");

	private final static ColorECD COLOR_NAME = new ColorECD("color");

	private final static StringRollbackECD ERROR_NAME = new StringRollbackECD(
			"error");

	public TextFieldDemo() {
		super(createBranch());
		setLayout(new BorderLayout());
		addToBoth(createColorPanel(), BorderLayout.EAST);
	}

	private JPanelBB createColorPanel() {
		JPanelBB labelPanel = new JPanelBB("LabelPanel");
		labelPanel.setLayout(new GridLayout(3, 1));
		labelPanel.add(new JLabel("Red: ", JLabel.RIGHT));
		labelPanel.add(new JLabel("Green: ", JLabel.RIGHT));
		labelPanel.add(new JLabel("Blue: ", JLabel.RIGHT));

		JPanelBB textFieldPanel = new JPanelBB("TextFieldPanel");
		textFieldPanel.setLayout(new GridLayout(3, 1));
		textFieldPanel.addToBoth(createTextField("RedTextField", RED_STRING,
				RED_STRING_ADJ, RED_INTEGER));
		textFieldPanel.addToBoth(createTextField("GreenTextField",
				GREEN_STRING, GREEN_STRING_ADJ, GREEN_INTEGER));
		textFieldPanel.addToBoth(createTextField("BlueTextField", BLUE_STRING,
				BLUE_STRING_ADJ, BLUE_INTEGER));

		JPanelBB colorButtonPanel = new JPanelBB("ColorButtonPanel");
		colorButtonPanel.setLayout(new FlowLayout());
		colorButtonPanel.addToBoth(new ColorButtonNB("TextFieldDemo",
				COLOR_NAME, COLOR_BUTTON_ENABLE_NAME, "Color Chooser",
				colorButtonPanel));

		JPanelBB northPanel = new JPanelBB("NorthPanel");
		northPanel.setLayout(new BorderLayout());
		northPanel.addToBoth(labelPanel, BorderLayout.WEST);
		northPanel.addToBoth(textFieldPanel, BorderLayout.CENTER);
		northPanel.addToBoth(colorButtonPanel, BorderLayout.SOUTH);

		EventChannelDescription[] colorText = new EventChannelDescription[] {
				RED_STRING, BLUE_STRING, GREEN_STRING };
		EventChannelDescription[] colorTextAdj = new EventChannelDescription[] {
				RED_STRING_ADJ, BLUE_STRING_ADJ, GREEN_STRING_ADJ };
		JButtonBB applyButton = new JButtonBB("Apply", APPLY_ENABLE,
				APPLY_TRIGGER);
		applyButton.setText("Apply");
		applyButton.getBranch().add(
				new ButtonEnableHandler("Apply", colorText, colorTextAdj,
						applyButton));

		JButtonBB resetButton = new JButtonBB("Reset", RESET_ENABLE,
				RESET_TRIGGER);
		resetButton.setText("Reset");
		resetButton.getBranch().add(
				new ButtonEnableHandler("ResetButton", colorText, colorTextAdj,
						resetButton));

		JPanelBB buttonPanel = new JPanelBB("ButtonPanel");
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.addToBoth(applyButton);
		buttonPanel.addToBoth(resetButton);

		JPanelBB colorPanel = new JPanelBB("ColorPanel");
		colorPanel.setLayout(new BorderLayout());
		colorPanel.addToBoth(northPanel, BorderLayout.NORTH);
		colorPanel.addToBoth(buttonPanel, BorderLayout.SOUTH);
		colorPanel.getBranch().add(new ErrorDialog(colorPanel, ERROR_NAME));
		colorPanel.getBranch().add(
				new IntegerColorConverter("ColorDemo", RED_INTEGER,
						GREEN_INTEGER, BLUE_INTEGER, COLOR_NAME));

		return colorPanel;
	}

	private JTextFieldModifiableBB createTextField(String name, StringECD text,
			StringECD textAdj, IntegerECD integer) {
		JTextFieldModifiableBB textField = new JTextFieldModifiableBB(name,
				text, textAdj, COLOR_BUTTON_ENABLE_NAME, APPLY_TRIGGER);
		textField.getBranch().add(
				new TriggeredEventChannelCopy("Apply[" + name + "]",
						APPLY_TRIGGER, textAdj, text));
		textField.getBranch().add(
				new TriggeredEventChannelCopy("Reset[" + name + "]",
						RESET_TRIGGER, text, textAdj));
		textField.getBranch().add(
				new IntegerStringConverter(name, text, integer, ERROR_NAME));

		return textField;
	}

	private static Branch createBranch() {
		RootFactory rf = new RootFactory();

		try {
			rf.addTerminator(RED_STRING, "0");
			rf.addTerminator(RED_STRING_ADJ, "0");
			rf.addTerminator(RED_INTEGER);

			rf.addTerminator(GREEN_STRING, "1");
			rf.addTerminator(GREEN_STRING_ADJ);
			rf.addTerminator(GREEN_INTEGER);

			rf.addTerminator(BLUE_STRING, "2");
			rf.addTerminator(BLUE_STRING_ADJ, "2");
			rf.addTerminator(BLUE_INTEGER);

			rf.addTerminator(APPLY_TRIGGER);
			rf.addTerminator(APPLY_ENABLE);
			rf.addTerminator(COLOR_BUTTON_ENABLE_NAME, Boolean.TRUE);
			rf.addTerminator(ERROR_NAME);
			rf.addTerminator(COLOR_NAME);
			rf.addTerminator(RESET_TRIGGER);
			rf.addTerminator(RESET_ENABLE);
			//rf.setLogging(true);
		} catch (ValueException e) {
			// This exception should not happen. So we convert it
			// to a runtime exception so the calling code does not
			// have to deal with it.
			throw new RuntimeException("Unexpected ValueException: "
					+ e.getMessage());
		}

		return (rf.create("TextFieldDemo", new AWTTransactionQueue()));
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				final JFrame frame = new JFrame("TextFieldDemo");
				TextFieldDemo tfd = new TextFieldDemo();
				frame.getContentPane().add(tfd, BorderLayout.CENTER);

				//frame.setSize(200, 200);
				frame.pack();
				frame.setVisible(true);
				//co2.ui.fw.visualize.Visualize.print(tfd.getBranch());
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						frame.dispose();
						System.exit(0);
					}
				});
			}
		});
	}
}
