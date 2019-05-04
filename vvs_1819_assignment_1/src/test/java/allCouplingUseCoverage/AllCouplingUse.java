package allCouplingUseCoverage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


import sut.ArrayNTree;

public class AllCouplingUse {
	
	
	
	
	
	/*
	 * Coupling du-pairs (last-def -> first-use):
	 * (delete, elem, node 1) ->  (proposePosition, elem, node 4)
	 * (proposePosition, index, node 8) -> (delete, position, node 7)
	 * (delete, children, node 11) ->(compact, children node 4)
	 */

	@Test
	public void testLeafElement() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(5);
		List<Integer> lista1 = Arrays.asList(1,2,3,4);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	
	
	
	/*
	 * Coupling du-pairs (last-def -> first-use):
	 * (delete, children, node 6) ->(compact, children node 4)
	 */
	@Test
	public void testRootDeletion() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(1);
		List<Integer> lista1 = Arrays.asList(2,3,4,5);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	
	
	/*
	 * Coupling du-pairs (last-def -> first-use):
	 * (proposePosition, index, node 1) -> (delete, position, node 7)
	 */

	@Test
	public void testNonExistantElementMiddle() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,3);
		tree.delete(2);
		List<Integer> lista1 = Arrays.asList(1,3,4,5);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	/*
	 * Coupling du-pairs (last-def -> first-use):
	 * (proposePosition, index, node 6) -> (delete, position, node 7)
	 */
	@Test
	public void testLowerElementThenAllTree() {
		List<Integer> lista = Arrays.asList(1,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,3);
		tree.delete(2);
		List<Integer> lista1 = Arrays.asList(1,3,4,5);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	
	
	
}
