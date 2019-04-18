package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class CloneTest {
	
	@Test
	public void testClone () {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		ArrayNTree<Integer> result = tree.clone();
		assertTrue(result.equals(tree),"as árvores sao iguais");
	}	
}
