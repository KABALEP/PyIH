/*     */ package ic2.core.item.upgrades;
/*     */ 
/*     */ import com.google.common.math.DoubleMath;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManagerExp;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.api.tile.IRecipeMachine;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.math.RoundingMode;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExpCollectorUpgrade
/*     */   extends BaseMetaUpgrade
/*     */ {
/*     */   public int getExtraEnergyDemand(ItemStack upgrade, IMachine machine) {
/*  32 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProcessEnd(ItemStack upgrade, IMachine machine, List<ItemStack> results) {
/*  38 */     if (machine instanceof IRecipeMachine) {
/*     */       
/*  40 */       IRecipeMachine recipe = (IRecipeMachine)machine;
/*  41 */       if (recipe.getRecipeList() != null) {
/*     */         
/*  43 */         IMachineRecipeManager rMachine = recipe.getRecipeList();
/*  44 */         if (rMachine instanceof IMachineRecipeManagerExp) {
/*     */           
/*  46 */           IMachineRecipeManagerExp exp = (IMachineRecipeManagerExp)rMachine;
/*  47 */           for (int i = 0; i < results.size(); i++) {
/*     */             
/*  49 */             ItemStack item = results.get(i);
/*  50 */             if (item != null && item.field_77994_a > 0) {
/*     */               
/*  52 */               float amount = exp.getExpResult(item) * item.field_77994_a;
/*  53 */               amount /= 10.0F;
/*  54 */               addExp(upgrade, amount);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachine.UpgradeType getType() {
/*  65 */     return IMachine.UpgradeType.Processing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  71 */     return "expCollectorUpgrade";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getTexture() {
/*  77 */     return Ic2Icons.getTexture("i0")[140];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/*  83 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addInfo(ItemStack par1, EntityPlayer par2, List<String> par3) {
/*  90 */     DecimalFormat format = new DecimalFormat("0.#");
/*  91 */     par3.add(StatCollector.func_74837_a("itemInfo.upgradeStoredExp.name", new Object[] { format.format(getStoredExp(par1)) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesExtraRightClick(ItemStack item) {
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRightClick(ItemStack item, World world, EntityPlayer player) {
/* 104 */     float exp = getExp(item);
/* 105 */     int realExp = DoubleMath.roundToInt(exp, RoundingMode.DOWN);
/* 106 */     if (realExp > 0) {
/*     */       
/* 108 */       player.func_71023_q(realExp);
/* 109 */       removeExp(item, realExp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addExp(ItemStack item, float exp) {
/* 115 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(item);
/* 116 */     float stuff = nbt.func_74760_g("Exp");
/* 117 */     stuff += exp;
/* 118 */     if (stuff > 250.0F)
/*     */     {
/* 120 */       stuff = 250.0F;
/*     */     }
/* 122 */     nbt.func_74776_a("Exp", stuff);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeExp(ItemStack par1, float exp) {
/* 127 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 128 */     float amount = nbt.func_74760_g("Exp");
/* 129 */     amount -= exp;
/* 130 */     if (amount < 0.0F)
/*     */     {
/* 132 */       amount = 0.0F;
/*     */     }
/* 134 */     nbt.func_74776_a("Exp", amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getExp(ItemStack item) {
/* 139 */     return StackUtil.getOrCreateNbtData(item).func_74760_g("Exp");
/*     */   }
/*     */ 
/*     */   
/*     */   public double getStoredExp(ItemStack par1) {
/* 144 */     return getExp(par1);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\ExpCollectorUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */