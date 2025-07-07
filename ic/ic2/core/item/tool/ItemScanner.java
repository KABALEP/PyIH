/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IScannerItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.item.BasicElectricItem;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemScanner
/*     */   extends BasicElectricItem
/*     */   implements IScannerItem
/*     */ {
/*     */   public ItemScanner(int index, int t) {
/*  23 */     super(index);
/*  24 */     this.maxCharge = 10000;
/*  25 */     this.transferLimit = 50;
/*  26 */     this.tier = t;
/*  27 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/*  32 */     if (!ElectricItem.manager.use(itemstack, getEnergyCost(itemstack), (EntityLivingBase)entityplayer))
/*     */     {
/*  34 */       return itemstack;
/*     */     }
/*  36 */     IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/ODScanner.ogg", true, IC2.audioManager.defaultVolume);
/*  37 */     if (searchOreValue(itemstack)) {
/*     */       
/*  39 */       int value = getOreValueOfArea(itemstack, world, Util.roundToNegInf(entityplayer.field_70165_t), Util.roundToNegInf(entityplayer.field_70163_u), Util.roundToNegInf(entityplayer.field_70161_v));
/*  40 */       IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.scannerResultValue.name", new Object[] { Integer.valueOf(value) }));
/*     */     }
/*     */     else {
/*     */       
/*  44 */       int value = getOreValueOfArea(itemstack, world, Util.roundToNegInf(entityplayer.field_70165_t), Util.roundToNegInf(entityplayer.field_70163_u), Util.roundToNegInf(entityplayer.field_70161_v));
/*  45 */       IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.scannerResultDensity.name", new Object[] { Integer.valueOf(value) }));
/*     */     } 
/*  47 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int valueOfArea(ItemStack stack, World worldObj, int x, int y, int z, int range, boolean oreValue) {
/*  52 */     int totalScore = 0;
/*  53 */     int blocksScanned = 0;
/*  54 */     for (int blockY = y; blockY > 0; blockY--) {
/*     */       
/*  56 */       for (int blockX = x - range; blockX <= x + range; blockX++) {
/*     */         
/*  58 */         for (int blockZ = z - range; blockZ <= z + range; blockZ++) {
/*     */           
/*  60 */           Block blockId = worldObj.func_147439_a(blockX, blockY, blockZ);
/*  61 */           int metaData = worldObj.func_72805_g(blockX, blockY, blockZ);
/*  62 */           if (oreValue) {
/*     */             
/*  64 */             totalScore += getOreValue(stack, blockId, metaData);
/*     */           }
/*  66 */           else if (isValuableOre(stack, blockId, metaData)) {
/*     */             
/*  68 */             totalScore++;
/*     */           } 
/*  70 */           blocksScanned++;
/*     */         } 
/*     */       } 
/*     */     } 
/*  74 */     return (blocksScanned > 0) ? (int)(1000.0D * totalScore / blocksScanned) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int startLayerScan(ItemStack itemStack) {
/*  80 */     return ElectricItem.manager.use(itemStack, getEnergyCost(itemStack), null) ? getRange(itemStack) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValuableOre(ItemStack par1, Block block, int metaData) {
/*  86 */     return (getOreValue(par1, block, metaData) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOreValue(ItemStack par1, Block block, int metaData) {
/*  92 */     if (IC2.valuableOres.containsKey(block)) {
/*     */       
/*  94 */       Map<Integer, Integer> metaMap = (Map<Integer, Integer>)IC2.valuableOres.get(block);
/*  95 */       if (metaMap.containsKey(Integer.valueOf(32767)))
/*     */       {
/*  97 */         return ((Integer)metaMap.get(Integer.valueOf(32767))).intValue();
/*     */       }
/*  99 */       if (metaMap.containsKey(Integer.valueOf(metaData)))
/*     */       {
/* 101 */         return ((Integer)metaMap.get(Integer.valueOf(metaData))).intValue();
/*     */       }
/*     */     } 
/* 104 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOreValueOfArea(ItemStack par1, World world, int x, int y, int z) {
/* 110 */     return valueOfArea(par1, world, x, y, z, getRange(par1), searchOreValue(par1));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRange(ItemStack par1) {
/* 115 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean searchOreValue(ItemStack par1) {
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost(ItemStack par1) {
/* 125 */     return 50;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */