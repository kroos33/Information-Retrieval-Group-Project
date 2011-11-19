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
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.CardLayout;


public class TBN {

	private JFrame frmJhuIrThesaurus;
	private InvertedIndex i;
	private JTree tree;
	private JComponent panel1;
	private JComponent panel2;
	private JPanel panel;

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

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Thesaurus Content");
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
		tree = new JTree(top);
		tree.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tree.setVisibleRowCount(50);
		
		
		frmJhuIrThesaurus.getContentPane().add(tree, BorderLayout.WEST);
		
		panel = new JPanel();
		frmJhuIrThesaurus.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
		
		panel1 = new JPanel();
		JTextArea a = new JTextArea("Add/Edit/Delete Term");
		panel1.add(a);
		
		
		panel2 = new JPanel();
		JTextArea b = new JTextArea("Add/Edit/Delete Term Relationship");
		panel2.add(b);
	
		panel1.setVisible(true);
		panel2.setVisible(false);
		
		panel.add(panel1, "TERM");
		panel.add(panel2, "RELATIONSHIP");
		
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		                           tree.getLastSelectedPathComponent();

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
	


}
