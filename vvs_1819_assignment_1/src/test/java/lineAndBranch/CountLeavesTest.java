package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sut.ArrayNTree;

public class CountLeavesTest {
	
	@Test
	public void testNumberLeaves () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		assertEquals(tree.countLeaves(),4,"numero de folhas errado");
	}
}
