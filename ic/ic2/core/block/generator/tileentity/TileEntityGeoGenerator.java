/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.generator.container.ContainerBaseGenerator;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidEvent;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ 
/*     */ public class TileEntityGeoGenerator
/*     */   extends TileEntityBaseGenerator
/*     */   implements ISidedInventory, IFluidHandler
/*     */ {
/*     */   public int maxLava;
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/*  28 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/*  33 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityGeoGenerator() {
/*  38 */     super(2, IC2.energyGeneratorGeo, IC2.energyGeneratorGeo);
/*  39 */     this.maxLava = 24000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int gaugeFuelScaled(int i) {
/*  45 */     if (this.fuel <= 0)
/*     */     {
/*  47 */       return 0;
/*     */     }
/*  49 */     return this.fuel * i / this.maxLava;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gainFuel() {
/*  55 */     if (this.inventory[1] == null || this.maxLava - this.fuel < 1000)
/*     */     {
/*  57 */       return false;
/*     */     }
/*  59 */     if (this.inventory[1].func_77973_b() == Items.field_151129_at) {
/*     */       
/*  61 */       this.fuel += 1000;
/*  62 */       this.inventory[1].func_150996_a(Items.field_151133_ar);
/*  63 */       return true;
/*     */     } 
/*  65 */     if (this.inventory[1].func_77973_b() == Ic2Items.lavaCell.func_77973_b()) {
/*     */       
/*  67 */       this.fuel += 1000;
/*  68 */       ItemStack itemStack = this.inventory[1];
/*  69 */       itemStack.field_77994_a--;
/*  70 */       if ((this.inventory[1]).field_77994_a <= 0)
/*     */       {
/*  72 */         this.inventory[1] = null;
/*     */       }
/*  74 */       return true;
/*     */     } 
/*  76 */     FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.inventory[1]);
/*  77 */     if (liquid != null && liquid.getFluidID() == FluidRegistry.LAVA.getID()) {
/*     */       
/*  79 */       this.fuel += liquid.amount;
/*  80 */       if (this.inventory[1].func_77973_b().hasContainerItem(this.inventory[1])) {
/*     */         
/*  82 */         this.inventory[1] = this.inventory[1].func_77973_b().getContainerItem(this.inventory[1]);
/*     */       }
/*     */       else {
/*     */         
/*  86 */         ItemStack itemStack2 = this.inventory[1];
/*  87 */         itemStack2.field_77994_a--;
/*  88 */         if ((this.inventory[1]).field_77994_a <= 0)
/*     */         {
/*  90 */           this.inventory[1] = null;
/*     */         }
/*     */       } 
/*  93 */       return true;
/*     */     } 
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean gainFuelSub(ItemStack stack) {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsFuel() {
/* 106 */     return (this.fuel <= this.maxLava);
/*     */   }
/*     */ 
/*     */   
/*     */   public int distributeLava(int amount) {
/* 111 */     int need = this.maxLava - this.fuel;
/* 112 */     if (need > amount)
/*     */     {
/* 114 */       need = amount;
/*     */     }
/* 116 */     amount -= need;
/* 117 */     this.fuel += need / 2;
/* 118 */     getNetwork().updateTileGuiField((TileEntity)this, "fuel");
/* 119 */     return amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeBucket() {
/* 124 */     return (this.fuel <= 23000);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 130 */     if (isRendering())
/*     */     {
/* 132 */       return "Geothermal Generator";
/*     */     }
/* 134 */     return "Geoth. Generator";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOperationSoundFile() {
/* 140 */     return "Generators/GeothermalLoop.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 145 */     return (ContainerIC2)new ContainerBaseGenerator(entityPlayer, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 150 */     return "block.generator.gui.GuiGeoGenerator";
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockBreak(int a, int b) {
/* 155 */     FluidEvent.fireEvent((FluidEvent)new FluidEvent.FluidSpilledEvent(new FluidStack(FluidRegistry.LAVA, this.fuel), this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 160 */     if (var1 > 1)
/*     */     {
/* 162 */       return new int[0];
/*     */     }
/* 164 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 170 */     return fill(0, resource, doFill);
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(int tankIndex, FluidStack resource, boolean doFill) {
/* 175 */     if (resource == null || resource.getFluidID() != FluidRegistry.LAVA.getID())
/*     */     {
/* 177 */       return 0;
/*     */     }
/* 179 */     int toAdd = Math.min(resource.amount, this.maxLava - this.fuel);
/* 180 */     if (doFill)
/*     */     {
/* 182 */       this.fuel += toAdd;
/*     */     }
/* 184 */     return toAdd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 193 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 199 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 205 */     if (fluid.getID() == FluidRegistry.LAVA.getID())
/*     */     {
/* 207 */       return true;
/*     */     }
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 221 */     return new FluidTankInfo[] { new FluidTankInfo(new FluidStack(FluidRegistry.LAVA, this.fuel), this.maxLava) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/* 227 */     return IC2.energyGeneratorGeo;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityGeoGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */