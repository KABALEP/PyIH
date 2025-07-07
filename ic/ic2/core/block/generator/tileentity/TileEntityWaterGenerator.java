/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.generator.container.ContainerWaterGenerator;
/*     */ import ic2.core.util.AabbUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityWaterGenerator
/*     */   extends TileEntityBaseGenerator
/*     */   implements ISidedInventory
/*     */ {
/*  29 */   public static AabbUtil.IBlockFilter filter = new WaterFilter();
/*  30 */   public static Random randomizer = new Random();
/*     */   public int ticker;
/*  32 */   public int water = 0;
/*  33 */   public int microStorage = 0;
/*  34 */   public int maxWater = 2000;
/*     */   
/*     */   public int fuelMultiplier;
/*     */   
/*     */   public TileEntityWaterGenerator() {
/*  39 */     this(1);
/*  40 */     this.ticker = randomizer.nextInt(tickRate());
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityWaterGenerator(int par4) {
/*  45 */     super(2 * par4, 2 * par4, 2 * par4);
/*  46 */     this.fuelMultiplier = par4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  52 */     super.onLoaded();
/*  53 */     updateWaterCount();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int gaugeFuelScaled(int i) {
/*  59 */     if (this.fuel <= 0)
/*     */     {
/*  61 */       return 0;
/*     */     }
/*  63 */     return this.fuel * i / this.maxWater;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gainEnergy() {
/*  69 */     if (isConverting()) {
/*     */       
/*  71 */       this.storage = (short)(this.storage + (short)this.production);
/*  72 */       this.fuel--;
/*  73 */       getNetwork().updateTileGuiField((TileEntity)this, "fuel");
/*  74 */       return true;
/*     */     } 
/*  76 */     return (this.water > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gainFuel() {
/*  82 */     if (this.inventory[1] != null && this.maxWater - this.fuel >= 500) {
/*     */       
/*  84 */       if (this.inventory[1].func_77973_b() == Items.field_151131_as) {
/*     */         
/*  86 */         this.production = this.fuelMultiplier;
/*  87 */         this.fuel += 500 / this.fuelMultiplier;
/*  88 */         this.inventory[1].func_150996_a(Items.field_151133_ar);
/*  89 */         return true;
/*     */       } 
/*  91 */       if (this.inventory[1].func_77973_b() == Ic2Items.waterCell.func_77973_b()) {
/*     */         
/*  93 */         this.production = this.fuelMultiplier * 2;
/*  94 */         this.fuel += 500 / this.fuelMultiplier;
/*  95 */         ItemStack itemStack = this.inventory[1];
/*  96 */         itemStack.field_77994_a--;
/*  97 */         if ((this.inventory[1]).field_77994_a <= 0)
/*     */         {
/*  99 */           this.inventory[1] = null;
/*     */         }
/* 101 */         return true;
/*     */       } 
/* 103 */       FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.inventory[1]);
/* 104 */       if (liquid != null && liquid.getFluidID() == FluidRegistry.WATER.getID())
/*     */       {
/* 106 */         this.fuel += liquid.amount / 2 / this.fuelMultiplier;
/* 107 */         if (this.inventory[1].func_77973_b().hasContainerItem(this.inventory[1])) {
/*     */           
/* 109 */           this.production = this.fuelMultiplier;
/* 110 */           this.inventory[1] = this.inventory[1].func_77973_b().getContainerItem(this.inventory[1]);
/*     */         }
/*     */         else {
/*     */           
/* 114 */           this.production = this.fuelMultiplier * 2;
/* 115 */           ItemStack itemStack2 = this.inventory[1];
/* 116 */           itemStack2.field_77994_a--;
/* 117 */           if ((this.inventory[1]).field_77994_a <= 0)
/*     */           {
/* 119 */             this.inventory[1] = null;
/*     */           }
/*     */         } 
/* 122 */         return true;
/*     */       }
/*     */     
/* 125 */     } else if (this.fuel <= 0) {
/*     */       
/* 127 */       flowPower();
/* 128 */       this.production = this.microStorage / 100;
/* 129 */       this.microStorage -= this.production * 100;
/* 130 */       if (this.production > 0) {
/*     */         
/* 132 */         this.fuel++;
/* 133 */         return true;
/*     */       } 
/* 135 */       return false;
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean gainFuelSub(ItemStack stack) {
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsFuel() {
/* 148 */     return (this.fuel <= this.maxWater);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flowPower() {
/* 153 */     if (this.ticker++ % tickRate() == 0)
/*     */     {
/* 155 */       updateWaterCount();
/*     */     }
/* 157 */     this.water = this.water * IC2.energyGeneratorWater / 100;
/* 158 */     if (this.water > 0)
/*     */     {
/* 160 */       this.microStorage += this.water;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWaterCount() {
/* 166 */     this.water = AabbUtil.getBlockCount(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, filter, true, false, Arrays.asList(ForgeDirection.VALID_DIRECTIONS));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 172 */     return "Water Mill";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 177 */     return "block.generator.gui.GuiWaterGenerator";
/*     */   }
/*     */ 
/*     */   
/*     */   public int tickRate() {
/* 182 */     return 128;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOperationSoundFile() {
/* 188 */     return "Generators/WatermillLoop.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delayActiveUpdate() {
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 199 */     return (ContainerIC2)new ContainerWaterGenerator(entityPlayer, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 214 */     return new int[] { 1 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/* 220 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class WaterFilter
/*     */     implements AabbUtil.IBlockFilter
/*     */   {
/*     */     public boolean isBlockValid(Block block, int meta) {
/* 228 */       return (block == Blocks.field_150358_i || block == Blocks.field_150355_j);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isBlockValid(World world, int x, int y, int z) {
/* 234 */       Block block = world.func_147439_a(x, y, z);
/* 235 */       return (block == Blocks.field_150358_i || block == Blocks.field_150355_j);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityWaterGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */