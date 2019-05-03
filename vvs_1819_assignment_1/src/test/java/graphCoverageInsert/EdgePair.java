package graphCoverageInsert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
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
		assertEquals(tree.size(),1,"tamanho errado");
		assertTrue(tree.contains(1),"elemento errado");
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
		assertEquals(tree.size(),2,"tamanho errado");
		assertTrue(tree.contains(2),"elemento errado");
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
		assertEquals(tree.size(),1,"tamanho errado");		
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
		int value = tree.toList().get(0);
		assertEquals(value,0,"nao fez swap dos valores quando a root e maior que o elemento inserido");
	}

	/*
	 * 
	 * 	Covers EPC = [9] [10] [11] [12] [13] [7,9] [9,10] [9,11] [11,12] [12,13] [7,9,10] [7,9,11] [9,11,12] [11,12,13]
	 * 
	 */

	@Test
	public void testInsertLowerElementInNonEmptyTree() {

		List<Integer> lista = Arrays.asList(1,3);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,2);
		tree.insert(2);
		int value = tree.toList().get(1);
		assertEquals(value,2,"nao inseriu um menor valor numa arvore vazia");
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
		int value = tree.toList().get(1);
		assertEquals(value,5,"nao havia lugar para inserir um elemento");
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
		assertEquals(tree.contains(30),true,"nao havia lugar para inserir um elemento");
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
		assertEquals(tree.contains(22),true,"nao havia lugar para inserir um elemento");
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
		System.out.println(tree);
		tree.insert(9);
		System.out.println(tree);
		assertTrue(tree.contains(9), "nao esta na root");
	}



}
