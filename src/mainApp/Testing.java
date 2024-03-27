package mainApp;

import static org.junit.Assert.assertEquals;

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
}
