package quickCheck;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import sut.ArrayNTree;
@RunWith(JUnitQuickcheck.class)
public class Properties {


	@Property
	public void shuffleTree(@From(TreeGenerator.class)@InRange(min="0",max= "100") ArrayNTree<Integer> tree) {

		List<Integer> shuffle = tree.toList() ;
		Collections.shuffle(shuffle);

		ArrayNTree<Integer> secondTree = new ArrayNTree<Integer>(shuffle,5);
		assertTrue(tree.equals(secondTree));

	}

	@Property
	public void deletedTree(@From(TreeGenerator.class)@InRange(min="0",max= "100") ArrayNTree<Integer> tree) {
		for(int e: tree.toList())
			tree.delete(e);
		assertTrue(tree.isEmpty());
	}

	@Property
	public void insertAndDeleteElement(@From(TreeGenerator.class)@InRange(min="0",max= "100") ArrayNTree<Integer> tree) {
		Random r = new Random();
		ArrayNTree<Integer> secondTree = tree.clone();
		int x = r.nextInt(1000);
		secondTree.insert(x);
		secondTree.delete(x);
		assertTrue(tree.equals(secondTree));
	}

	@Property
	public void insertSameElements(@From(TreeGenerator.class)@InRange(min="0",max= "100") ArrayNTree<Integer> tree) {
		ArrayNTree<Integer> secondTree = tree.clone();
		for (int e :tree)
			tree.insert(e);
		assertTrue(tree.equals(secondTree));
	}

	@Property
	public void insertSameElementSeveralTimes(@From(TreeGenerator.class)@InRange(min="0",max= "100") ArrayNTree<Integer> tree) {
		ArrayNTree<Integer> secondTree = tree.clone();
		Random r = new Random();
		int number = tree.toList().get(r.nextInt(tree.size()));
		int repeat = r.nextInt(100);
		for (int i =0; i<repeat; i++)
			tree.insert(number);
		assertTrue(tree.equals(secondTree));
	}







}
