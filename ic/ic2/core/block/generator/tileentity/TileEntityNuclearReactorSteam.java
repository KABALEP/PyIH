/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.api.reactor.IReactorProduct;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ 
/*     */ public class TileEntityNuclearReactorSteam extends TileEntityNuclearReactor implements ISteamReactor, IFluidHandler {
/*  20 */   private FluidTank water = new FluidTank(2000);
/*  21 */   private FluidTank steam = new FluidTank(20000);
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickRate() {
/*  26 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean produceEnergy() {
/*  32 */     return (this.redstonePowered || this.reactorPower);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsefulItem(ItemStack item) {
/*  38 */     if (item == null)
/*     */     {
/*  40 */       return false;
/*     */     }
/*  42 */     Item id = item.func_77973_b();
/*  43 */     if (id instanceof ISteamReactorComponent || (id instanceof IReactorProduct && ((IReactorProduct)id).isProduct(item)))
/*     */     {
/*  45 */       return true;
/*     */     }
/*     */     
/*  48 */     return (id == Ic2Items.reEnrichedUraniumCell.func_77973_b() || id == Ic2Items.nearDepletedUraniumCell.func_77973_b());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChambers() {
/*  54 */     int size = getReactorSize();
/*  55 */     for (int pass = 0; pass < 2; pass++) {
/*     */       
/*  57 */       for (int y = 0; y < 6; y++) {
/*     */         
/*  59 */         for (int x = 0; x < size; x++) {
/*     */           
/*  61 */           ItemStack thing = getMatrixCoord(x, y);
/*  62 */           if (thing != null && thing.func_77973_b() instanceof ISteamReactorComponent) {
/*     */             
/*  64 */             ISteamReactorComponent comp = (ISteamReactorComponent)thing.func_77973_b();
/*  65 */             comp.processTick(this, thing, x, y, (pass == 0), (this.field_145850_b.func_82737_E() % 20L == 0L && pass == 1));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getActive() {
/*  75 */     return (super.getActive() || produceEnergy());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTank getWaterTank() {
/*  81 */     return this.water;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTank getSteamTank() {
/*  87 */     return this.steam;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/*  93 */     if (resource != null && resource.getFluidID() == FluidRegistry.WATER.getID())
/*     */     {
/*  95 */       return this.water.fill(resource, doFill);
/*     */     }
/*  97 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 103 */     return drain(from, resource.amount, doDrain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 109 */     return this.steam.drain(maxDrain, doDrain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 115 */     return (fluid == FluidRegistry.WATER);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 127 */     return new FluidTankInfo[] { this.water.getInfo(), this.steam.getInfo() };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 133 */     super.func_145839_a(nbt);
/* 134 */     if (nbt.func_74764_b("water"))
/*     */     {
/* 136 */       this.water.readFromNBT(nbt.func_74775_l("water"));
/*     */     }
/* 138 */     if (nbt.func_74764_b("steam"))
/*     */     {
/* 140 */       this.steam.readFromNBT(nbt.func_74775_l("steam"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 146 */     super.func_145841_b(nbt);
/* 147 */     NBTTagCompound waterTank = new NBTTagCompound();
/* 148 */     this.water.writeToNBT(waterTank);
/* 149 */     nbt.func_74782_a("water", (NBTBase)waterTank);
/* 150 */     NBTTagCompound steamTank = new NBTTagCompound();
/* 151 */     this.steam.writeToNBT(steamTank);
/* 152 */     nbt.func_74782_a("steam", (NBTBase)steamTank);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getReactorEnergyOutput() {
/* 158 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityNuclearReactorSteam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */