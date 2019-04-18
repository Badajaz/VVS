package lineAndBranch;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import sut.ArrayNTree;
public class ToListTest {

	
	@Test
	public void testBigList() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		assertTrue(lista.equals(tree.toList()));
		
	}
	
	@Test
	public void tesEmptyList() {
		List<Integer> lista = new ArrayList <> ();
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		assertTrue(lista.equals(tree.toList()));
		
	}
	
	
	
	
	
}
