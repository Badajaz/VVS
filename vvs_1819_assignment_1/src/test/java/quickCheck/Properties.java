package quickCheck;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import sut.ArrayNTree;
@RunWith(JUnitQuickcheck.class)
public class Properties {
	

	
	@Property(trials = 1000)
	public void shuffleTree(@From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		List<Integer> shuffle = tree.toList() ;
		Collections.shuffle(shuffle);
		ArrayNTree<Integer> secondTree = new ArrayNTree<Integer>(shuffle,5);
		assertTrue(tree.equals(secondTree));
		
	}
	
	@Property(trials = 1000)
	public void deletedTree(@From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		for(int e: tree.toList())
			tree.delete(e);
		assertTrue(tree.isEmpty());
	}
	
	
	@Property(trials = 1000)
	public void insertAndDeleteElement(@From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		Random r = new Random();
		ArrayNTree<Integer> secondTree = tree.clone();
		int x = r.nextInt(1000);
		assumeTrue(!tree.contains(x));
		secondTree.insert(x);
		secondTree.delete(x);
		assertTrue(tree.equals(secondTree));
	}


 
	@Property(trials = 1000)
	public void insertSameElements(@From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		ArrayNTree<Integer> secondTree = tree.clone();
		for (int e :tree)
			tree.insert(e);
		assertTrue(tree.equals(secondTree));
	}

	@Property(trials = 1000)
	public void insertSameElementSeveralTimes(@From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		assumeTrue((!tree.isEmpty()));
		ArrayNTree<Integer> secondTree = tree.clone();
		Random r = new Random();
		int number = tree.toList().get(r.nextInt(tree.size()));
		int repeat = r.nextInt(100);
		for (int i =0; i<repeat; i++)
			tree.insert(number);
		assertTrue(tree.equals(secondTree));
	}

}
