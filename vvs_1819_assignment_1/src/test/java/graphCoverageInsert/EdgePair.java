package graphCoverageInsert;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class EdgePair {

	/*
	 * Covers EPC = [1],[2],[1,2]
	 * 
	 * 
	 */

	@Test
	public void testInsertEmptyTree () {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(2);
		tree.insert(1);
		List<Integer> lista1 = Arrays.asList(1);
		assertTrue(lista1.equals(tree.toList()));
	}

	/*
	 * 
	 * Covers EPC=[5,7][3,5,7][5,7,8]
	 * 
	 */

	@Test
	public void testInsertIsLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(1,2);
		tree.insert(2);
		List<Integer> lista1 = Arrays.asList(1,2);
		assertTrue(lista1.equals(tree.toList()));
	}

	/*
	 * 
	 * Covers EPC = [3][4][1,3][3,4][1,3,4]
	 * 
	 */

	@Test
	public void testInsertRepetitions() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(4);
		tree.insert(1);
		tree.insert(1);
		List<Integer> lista1 = Arrays.asList(1);
		assertTrue(lista1.equals(tree.toList()));	
	}

	/*
	 * 
	 * Covers EPC = [5][6][7][8][3,5][5,6][6,7][7,8][1,3,5][3,5,6][5,6,7][6,7,8]
	 * 
	 */

	@Test
	public void testSwapElementsAtRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(1,4);
		tree.insert(0);
		List<Integer> lista1 = Arrays.asList(0,1);
		assertTrue(lista1.equals(tree.toList()));	
	}
	/*
	 * 
	 * 	Covers EPC = [9] [10] [11] [12] [13] [7,9] [9,10] [9,11] [11,12] [12,13] [7,9,10] [7,9,11] [9,11,12] [11,12,13]
	 * 
	 */

	@Test
	public void testInsertLowerElementInNonEmptyTree() {
		List<Integer> lista =Arrays.asList(1,3);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,2);
		tree.insert(2);
		List<Integer> lista1 = Arrays.asList(1,2,3);
		assertTrue(lista1.equals(tree.toList()));	
	}

	/*
	 * 
	 * Covers EPC = [15] [16] [11,15] [15,16] [9,11,12] [9,11,15] [11,15,16] [11,15,17]
	 * 
	 * 
	 */

	@Test
	public void testNewLevel() {
		List<Integer> lista = Arrays.asList(1,10,20);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,3);
		tree.insert(5);
		List<Integer> lista1 = Arrays.asList(1,5,10,20);
		assertTrue(lista1.equals(tree.toList()));	
	}

	/*
	 * 
	 * 
	 * Covers EPC = [17] [18] [19] [20] [21] [15,17] [17,18] [18,20] [18,21] [15,16,17] [17,18,20] [17,18,21]
	 * 
	 */

	@Test
	public void testIfNodeCapacityIsFullAndElemLagerThanAll() {
		List<Integer> list = Arrays.asList(10,20,16,17,30,40,15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		tree.delete(30);
		tree.insert(30);
		List<Integer> lista1 = Arrays.asList(10,20,16,17,30,40,15);
		Collections.sort(lista1);
		assertTrue(lista1.equals(tree.toList()));
	}	

	/*
	 * 
	 * Covers EPC = [11,12,14]
	 * 
	 * 
	 */

	@Test
	public void testCapacityNotFull() {
		List<Integer> list = Arrays.asList(10,17,20,21,23,25,26,40,50,35);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		tree.delete(20);
		tree.insert(22);
		List<Integer> lista1 = Arrays.asList(10,17,21,23,25,26,40,50,35,22);
		Collections.sort(lista1);
		assertTrue(lista1.equals(tree.toList()));	
	}

	/*
	 * 
	 * Covers EPC = [6,7,9]
	 * 
	 */

	@Test
	public void testAtRoot ()  {
		List<Integer> list = Arrays.asList(10,20,16,17,30,40,15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		tree.insert(9);
		List<Integer> lista1 = Arrays.asList(9,10,20,16,17,30,40,15);
		Collections.sort(lista1);
		assertTrue(lista1.equals(tree.toList()));	
	}



}
