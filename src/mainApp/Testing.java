package mainApp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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
		Generation g = new Generation(survivors, new GenParams(.1, 2, "t", 1), new FakeFitnessMethod());
		g.rouletteWheelReproduce(survivors, .1);
		ArrayList<ChromosomeComponent> result = g.getList();
		for(int i = 0; i < result.size(); i++) {
			result.get(i).getGenes()[0] = 0;
			result.get(i).getGenes()[1] = 1;
		}
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
			component.mutate(1.0, Math.random());
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
