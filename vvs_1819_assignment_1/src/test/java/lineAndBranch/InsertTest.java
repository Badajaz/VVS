package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class InsertTest {



	@Test
	public void testInsert () {

		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(2);
		tree.insert(1);
		assertEquals(tree.size(),1,"tamanho errado");
		assertEquals(tree.contains(1),true,"elemento errado");
	}


	@Test
	public void testInsertRepetitions() {

		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(4);
		tree.insert(1);
		tree.insert(1);
		assertEquals(tree.size(),1,"tamanho errado");		

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

		List<Integer> lista = new ArrayList<>();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,2);
		tree.insert(0);
		int value = tree.toList().get(0);
		assertEquals(value,0,"nao inseriu um menor valor numa arvore vazia");

	}


	@Test
	public void testInsertIfSpaceAvailable() {

		List<Integer> lista = new ArrayList<>();
		lista.add(1);
		lista.add(2);
		lista.add(4);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,3);
		tree.insert(3);
		int value = tree.toList().get(3);
		assertEquals(value,4,"nao havia lugar para inserir um elemento");

	}

	@Test
	public void testPlaceBelowLastChild() {
		List<Integer> lista = new ArrayList<>();
		lista.add(1);
		lista.add(2);
		lista.add(7);
		lista.add(9);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,3);
		tree.insert(3);
		int value = tree.toList().get(2);
		assertEquals(value,3,"nao havia lugar para inserir um elemento");
	}
	
	@Test
	public void test() {
		List<Integer> lista = new ArrayList<>();
		lista.add(1);
		lista.add(10);
		lista.add(20);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,3);
		tree.insert(5);
		int value = tree.toList().get(1);
		assertEquals(value,5,"nao havia lugar para inserir um elemento");
	
	}
	
	
	/*@Test
	public void testIfNodeCapacityIsFullAndElemLagerThanAll() {
		List<Integer> lista = new ArrayList<>();
		lista.add(1);
		lista.add(10);
		lista.add(20);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,3);
		tree.insert(30);
		int value = tree.toList().get(3);
		System.out.println(value);
		assertEquals(value,30,"nao havia lugar para inserir um elemento");
	
	}*/
	
	
	








}
