package com.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.faits.InterfaceFait;

public class Tree extends JPanel implements ActionListener {
  GenealogyTree tree;
  private static String SHOW_ANCESTOR_CMD = "showAncestor";

  public Tree(InterfaceFait root) {
    super(new BorderLayout());

    // Construct the panel with the toggle buttons.
    JRadioButton showDescendant = new JRadioButton("Show descendants", true);
    final JRadioButton showAncestor = new JRadioButton("Show ancestors");
    ButtonGroup bGroup = new ButtonGroup();
    bGroup.add(showDescendant);
    bGroup.add(showAncestor);
    showDescendant.addActionListener(this);
    showAncestor.addActionListener(this);
    showAncestor.setActionCommand(SHOW_ANCESTOR_CMD);
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(showDescendant);
    buttonPanel.add(showAncestor);

    // Construct the tree.
    tree = new GenealogyTree(root);
    JScrollPane scrollPane = new JScrollPane(tree);
    scrollPane.setPreferredSize(new Dimension(200, 200));

    // Add everything to this panel.
    add(buttonPanel, BorderLayout.PAGE_START);
    add(scrollPane, BorderLayout.CENTER);
  }
  
  public void setTreeRoot(InterfaceFait root){
	  tree.setGraphNode(root);
  }

  /**
   * Required by the ActionListener interface. Handle events on the
   * showDescendant and showAncestore buttons.
   */
  public void actionPerformed(ActionEvent ae) {
    if (ae.getActionCommand() == SHOW_ANCESTOR_CMD) {
      tree.showAncestor(true);
    } else {
      tree.showAncestor(false);
    }
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event-dispatching thread.
   */
  private static void createAndShowGUI() {
    // Create and set up the window.
    JFrame frame = new JFrame("Tree");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create and set up the content pane.
    Tree newContentPane = new Tree(null);
    newContentPane.setOpaque(true); // content panes must be opaque
    frame.setContentPane(newContentPane);

    // Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
}


class GenealogyTree extends JTree {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   GenealogyModel model;

  public GenealogyTree(InterfaceFait graphNode) {
    super(new GenealogyModel(graphNode));
    getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    Icon personIcon = null;
    renderer.setLeafIcon(personIcon);
    renderer.setClosedIcon(personIcon);
    renderer.setOpenIcon(personIcon);
    setCellRenderer(renderer);
  }
  
  public void setGraphNode(InterfaceFait root){
	  model.setGraphNode(root);
  }

  /**
   * Get the selected item in the tree, and call showAncestor with this item on
   * the model.
   */
  public void showAncestor(boolean b) {
    Object newRoot = null;
    TreePath path = getSelectionModel().getSelectionPath();
    if (path != null) {
      newRoot = path.getLastPathComponent();
    }
    ((GenealogyModel) getModel()).showAncestor(b, newRoot);
  }
}

class GenealogyModel implements TreeModel {
  private boolean showAncestors;
  private Vector<TreeModelListener> treeModelListeners = new Vector<TreeModelListener>();
  private InterfaceFait rootPerson;

  public GenealogyModel(InterfaceFait root) {
    showAncestors = false;
    rootPerson = root;
  }

  public void setGraphNode(InterfaceFait root) {
	
	this.rootPerson=root;
  }

/**
   * Used to toggle between show ancestors/show descendant and to change the
   * root of the tree.
   */
  public void showAncestor(boolean b, Object newRoot) {
    showAncestors = b;
    InterfaceFait oldRoot = rootPerson;
    if (newRoot != null) {
      rootPerson = (InterfaceFait) newRoot;
    }
    fireTreeStructureChanged(oldRoot);
  }

  // ////////////// Fire events //////////////////////////////////////////////

  /**
   * The only event raised by this model is TreeStructureChanged with the root
   * as path, i.e. the whole tree has changed.
   */
  protected void fireTreeStructureChanged(InterfaceFait oldRoot) {
    int len = treeModelListeners.size();
    TreeModelEvent e = new TreeModelEvent(this, new Object[] { oldRoot });
    for (TreeModelListener tml : treeModelListeners) {
      tml.treeStructureChanged(e);
    }
  }

  // ////////////// TreeModel interface implementation ///////////////////////

  /**
   * Adds a listener for the TreeModelEvent posted after the tree changes.
   */
  public void addTreeModelListener(TreeModelListener l) {
    treeModelListeners.addElement(l);
  }

  /**
   * Returns the child of parent at index index in the parent's child array.
   */
  public Object getChild(Object parent, int index) {
    InterfaceFait p = (InterfaceFait) parent;
    if (showAncestors) {
      if ((index > 0) && (p.getFather() != null)) {
        return p.getFather();
      }
      return p.getFather();
    }
    return p.getChildAt(index);
  }

  /**
   * Returns the number of children of parent.
   */
  public int getChildCount(Object parent) {
    InterfaceFait p = (InterfaceFait) parent;
    if (showAncestors) {
      int count = 0;
      if (p.getFather() != null) {
        count++;
      }
      if (p.getFather() != null) {
        count++;
      }
      return count;
    }
    return p.getChildCount();
  }

  /**
   * Returns the index of child in parent.
   */
  public int getIndexOfChild(Object parent, Object child) {
    InterfaceFait p=(InterfaceFait) parent;
    if (showAncestors) {
      int count = 0;
      InterfaceFait father = (InterfaceFait) p.getFather();
      if (father != null) {
        count++;
        if (father == child) {
          return 0;
        }
      }
      if (p.getFather() != child) {
        return count;
      }
      return -1;
    }
    return p.getIndexOfChild((InterfaceFait) child);
  }

  /**
   * Returns the root of the tree.
   */
  public Object getRoot() {
    return rootPerson;
  }

  /**
   * Returns true if node is a leaf.
   */
  public boolean isLeaf(Object node) {
    InterfaceFait p = (InterfaceFait) node;
    if (showAncestors) {
      return ((p.getFather() == null));
    }
    return p.getChildCount() == 0;
  }

  /**
   * Removes a listener previously added with addTreeModelListener().
   */
  public void removeTreeModelListener(TreeModelListener l) {
    treeModelListeners.removeElement(l);
  }

  /**
   * Messaged when the user has altered the value for the item identified by
   * path to newValue. Not used by this model.
   */
  public void valueForPathChanged(TreePath path, Object newValue) {
    System.out
        .println("*** valueForPathChanged : " + path + " --> " + newValue);
  }
}
