/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMachine;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityFluidOMat
/*     */   extends TileEntityMachine
/*     */   implements IHasGui, IFluidHandler, IPersonalInventory, IPersonalBlock, INetworkClientTileEntityEventListener
/*     */ {
/*  32 */   private static Direction[] directions = Direction.values();
/*     */   private UUID owner;
/*  34 */   public FluidTank fluid = new FluidTank(32000);
/*  35 */   public int transferlimit = 20;
/*     */   public int paidFor;
/*  37 */   public int fluidOffer = 1000;
/*  38 */   private PersonalInventory inv = new PersonalInventory(this, "Fluid-O-Mat", 2);
/*     */ 
/*     */   
/*     */   public TileEntityFluidOMat() {
/*  42 */     super(1);
/*  43 */     addNetworkFields(new String[] { "owner" });
/*  44 */     addNetworkFields(new String[] { "fluid", "paidFor", "fluidOffer" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  50 */     super.func_145839_a(nbt);
/*  51 */     if (nbt.func_74764_b("PlayerOwner")) {
/*     */       
/*  53 */       NBTTagCompound nbtTag = nbt.func_74775_l("PlayerOwner");
/*  54 */       this.owner = new UUID(nbtTag.func_74763_f("UUIDMost"), nbtTag.func_74763_f("UUIDLeast"));
/*     */     }
/*     */     else {
/*     */       
/*  58 */       this.owner = null;
/*     */     } 
/*  60 */     this.paidFor = nbt.func_74762_e("Paid");
/*  61 */     this.fluidOffer = nbt.func_74762_e("Offer");
/*  62 */     if (nbt.func_74764_b("Tank"))
/*     */     {
/*  64 */       this.fluid = this.fluid.readFromNBT(nbt.func_74775_l("Tank"));
/*     */     }
/*  66 */     this.inv.readFromNBT(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/*  72 */     super.func_145841_b(nbt);
/*  73 */     nbt.func_74768_a("Paid", this.paidFor);
/*  74 */     nbt.func_74768_a("Offer", this.fluidOffer);
/*  75 */     NBTTagCompound data = new NBTTagCompound();
/*  76 */     this.fluid.writeToNBT(data);
/*  77 */     nbt.func_74782_a("Tank", (NBTBase)data);
/*  78 */     if (this.owner != null) {
/*     */       
/*  80 */       NBTTagCompound NBTTag = new NBTTagCompound();
/*  81 */       NBTTag.func_74772_a("UUIDMost", this.owner.getMostSignificantBits());
/*  82 */       NBTTag.func_74772_a("UUIDLeast", this.owner.getLeastSignificantBits());
/*  83 */       nbt.func_74782_a("PlayerOwner", (NBTBase)NBTTag);
/*     */     } 
/*  85 */     this.inv.writeToNBT(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 103 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 109 */     if (doFill)
/*     */     {
/* 111 */       getNetwork().updateTileGuiField((TileEntity)this, "fluid");
/*     */     }
/* 113 */     return this.fluid.fill(resource, doFill);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 119 */     return drain(from, resource.amount, doDrain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 125 */     int amount = Math.min(this.paidFor, maxDrain);
/* 126 */     if (amount <= 0)
/*     */     {
/* 128 */       return null;
/*     */     }
/* 130 */     if (doDrain) {
/*     */       
/* 132 */       this.paidFor -= amount;
/* 133 */       getNetwork().updateTileGuiField((TileEntity)this, "paidFor");
/* 134 */       getNetwork().updateTileGuiField((TileEntity)this, "fluid");
/*     */     } 
/* 136 */     return this.fluid.drain(amount, doDrain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 142 */     return (getFacing() != from.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 148 */     return (getFacing() == from.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 154 */     return new FluidTankInfo[] { this.fluid.getInfo() };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 160 */     if (canAccess(p0))
/*     */     {
/* 162 */       return new ContainerFluidOMatOpen(p0, this);
/*     */     }
/* 164 */     return new ContainerFluidOMatClosed(p0, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 170 */     if (canAccess(p0))
/*     */     {
/* 172 */       return "block.personal.GuiFluidOMatOpen";
/*     */     }
/* 174 */     return "block.personal.GuiFluidOMatClosed";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 186 */     return "Fluid-O-Mat";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 192 */     super.func_145845_h();
/* 193 */     if (isSimulating()) {
/*     */       
/* 195 */       if (this.paidFor > 0) {
/*     */         
/* 197 */         int mB = Math.min(this.paidFor, this.transferlimit);
/* 198 */         if (mB > 0) {
/*     */           
/* 200 */           Direction dir = getDirection(getFacing());
/* 201 */           ForgeDirection fDir = dir.toForgeDirection().getOpposite();
/* 202 */           TileEntity tile = dir.applyToTileEntity((TileEntity)this);
/* 203 */           if (tile != null && tile instanceof IFluidHandler) {
/*     */             
/* 205 */             IFluidHandler fluid = (IFluidHandler)tile;
/* 206 */             FluidStack stack = drain(fDir, mB, false);
/* 207 */             if (stack != null && fluid.canFill(fDir, stack.getFluid())) {
/*     */               
/* 209 */               int filled = fluid.fill(fDir, stack, true);
/* 210 */               if (filled > 0)
/*     */               {
/* 212 */                 drain(fDir, filled, true);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 218 */       if (this.inventory[0] != null && this.inv.func_70301_a(0) != null && this.inventory[0].func_77969_a(this.inv.func_70301_a(0))) {
/*     */         
/* 220 */         int originalStackSize = (this.inventory[0]).field_77994_a;
/* 221 */         for (Direction direction : directions) {
/*     */           
/* 223 */           TileEntity target = direction.applyToTileEntity((TileEntity)this);
/* 224 */           if ((target instanceof IInventory && !(target instanceof IPersonalBlock)) || (target instanceof TileEntityPersonalChest && ((TileEntityPersonalChest)target).canAccess(this.owner))) {
/*     */             
/* 226 */             IInventory targetInventory = (IInventory)target;
/* 227 */             if (target instanceof net.minecraft.tileentity.TileEntityChest)
/*     */             {
/* 229 */               targetInventory = Blocks.field_150486_ae.func_149951_m(target.func_145831_w(), target.field_145851_c, target.field_145848_d, target.field_145849_e);
/*     */             }
/* 231 */             ItemStack stack = this.inventory[0].func_77946_l();
/* 232 */             int amount = StackUtil.putInInventory(targetInventory, direction, this.owner, stack);
/* 233 */             stack.field_77994_a -= amount;
/* 234 */             if (stack.field_77994_a <= 0)
/*     */             {
/* 236 */               stack = null;
/*     */             }
/* 238 */             this.inventory[0] = stack;
/*     */           } 
/*     */         } 
/* 241 */         int numPaymentMoved = originalStackSize - ((this.inventory[0] == null) ? 0 : (this.inventory[0]).field_77994_a);
/* 242 */         if (numPaymentMoved > 0) {
/*     */           
/* 244 */           this.paidFor += this.fluidOffer / (this.inv.func_70301_a(0)).field_77994_a * numPaymentMoved;
/* 245 */           ((NetworkManager)IC2.network.get()).updateTileGuiField((TileEntity)this, "paidFor");
/* 246 */           func_70296_d();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Direction getDirection(int dir) {
/* 254 */     for (Direction dirs : directions) {
/*     */       
/* 256 */       if (dirs.toSideValue() == dir)
/*     */       {
/* 258 */         return dirs;
/*     */       }
/*     */     } 
/* 261 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(EntityPlayer player) {
/* 267 */     if (this.owner == null) {
/*     */       
/* 269 */       this.owner = player.func_146103_bH().getId();
/* 270 */       getNetwork().updateTileEntityField((TileEntity)this, "owner");
/* 271 */       return true;
/*     */     } 
/* 273 */     return canAccess(player.func_146103_bH().getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(UUID player) {
/* 279 */     if (this.owner == null)
/*     */     {
/* 281 */       return true;
/*     */     }
/* 283 */     return this.owner.equals(player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 289 */     if (!canAccess(player)) {
/*     */       return;
/*     */     }
/*     */     
/* 293 */     switch (event) {
/*     */ 
/*     */       
/*     */       case 0:
/* 297 */         attemptSet(-1000);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 302 */         attemptSet(-100);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 307 */         attemptSet(1000);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 312 */         attemptSet(100);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 317 */         attemptSet(-10);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 322 */         attemptSet(10);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptSet(int amount) {
/* 330 */     if (this.fluidOffer + amount <= 0)
/*     */     {
/* 332 */       amount = 0;
/*     */     }
/* 334 */     this.fluidOffer += amount;
/* 335 */     getNetwork().updateTileGuiField((TileEntity)this, "fluidOffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 340 */     return (getFacing() != side && canAccess(entityPlayer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 346 */     return canAccess(entityPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(EntityPlayer player) {
/* 352 */     if (!canAccess(player))
/*     */     {
/* 354 */       return this;
/*     */     }
/* 356 */     return getInventory(player.func_146103_bH().getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(UUID player) {
/* 362 */     if (!canAccess(player))
/*     */     {
/* 364 */       return this;
/*     */     }
/* 366 */     return this.inv;
/*     */   }
/*     */ 
/*     */   
/*     */   UUID getOwner() {
/* 371 */     return this.owner;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\TileEntityFluidOMat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */