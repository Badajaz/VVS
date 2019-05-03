package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class InsertTest {



	@Test
	public void testInsertIntoEmptyTree () {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(2);
		tree.insert(1);
		List<Integer> lista1 = Arrays.asList(1);
		assertTrue(lista1.equals(tree.toList()));
	}


	@Test
	public void testInsertRepetitions() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(4);
		tree.insert(1);
		tree.insert(1);
		List<Integer> lista1 = Arrays.asList(1);
		assertTrue(lista1.equals(tree.toList()));
	}

	@Test
	public void testSwapElementsAtRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(1,4);
		tree.insert(0);
		int value = tree.toList().get(0);
		assertEquals(value,0,"nao fez swap dos valores quando a root e maior que o elemento inserido");
	}


	@Test
	public void testInsertLowerElementInNonEmptyTree() {
		List<Integer> lista = Arrays.asList(1,2,3);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,2);
		tree.insert(0);
		int value = tree.toList().get(0);
		assertEquals(value,0,"nao inseriu um menor valor numa arvore vazia");
	}


	@Test
	public void testInsertIfSpaceAvailable() {
		List<Integer> lista =  Arrays.asList(1,2,4);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,3);
		tree.insert(3);
		List<Integer> lista1 =  Arrays.asList(1,2,3,4);
		assertTrue(lista1.equals(tree.toList()));
	}

	@Test
	public void testPlaceBelowLastChild() {
		List<Integer> lista = Arrays.asList(1,2,7,9);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,3);
		tree.insert(3);
		List<Integer> lista1 = Arrays.asList(1,2,3,7,9);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	@Test
	public void testInsertSmallerThanAllChildren() {
		List<Integer> lista = Arrays.asList(1,10,20);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,3);
		tree.insert(5);
		List<Integer> lista1 = Arrays.asList(1,5,10,20);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	
	@Test
	public void testIfNodeCapacityIsFullAndElemLagerThanAll() {
		List<Integer> list = Arrays.asList(10,20,16,17,30,40,15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		tree.delete(30);
		tree.insert(30);
		List<Integer> lista1 = Arrays.asList(10,15,16,17,20,30,40);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	@Test
	public void testLastCaseInsert() {
		List<Integer> list = Arrays.asList(10,20,30);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		tree.insert(15);
		List<Integer> lista1 = Arrays.asList(10,15,20,30);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	@Test
	public void testInsertNotFullLevelWithElementlargerThanAllChildrenButNotBiggerThanPreviousChildren() {
		List<Integer> list = Arrays.asList(10,20,30,50,55,57,58,25,27,28);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		tree.delete(30);
		tree.insert(51);
		List<Integer> lista1 = Arrays.asList(10,20,50,55,57,58,25,27,28,51);
		Collections.sort(lista1);
		assertTrue(lista1.equals(tree.toList()));
	}
	
	@Test
	public void testHasCapacityAndElementsSmallerThenAllRecomendedChildren() {
		List<Integer> list = Arrays.asList(2,7,11,15,25,30,17,18,19);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 5);
		tree.delete(30);
		tree.insert(16);
		List<Integer> lista1 = Arrays.asList(2,7,11,15,16,25,17,18,19);
		Collections.sort(lista1);
		assertTrue(lista1.equals(tree.toList()));
	}


}
