import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTree;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.CardLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JComboBox;



public class TBN {

	public JFrame frmJhuIrThesaurus;
	private InvertedIndex i;
	private JTree tree;
	private JComponent panel1;
	private JComponent panel2;
	private JComponent panel3;
	private JPanel panel;
	private JTextField textField;
	private JSeparator separator_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	public DefaultMutableTreeNode top;
	public DefaultTreeModel treeModel;
	public DefaultMutableTreeNode selected;
	private JTextField textField_1;
	private Component horizontalGlue;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JButton btnNewButton_2;
	private JButton button;
	private JComboBox comboBox_1;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TBN window = new TBN();
					
					window.frmJhuIrThesaurus.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TBN() {
		initialize();
	}
	
	public void RenderTree()
	{
		top.removeAllChildren();
		java.util.Hashtable<String, HashSet<InvertedIndexItem>> table = i.getIndex();
		if(table != null)
		{
			java.util.Set  entry = null;
			Enumeration entries = table.keys();
			String temp_element = null;
			while(entries.hasMoreElements())
			{
				temp_element = (String)entries.nextElement();
				DefaultMutableTreeNode temp = new DefaultMutableTreeNode(temp_element);
				top.add(temp);
				entry = (java.util.Set)table.get(temp_element);
				Iterator i = entry.iterator();
				while(i.hasNext())
				{
					DefaultMutableTreeNode relationsihp = new DefaultMutableTreeNode(i.next());
					temp.add(relationsihp);
				}
				DefaultMutableTreeNode newrelationship = new DefaultMutableTreeNode("Add New Relationship");
				temp.add(newrelationship);
			}
			
		}
		DefaultMutableTreeNode newterm = new DefaultMutableTreeNode("Add New Term");
		top.add(newterm);
		treeModel.reload();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJhuIrThesaurus = new JFrame();
		frmJhuIrThesaurus.setTitle("JHU Information Retrieval Thesaurus");
		frmJhuIrThesaurus.setBounds(100, 100, 450, 300);
		frmJhuIrThesaurus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		i = new InvertedIndex();
		try {
			i.loadIndexFromFile("mytest");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		top = new DefaultMutableTreeNode("Thesaurus Content");
		java.util.Hashtable<String, HashSet<InvertedIndexItem>> table = i.getIndex();
		if(table != null)
		{
			java.util.Set  entry = null;
			Enumeration entries = table.keys();
			String temp_element = null;
			while(entries.hasMoreElements())
			{
				temp_element = (String)entries.nextElement();
				DefaultMutableTreeNode temp = new DefaultMutableTreeNode(temp_element);
				top.add(temp);
				entry = (java.util.Set)table.get(temp_element);
				Iterator i = entry.iterator();
				while(i.hasNext())
				{
					DefaultMutableTreeNode relationsihp = new DefaultMutableTreeNode(i.next());
					temp.add(relationsihp);
				}
				DefaultMutableTreeNode newrelationship = new DefaultMutableTreeNode("Add New Relationship");
				temp.add(newrelationship);
			}
			
		}
		

		
		DefaultMutableTreeNode newterm = new DefaultMutableTreeNode("Add New Term");
		top.add(newterm);
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		frmJhuIrThesaurus.getContentPane().add(separator, BorderLayout.NORTH);
		
		treeModel = new DefaultTreeModel(top);
		treeModel.addTreeModelListener(new MyTreeModelListener());

		tree = new JTree(treeModel);
		tree.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tree.setVisibleRowCount(50);
		
		
		frmJhuIrThesaurus.getContentPane().add(tree, BorderLayout.WEST);
		frmJhuIrThesaurus.setSize(400, 500);
		
		panel = new JPanel();
		frmJhuIrThesaurus.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
		
		
		
		panel1 = new JPanel();
		panel1.setLayout(null);
		JTextArea a = new JTextArea("Add/Edit/Delete Term:");
		a.setBounds(61, 12, 143, 16);
		a.setBackground(SystemColor.window);
		panel1.add(a);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(147, 28, 12, 0);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panel1.add(separator_1);
		
		textField = new JTextField();
		textField.setBounds(2, 56, 261, 28);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(textField);
		textField.setColumns(25);
		
		
		panel2 = new JPanel();
		panel2.setLayout(null);
		JTextArea b = new JTextArea("Add/Edit/Delete Term Relationship");
		b.setBackground(SystemColor.window);
		b.setBounds(26, 18, 221, 16);
		panel2.add(b);
		

		
	
		panel1.setVisible(false);
		panel2.setVisible(true);
		
		panel.add(panel1, "TERM");
		
		btnNewButton_1 = new JButton("Save");
		btnNewButton_1.setBounds(12, 102, 75, 29);
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selected == null || selected.getUserObject().toString().equals("Add New Term") || selected.getUserObject().toString().equals("Thesaurus Content") )
				{
					i.addConcept(new Concept(textField.getText()));
				}
				else
				{
					if(i.isNewConcept(new Concept(textField.getText().trim())))
					{
						i.updateConcept(new Concept(selected.getUserObject().toString()), new Concept(textField.getText()));
					}
					else
					{
						JOptionPane.showMessageDialog(frmJhuIrThesaurus,"Error - A different concept with the same name already exists.");
					}
				}
				textField.setText("");
				panel1.setVisible(false);
				panel2.setVisible(false);
				RenderTree();
			}
		});
		
		btnNewButton = new JButton("Delete");
		btnNewButton.setBounds(135, 104, 84, 29);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText() != null && textField.getText().trim().length() > 0)
				{
					i.removeConcept(new Concept(textField.getText()));
				}
				RenderTree();
				panel1.setVisible(false);
				panel2.setVisible(false);
				
			}
		});
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(btnNewButton);
		panel.add(panel2, "RELATIONSHIP");
		
		textField_1 = new JTextField();
		textField_1.setBounds(62, 46, 134, 28);
		panel2.add(textField_1);
		textField_1.setColumns(10);
		
		lblNewLabel = new JLabel("is related to");
		lblNewLabel.setBounds(87, 97, 75, 16);
		panel2.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setBounds(62, 139, 134, 27);
		panel2.add(comboBox);
		
		horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(163, 72, 0, 0);
		panel2.add(horizontalGlue);
		
		JLabel lblTheRelatioshipType = new JLabel("The relatioship type is:");
		lblTheRelatioshipType.setBounds(43, 209, 190, 16);
		panel2.add(lblTheRelatioshipType);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(62, 247, 134, 27);
		panel2.add(comboBox_1);
		
		btnNewButton_2 = new JButton("Save");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(selected != null && selected.getUserObject().toString().equals("Add New Relationship"))
	        	{
					Relationship r;
					String type = comboBox_1.getSelectedItem().toString();
					if(type.equals("RELATED"))
					{
						r = Relationship.RELATED;
					}
					else if(type.equals("NARROW"))
					{
						r = Relationship.NARROW;
					}
					else
					{
						r = Relationship.BROAD;
					}
					Concept c2 = new Concept(comboBox.getSelectedItem().toString());
					InvertedIndexItem rel = new InvertedIndexItem(c2, r);
	        		i.addItem(new Concept(selected.getParent().toString()), rel);
	        	}
	        	else if(selected != null)
	        	{
	        		Concept c = new Concept(selected.getParent().toString());
	        		InvertedIndexList ilist = i.getConceptList(c);
	        		Concept c2 = new Concept(comboBox.getSelectedItem().toString());
	        		InvertedIndexItem any = new InvertedIndexItem(c2, Relationship.ANY);
	        		ilist.remove(any);
	        		System.out.println("Removed " + c2.getConcept());
	        		any = null;
	        		Relationship r;
					String type = comboBox_1.getSelectedItem().toString();
					if(type.equals("RELATED"))
					{
						r = Relationship.RELATED;
					}
					else if(type.equals("NARROW"))
					{
						r = Relationship.NARROW;
					}
					else
					{
						r = Relationship.BROAD;
					}
	        		InvertedIndexItem newItem = new InvertedIndexItem(c2, r);
	        		ilist.add(newItem);
	        		//ilist
	        		
	        	}
				RenderTree();
				panel1.setVisible(false);
				panel2.setVisible(false);
	        	
				
			}
		});
		btnNewButton_2.setBounds(6, 309, 117, 29);
		panel2.add(btnNewButton_2);
		
		button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if(selected != null && selected.getUserObject().toString().equals("Add New Relationship"))
	        	{
					//Should never happen, just do nothing if it does.
	        	}
	        	else if(selected != null)
	        	{
	        		Concept c = new Concept(selected.getParent().toString());
	        		InvertedIndexList ilist = i.getConceptList(c);
	        		Concept c2 = new Concept(comboBox.getSelectedItem().toString());
	        		InvertedIndexItem any = new InvertedIndexItem(c2, Relationship.ANY);
	        		ilist.remove(any);
	        		System.out.println("Removed " + c2.getConcept());
	        		any = null;
	        	}
				RenderTree();
				panel1.setVisible(false);
				panel2.setVisible(false);
	        	
				
			}
				
			
		});
		button.setBounds(130, 309, 117, 29);
		panel2.add(button);
		
		//panel.add(panel1, "TERM");
		
		
		
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		                           tree.getLastSelectedPathComponent();
		        selected = node;

		    /* if nothing is selected */ 
		        if (node == null) return;

		    /* retrieve the node that was selected */ 
		        Object nodeInfo = node.getUserObject();
		        DefaultMutableTreeNode n = node;
		        int depth = n.getLevel();
		        System.out.println(depth);
		        if(depth == 1)
		        {
		        	CardLayout c1 = (CardLayout)panel.getLayout();
		        	c1.show(panel, "TERM");
		        	if(n.getUserObject().equals("Add New Term"))
		        	{
		        		textField.setText("");
		        	}
		        	else
		        	{
		        		textField.setText(n.getUserObject().toString());
		        	}
		        	
		        }
		        else if(depth == 2)
		        {
		        	CardLayout c1 = (CardLayout)panel.getLayout();
		        	c1.show(panel, "RELATIONSHIP");
		        	Hashtable<String, HashSet<InvertedIndexItem>> t = i.getIndex();
		        	Iterator<String> key_iterator = t.keySet().iterator();
		        	comboBox.removeAllItems();
		        	while(key_iterator.hasNext())
		        	{
		        		comboBox.addItem(key_iterator.next());
		        	}
		        	comboBox_1.setEditable(false);
		        	comboBox_1.removeAllItems();
		        	comboBox_1.addItem("BROAD");
		        	comboBox_1.addItem("NARROW");
		        	comboBox_1.addItem("RELATED");
		        	textField_1.setEditable(false);
		        	if(nodeInfo.toString().equals("Add New Relationship"))
		        	{
		        		textField_1.setText(n.getParent().toString());
		        	}
		        	else
		        	{
		        		StringTokenizer st = new StringTokenizer(nodeInfo.toString(), "[]., ", false);
		        		comboBox.setSelectedItem(st.nextToken());
		        		comboBox_1.setSelectedItem(st.nextToken());
		        		textField_1.setText(n.getParent().toString());
		        		st = null;
		        	}
		        	
		        }
		        
		 
		        
		   
		    /* React to the node selection. */
		    }
		});
		


		


		
		//frmJhuIrThesaurus.getContentPane().add(panel2, BorderLayout.CENTER);
	}
	
	
	class MyTreeModelListener implements TreeModelListener {
	    public void treeNodesChanged(TreeModelEvent e) {
	        DefaultMutableTreeNode node;
	        node = (DefaultMutableTreeNode)
	                 (e.getTreePath().getLastPathComponent());

	        /*
	         * If the event lists children, then the changed
	         * node is the child of the node we have already
	         * gotten.  Otherwise, the changed node and the
	         * specified node are the same.
	         */
	        try {
	            int index = e.getChildIndices()[0];
	            node = (DefaultMutableTreeNode)
	                   (node.getChildAt(index));
	        } catch (NullPointerException exc) {}

	        System.out.println("The user has finished editing the node.");
	        System.out.println("New value: " + node.getUserObject());
	    }
	    public void treeNodesInserted(TreeModelEvent e) {
	    }
	    public void treeNodesRemoved(TreeModelEvent e) {
	    }
	    public void treeStructureChanged(TreeModelEvent e) {
	    }
	}
}
