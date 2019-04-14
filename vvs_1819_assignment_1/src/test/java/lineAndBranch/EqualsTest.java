package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class EqualsTest {

	
	
	@Test
	public void testEquals () {
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

	}	
	
	
	
	
}
