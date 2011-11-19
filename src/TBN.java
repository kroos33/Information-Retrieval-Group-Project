import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTree;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JComponent;
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


public class TBN {

	private JFrame frmJhuIrThesaurus;
	private InvertedIndex i;
	private JTree tree;
	private JComponent panel1;
	private JComponent panel2;
	private JPanel panel;
	private JTextField textField;
	private JSeparator separator_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	public DefaultMutableTreeNode top;
	public DefaultTreeModel treeModel;
	public DefaultMutableTreeNode selected;

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
		frmJhuIrThesaurus.setTitle("JHU IR Thesaurus");
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
		
		panel = new JPanel();
		frmJhuIrThesaurus.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
		
		panel1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel1.getLayout();
		flowLayout.setVgap(20);
		flowLayout.setHgap(2);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		JTextArea a = new JTextArea("Add/Edit/Delete Term:");
		panel1.add(a);
		
		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panel1.add(separator_1);
		
		textField = new JTextField();
		panel1.add(textField);
		textField.setColumns(10);
		
		
		panel2 = new JPanel();
		JTextArea b = new JTextArea("Add/Edit/Delete Term Relationship");
		panel2.add(b);
	
		panel1.setVisible(true);
		panel2.setVisible(false);
		
		panel.add(panel1, "TERM");
		
		btnNewButton_1 = new JButton("Save");
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selected.getUserObject().toString().equals("Add New Term"))
				{
					i.addConcept(new Concept(textField.getText()));
				}
				else
				{
					i.updateConcept(new Concept(selected.getUserObject().toString()), new Concept(textField.getText()));
				}
				RenderTree();
			}
		});
		
		btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				i.removeConcept(new Concept(textField.getText()));
				RenderTree();
				
			}
		});
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel1.add(btnNewButton);
		panel.add(panel2, "RELATIONSHIP");
		
		
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
