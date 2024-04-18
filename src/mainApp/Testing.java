package mainApp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class Testing {

	@Test
	public void testGeneration() {
		ChromosomeComponent[] survivors = new ChromosomeComponent[100];
		for(int i = 0; i < 100; i++) {
			survivors[i] = new ChromosomeComponent();
		}
		Generation g = new Generation(survivors, 0, 0, "la", 0, "Smiley");
		ChromosomeComponent[] survivors1 = new ChromosomeComponent[100];
		for(int i = 0; i < 100; i++) {
			survivors1[i] = new ChromosomeComponent();
		}
		assertEquals(1, g.chromosomeList.size());
		g.rankingReproduce(survivors1);
		assertEquals(g.rankTopIndex, 1);
		assertEquals(2, g.chromosomeList.size());
		assertEquals("mainApp.ChromosomeComponent[,0,0,0x0,invalid,alignmentX=0.0,alignmentY=0.0,border=,flags=0,maximumSize=,minimumSize=,preferredSize=]", g.topTier.toString());
	}
	
	@Test
	public void testMutate() {
		ChromosomeComponent c = new ChromosomeComponent();
		int[] genetics = new int[2];
		genetics[0] = 0;
		genetics[1] = 1;
		c.setGenes(genetics);
		c.mutate(.1, .2);
		int[] result = c.getGenes();
		assertEquals(0, result[0]);
		assertEquals(1, result[1]);
		c.mutate(.2, 0);
		result = c.getGenes();
		assertEquals(1, result[0]);
		assertEquals(0, result[1]);
	}
	
	@Test
	public void testRouletteWheelReproduce() {
		ChromosomeComponent c1 = new ChromosomeComponent();
		int[] genetics = new int[2];
		genetics[0] = 0;
		genetics[1] = 1;
		c1.setGenes(genetics);
		ChromosomeComponent c2 = new ChromosomeComponent();
		c2.setGenes(genetics);
		ChromosomeComponent[] survivors = new ChromosomeComponent[2];
		survivors[0] = c1;
		survivors[1] = c2;
		Generation g = new Generation(survivors, .1, 2, "t", 1, "a");
		g.rouletteWheelReproduce(survivors, .1);
		ArrayList<ChromosomeComponent> result = g.getList();
		for(int i = 0; i < result.size(); i++) {
			result.get(i).getGenes()[0] = 0;
			result.get(i).getGenes()[1] = 1;
		}
	}
}
