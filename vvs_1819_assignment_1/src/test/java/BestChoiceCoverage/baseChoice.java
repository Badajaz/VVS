package BestChoiceCoverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;
public class baseChoice {



/*
	@Test
	public void testEmptyTreeOne() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		ArrayNTree<Integer>  tree1 = tree.clone();
		assertTrue(tree.equals(tree1),"N");
	}
*/

	/*
	 * 
	 * Tree 1 instersection of Tree 2(partial)
	 */
	@Test
	public void testPartialEquals () {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);

		List<Integer> list2 = new ArrayList <> ();
		list2.add(2);
		list2.add(3);
		list2.add(4);
		list2.add(5);
		list2.add(6);
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(list2,5);

		boolean result = tree.equals(tree1);
		assertEquals(result,false,"arvores nao sao iguais");

	}
	
	/*
	 * 
	 * 
	 * Tree 1 instersection of Tree 2(full)
	 * 
	 */
	@Test
	public void testEqualsWithSameElements () {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);

		List<Integer> list2 = new ArrayList <> ();
		list2.add(1);
		list2.add(2);
		list2.add(3);
		list2.add(4);
		list2.add(5);
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(list2,5);

		boolean result = tree.equals(tree1);
		assertEquals(result,true,"arvores nao sao iguais");

	}	
	
	/*
	 * 
	 * 
	 * Tree 1 instersection of Tree 2(empty)
	 * 
	 */
	@Test
	public void testEqualsWithDifferentIntersections () {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);

		List<Integer> list2 = new ArrayList <> ();
		list2.add(6);
		list2.add(7);
		list2.add(8);
		list2.add(9);
		list2.add(10);
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(list2,5);

		boolean result = tree.equals(tree1);
		assertEquals(result,false,"arvores nao sao iguais");

	}	
	
	/*
	 * 
	 * 
	 * Tree 2 is null
	 * 
	 */
	@Test
	public void testTreeNull () {
	List<Integer> lista = new ArrayList <> ();
	lista.add(1);
	lista.add(2);
	lista.add(3);
	lista.add(4);
	lista.add(5);
	ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
	
	ArrayNTree<Integer> tree2 = null;
	assertFalse(tree.equals(tree2), "nao sao iguais");
	
	}

	
	/*
	 * 
	 * 
	 * Tree 2 is empty
	 * 
	 */
	@Test
	public void testTreeEmpty () {
	List<Integer> lista = new ArrayList <> ();
	lista.add(1);
	lista.add(2);
	lista.add(3);
	lista.add(4);
	lista.add(5);
	ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
	
	ArrayNTree<Integer> tree2 = new ArrayNTree<Integer>(0);
	assertFalse(tree.equals(tree2), "nao sao iguais");
	
	}
	
	
	/*
	 * 
	 * 
	 * Tree 1 is empty
	 * 
	 */
	@Test
	public void testTree1Empty () {
	
	ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(0);
	
	List<Integer> lista = new ArrayList <> ();
	lista.add(1);
	lista.add(2);
	lista.add(3);
	lista.add(4);
	lista.add(5);
	ArrayNTree<Integer> tree2 = new ArrayNTree<Integer>(lista,5);
	assertFalse(tree2.equals(tree1), "nao sao iguais");
	
	}
	
	
/*
	@Test
	public void testEqualsReferences() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		assertEquals(tree.equals(tree),true,"referencias não são iguais");

	}	


	@Test
	public void testEqualsDifferentObjects () {

		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);


		boolean result = tree.equals(lista);
		assertEquals(result,false,"nao tem o tipo desejado");

	}*/

	
}