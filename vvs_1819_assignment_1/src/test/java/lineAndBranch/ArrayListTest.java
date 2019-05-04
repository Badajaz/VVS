package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


import sut.ArrayNTree;

public class ArrayListTest {

	
	
	@Test
	public void testArrayList () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		List<Integer> result = tree.toList();
		assertEquals(result.equals(lista),true,"as listas sao iguais");
	}	
}
