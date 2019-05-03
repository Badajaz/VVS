package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class SizeTest {

	@Test
	public void testNonEmptyTree () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		int result = tree.size();
		assertEquals(result,5,"tamanho errado");
		
	}
	
	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		int result = tree.size();
		assertEquals(result,0,"tamanho errado");
	}
	
	
	
}
