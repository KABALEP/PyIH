/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySourceInfo;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMachine;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityEnergyOMat
/*     */   extends TileEntityMachine
/*     */   implements IPersonalBlock, IHasGui, IPersonalInventory, ISidedInventory, IEnergySink, IEnergySourceInfo, INetworkClientTileEntityEventListener
/*     */ {
/*  36 */   private static Direction[] directions = Direction.values();
/*  37 */   public int euOffer = 1000;
/*     */   private UUID owner;
/*     */   private boolean addedToEnergyNet = false;
/*  40 */   public int paidFor = 0;
/*  41 */   public int euBuffer = 0;
/*  42 */   private int euBufferMax = 10000;
/*  43 */   private int maxOutputRate = 32;
/*  44 */   private PersonalInventory inv = new PersonalInventory(this, "Energy-O-Mat", 2);
/*     */ 
/*     */   
/*     */   public TileEntityEnergyOMat() {
/*  48 */     super(1);
/*  49 */     addNetworkFields(new String[] { "owner" });
/*  50 */     addGuiFields(new String[] { "paidFor", "euBuffer" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  56 */     return "Energy-O-Mat";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  62 */     super.func_145839_a(nbttagcompound);
/*  63 */     this.euOffer = nbttagcompound.func_74762_e("euOffer");
/*  64 */     this.paidFor = nbttagcompound.func_74762_e("paidFor");
/*  65 */     this.euBuffer = nbttagcompound.func_74762_e("euBuffer");
/*  66 */     if (nbttagcompound.func_74764_b("PlayerOwner")) {
/*     */       
/*  68 */       NBTTagCompound nbt = nbttagcompound.func_74775_l("PlayerOwner");
/*  69 */       this.owner = new UUID(nbt.func_74763_f("UUIDMost"), nbt.func_74763_f("UUIDLeast"));
/*     */     }
/*     */     else {
/*     */       
/*  73 */       this.owner = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  80 */     super.func_145841_b(nbttagcompound);
/*  81 */     nbttagcompound.func_74768_a("euOffer", this.euOffer);
/*  82 */     nbttagcompound.func_74768_a("paidFor", this.paidFor);
/*  83 */     nbttagcompound.func_74768_a("euBuffer", this.euBuffer);
/*  84 */     if (this.owner != null) {
/*     */       
/*  86 */       NBTTagCompound nbt = new NBTTagCompound();
/*  87 */       nbt.func_74772_a("UUIDMost", this.owner.getMostSignificantBits());
/*  88 */       nbt.func_74772_a("UUIDLeast", this.owner.getLeastSignificantBits());
/*  89 */       nbttagcompound.func_74782_a("PlayerOwner", (NBTBase)nbt);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/*  95 */     return canAccess(entityPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(EntityPlayer player) {
/* 101 */     if (this.owner == null) {
/*     */       
/* 103 */       this.owner = player.func_146103_bH().getId();
/* 104 */       getNetwork().updateTileEntityField((TileEntity)this, "owner");
/* 105 */       return true;
/*     */     } 
/* 107 */     return canAccess(player.func_146103_bH().getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(UUID player) {
/* 113 */     if (this.owner == null)
/*     */     {
/* 115 */       return true;
/*     */     }
/* 117 */     return this.owner.equals(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 122 */     super.onLoaded();
/* 123 */     if (isSimulating()) {
/*     */       
/* 125 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 126 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 132 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/* 134 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 135 */       this.addedToEnergyNet = false;
/*     */     } 
/* 137 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 143 */     super.func_145845_h();
/* 144 */     if (isSimulating()) {
/*     */       
/* 146 */       this.euBufferMax = 10000;
/* 147 */       this.maxOutputRate = 32;
/* 148 */       if (this.inv.func_70301_a(1) != null)
/*     */       {
/* 150 */         if (this.inv.func_70301_a(1).func_77969_a(Ic2Items.energyStorageUpgrade)) {
/*     */           
/* 152 */           this.euBufferMax = 10000 * ((this.inv.func_70301_a(1)).field_77994_a + 1);
/*     */         }
/* 154 */         else if (this.inv.func_70301_a(1).func_77969_a(Ic2Items.transformerUpgrade)) {
/*     */           
/* 156 */           this.maxOutputRate = 32 * (int)Math.pow(4.0D, Math.min(4, (this.inv.func_70301_a(1)).field_77994_a));
/*     */         } 
/*     */       }
/* 159 */       if (this.inventory[0] != null && this.inv.func_70301_a(0) != null && this.inventory[0].func_77969_a(this.inv.func_70301_a(0))) {
/*     */         
/* 161 */         int originalStackSize = (this.inventory[0]).field_77994_a;
/* 162 */         for (Direction direction : directions) {
/*     */           
/* 164 */           TileEntity target = direction.applyToTileEntity((TileEntity)this);
/* 165 */           if ((target instanceof IInventory && !(target instanceof IPersonalBlock)) || (target instanceof TileEntityPersonalChest && ((TileEntityPersonalChest)target).canAccess(this.owner))) {
/*     */             
/* 167 */             IInventory targetInventory = (IInventory)target;
/* 168 */             if (target instanceof net.minecraft.tileentity.TileEntityChest)
/*     */             {
/* 170 */               targetInventory = Blocks.field_150486_ae.func_149951_m(target.func_145831_w(), target.field_145851_c, target.field_145848_d, target.field_145849_e);
/*     */             }
/* 172 */             ItemStack stack = this.inventory[0].func_77946_l();
/* 173 */             int amount = StackUtil.putInInventory(targetInventory, direction, this.owner, stack);
/* 174 */             stack.field_77994_a -= amount;
/* 175 */             if (stack.field_77994_a <= 0)
/*     */             {
/* 177 */               stack = null;
/*     */             }
/* 179 */             this.inventory[0] = stack;
/*     */           } 
/*     */         } 
/* 182 */         int numPaymentMoved = originalStackSize - ((this.inventory[0] == null) ? 0 : (this.inventory[0]).field_77994_a);
/* 183 */         if (numPaymentMoved > 0) {
/*     */           
/* 185 */           this.paidFor += this.euOffer / (this.inv.func_70301_a(0)).field_77994_a * numPaymentMoved;
/* 186 */           getNetwork().updateTileGuiField((TileEntity)this, "paidFor");
/* 187 */           func_70296_d();
/*     */         } 
/*     */       } 
/* 190 */       if (this.euBuffer > this.euBufferMax) {
/*     */         
/* 192 */         this.euBuffer = this.euBufferMax;
/* 193 */         getNetwork().updateTileGuiField((TileEntity)this, "euBuffer");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 200 */     return (getFacing() != side && canAccess(entityPlayer));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 205 */     return facingMatchesDirection(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean facingMatchesDirection(ForgeDirection direction) {
/* 210 */     return (direction.ordinal() == getFacing());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 215 */     return !facingMatchesDirection(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 220 */     return Math.min(this.maxOutputRate, this.euBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 226 */     this.euBuffer = (int)(this.euBuffer - amount);
/* 227 */     getNetwork().updateTileGuiField((TileEntity)this, "euBuffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 232 */     return Math.min(this.paidFor, this.euBufferMax - this.euBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volt) {
/* 237 */     int toAdd = (int)Math.min(Math.min(amount, this.paidFor), (this.euBufferMax - this.euBuffer));
/* 238 */     this.paidFor -= toAdd;
/* 239 */     this.euBuffer += toAdd;
/* 240 */     getNetwork().updateTileGuiField((TileEntity)this, "paidFor");
/* 241 */     getNetwork().updateTileGuiField((TileEntity)this, "euBuffer");
/* 242 */     return amount - toAdd;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 247 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 253 */     if (canAccess(entityPlayer))
/*     */     {
/* 255 */       return new ContainerEnergyOMatOpen(entityPlayer, this);
/*     */     }
/* 257 */     return new ContainerEnergyOMatClosed(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 263 */     if (canAccess(entityPlayer))
/*     */     {
/* 265 */       return "block.personal.GuiEnergyOMatOpen";
/*     */     }
/* 267 */     return "block.personal.GuiEnergyOMatClosed";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 277 */     if (!canAccess(player)) {
/*     */       return;
/*     */     }
/*     */     
/* 281 */     switch (event) {
/*     */ 
/*     */       
/*     */       case 0:
/* 285 */         attemptSet(-1000);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 290 */         attemptSet(-100);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 295 */         attemptSet(1000);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 300 */         attemptSet(100);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptSet(int amount) {
/* 308 */     if (this.euOffer + amount <= 0)
/*     */     {
/* 310 */       amount = 0;
/*     */     }
/* 312 */     this.euOffer += amount;
/* 313 */     getNetwork().updateTileGuiField((TileEntity)this, "euOffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 318 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 324 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 330 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 336 */     return EnergyNet.instance.getTierFromPower(this.maxOutputRate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(EntityPlayer player) {
/* 342 */     if (!canAccess(player))
/*     */     {
/* 344 */       return this;
/*     */     }
/* 346 */     return getInventory(player.func_146103_bH().getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(UUID player) {
/* 352 */     if (!canAccess(player))
/*     */     {
/* 354 */       return this;
/*     */     }
/* 356 */     return this.inv;
/*     */   }
/*     */ 
/*     */   
/*     */   UUID getOwner() {
/* 361 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/* 367 */     return 8192;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\TileEntityEnergyOMat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */