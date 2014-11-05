import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class sqlFomatter {
	static final int btnWidth = 100;
	static final int btnHeight = 30;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = getMyFrame();
		frame.pack();
		frame.setVisible(true);
	}

	public static JFrame getMyFrame() {
		JFrame f = new JFrame("BorderLayout");

		JPanel panel = new JPanel();
		JButton btn = new JButton("写参数");
		btn.setBounds(0, 0, btnWidth, btnHeight);
		JButton btn2 = new JButton("拼接SB");
		btn2.setBounds(0, 0, btnWidth, btnHeight);
		panel.add(btn);
		panel.add(btn2);
		f.add(panel, BorderLayout.NORTH);

		final JTextArea input = new JTextArea(40, 50);
		input.setLineWrap(true);
		JScrollPane inputPane = new JScrollPane(input);
		f.add(inputPane, BorderLayout.WEST);
		final JTextArea output = new JTextArea(40, 50);
		output.setLineWrap(true);
		JScrollPane outputPane = new JScrollPane(output);
		f.add(outputPane, BorderLayout.EAST);
		final JTextField argsField = new JTextField();
		f.add(argsField, BorderLayout.SOUTH);

		btn2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				output.setText(SBFomatter(input));
			}
		});
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				output.setText(argFomatter(input, argsField));
			}
		});

		return f;
	}

	public static String SBFomatter(JTextArea input) {
		StringBuffer sBuffer = new StringBuffer();
		String[] inputString = input.getText().split("\n");
		for (int i = 1; i < inputString.length; i++) {
			sBuffer.append("sb.append(\"" + inputString[i] + " \");\n");
		}
		return sBuffer.toString();
	}

	public static String argFomatter(JTextArea input, JTextField argsField) {
		String inputString = input.getText();
		String[] args = argsField.getText().split(",");
		for (int i = 0; i < args.length; i++) {
			if (inputString.indexOf("?") != -1) {
				inputString = inputString.replaceFirst("\\?", "'" + args[i].substring(args[i].indexOf(":")+1) + "'");
			}
		}
		return inputString;
	}
}
