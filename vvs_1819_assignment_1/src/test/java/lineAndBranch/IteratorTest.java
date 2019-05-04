package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import sut.ArrayNTree;

public class IteratorTest {
	@Test
	public void testNoSuchElement() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5,6,7);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		Iterator<Integer> i = tree.iterator();
		List<Integer> listaIterador = new ArrayList<>();
		assertThrows(NoSuchElementException.class, ()-> {
			for (int j = 0; j <= lista.size(); j++) {
				listaIterador.add(i.next());
			}});
	}



	@Test
	public void testBiggerTree() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5,6,7);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		Iterator<Integer> i = tree.iterator();
		List<Integer> listaIterador = new ArrayList<>();
		while(i.hasNext()) {
			listaIterador.add(i.next());
		}
		assertTrue(lista.equals(listaIterador));
	}


	@Test
	public void testEmptyCase() {
		List<Integer> shuffle = Arrays.asList() ;
		ArrayNTree<Integer> secondTree = new ArrayNTree<Integer>(shuffle,5);
		Collections.shuffle(shuffle);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(shuffle,5);
		assertTrue(tree.equals(secondTree));
		
	}

}
