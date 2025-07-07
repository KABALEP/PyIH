/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.event.FoamEvent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.wiring.TileEntityCable;
/*     */ import ic2.core.item.ItemGradual;
/*     */ import ic2.core.item.armor.ItemArmorCFPack;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.ChunkPosition;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ 
/*     */ public class ItemSprayer
/*     */   extends ItemGradual
/*     */ {
/*     */   public ItemSprayer(int index) {
/*  28 */     super(index);
/*  29 */     func_77656_e(1602);
/*  30 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
/*  35 */     if (!IC2.platform.isSimulating())
/*     */     {
/*  37 */       return true;
/*     */     }
/*  39 */     ItemStack pack = entityplayer.field_71071_by.field_70460_b[2];
/*  40 */     boolean pulledFromCFPack = (pack != null && pack.func_77973_b() == Ic2Items.cfPack.func_77973_b() && ((ItemArmorCFPack)pack.func_77973_b()).getCFPellet(entityplayer, pack));
/*  41 */     if (!pulledFromCFPack && itemstack.func_77960_j() > 1501)
/*     */     {
/*  43 */       return false;
/*     */     }
/*  45 */     if (world.func_147439_a(i, j, k) == Block.func_149634_a(Ic2Items.scaffold.func_77973_b())) {
/*     */       
/*  47 */       sprayFoam(world, i, j, k, calculateDirectionsFromPlayer(entityplayer), true);
/*  48 */       if (!pulledFromCFPack)
/*     */       {
/*  50 */         itemstack.func_77972_a(100, (EntityLivingBase)entityplayer);
/*     */       }
/*  52 */       return true;
/*     */     } 
/*  54 */     if (l == 0)
/*     */     {
/*  56 */       j--;
/*     */     }
/*  58 */     if (l == 1)
/*     */     {
/*  60 */       j++;
/*     */     }
/*  62 */     if (l == 2)
/*     */     {
/*  64 */       k--;
/*     */     }
/*  66 */     if (l == 3)
/*     */     {
/*  68 */       k++;
/*     */     }
/*  70 */     if (l == 4)
/*     */     {
/*  72 */       i--;
/*     */     }
/*  74 */     if (l == 5)
/*     */     {
/*  76 */       i++;
/*     */     }
/*     */     
/*  79 */     if (sprayFoam(world, i, j, k, calculateDirectionsFromPlayer(entityplayer), false)) {
/*     */       
/*  81 */       if (!pulledFromCFPack)
/*     */       {
/*  83 */         itemstack.func_77972_a(100, (EntityLivingBase)entityplayer);
/*     */       }
/*  85 */       return true;
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean[] calculateDirectionsFromPlayer(EntityPlayer player) {
/*  92 */     float yaw = ((Entity)player).field_70177_z % 360.0F;
/*  93 */     float pitch = ((Entity)player).field_70125_A;
/*  94 */     boolean[] r = { true, true, true, true, true, true };
/*  95 */     if (pitch >= -65.0F && pitch <= 65.0F) {
/*     */       
/*  97 */       if ((yaw >= 300.0F && yaw <= 360.0F) || (yaw >= 0.0F && yaw <= 60.0F))
/*     */       {
/*  99 */         r[2] = false;
/*     */       }
/* 101 */       if (yaw >= 30.0F && yaw <= 150.0F)
/*     */       {
/* 103 */         r[5] = false;
/*     */       }
/* 105 */       if (yaw >= 120.0F && yaw <= 240.0F)
/*     */       {
/* 107 */         r[3] = false;
/*     */       }
/* 109 */       if (yaw >= 210.0F && yaw <= 330.0F)
/*     */       {
/* 111 */         r[4] = false;
/*     */       }
/*     */     } 
/* 114 */     if (pitch <= -40.0F)
/*     */     {
/* 116 */       r[0] = false;
/*     */     }
/* 118 */     if (pitch >= 40.0F)
/*     */     {
/* 120 */       r[1] = false;
/*     */     }
/* 122 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean sprayFoam(World world, int i, int j, int k, boolean[] directions, boolean scaffold) {
/* 127 */     Block blockId = world.func_147439_a(i, j, k);
/* 128 */     FoamEvent.Check eventCheck = new FoamEvent.Check(world, i, j, k);
/* 129 */     MinecraftForge.EVENT_BUS.post((Event)eventCheck);
/* 130 */     if ((!scaffold && !Block.func_149634_a(Ic2Items.constructionFoam.func_77973_b()).func_149742_c(world, i, j, k) && (blockId != Block.func_149634_a(Ic2Items.copperCableBlock.func_77973_b()) || world.func_72805_g(i, j, k) == 13) && !eventCheck.isCanceled()) || (scaffold && blockId != Block.func_149634_a(Ic2Items.scaffold.func_77973_b())))
/*     */     {
/* 132 */       return false;
/*     */     }
/* 134 */     ArrayList<ChunkPosition> check = new ArrayList<>();
/* 135 */     ArrayList<ChunkPosition> place = new ArrayList<>();
/* 136 */     int foamcount = getSprayMass();
/* 137 */     check.add(new ChunkPosition(i, j, k));
/* 138 */     for (int x = 0; x < check.size() && foamcount > 0; x++) {
/*     */       
/* 140 */       ChunkPosition set = check.get(x);
/* 141 */       Block targetBlockId = world.func_147439_a(set.field_151329_a, set.field_151327_b, set.field_151328_c);
/* 142 */       FoamEvent.Check nextCheck = new FoamEvent.Check(world, set.field_151329_a, set.field_151327_b, set.field_151328_c);
/* 143 */       MinecraftForge.EVENT_BUS.post((Event)nextCheck);
/* 144 */       if ((!scaffold && (Block.func_149634_a(Ic2Items.constructionFoam.func_77973_b()).func_149742_c(world, set.field_151329_a, set.field_151327_b, set.field_151328_c) || (targetBlockId == Block.func_149634_a(Ic2Items.copperCableBlock.func_77973_b()) && world.func_72805_g(set.field_151329_a, set.field_151327_b, set.field_151328_c) != 13) || nextCheck.isCanceled())) || (scaffold && targetBlockId == Block.func_149634_a(Ic2Items.scaffold.func_77973_b()))) {
/*     */         
/* 146 */         considerAddingCoord(set, place);
/* 147 */         addAdjacentSpacesOnList(set.field_151329_a, set.field_151327_b, set.field_151328_c, check, directions, scaffold);
/* 148 */         foamcount--;
/*     */       } 
/*     */     } 
/* 151 */     for (ChunkPosition pos : place) {
/*     */       
/* 153 */       Block targetBlockId = world.func_147439_a(pos.field_151329_a, pos.field_151327_b, pos.field_151328_c);
/* 154 */       if (targetBlockId == Block.func_149634_a(Ic2Items.scaffold.func_77973_b())) {
/*     */         
/* 156 */         Block.func_149634_a(Ic2Items.scaffold.func_77973_b()).func_149697_b(world, pos.field_151329_a, pos.field_151327_b, pos.field_151328_c, world.func_72805_g(pos.field_151329_a, pos.field_151327_b, pos.field_151328_c), 0);
/* 157 */         world.func_147449_b(pos.field_151329_a, pos.field_151327_b, pos.field_151328_c, Block.func_149634_a(Ic2Items.constructionFoam.func_77973_b())); continue;
/*     */       } 
/* 159 */       if (targetBlockId == Block.func_149634_a(Ic2Items.copperCableBlock.func_77973_b())) {
/*     */         
/* 161 */         TileEntity te = world.func_147438_o(pos.field_151329_a, pos.field_151327_b, pos.field_151328_c);
/* 162 */         if (!(te instanceof TileEntityCable)) {
/*     */           continue;
/*     */         }
/*     */         
/* 166 */         ((TileEntityCable)te).changeFoam((byte)1);
/*     */         
/*     */         continue;
/*     */       } 
/* 170 */       FoamEvent.Foam foamPlace = new FoamEvent.Foam(world, pos.field_151329_a, pos.field_151327_b, pos.field_151328_c);
/* 171 */       MinecraftForge.EVENT_BUS.post((Event)foamPlace);
/* 172 */       if (!foamPlace.isCanceled())
/*     */       {
/* 174 */         world.func_147449_b(pos.field_151329_a, pos.field_151327_b, pos.field_151328_c, Block.func_149634_a(Ic2Items.constructionFoam.func_77973_b()));
/*     */       }
/*     */     } 
/*     */     
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdjacentSpacesOnList(int x, int y, int z, ArrayList<ChunkPosition> foam, boolean[] directions, boolean ignoreDirections) {
/* 183 */     int[] order = generateRngSpread(IC2.random);
/* 184 */     for (int i = 0; i < order.length; i++) {
/*     */       
/* 186 */       if (ignoreDirections || directions[order[i]])
/*     */       {
/* 188 */         switch (order[i]) {
/*     */ 
/*     */           
/*     */           case 0:
/* 192 */             considerAddingCoord(new ChunkPosition(x, y - 1, z), foam);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 1:
/* 197 */             considerAddingCoord(new ChunkPosition(x, y + 1, z), foam);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 2:
/* 202 */             considerAddingCoord(new ChunkPosition(x, y, z - 1), foam);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 3:
/* 207 */             considerAddingCoord(new ChunkPosition(x, y, z + 1), foam);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 4:
/* 212 */             considerAddingCoord(new ChunkPosition(x - 1, y, z), foam);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 5:
/* 217 */             considerAddingCoord(new ChunkPosition(x + 1, y, z), foam);
/*     */             break;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void considerAddingCoord(ChunkPosition coord, ArrayList<ChunkPosition> list) {
/* 227 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 229 */       if (((ChunkPosition)list.get(i)).field_151329_a == coord.field_151329_a && ((ChunkPosition)list.get(i)).field_151327_b == coord.field_151327_b && ((ChunkPosition)list.get(i)).field_151328_c == coord.field_151328_c) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 234 */     list.add(coord);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] generateRngSpread(Random random) {
/* 239 */     int[] re = { 0, 1, 2, 3, 4, 5 };
/* 240 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 242 */       int first = random.nextInt(6);
/* 243 */       int second = random.nextInt(6);
/* 244 */       int save = re[first];
/* 245 */       re[first] = re[second];
/* 246 */       re[second] = save;
/*     */     } 
/* 248 */     return re;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSprayMass() {
/* 253 */     return 13;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
/* 259 */     Block block = player.field_70170_p.func_147439_a(X, Y, Z);
/* 260 */     if (block == Block.func_149634_a(Ic2Items.constructionFoam.func_77973_b())) {
/*     */       
/* 262 */       player.field_70170_p.func_147468_f(X, Y, Z);
/* 263 */       if (!player.field_70170_p.field_72995_K)
/*     */       {
/* 265 */         player.field_70170_p.func_72838_d((Entity)new EntityItem(player.field_70170_p, X, Y, Z, new ItemStack(Ic2Items.constructionFoam.func_77973_b())));
/*     */       }
/* 267 */       return true;
/*     */     } 
/* 269 */     return super.onBlockStartBreak(itemstack, X, Y, Z, player);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemSprayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */