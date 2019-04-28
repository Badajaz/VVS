package BestChoiceCoverage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;
public class baseChoice {




	/*
	 * 
	 * Covers BCC = [~et1,~et2,~nt2,partial]
	 */

	@Test
	public void testPartialEquals () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		List<Integer> list2 = Arrays.asList(2,3,4,5,6);
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(list2,5);
		assertFalse(tree.equals(tree1),"arvores nao sao iguais");
	}

	/*
	 * 
	 * 
	 * Covers BCC  =[~et1,~et2,~nt2,full] 
	 * 
	 */

	@Test
	public void testEqualsWithSameElements () {
		List<Integer> lista =  Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		List<Integer> list2 =  Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(list2,5);
		assertTrue(tree.equals(tree1),"arvores nao sao iguais");
	}	

	/*
	 * 
	 * 
	 * Covers BCC  = [~et1,~et2,~nt2,empty]
	 * 
	 */

	@Test
	public void testEqualsWithDifferentIntersections () {
		List<Integer> lista =  Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		List<Integer> list2 =  Arrays.asList(6,7,8,9,10);
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(list2,5);
		assertFalse(tree.equals(tree1),"arvores nao sao iguais");
	}	

	/*
	 * 
	 * 
	 *  Covers BCC  = [~et1,~et2,nt2,empty]
	 * 
	 */

	@Test
	public void testTreeNull () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		ArrayNTree<Integer> tree2 = null;
		assertFalse(tree.equals(tree2), "nao sao iguais");
	}


	/*
	 * 
	 * 
	 * Covers BCC = [et1,et2,nt2,empty]
	 * 
	 */

	@Test
	public void testTreeEmpty () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		ArrayNTree<Integer> tree2 = new ArrayNTree<Integer>(0);
		assertFalse(tree.equals(tree2), "nao sao iguais");
	}


	/*
	 * 
	 * 
	 * Covers BCC  = [et1,et2,nt2,empty]
	 * 
	 */

	@Test
	public void testTree1Empty () {
		ArrayNTree<Integer> tree1 = new ArrayNTree<Integer>(0);
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree2 = new ArrayNTree<Integer>(lista,5);
		assertFalse(tree2.equals(tree1), "nao sao iguais");
	}

}