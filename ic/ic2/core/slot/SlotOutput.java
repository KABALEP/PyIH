/*     */ package ic2.core.slot;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManagerExp;
/*     */ import ic2.api.tile.IRecipeMachine;
/*     */ import ic2.core.IC2;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class SlotOutput
/*     */   extends Slot
/*     */ {
/*     */   private EntityPlayer player;
/*  20 */   private int crafted = 0;
/*     */ 
/*     */   
/*     */   public SlotOutput(EntityPlayer player, IInventory par1iInventory, int par2, int par3, int par4) {
/*  24 */     super(par1iInventory, par2, par3, par4);
/*  25 */     this.player = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75214_a(ItemStack stack) {
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_75209_a(int p_75209_1_) {
/*  36 */     if (func_75216_d())
/*     */     {
/*  38 */       this.crafted += Math.min(p_75209_1_, (func_75211_c()).field_77994_a);
/*     */     }
/*  40 */     return super.func_75209_a(p_75209_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75210_a(ItemStack stack, int stackSize) {
/*  46 */     IC2.achievements.onMachineOp(this.player, stack, this.field_75224_c);
/*  47 */     this.crafted += stackSize;
/*  48 */     func_75208_c(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_82870_a(EntityPlayer player, ItemStack stack) {
/*  54 */     func_75208_c(stack);
/*  55 */     IC2.achievements.onMachineOp(player, stack, this.field_75224_c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_75208_c(ItemStack p_75208_1_) {
/*  61 */     if (!this.player.field_70170_p.field_72995_K) {
/*     */       
/*  63 */       int i = this.crafted;
/*  64 */       float f = 0.0F;
/*  65 */       if (this.field_75224_c instanceof IRecipeMachine) {
/*     */         
/*  67 */         IMachineRecipeManager manager = ((IRecipeMachine)this.field_75224_c).getRecipeList();
/*  68 */         if (manager != null && manager instanceof IMachineRecipeManagerExp)
/*     */         {
/*  70 */           f = ((IMachineRecipeManagerExp)manager).getExpResult(p_75208_1_);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  76 */       if (f == 0.0F) {
/*     */         
/*  78 */         i = 0;
/*     */       }
/*  80 */       else if (f < 1.0F) {
/*     */         
/*  82 */         int j = MathHelper.func_76141_d(i * f);
/*     */         
/*  84 */         if (j < MathHelper.func_76123_f(i * f) && (float)Math.random() < i * f - j)
/*     */         {
/*  86 */           j++;
/*     */         }
/*     */         
/*  89 */         i = j;
/*     */       } 
/*     */       
/*  92 */       while (i > 0) {
/*     */         
/*  94 */         int j = EntityXPOrb.func_70527_a(i);
/*  95 */         i -= j;
/*  96 */         this.player.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.player.field_70170_p, this.player.field_70165_t, this.player.field_70163_u + 0.5D, this.player.field_70161_v + 0.5D, j));
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     this.crafted = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\slot\SlotOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */