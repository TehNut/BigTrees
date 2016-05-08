package karob.bigtrees.compat;

import java.util.Random;

public class BlockPos {

	private int x;
	private int y;
	private int z;
	
	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BlockPos(net.minecraft.util.math.BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public BlockPos random2dChunkMove(Random random) {
		int newX = x + random.nextInt(16) + 8;
		int newZ = z + random.nextInt(16) + 8;
		
		return new BlockPos(newX, y, newZ);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
	
	public net.minecraft.util.math.BlockPos toPos() {
		return new net.minecraft.util.math.BlockPos(x, y, z);
	}
	
	public String toString() {
		return String.format("Pos[x:%s, y:%s, z:%s]", x, y, z);
	}
}
