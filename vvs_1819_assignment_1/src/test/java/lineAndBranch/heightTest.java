package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class heightTest {

	@Test
	public void testHeightNonEmpty () {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		int result = tree.height();
		assertEquals(result,2,"altura errada");
	}
	@Test
	public void testHeightEmpty () {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		int result = tree.height();
		assertEquals(result,0,"altura errada");
	}
}

