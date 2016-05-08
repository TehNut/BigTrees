package karob.bigtrees.config.defaults;

import karob.bigtrees.config.Algorithm;
import karob.bigtrees.config.BlockAndMeta;
import karob.bigtrees.config.TreeConfiguration;
import net.minecraft.init.Blocks;

public class BigPineDefaults extends TreeConfiguration {
	public BigPineDefaults() {
		algorithm = Algorithm.BigPine;
		name = "Big Pine";
		minHeight = 18;
		maxHeight = 22;
		wood = new BlockAndMeta(Blocks.LOG, 1);
		leaf = new BlockAndMeta(Blocks.LEAVES, 1);
		baseBlocks.add(new BlockAndMeta(Blocks.GRAVEL));
		baseBlocks.add(new BlockAndMeta(Blocks.DIRT, 2));
		minNoiseValue = 60;
		maxNoiseValue = 80;
	}
}
