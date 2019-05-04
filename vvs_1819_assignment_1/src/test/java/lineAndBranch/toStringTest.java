package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


import sut.ArrayNTree;

public class toStringTest {

	@Test
	public void TestEmptyString() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		String result = tree.toString();
		assertEquals(result,"[]","não imprime correctamente arvore vazia");
	}
	
	@Test 
	public void TestLeafTreePrint() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		String result = tree.toString();
		assertEquals(result.equals("[1]"),true,"não imprime correctamente uma folha");
		
	}
	
	@Test
	public void TestTreePrint() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,2);
		String result = tree.toString();
		assertTrue(result.equals("[1:[2][3:[4][5]]]"),"não imprime correctamente toda a arvore");
	}
}
