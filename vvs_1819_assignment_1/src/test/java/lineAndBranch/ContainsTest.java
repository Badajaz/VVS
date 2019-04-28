package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class ContainsTest {

	@Test
	public void TestTreeContainsElement() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		assertTrue(tree.contains(2),"contains a funcionar mal");
	}
	
	@Test 
	public void TestEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);
		boolean result = tree.contains(458);
		assertFalse(result, "contain diz que tem algo numa arvore vazia");
	}
	
	@Test
	public void TestElementAtRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1,0);
		assertTrue(tree.contains(1), "diz que nao contem o 1 apesar de la estar");
	}
	
	
	@Test
	public void TestTreeDoesNotContainsElement() {
		List<Integer> lista = Arrays.asList(2,3,4,5,6);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		assertFalse( tree.contains(1),"contains a funcionar mal");
	}
	
	@Test
	public void TestTreeRecursive() {
		List<Integer> lista = Arrays.asList(2,3,4,5,6);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,1);
		assertTrue(tree.contains(6),"contains a funcionar mal");
	}
	
	
	
	
	
}
