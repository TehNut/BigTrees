package karob.bigtrees.config.defaults;

import java.util.ArrayList;

import karob.bigtrees.config.Algorithm;
import karob.bigtrees.config.BlockAndMeta;
import karob.bigtrees.config.TreeConfiguration;
import net.minecraft.init.Blocks;

public class DeadTreeDefaults extends TreeConfiguration {
	public DeadTreeDefaults() {
		algorithm = Algorithm.Dead;
		name = "Dead Tree";
		minHeight = 13;
		maxHeight = 28;
		wood = new BlockAndMeta(Blocks.LOG, 0);
		leaf = new BlockAndMeta(Blocks.LEAVES, 0);
		baseBlocks = new ArrayList<BlockAndMeta>();
		baseBlocks.add(new BlockAndMeta(Blocks.GRASS));
		baseBlocks.add(new BlockAndMeta(Blocks.SAND));
		minNoiseValue = 0;
		maxNoiseValue = 60;
	}
}
