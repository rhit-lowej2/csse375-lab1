package mainApp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;

public class Testing {

	@Test
	public void testGeneration() {
		ChromosomeComponent[] survivors = new ChromosomeComponent[100];
		for(int i = 0; i < 100; i++) {
			survivors[i] = new ChromosomeComponent();
		}
		Generation g = new Generation(survivors, new GenParams(0, 0, "la", 0), new StringCompareFitnessMethod("Smiley"));
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
	public void testUndo() {
		ChromosomeComponent component = new ChromosomeComponent();
		int[] genes = new int[100];
		for (int i = 0; i < 100; i++) {
			genes[i] = 1;
		}
		component.setGenes(genes);
		for (int i = 0; i < 50; i++) {
			component.mutate(1.0);
		}
		for (int i = 0; i < 50; i++) {
			component.undo();
		}
		List<Chromosome> chromosomes = component.getChromosomes();
		for (Chromosome c: chromosomes) {
			assertEquals(c.getNum(), 1);
		}
	}

	@Test
	public void testFakeFitness() {

	}
}
