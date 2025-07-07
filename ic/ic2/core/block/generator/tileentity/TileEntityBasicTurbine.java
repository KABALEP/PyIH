/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import com.google.common.math.DoubleMath;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyContainer;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.fluids.IC2Fluid;
/*     */ import java.math.RoundingMode;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityBasicTurbine
/*     */   extends TileEntityBlock
/*     */   implements IEnergySource, IFluidHandler, IEnergyContainer
/*     */ {
/*     */   boolean addedToEnergyNet;
/*     */   public int storedEnergy;
/*     */   public float speed;
/*  33 */   public float[] change = new float[5];
/*  34 */   public byte current = 0;
/*  35 */   public FluidTank tank = new FluidTank(4000);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  44 */     return isSimulating();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/*  56 */     return Math.min(40, this.storedEnergy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/*  62 */     this.storedEnergy = (int)(this.storedEnergy - amount);
/*  63 */     getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/*  69 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/*  75 */     if (resource != null && resource.getFluid() == IC2Fluid.fluids.get("steam"))
/*     */     {
/*  77 */       return this.tank.fill(resource, doFill);
/*     */     }
/*  79 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/*  97 */     return (IC2Fluid.fluids.get("steam") == fluid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 109 */     return new FluidTankInfo[] { this.tank.getInfo() };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 115 */     if (this.speed > 0.0F) {
/*     */       
/* 117 */       if (this.storedEnergy >= 4000)
/*     */       {
/* 119 */         if (this.speed > 0.0F)
/*     */         {
/* 121 */           addChange(-0.005F);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 126 */         int usage = DoubleMath.roundToInt((64.0F * this.speed), RoundingMode.DOWN);
/* 127 */         FluidStack drained = this.tank.drain(usage, true);
/* 128 */         if (drained == null)
/*     */         {
/* 130 */           if (this.speed > 0.0F)
/*     */           {
/* 132 */             addChange(-0.02F);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 137 */           int used = drained.amount;
/* 138 */           int produced = (int)(used / 64.0F * 40.0F);
/* 139 */           this.storedEnergy += produced;
/* 140 */           getNetwork().updateTileGuiField((TileEntity)this, "storedEnergy");
/* 141 */           if (usage > used)
/*     */           {
/* 143 */             if (this.speed > 0.0F)
/*     */             {
/* 145 */               addChange(-0.005F);
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 150 */           else if (this.storedEnergy > 4000)
/*     */           {
/* 152 */             if (this.speed > 0.0F)
/*     */             {
/* 154 */               addChange(-0.02F);
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 159 */           else if (this.speed < 1.0F)
/*     */           {
/* 161 */             addChange(0.01F);
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 170 */     else if (this.tank.getFluidAmount() > 10 && this.storedEnergy <= 1000) {
/*     */       
/* 172 */       addChange(0.01F);
/*     */     } 
/*     */     
/* 175 */     this.current = (byte)(this.current + 1);
/* 176 */     if (this.current >= 5) {
/*     */       
/* 178 */       this.current = 0;
/* 179 */       float data = 0.0F;
/* 180 */       for (int i = 0; i < 5; i++)
/*     */       {
/* 182 */         data += this.change[i];
/*     */       }
/* 184 */       this.change = new float[5];
/* 185 */       data /= 5.0F;
/* 186 */       this.speed += data;
/* 187 */       if (this.speed > 1.0F)
/*     */       {
/* 189 */         this.speed = 1.0F;
/*     */       }
/* 191 */       if (this.speed < 0.0F)
/*     */       {
/* 193 */         this.speed = 0.0F;
/*     */       }
/* 195 */       getNetwork().updateTileGuiField((TileEntity)this, "speed");
/*     */     } 
/* 197 */     setActive((this.speed > 0.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChange(float par1) {
/* 202 */     this.change[this.current] = this.change[this.current] + par1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 208 */     super.onLoaded();
/* 209 */     if (isSimulating() && !this.addedToEnergyNet) {
/*     */       
/* 211 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 212 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 219 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/* 221 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 222 */       this.addedToEnergyNet = false;
/*     */     } 
/* 224 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 230 */     super.func_145839_a(nbt);
/* 231 */     this.storedEnergy = nbt.func_74762_e("Energy");
/* 232 */     this.speed = nbt.func_74760_g("Speed");
/* 233 */     this.tank.readFromNBT(nbt.func_74775_l("Tank"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 239 */     super.func_145841_b(nbt);
/* 240 */     nbt.func_74768_a("Energy", this.storedEnergy);
/* 241 */     nbt.func_74776_a("Speed", this.speed);
/* 242 */     this.tank.writeToNBT(getNBT(nbt, "Tank"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStoredEnergy() {
/* 249 */     return this.storedEnergy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCapacity() {
/* 255 */     return 4000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 261 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyProduction() {
/* 267 */     return DoubleMath.roundToInt((40.0F * this.speed), RoundingMode.DOWN);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyInput() {
/* 273 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityBasicTurbine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */