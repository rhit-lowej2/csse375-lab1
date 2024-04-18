package mainApp;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
	public void testChromosomeComponentWithFakeFitness() {
		ChromosomeComponent component = new ChromosomeComponent();
		assertEquals(component.calcTotalFitness(new FakeFitnessMethod()), 0);
	}

	@Test
	public void testGenerationWithFakeFitness() {
		ChromosomeComponent[] survivors = new ChromosomeComponent[100];
		for(int i = 0; i < 100; i++) {
			int genesToFill[] = new int[i + 1];
			Arrays.fill(genesToFill, i);
			survivors[i] = new ChromosomeComponent();
			survivors[i].setGenes(genesToFill);
		}
		Generation g = new Generation(survivors, new GenParams(0, 0, "", 0), new FakeFitnessMethod());
		assertEquals(g.getAverageFit(), 50.5, 0.1);
		assertEquals(g.getBestFit(), 100);
		assertEquals(g.getWorstFit(), 1);
		assertEquals(g.getHammingFit(), 0, 1);
	}

	@Test
	public void testGenCompRateZero() {
		TestGenerationComponent comp = new TestGenerationComponent();
		comp.randomize(0, "", 150, 100, 1, new FakeFitnessMethod());
		assertEquals(comp.getDebugOutput().toString(), "[100.0]");
		comp.nextGen();
		assertTrue(comp.getDebugOutput().toString().startsWith("[100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, "));
	}

	@Test
	public void testGenCompRateNonZero() {
		TestGenerationComponent comp = new TestGenerationComponent();
		comp.randomize(0.5, "la", 150, 100, 1, new FakeFitnessMethod());
		assertEquals(comp.getDebugOutput().toString(), "[100.0]");
		comp.nextGen();
		assertFalse(comp.getDebugOutput().toString().startsWith("[100.0, 100.0, 100.0, 100.0, 100.0, 100.0, 100.0, "));
	}

	@Test
	public void testGenCompRatePopSize() {
		TestGenerationComponent comp = new TestGenerationComponent();
		comp.randomize(0, "la", 150, 20, 1, new FakeFitnessMethod());
		assertEquals(comp.getDebugOutput().toString(), "[20.0]");
		comp.nextGen();
		assertTrue(comp.getDebugOutput().toString().startsWith("[20.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0"));
	}

	@Test
	public void testGenCompRateElitismZero() {
		TestGenerationComponent comp = new TestGenerationComponent();
		comp.randomize(0, "la", 150, 20, 0, new FakeFitnessMethod());
		assertEquals(comp.getDebugOutput().toString(), "[20.0]");
		comp.nextGen();
		assertTrue(comp.getDebugOutput().toString().startsWith("[20.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0"));
	}
}
