package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class toStringTest {

	@Test
	public void TestTreeContainsElement() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		boolean result = tree.contains(2);
		assertEquals(result,true,"contains a funcionar mal");
	}
	
	@Test 
	public void TestEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);
		boolean result = tree.contains(458);
		assertEquals(result, false, "contain diz que tem algo numa arvore vazia");
	}
	
	@Test
	public void TestElementAtRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1,0);
		boolean result = tree.contains(1);
		assertEquals(result,true, "diz que nao contem o 1 apesar de la estar");
	}
}
