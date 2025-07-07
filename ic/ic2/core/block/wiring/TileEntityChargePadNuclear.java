/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import com.google.common.math.DoubleMath;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.reactor.ItemReactorUranium;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityChargePadNuclear
/*     */   extends TileEntityChargePad
/*     */ {
/*  23 */   public int nuclearTicks = 0;
/*  24 */   public int energyProduct = 0;
/*     */ 
/*     */   
/*     */   public TileEntityChargePadNuclear() {
/*  28 */     super(TileEntityChargePad.ChargePadType.Nuclear);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected int getMaxParticalAge() {
/*  35 */     return 14;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected float[] getParticalColour(Random rand) {
/*  42 */     float red = 0.9804688F;
/*  43 */     float green = 0.835938F;
/*  44 */     float blue = 0.1367188F;
/*  45 */     if (this.installedUpgrades[TileEntityChargePad.PadUpgrade.Drain.ordinal()]) {
/*     */       
/*  47 */       green /= 2.0F;
/*  48 */       blue /= 2.0F;
/*     */     } 
/*  50 */     if (this.installedUpgrades[TileEntityChargePad.PadUpgrade.Damage.ordinal()]) {
/*     */       
/*  52 */       green = blue = 0.4F;
/*     */     }
/*     */     else {
/*     */       
/*  56 */       red *= 0.6F + rand.nextFloat() * 0.4F;
/*     */     } 
/*  58 */     green *= 0.6F + rand.nextFloat() * 0.4F;
/*  59 */     blue *= 0.6F + rand.nextFloat() * 0.4F;
/*  60 */     return new float[] { red, green, blue };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected double[] getParticleVelocity(Random rand) {
/*  67 */     return new double[] { 0.0D, 7.6D, 0.0D };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected int getParticalAmount(Random rand) {
/*  74 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
/*  80 */     return Ic2Items.nuclearChargePad.func_77946_l();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  86 */     super.func_145839_a(nbt);
/*  87 */     this.nuclearTicks = nbt.func_74762_e("NuclearTicks");
/*  88 */     this.energyProduct = nbt.func_74762_e("NuclearProduction");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/*  94 */     super.func_145841_b(nbt);
/*  95 */     nbt.func_74768_a("NuclearTicks", this.nuclearTicks);
/*  96 */     nbt.func_74768_a("NuclearProduction", this.energyProduct);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean provideEnergy() {
/* 102 */     boolean up = super.provideEnergy();
/* 103 */     if (this.nuclearTicks > 0)
/*     */     {
/* 105 */       this.nuclearTicks--;
/*     */     }
/* 107 */     int demanded = DoubleMath.roundToInt(getDemandedEnergy(), RoundingMode.DOWN);
/* 108 */     if (demanded > 0) {
/*     */       
/* 110 */       if (this.nuclearTicks == 0) {
/*     */         
/* 112 */         ItemStack item = this.inv.func_70301_a(0);
/* 113 */         if (!useCell())
/*     */         {
/* 115 */           return up;
/*     */         }
/* 117 */         up = true;
/* 118 */         this.nuclearTicks = 20;
/* 119 */         this.energyProduct = getEnergy(item);
/*     */       } 
/* 121 */       this.storedEnergy += this.energyProduct;
/* 122 */       getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/*     */     } 
/* 124 */     return up;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isUraniumCell(ItemStack item) {
/* 129 */     return (getEnergy(item) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getEnergy(ItemStack stack) {
/* 134 */     if (stack == null)
/*     */     {
/* 136 */       return 0;
/*     */     }
/* 138 */     Item item = stack.func_77973_b();
/* 139 */     if (item instanceof ItemReactorUranium)
/*     */     {
/* 141 */       return getEnergyFromCellAmount(((ItemReactorUranium)item).numberOfCells);
/*     */     }
/* 143 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getEnergyFromCellAmount(int cells) {
/* 148 */     switch (cells) {
/*     */       case 1:
/* 150 */         return 5;
/* 151 */       case 2: return 20;
/* 152 */       case 4: return 60;
/*     */     } 
/* 154 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean useCell() {
/* 159 */     ItemStack item = this.inv.func_70301_a(0);
/* 160 */     if (!isCell(item))
/*     */     {
/* 162 */       return false;
/*     */     }
/* 164 */     if (item.func_77960_j() + 1 >= item.func_77958_k()) {
/*     */       
/* 166 */       this.inv.func_70299_a(0, createUsedCells(item));
/*     */     }
/*     */     else {
/*     */       
/* 170 */       item.func_77964_b(item.func_77960_j() + 1);
/*     */     } 
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemStack createUsedCells(ItemStack stack) {
/* 177 */     if (stack == null)
/*     */     {
/* 179 */       return null;
/*     */     }
/* 181 */     Item item = stack.func_77973_b();
/* 182 */     if (item instanceof ItemReactorUranium)
/*     */     {
/* 184 */       if (this.field_145850_b.field_73012_v.nextInt(3) == 0)
/*     */       {
/* 186 */         return StackUtil.copyWithSize(Ic2Items.nearDepletedUraniumCell, ((ItemReactorUranium)item).numberOfCells);
/*     */       }
/*     */     }
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCell(ItemStack stack) {
/* 194 */     if (stack == null)
/*     */     {
/* 196 */       return false;
/*     */     }
/* 198 */     Item item = stack.func_77973_b();
/* 199 */     if (item instanceof ItemReactorUranium)
/*     */     {
/* 201 */       return true;
/*     */     }
/* 203 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityChargePadNuclear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */