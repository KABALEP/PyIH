/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemArmorJetpackQuantumSuit
/*     */   extends ItemArmorQuantumSuit
/*     */ {
/*  16 */   public ItemArmorJetpack jetpack = new QuantumJetpackHelper();
/*     */ 
/*     */   
/*     */   public ItemArmorJetpackQuantumSuit(int index, int armorrendering) {
/*  20 */     super(index, armorrendering, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
/*  27 */     super.func_77624_a(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
/*  28 */     this.jetpack.func_77624_a(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
/*  34 */     super.onArmorTick(world, player, itemStack);
/*  35 */     this.jetpack.onArmorTick(world, player, itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack itemStack) {
/*  41 */     return 2500000.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureName() {
/*  47 */     return "quantumjetpack";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class QuantumJetpackHelper
/*     */     extends ItemArmorJetpackElectric
/*     */   {
/*     */     public QuantumJetpackHelper() {
/*  56 */       super(0, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canDoAdvHover() {
/*  63 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canDoRocketMode() {
/*  69 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getDropPercentage() {
/*  75 */       return 0.05F;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getPower() {
/*  81 */       return 2.5F;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getFuelCost(ItemArmorJetpack.HoverMode hoverMode) {
/*  87 */       switch (hoverMode) {
/*     */         case None:
/*  89 */           return 30;
/*  90 */         case Basic: return 27;
/*  91 */         case Adv: return 30;
/*     */       } 
/*  93 */       return 27;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMaxHeight(int worldHight) {
/*  99 */       return worldHight;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getThruster(ItemArmorJetpack.HoverMode hoverMode) {
/* 105 */       switch (hoverMode) {
/*     */         case Adv:
/* 107 */           return 3.5F;
/* 108 */         case Basic: return 2.5F;
/* 109 */         case None: return 1.5F;
/*     */       } 
/* 111 */       return 2.5F;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMaxRocketCharge() {
/* 117 */       return 30000;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorJetpackQuantumSuit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */