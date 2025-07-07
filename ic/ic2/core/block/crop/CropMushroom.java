/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ public class CropMushroom
/*     */   extends CropCardBase
/*     */ {
/*     */   boolean red;
/*     */   
/*     */   public CropMushroom(boolean par1) {
/*  20 */     this.red = par1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  26 */     return this.red ? "RedMushroom" : "BrownMushroom";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  32 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  38 */     switch (n) {
/*     */       case 0:
/*  40 */         return 0;
/*  41 */       case 1: return 4;
/*  42 */       case 2: return 0;
/*  43 */       case 3: return 0;
/*  44 */       case 4: return 4;
/*     */     } 
/*  46 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  52 */     String color = this.red ? "Red" : "Brown";
/*  53 */     return new String[] { color, "Food", "Mushroom" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/*  59 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  65 */     return (crop.getSize() < maxSize() && crop.getHumidity() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/*  71 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  77 */     return (crop.getSize() >= 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  83 */     if (crop.getSize() == 3)
/*     */     {
/*  85 */       return this.red ? new ItemStack((Block)Blocks.field_150337_Q) : new ItemStack((Block)Blocks.field_150338_P);
/*     */     }
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSprites(IIconRegister iconRegister) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getSprite(ICropTile crop) {
/* 100 */     int base = 51;
/* 101 */     if (this.red)
/*     */     {
/* 103 */       base += 3;
/*     */     }
/* 105 */     return Ic2Icons.getTexture("bc")[base + crop.getSize()];
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */