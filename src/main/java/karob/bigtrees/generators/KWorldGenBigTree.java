// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package karob.bigtrees.generators;

import java.util.Random;

import karob.bigtrees.KTreeCfg;
import karob.bigtrees.compat.WorldWrapper;
import karob.bigtrees.config.BlockAndMeta;
import karob.bigtrees.config.ITreeConfigurable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;

//import net.minecraft.src.KTreeCfg;

// Referenced classes of package net.minecraft.src:
//            WorldGenerator, World, Block, BlockLeaves, 
//            BlockGrass

public class KWorldGenBigTree extends AbstractWorldGenerator implements ITreeConfigurable
{


    static final byte otherCoordPairs[] = {
        2, 0, 0, 1, 2, 1
    };
    int tapRootRand;
    int basePos[] = {
        0, 0, 0
    };
    int heightLimit;
    int height;
    double heightAttenuation;
    double branchDensity;
    double branchSlope;
    double scaleWidth;
    double leafDensity;
    int trunkSize;
    int heightLimitLimit;
    int leafDistanceLimit;
    int[][] leafNodes;


    public KWorldGenBigTree(boolean doBlockNotify)
    {
        super(doBlockNotify);
        rand = new Random();
        rootRand = 0;
        rootAlt = 0;
        tapRootRand = 0;
        heightLimit = 0; // Tree height
        heightAttenuation = 0.318D; // Trunk percentage height
        branchDensity = 1.0D;
        branchSlope = 0.618D; // Branch height to base hight stuff
        scaleWidth = 1.0D; // Branch length
        leafDensity = 1.0D;
        trunkSize = 1; // Trunk height
        heightLimitLimit = 12; // Height variation
        leafDistanceLimit = 4; // Leaf thickness
//        KTreeCfg.init();
    }

    

    private boolean generateLeafNodeList()
    {
        //calculate trunk height
        height = (int)((double)heightLimit * heightAttenuation);
        //minimal 1 block branch height
        if(height >= heightLimit) height = heightLimit - 1;
        //try stunting or just eliminate trees growing past the top of the world.
        if(basePos[1] + heightLimit > 256 - 4){
          height = height / 2;
          heightLimit = heightLimit / 2;
          if(height >= heightLimit) height = heightLimit - 1;
          if(height < 1) return false;
          if(basePos[1] + heightLimit > 256 - 4)
            return false;
        }
        int i = (int)(1.382D + Math.pow((leafDensity * (double)heightLimit) / 13D, 2D));
        if(i < 1)
        {
            i = 1;
        }
        int ai[][] = new int[i * heightLimit][4];
        int j = (basePos[1] + heightLimit) - leafDistanceLimit;
        int k = 1;
        int l = basePos[1] + height;
        int i1 = j - basePos[1];
        //purely vertical "branch"
        ai[0][0] = basePos[0];
        ai[0][1] = j;
        ai[0][2] = basePos[2];
        ai[0][3] = l;
        j--;
        while(i1 >= 0) 
        {
            int j1 = 0;
            float f = layerSize(i1); //Conditional AND branch length factor.
              if(k >= i * heightLimit) f = -1.0F;
            if(f < 0.0F) //not time to grow branches
            {
                j--;
                i1--;
            } else // grow branches! All branches grow from one spot, radiating from vertical to horizontal.
            {
                for(double d0 = 0.5D; j1 < i; j1++)
                {
                    // branch length
                    double d1 = scaleWidth * ((double)f * ((double)rand.nextFloat() + 0.328D));
                    //if(d1 > 8D) d1 = 8D;
                    //if(d1 < -5D) d1 = -5D;
                    // branch angle (around trunk)
                    double d2 = (double)rand.nextFloat() * 2D * Math.PI;
                    int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + (double)basePos[0] + d0);
                    int l1 = MathHelper.floor_double(d1 * Math.cos(d2) + (double)basePos[2] + d0);
                    int ai1[] = {
                        k1, j, l1
                    };
                    int ai2[] = {
                        k1, j + leafDistanceLimit, l1
                    };
                    if(checkBlockLine(ai1, ai2) != -1)
                    {
                        continue;
                    }
                    int ai3[] = {
                        basePos[0], basePos[1], basePos[2]
                    };
                    double d3 = Math.sqrt(Math.pow(Math.abs(basePos[0] - ai1[0]), 2D) + Math.pow(Math.abs(basePos[2] - ai1[2]), 2D));
                    double d4 = d3 * branchSlope;
                    if((double)ai1[1] - d4 > (double)l)
                    {
                        ai3[1] = l;
                    } else
                    {
                        ai3[1] = (int)((double)ai1[1] - d4);
                    }
                    if(checkBlockLine(ai3, ai1) == -1)
                    {
                        ai[k][0] = k1;
                        ai[k][1] = j;
                        ai[k][2] = l1;
                        ai[k][3] = ai3[1];
                        k++;
                    }
                }

                j--;
                i1--;
            }
        }
// k = number of branches
        leafNodes = new int[k][4];
        System.arraycopy(ai, 0, leafNodes, 0, k);
// leafNodes = { x.branchend, y.branchend, z.branchend, y.branchstart }
		return true;
    }

// GENERATE LEAF BLOCKS
    void func_523_a(int i, int j, int k, float f, byte byte0)
    {
        int i1 = (int)((double)f + 0.618D);
        byte byte1 = otherCoordPairs[byte0];
        byte byte2 = otherCoordPairs[byte0 + 3];
        int ai[] = {
            i, j, k
        };
        int ai1[] = {
            0, 0, 0
        };
        int j1 = -i1;
        int k1 = -i1;
        ai1[byte0] = ai[byte0];
        for(; j1 <= i1; j1++)
        {
            ai1[byte1] = ai[byte1] + j1;
            for(int l1 = -i1; l1 <= i1;)
            {
                double d = Math.sqrt(Math.pow((double)Math.abs(j1) + 0.5D, 2D) + Math.pow((double)Math.abs(l1) + 0.5D, 2D));
                if(d > (double)f)
                {
                    l1++;
                } else
                {
                    ai1[byte2] = ai[byte2] + l1;
                    BlockAndMeta i2 = this.getBlock(ai1[0], ai1[1], ai1[2]);
                    if (!i2.areEqual(Blocks.AIR, Blocks.LEAVES))
                    {
                        l1++;
                    } else
                    {
                        this.setBlockAndMetadata(ai1[0], ai1[1], ai1[2], leaf);
                        //worldObj.setBlock(ai1[0], ai1[1], ai1[2], l);
                        l1++;
                    }
                }
            }

        }

    }





	// CHECK IF TIME TO GROW BRANCHES - and partially decide branch length
    float layerSize(int i)
    {
        if(trunkSize == 0){
            //100% branch density
            return heightLimit - rand.nextFloat();
        }else if(trunkSize == 3){
            //100% branch density
            //if(field_881_b.nextFloat() > 1.0F) return -1.618F;
        }else if(trunkSize > 1){
            //70% branch density
            if(rand.nextFloat() > 0.70F) return -1.618F;
        }
        //Branch tips have to be at least 30% up the tree.
        if(trunkSize == 3){
          if((double)i < (double)(float)heightLimit * 0.19999999999999999D){
            return -1.618F;
          }
	}
        if(trunkSize < 3){
          if((double)i < (double)(float)heightLimit * 0.29999999999999999D){
            return -1.618F;
          }
	}
        if(trunkSize == 4){
          if((double)i < (double)(float)heightLimit * 0.15999999999999999D){
            return -1.618F;
          }
	}
        float f = (float)heightLimit / 2.0F;
        float f1 = (float)heightLimit / 2.0F - (float)i;
        float f2;
        if(f1 == 0.0F)
        {
        //If at middle of tree, pass.
            f2 = f;
        } else
        if(Math.abs(f1) >= f)
        {
        //If off the tree, fail.
            f2 = 0.0F;
        } else
        {
        //This will always pass.
            f2 = (float)Math.sqrt(Math.pow(Math.abs(f), 2D) - Math.pow(Math.abs(f1), 2D));
        }
        f2 *= 0.5F;
        return f2;
    }

//  LEAF GEN CHECK RADIUS
    float leafSize(int i)
    {
        if(i < 0 || i >= leafDistanceLimit)
        {
            return -1F;
        }
        return i != 0 && i != leafDistanceLimit - 1 ? 3F : 2.0F;
    }

//  GENERATE LEAF BLOCKS somehow
    void generateLeafNode(int i, int j, int k)
    {
        int l = j;
        for(int i1 = j + leafDistanceLimit; l < i1; l++)
        {
            float f = leafSize(l - j);
            func_523_a(i, l, k, f, (byte)1);
            //func_523_a(i, l, k, f, (byte)1, 18);
        }

    }

