package irgroupproject.qp;

import irgroupproject.qp.client.RmiClient;
import irgroupproject.qp.client.RmiClientException;
import irgroupproject.qp.token.TokenList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class QueryParserGui extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Query Parser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		QueryParserGui newContentPane = new QueryParserGui();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private JButton queryButton;
	private JButton submitButton;
	private JTextPane messageBox;
	private JTextField queryInput;
	private JTextField queryOutput;
	private JLabel jLabel1, jLabel2;

	private QueryGenerator generator = null;
	private QueryParser parser = null;
	private GoogleQueryLinkGenerator linkGenerator = null;
	private Desktop desktop = Desktop.getDesktop();

	public QueryParserGui() {
		init();

		JPanel topPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		topPanel.setLayout(flowLayout);
		topPanel.add(jLabel1);
		topPanel.add(queryInput);
		topPanel.add(queryButton);

		JPanel bottonPanel = new JPanel();
		FlowLayout flowLayout2 = new FlowLayout();
		bottonPanel.setLayout(flowLayout2);
		bottonPanel.add(jLabel2);
		bottonPanel.add(queryOutput);
		bottonPanel.add(submitButton);

		BorderLayout layout = new BorderLayout();
		setLayout(layout);

		add(topPanel, BorderLayout.NORTH);
		add(messageBox, BorderLayout.CENTER);
		add(bottonPanel, BorderLayout.SOUTH);
	}

	private void init() {
		jLabel1 = new JLabel();
		jLabel1.setText("Enter query:");
		queryInput = new JTextField();
		queryInput.setColumns(50);
		queryButton = new JButton("Expand");
		queryButton.addActionListener(this);
		
		
		

		messageBox = new JTextPane();
		StyledDocument doc = messageBox.getStyledDocument();
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setAlignment(attrSet, StyleConstants.ALIGN_CENTER);
		StyleConstants.setForeground(attrSet, Color.RED);
		doc.setParagraphAttributes(0, doc.getLength(), attrSet, false);

		messageBox.setEditable(false);
		messageBox.setSize(100, 50);

		jLabel2 = new JLabel();
		jLabel2.setText("Result:");
		queryOutput = new JTextField();
		queryOutput.setColumns(50);
		queryOutput.setEditable(true);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);

		parser = new QueryParser();
		linkGenerator = new GoogleQueryLinkGenerator();
	}

	private QueryGenerator getQueryGenerator() {
		if (generator == null) {
			TokenExpander expander;
			try {
				expander = new TokenExpander(new RmiClient());
				generator = new QueryGenerator(expander);
			} catch (RmiClientException e) {
				setErrorMessage(e.getMessage());
			}
		}
		return generator;
	}

	private void clearErrorMessage() {
		setErrorMessage(null);
	}

	private void setErrorMessage(String message) {
		messageBox.setText(message);
	}

	private void setQueryOutput(String output) {
		queryOutput.setText(output);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.println(event.getActionCommand());
		if ("Expand".equals(event.getActionCommand())) {
			clearErrorMessage();
			TokenList tokens;
			java.net.URI uri;
			try {
				tokens = parser.parse(queryInput.getText());
				String query = getQueryGenerator().generate(tokens);
				String link = linkGenerator.generateLink(query);
				setQueryOutput(query);
				submitButton.setEnabled(true);
			} catch (QueryParserException e) {
				setErrorMessage(e.getMessage());
				submitButton.setEnabled(false);
			}
		}
		else if ("Submit".equals(event.getActionCommand())) {
			clearErrorMessage();
			TokenList tokens;
			java.net.URI uri;
			try {
				String query = queryOutput.getText();
				String link = linkGenerator.generateLink(query);
				uri = new java.net.URI(link);
				desktop.browse(uri);
			} catch (URISyntaxException e) {
				setErrorMessage(e.getMessage());
			} catch (IOException e) {
				setErrorMessage(e.getMessage());
			}
		}
	}
}
