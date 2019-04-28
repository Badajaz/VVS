package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
		assertEquals(tree.size(),1,"root apagada");
	}


	/*
	 * Coupling du-pairs (last-def -> first-use):
	 * (delete, elem, node 1) ->  (proposePosition, elem, node 4)
	 * (proposePosition, index, node 6) -> (delete, position, node 7)
	 */

	@Test
	public void testLeafElement() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(5);
		assertEquals(tree.size(),4,"nao apaga o elemento na folha");
		assertFalse(tree.contains(5), "nao apaga o elemento correto");
	}

	@Test
	public void testRootDeletion() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(1);
		assertEquals(tree.size(),4,"nao apaga o elemento na raiz");
		assertFalse(tree.contains(1), "nao apaga o elemento correto");
	}

	@Test
	public void testNonRootDeletion() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,2);
		tree.delete(2);
		assertEquals(tree.size(),4,"nao apaga um elemento");
		assertFalse(tree.contains(2), "nao apaga o elemento correto");
	}

	@Test
	public void testNonExistantElementEnd() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,2);
		tree.delete(8);
		assertEquals(tree.size(),5,"apaga um elemento que nao existe");
	}

	/*
	 * Coupling du-pairs (last-def -> first-use):
	 * (proposePosition, index, node 5) -> (delete, position, node 7)
	 */

	@Test
	public void testNonExistantElementMiddle() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,3);
		tree.delete(2);
		assertEquals(tree.size(),4,"apaga um elemento que nao existe");
	}


}
