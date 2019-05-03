package allCouplingUseCoverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sut.ArrayNTree;

public class AllCouplingUse {

	
	/*
	 * Coupling du-pairs (last-def -> first-use):
	 * (delete, elem, node 1) ->  (proposePosition, elem, node 4)
	 * (proposePosition, index, node 6) -> (delete, position, node 7)
	 * (delete, children, node 11) ->(compact, children node 4)
	 */

	@Test
	public void testLeafElement() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(5);
		assertEquals(tree.size(),4,"nao apaga o elemento na folha");
		assertFalse(tree.contains(5), "nao apaga o elemento correto");
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
		assertEquals(tree.size(),4,"nao apaga o elemento na raiz");
		assertFalse(tree.contains(1), "nao apaga o elemento correto");
	}
	
	
	
	/*
	 * Coupling du-pairs (last-def -> first-use):
	 * (proposePosition, index, node 5) -> (delete, position, node 7)
	 */

	@Test
	public void testNonExistantElementMiddle() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,3);
		System.out.println(tree);
		tree.delete(2);
		System.out.println(tree);
		assertEquals(tree.size(),4,"apaga um elemento que nao existe");
	}
	
}
