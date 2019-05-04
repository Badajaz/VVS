package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


import sut.ArrayNTree;

public class EqualsTest {


	@Test
	public void testEquals () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		List<Integer> list2 = Arrays.asList(2,3,4,5,6);
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(list2,5);
		assertFalse(tree.equals(tree1),"arvores nao sao iguais");

	}	


	@Test
	public void testEqualsReferences() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		assertTrue(tree.equals(tree),"referencias não são iguais");
	}	

	@Test
	public void testEqualsWithSameElements () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		List<Integer> list2 = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(list2,5);
		assertTrue(tree.equals(tree1),"arvores nao sao iguais");
	}	

	@Test
	public void testEqualsDifferentObjects () {
		List<Integer> lista =Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		assertFalse(tree.equals(lista),"nao tem o tipo desejado");
	}	



}
