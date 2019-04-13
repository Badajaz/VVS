package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class InsertTest {

	
	
	@Test
	public void testInsert () {
		
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(2);
		tree.insert(1);
		assertEquals(tree.size(),1,"tamanho errado");
		assertEquals(tree.contains(1),true,"elemento errado");

	}
	
	
	
}
