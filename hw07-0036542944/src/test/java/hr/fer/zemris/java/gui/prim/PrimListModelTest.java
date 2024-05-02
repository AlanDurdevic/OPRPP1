package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrimListModelTest {
	
	private PrimListModel model;
	
	@BeforeEach
	public void setUp() {
		model = new PrimListModel();
	}
	
	@Test
	public void testStartingValuesInModel() {
		assertEquals(1, model.getSize());
		assertEquals(1, model.getElementAt(0));
	}
	
	@Test
	public void testExample() {
		model.next();
		model.next();
		model.next();
		assertEquals(4, model.getSize());
		assertEquals(1, model.getElementAt(0));
		assertEquals(2, model.getElementAt(1));
		assertEquals(3, model.getElementAt(2));
		assertEquals(5, model.getElementAt(3));
	}

}
