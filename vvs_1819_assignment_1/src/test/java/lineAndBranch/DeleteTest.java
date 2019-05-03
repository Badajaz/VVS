package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sut.ArrayNTree;

public class DeleteTest {

	/*
	 * 
	 * 
	 * teste para o purposePosition que apenas retorna 0 é infeasible
	 * 
	 * 
	 */

	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1);
		tree.delete(0);
		assertTrue(tree.isEmpty(),"apaga mal arvore vazia");
	}

	@Test
	public void testBigRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(100,1);
		tree.delete(0);
		List<Integer> lista1 = Arrays.asList(100);
		assertTrue(lista1.equals(tree.toList()));
	}


	

	@Test
	public void testLeafElement() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(5);
		List<Integer> lista1 = Arrays.asList(1,2,3,4);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	
	
	@Test
	public void testRootDeletion() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(1);
		List<Integer> lista1 = Arrays.asList(2,3,4,5);
		assertTrue(lista1.equals(tree.toList()));
	}

	@Test
	public void testNonRootDeletion() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,2);
		tree.delete(2);
		List<Integer> lista1 = Arrays.asList(1,3,4,5);
		assertTrue(lista1.equals(tree.toList()));
	}

	@Test
	public void testNonExistantElementEnd() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,2);
		tree.delete(8);
		List<Integer> lista1 = Arrays.asList(1,2,3,4,5);
		assertTrue(lista1.equals(tree.toList()));
	}
	

	@Test
	public void testNonExistantElementMiddle() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,3);
		tree.delete(2);
		List<Integer> lista1 = Arrays.asList(1,3,4,5);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	@Test
	public void testLowerElementThenAllTree() {
		List<Integer> lista = Arrays.asList(1,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,3);
		tree.delete(2);
		List<Integer> lista1 = Arrays.asList(1,3,4,5);
		assertTrue(lista1.equals(tree.toList()));
	}
	


}