// GENERATES WOOD BLOCKS FROM ai TO ai1 (used by trunk and branch)
    void placeBlockLine(int ai[], int ai1[])
    {
        int ai2[] = {
            0, 0, 0
        };
        byte byte0 = 0;
        int j = 0;
        for(; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if(Math.abs(ai2[byte0]) > Math.abs(ai2[j]))
            {
                j = byte0;
            }
        }

        if(ai2[j] == 0)
        {
            return;
        }
        byte byte1 = otherCoordPairs[j];
        byte byte2 = otherCoordPairs[j + 3];
        byte byte3;
        if(ai2[j] > 0)
        {
            byte3 = 1;
        } else
        {
            byte3 = -1;
        }
        double d = (double)ai2[byte1] / (double)ai2[j];
        double d1 = (double)ai2[byte2] / (double)ai2[j];
        int ai3[] = {
            0, 0, 0
        };
        int k = 0;
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double((double)(ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.floor_double((double)ai[byte1] + (double)k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double((double)ai[byte2] + (double)k * d1 + 0.5D);
            this.setBlockAndMetadata(ai3[0], ai3[1], ai3[2], wood);
            //worldObj.setBlock(ai3[0], ai3[1], ai3[2], i);
        }

    }

// GROW LEAVES FROM BRANCHES
    void generateLeaves()
    {
        int j = this.leafNodes.length;
        for(int i = 0; i < j; i++)
        {
            int k = this.leafNodes[i][0];
            int l = this.leafNodes[i][1];
            int i1 = this.leafNodes[i][2];
            generateLeafNode(k, l, i1);
        }

    }

// Clips off low branches.
    boolean leafNodeNeedsBase(int i)
    {
        if(trunkSize != 2) return true;
        return (double)i >= (double)heightLimit * 0.2D;
    }

// GENERATES TRUNK
    void generateTrunk()
    {
        //int qq = 17;
        //int qr = 0;
        //if(trunkSize == 3){
        //    qr = 1;
        //}
        int i = basePos[0];
        int j = basePos[1];
//        if(trunkSize >= 1) j = j - 1;
//        if(trunkSize > 3) j = j - 1;
//if(trunksize == 2) j = j + 1;
        int k = basePos[1] + height + 2;
        int l = basePos[2];
        int ai[] = {
            i, j, l
        };
        int ai1[] = {
            i, k, l
        };
        //Create various trunk sizes.
/*        placeBlockLine(ai, ai1, 17);
        if(trunkSize == 2)
        {
            ai[0]++;
            ai1[0]++;
            placeBlockLine(ai, ai1, 17);
            ai[2]++;
            ai1[2]++;
            placeBlockLine(ai, ai1, 17);
            ai[0]--;
            ai1[0]--;
            placeBlockLine(ai, ai1, 17);
        }
*/
        if(trunkSize == 1)
        {
            placeBlockLine(ai, ai1);
        }
        if(trunkSize == 2)
        {
rootAlt = 0;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            growRoot(ai[0],ai[1]-2,ai[2],5.0/8.0,-1.0/16.0);
rootAlt = 1;
            growRoot(ai[0],ai[1],ai[2],5.7/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[0]++;
            ai1[0]++;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            growRoot(ai[0],ai[1],ai[2],6.3/8.0,-1.0/16.0);
            growRoot(ai[0],ai[1]-2,ai[2],7.0/8.0,-1.0/16.0);
rootAlt = 1;
            growRoot(ai[0],ai[1],ai[2],7.7/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[2]++;
            ai1[2]++;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            growRoot(ai[0],ai[1],ai[2],0.3/8.0,-1.0/16.0);
            growRoot(ai[0],ai[1]-2,ai[2],1.0/8.0,-1.0/16.0);
rootAlt = 1;
            growRoot(ai[0],ai[1],ai[2],1.7/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[0]--;
            ai1[0]--;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            growRoot(ai[0],ai[1],ai[2],2.3/8.0,-1.0/16.0);
            growRoot(ai[0],ai[1]-2,ai[2],3.0/8.0,-1.0/16.0);
rootAlt = 1;
            growRoot(ai[0],ai[1],ai[2],3.7/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            growRoot(ai[0],ai[1],ai[2]-1,4.3/8.0,-1.0/16.0);
        }
        if(trunkSize == 3)
        {
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            placeBlockLine(ai, ai1);
/*
            ai[0]++;
            ai1[0]++;
            placeBlockLine(ai, ai1);
            ai[2]++;
            ai1[2]++;
            placeBlockLine(ai, ai1);
            ai[0]--;
            ai1[0]--;
            placeBlockLine(ai, ai1);
*/
            ai[0]++;
            ai1[0]++;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            placeBlockLine(ai, ai1);
            ai[2]++;
            ai1[2]++;
            ai[0]--;
            ai1[0]--;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            placeBlockLine(ai, ai1);
            ai[2]--;
            ai1[2]--;
            ai[0]--;
            ai1[0]--;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            placeBlockLine(ai, ai1);
            ai[2]--;
            ai1[2]--;
            ai[0]++;
            ai1[0]++;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            placeBlockLine(ai, ai1);
        }
        if(trunkSize == 4)
        {
rootAlt = 10;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            growRoot(ai[0],ai[1]+1,ai[2],5.0/8.0,-1.0/16.0);
/*            growRoot(ai[0],ai[1],ai[2],4.3/8.0,-1.0/16.0);
            growRoot(ai[0],ai[1],ai[2],5.7/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
*/            ai[0]++;
            ai1[0]++;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            growRoot(ai[0],ai[1]+1,ai[2],7.0/8.0,-1.0/16.0);
/*            growRoot(ai[0],ai[1],ai[2],6.3/8.0,-1.0/16.0);
            growRoot(ai[0],ai[1],ai[2],7.7/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
*/            ai[2]++;
            ai1[2]++;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            growRoot(ai[0],ai[1]+1,ai[2],1.0/8.0,-1.0/16.0);
/*            growRoot(ai[0],ai[1],ai[2],0.3/8.0,-1.0/16.0);
            growRoot(ai[0],ai[1],ai[2],1.7/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
*/            ai[0]--;
            ai1[0]--;
            growTapRoot(ai[0],ai[1],ai[2],1.0);
            growRoot(ai[0],ai[1]+1,ai[2],3.0/8.0,-1.0/16.0);
/*            growRoot(ai[0],ai[1],ai[2],2.3/8.0,-1.0/16.0);
            growRoot(ai[0],ai[1],ai[2],3.7/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
*/
            ai[0]--;
            ai1[0]--;
            ai[2]--;
            ai1[2]--;
            growTapRoot(ai[0],ai[1],ai[2],0.5);
            growRoot(ai[0],ai[1]+1,ai[2],4.4/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[0]++;
            ai1[0]++;
            ai[2]--;
            ai1[2]--;
            growTapRoot(ai[0],ai[1],ai[2],0.5);
            growRoot(ai[0],ai[1]+1,ai[2],5.6/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[0]++;
            ai1[0]++;
            growTapRoot(ai[0],ai[1],ai[2],0.5);
            growRoot(ai[0],ai[1]+1,ai[2],6.4/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[0]++;
            ai1[0]++;
            ai[2]++;
            ai1[2]++;
            growTapRoot(ai[0],ai[1],ai[2],0.5);
            growRoot(ai[0],ai[1]+1,ai[2],7.6/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[2]++;
            ai1[2]++;
            growTapRoot(ai[0],ai[1],ai[2],0.5);
            growRoot(ai[0],ai[1]+1,ai[2],0.4/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[0]--;
            ai1[0]--;
            ai[2]++;
            ai1[2]++;
            growTapRoot(ai[0],ai[1],ai[2],0.5);
            growRoot(ai[0],ai[1]+1,ai[2],1.6/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[0]--;
            ai1[0]--;
            growTapRoot(ai[0],ai[1],ai[2],0.5);
            growRoot(ai[0],ai[1]+1,ai[2],2.4/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
            ai[0]--;
            ai1[0]--;
            ai[2]--;
            ai1[2]--;
            growTapRoot(ai[0],ai[1],ai[2],0.5);
            growRoot(ai[0],ai[1]+1,ai[2],3.6/8.0,-1.0/16.0);
            placeBlockLine(ai, ai1);
        }
    }

// Grows roots
    void growTapRoot(int i, int j, int k, double flen)
    {
        if(KTreeCfg.rootsEnable == false) return;
        //int len = 2 + (3*trunkSize) + rand.nextInt(2);
        int med;
        int len = (int)((6.0 + rand.nextFloat()*6.0)*flen);
        if(len == tapRootRand || len == tapRootRand + 1 || len == tapRootRand - 1){
          len = (int)((6.0 + rand.nextFloat()*6.0)*flen);
        }
        for(int jj = 1; jj <= len; jj ++){
          med = getMedium(i, j-jj, k); 
          if(med == 1){
            len -= 1;
          }else if(med == 0){
            len = Math.min(len, jj-1);
            break;
          }
        }
        tapRootRand = len;
        for(int jj = 1; jj <= len; jj ++){
          //zz = world.getBlockId(ai[0], ai[1] - jj, ai[2]);
          //if(zz != 0 && zz != trunkBlock && zz != trunkMeta && zz != 2 && zz != 3 && zz != 8 && zz != 9 && zz != 12 && zz != 13) break;
          //else
          this.setBlockAndMetadata(i, j-jj, k, wood);
        }
    }

// GENERATES BRANCHES
    void generateLeafNodeBases()
    {
        //int qq = 17;
        //int qr = 0;
        //if(trunkSize == 3){
        //    qr = 1;
        //}
        int i = 0;
        int j = leafNodes.length;
        int ai[] = {
            basePos[0], basePos[1], basePos[2]
        };
        for(; i < j; i++)
        {
            int ai1[] = leafNodes[i];
            int ai2[] = {
                ai1[0], ai1[1], ai1[2]
            };
            ai[1] = ai1[3];
            int k = ai[1] - basePos[1];
            if(leafNodeNeedsBase(k))
            {
                //placeBlockLine(ai, ai2, 17);
                placeBlockLine(ai, ai2);
            }
        }

    }

// CHECKS IF STUFF IS IN AIR/LEAVES OR IN SOLID
    int checkBlockLine(int ai[], int ai1[])
    {
        int ai2[] = {
            0, 0, 0
        };
        byte byte0 = 0;
        int i = 0;
        for(; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if(Math.abs(ai2[byte0]) > Math.abs(ai2[i]))
            {
                i = byte0;
            }
        }

        if(ai2[i] == 0)
        {
            return -1;
        }
        byte byte1 = otherCoordPairs[i];
        byte byte2 = otherCoordPairs[i + 3];
        byte byte3;
        if(ai2[i] > 0)
        {
            byte3 = 1;
        } else
        {
            byte3 = -1;
        }
        double d = (double)ai2[byte1] / (double)ai2[i];
        double d1 = (double)ai2[byte2] / (double)ai2[i];
        int ai3[] = {
            0, 0, 0
        };
        int j = 0;
        int k = ai2[i] + byte3;
        do
        {
            if(j == k)
            {
                break;
            }
            ai3[i] = ai[i] + j;
            ai3[byte1] = MathHelper.floor_double((double)ai[byte1] + (double)j * d);
            ai3[byte2] = MathHelper.floor_double((double)ai[byte2] + (double)j * d1);
            BlockAndMeta l = this.getBlock(ai3[0], ai3[1], ai3[2]);
            if (!l.isAir() && !l.areEqual(wood, leaf)) {
            	break;
            }
            j += byte3;
        } while(true);
        if(j == k)
        {
            return -1;
        } else
        {
            return Math.abs(j);
        }
    }

    boolean validTreeLocation()
    {
    	if (!isSupportedBaseBlock(basePos[0], basePos[1] - 1, basePos[2])) {
    		return false;
    	}

        return true;
    }

    public void func_517_a(double d, double d1, double d2)
    {
        heightLimitLimit = (int)(d * 12D);
/*        if(d > 0.5D)
        {
            leafDistanceLimit = 5;
        }
        field_873_j = d1;
        field_872_k = d2;
*/    }


	@Override
	public boolean generate(WorldWrapper world, Random random, int x, int y, int z) {
		worldObject = world;
        long l = random.nextLong();
        rand.setSeed(l);
        basePos[0] = x;
        basePos[1] = y;
        basePos[2] = z;
        
        if (!validTreeLocation()) {
        	worldObject = null;
        	return false;
        }
        
        int[] heightvector = {heightmin, heightmax-heightmin};
        heightLimit = KTreeCfg.vary(rand,heightvector);
        
        rootRand = rand.nextInt(4);
        if(generateLeafNodeList()){ //Generate tree and branch arrays.
        	 //world.lightUpdates = false;
	         generateLeaves(); //Grow leaves from branches.
	         generateTrunk(); //Add trunk blocks to world.
	         generateLeafNodeBases(); //Add branch blocks to world.
	         //world.lightUpdates = true;
	         worldObject = null;
	         return true;
        }else{
        	worldObject = null;
        	return false;
    	}
	}

	
}
