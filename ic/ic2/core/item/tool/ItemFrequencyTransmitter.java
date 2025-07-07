/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTeleporter;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class ItemFrequencyTransmitter
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemFrequencyTransmitter(int index) {
/*  22 */     super(index);
/*  23 */     this.field_77777_bU = 1;
/*  24 */     func_77656_e(0);
/*  25 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/*  30 */     if (IC2.platform.isSimulating())
/*     */     {
/*  32 */       if (itemstack.func_77960_j() == 0) {
/*     */         
/*  34 */         NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
/*  35 */         if (nbtData.func_74767_n("targetSet"))
/*     */         {
/*  37 */           nbtData.func_74757_a("targetSet", false);
/*  38 */           IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.clearFreqTransmitter.name"));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  43 */         itemstack.func_77964_b(0);
/*     */       } 
/*     */     }
/*  46 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float hitX, float hitY, float hitZ) {
/*  51 */     TileEntity tileEntity = world.func_147438_o(i, j, k);
/*  52 */     if (tileEntity instanceof TileEntityTeleporter && IC2.platform.isSimulating()) {
/*     */       
/*  54 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
/*  55 */       boolean targetSet = nbtData.func_74767_n("targetSet");
/*  56 */       int targetX = nbtData.func_74762_e("targetX");
/*  57 */       int targetY = nbtData.func_74762_e("targetY");
/*  58 */       int targetZ = nbtData.func_74762_e("targetZ");
/*  59 */       int targetDimension = nbtData.func_74762_e("targetDimension");
/*  60 */       World targetWorld = (world.field_73011_w.field_76574_g == targetDimension) ? world : getWorld(targetDimension);
/*  61 */       TileEntityTeleporter tp = (TileEntityTeleporter)tileEntity;
/*  62 */       if (targetSet) {
/*     */         
/*  64 */         boolean prevWorldChunkLoadOverride = targetWorld.field_72987_B;
/*  65 */         targetWorld.field_72987_B = true;
/*  66 */         Chunk chunk = targetWorld.func_72863_F().func_73154_d(targetX >> 4, targetZ >> 4);
/*  67 */         targetWorld.field_72987_B = prevWorldChunkLoadOverride;
/*  68 */         if (chunk == null || chunk.func_150810_a(targetX & 0xF, targetY, targetZ & 0xF) != Block.func_149634_a(Ic2Items.teleporter.func_77973_b()) || chunk.func_76628_c(targetX & 0xF, targetY, targetZ & 0xF) != Ic2Items.teleporter.func_77960_j())
/*     */         {
/*  70 */           targetSet = false;
/*     */         }
/*     */       } 
/*  73 */       if (!targetSet) {
/*     */         
/*  75 */         targetSet = true;
/*  76 */         targetX = tp.field_145851_c;
/*  77 */         targetY = tp.field_145848_d;
/*  78 */         targetZ = tp.field_145849_e;
/*  79 */         targetDimension = (tp.func_145831_w()).field_73011_w.field_76574_g;
/*  80 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.setFreqTransmitter.name"));
/*     */       }
/*  82 */       else if (tp.field_145851_c == targetX && tp.field_145848_d == targetY && tp.field_145849_e == targetZ && tp.func_145831_w() == targetWorld) {
/*     */         
/*  84 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.targetEqualsEmitter.name"));
/*     */       }
/*  86 */       else if (tp.targetSet && tp.targetX == targetX && tp.targetY == targetY && tp.targetZ == targetZ && tp.targetDimension == targetDimension) {
/*     */         
/*  88 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.targetNotChanged.name"));
/*     */       }
/*     */       else {
/*     */         
/*  92 */         tp.setTarget(targetDimension, targetX, targetY, targetZ);
/*  93 */         TileEntity te = targetWorld.func_147438_o(targetX, targetY, targetZ);
/*  94 */         if (te instanceof TileEntityTeleporter) {
/*     */           
/*  96 */           TileEntityTeleporter tp2 = (TileEntityTeleporter)te;
/*  97 */           if (!tp2.targetSet)
/*     */           {
/*  99 */             tp2.setTarget((tp.func_145831_w()).field_73011_w.field_76574_g, tp.field_145851_c, tp.field_145848_d, tp.field_145849_e);
/*     */           }
/*     */         } 
/* 102 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.targetUpdated.name"));
/*     */       } 
/* 104 */       nbtData.func_74757_a("targetSet", targetSet);
/* 105 */       nbtData.func_74768_a("targetX", targetX);
/* 106 */       nbtData.func_74768_a("targetY", targetY);
/* 107 */       nbtData.func_74768_a("targetZ", targetZ);
/* 108 */       nbtData.func_74768_a("targetDimension", targetDimension);
/* 109 */       itemstack.func_77964_b(1);
/* 110 */       return false;
/*     */     } 
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld(int dimID) {
/* 117 */     return (World)MinecraftServer.func_71276_C().func_71218_a(dimID);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemFrequencyTransmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */