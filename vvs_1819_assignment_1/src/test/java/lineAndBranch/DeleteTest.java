package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import sut.ArrayNTree;

public class DeleteTest {

	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1);
		tree.delete(0);
		assertEquals(tree.isEmpty(),true,"apaga mal arvore vazia");
	}
	
	@Test
	public void testBigRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(100,1);
		tree.delete(0);
		assertEquals(tree.size(),1,"root apagada");
	}
	
	
	@Test
	public void testLeafElement() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(5);
		assertEquals(tree.size(),4,"nao apaga o elemento na folha");
		assertEquals(tree.contains(5), false, "nao apaga o elemento correto");
	}
	
	@Test
	public void testRootDeletion() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,5);
		tree.delete(1);
		assertEquals(tree.size(),4,"nao apaga o elemento na raiz");
		assertEquals(tree.contains(1), false, "nao apaga o elemento correto");
	}
	
	@Test
	public void testNonRootDeletion() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,2);
		tree.delete(2);
		assertEquals(tree.size(),4,"nao apaga um elemento");
		assertEquals(tree.contains(2), false, "nao apaga o elemento correto");
	}
	
	@Test
	public void testNonExistantElementEnd() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,2);
		tree.delete(8);
		assertEquals(tree.size(),5,"apaga um elemento que nao existe");
	}
	
	
	
	@Test
	public void testNonExistantElementMiddle() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<>(lista,3);
		tree.delete(2);
		assertEquals(tree.size(),4,"apaga um elemento que nao existe");
	}
}
