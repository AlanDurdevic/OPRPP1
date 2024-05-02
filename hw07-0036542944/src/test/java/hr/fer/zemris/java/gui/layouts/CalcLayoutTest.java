package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class CalcLayoutTest {
	
	private CalcLayout layout = new CalcLayout();
	
	@Test
	public void testAddLayoutComponentMethod1() {
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(),"0, 5"));
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(),"6, 5"));
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(),"2, 0"));
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(),"2, 8"));
	}
	
	@Test
	public void testAddLayoutComponentMethod2() {
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(),"1, 5"));
	}
	
	@Test
	public void testAddLayoutComponentMethod3() {
		layout.addLayoutComponent(new JLabel(),"2, 5");
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(),"2, 5"));
	}
	
	@Test
	public void testSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.getWidth());
		assertEquals(158, dim.getHeight());
	}
	
	@Test
	public void testSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.getWidth());
		assertEquals(158, dim.getHeight());
	}

}
