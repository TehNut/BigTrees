package karob.bigtrees.config.defaults;

import karob.bigtrees.config.Algorithm;
import karob.bigtrees.config.BlockAndMeta;
import karob.bigtrees.config.TreeConfiguration;
import net.minecraft.init.Blocks;

public class SwampOakDefaults extends TreeConfiguration {
	public SwampOakDefaults() {
		algorithm = Algorithm.SwampOak;
		name = "Swamp Oak";
		minHeight = 28;
		maxHeight = 32;
		wood = new BlockAndMeta(Blocks.LOG, 0);
		leaf = new BlockAndMeta(Blocks.LEAVES, 0);
		minNoiseValue = 55;
		maxNoiseValue = 75;
	}
}
