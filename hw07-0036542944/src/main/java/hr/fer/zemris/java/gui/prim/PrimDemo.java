package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class that represents program for displaying two lists of prime numbers.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class PrimDemo extends JFrame{
	
		private static final long serialVersionUID = 1L;
		
		/**
		 * Default constructor.
		 * @since 1.0.0.
		 */

		public PrimDemo() {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			setSize(500, 300);
			initGUI();
		}
		
		/**
		 * Method for initializing GUI.
		 * @since 1.0.0.
		 */

		private void initGUI() {
			Container cp = getContentPane();
			cp.setLayout(new BorderLayout());

			PrimListModel model = new PrimListModel();
			JList<Integer> list1 = new JList<>(model);
			JList<Integer> list2 = new JList<>(model);

			JPanel listPanel = new JPanel();
			listPanel.setLayout(new GridLayout(0, 1));
			listPanel.add(new JScrollPane(list1));
			listPanel.add(new JScrollPane(list2));

			JButton next = new JButton("sljedeći");
			next.addActionListener((a) -> model.next());

			cp.add(listPanel, BorderLayout.CENTER);
			cp.add(next, BorderLayout.PAGE_END);
		}
		
	/**
	 * Main method.
	 * @param args
	 * @since 1.0.0.
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}

}
