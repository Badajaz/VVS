package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class SizeTest {

	@Test
	public void testNonEmptyTree () {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		int result = tree.size();
		assertEquals(result,5,"tamanho errado");
		
	}
	
	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		int result = tree.size();
		assertEquals(result,0,"tamanho errado");
	}
	
	
	
}
