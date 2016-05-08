package karob.bigtrees.config.defaults;

import karob.bigtrees.config.Algorithm;
import karob.bigtrees.config.BlockAndMeta;
import karob.bigtrees.config.TreeConfiguration;
import net.minecraft.init.Blocks;

public class BigBirchDefaults extends TreeConfiguration {
	public BigBirchDefaults() {
		algorithm = Algorithm.BigBirch;
		name = "Big Birch";
		minHeight = 19;
		maxHeight = 24;
		wood = new BlockAndMeta(Blocks.LOG, 2);
		leaf = new BlockAndMeta(Blocks.LEAVES, 2);
		minNoiseValue = 0;
		maxNoiseValue = 60;
	}
}
