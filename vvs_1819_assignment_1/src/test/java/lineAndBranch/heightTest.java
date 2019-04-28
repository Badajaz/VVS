package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class heightTest {

	@Test
	public void testHeightNonEmpty () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		assertEquals(tree.height(),2,"altura errada");
	}
	@Test
	public void testHeightEmpty () {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		assertEquals(tree.height(),0,"altura errada");
	}
}

