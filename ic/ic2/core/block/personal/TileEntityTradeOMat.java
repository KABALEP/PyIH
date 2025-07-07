/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.inventory.IItemTransporter;
/*     */ import ic2.core.block.inventory.filter.BasicItemFilter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMachine;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityTradeOMat
/*     */   extends TileEntityMachine
/*     */   implements IPersonalBlock, IHasGui, IPersonalInventory, ISidedInventory, INetworkTileEntityEventListener
/*     */ {
/*  33 */   private static Direction[] directions = Direction.values();
/*  34 */   public static Random randomizer = new Random();
/*     */   private UUID owner;
/*  36 */   public int totalTradeCount = 0;
/*  37 */   public int stock = 0;
/*  38 */   private static int EventTrade = 0;
/*  39 */   private PersonalInventory inv = new PersonalInventory(this, "Trade-O-Mat", 2);
/*     */ 
/*     */   
/*     */   public TileEntityTradeOMat() {
/*  43 */     super(2);
/*  44 */     addNetworkFields(new String[] { "owner" });
/*  45 */     addGuiFields(new String[] { "totalTradeCount", "stock" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  51 */     super.func_145839_a(nbttagcompound);
/*  52 */     if (nbttagcompound.func_74764_b("PlayerOwner")) {
/*     */       
/*  54 */       NBTTagCompound nbt = nbttagcompound.func_74775_l("PlayerOwner");
/*  55 */       this.owner = new UUID(nbt.func_74763_f("UUIDMost"), nbt.func_74763_f("UUIDLeast"));
/*     */     }
/*     */     else {
/*     */       
/*  59 */       this.owner = null;
/*     */     } 
/*  61 */     this.totalTradeCount = nbttagcompound.func_74762_e("totalTradeCount");
/*  62 */     this.inv.readFromNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  68 */     super.func_145841_b(nbttagcompound);
/*  69 */     if (this.owner != null) {
/*     */       
/*  71 */       NBTTagCompound nbt = new NBTTagCompound();
/*  72 */       nbt.func_74772_a("UUIDMost", this.owner.getMostSignificantBits());
/*  73 */       nbt.func_74772_a("UUIDLeast", this.owner.getLeastSignificantBits());
/*  74 */       nbttagcompound.func_74782_a("PlayerOwner", (NBTBase)nbt);
/*     */     } 
/*  76 */     nbttagcompound.func_74768_a("totalTradeCount", this.totalTradeCount);
/*  77 */     this.inv.writeToNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  83 */     super.func_145845_h();
/*  84 */     if (this.inv.func_70301_a(0) != null && this.inv.func_70301_a(1) != null && this.inventory[0] != null && StackUtil.isStackEqual(this.inv.func_70301_a(0), this.inventory[0]) && (this.inventory[0]).field_77994_a >= (this.inv.func_70301_a(0)).field_77994_a && (this.inventory[1] == null || (StackUtil.isStackEqual(this.inv.func_70301_a(1), this.inventory[1]) && (this.inventory[1]).field_77994_a + (this.inv.func_70301_a(1)).field_77994_a <= this.inventory[1].func_77976_d()))) {
/*     */       
/*  86 */       boolean tradePerformed = false;
/*  87 */       for (Direction direction : directions) {
/*     */         
/*  89 */         TileEntity target = direction.applyToTileEntity((TileEntity)this);
/*  90 */         if (target instanceof IInventory && (!(target instanceof TileEntityPersonalChest) || ((TileEntityPersonalChest)target).owner.equals(this.owner))) {
/*     */           
/*  92 */           IInventory targetInventory = (IInventory)target;
/*  93 */           if (target instanceof net.minecraft.tileentity.TileEntityChest)
/*     */           {
/*  95 */             targetInventory = Blocks.field_150486_ae.func_149951_m(target.func_145831_w(), target.field_145851_c, target.field_145848_d, target.field_145849_e);
/*     */           }
/*  97 */           if (targetInventory != null && targetInventory.func_70302_i_() >= 18) {
/*     */             
/*  99 */             int inputSpace = 0;
/* 100 */             int outputAvailable = 0;
/* 101 */             for (int i = 0; i < targetInventory.func_70302_i_(); i++) {
/*     */               
/* 103 */               ItemStack itemStack = targetInventory.func_70301_a(i);
/* 104 */               if (itemStack == null) {
/*     */                 
/* 106 */                 inputSpace += this.inv.func_70301_a(0).func_77976_d();
/*     */               }
/*     */               else {
/*     */                 
/* 110 */                 if (StackUtil.isStackEqual(itemStack, this.inv.func_70301_a(0)))
/*     */                 {
/* 112 */                   inputSpace += itemStack.func_77976_d() - itemStack.field_77994_a;
/*     */                 }
/* 114 */                 if (StackUtil.isStackEqual(itemStack, this.inv.func_70301_a(1)))
/*     */                 {
/* 116 */                   outputAvailable += itemStack.field_77994_a;
/*     */                 }
/*     */               } 
/*     */             } 
/* 120 */             int outputSpace = (this.inventory[1] == null) ? this.inv.func_70301_a(1).func_77976_d() : (this.inventory[1].func_77976_d() - (this.inventory[1]).field_77994_a);
/* 121 */             int tradeCount = Math.min(Math.min(Math.min((this.inventory[0]).field_77994_a / (this.inv.func_70301_a(0)).field_77994_a, inputSpace / (this.inv.func_70301_a(0)).field_77994_a), outputSpace / (this.inv.func_70301_a(1)).field_77994_a), outputAvailable / (this.inv.func_70301_a(1)).field_77994_a);
/* 122 */             if (tradeCount > 0) {
/*     */               
/* 124 */               int inputCount = (this.inv.func_70301_a(0)).field_77994_a * tradeCount;
/* 125 */               int outputCount = (this.inv.func_70301_a(1)).field_77994_a * tradeCount;
/* 126 */               ItemStack itemStack2 = this.inventory[0];
/* 127 */               itemStack2.field_77994_a -= inputCount;
/* 128 */               if ((this.inventory[0]).field_77994_a == 0)
/*     */               {
/* 130 */                 this.inventory[0] = null;
/*     */               }
/* 132 */               ItemStack gs = StackUtil.getFromInventory(targetInventory, direction, this.owner, (IItemTransporter.IFilter)new BasicItemFilter(this.inv.func_70301_a(1).func_77946_l()), outputCount, true);
/* 133 */               if (gs != null)
/*     */               {
/* 135 */                 if (this.inventory[1] == null) {
/*     */                   
/* 137 */                   this.inventory[1] = gs;
/*     */                 }
/*     */                 else {
/*     */                   
/* 141 */                   ItemStack itemStack3 = this.inventory[1];
/* 142 */                   itemStack3.field_77994_a += gs.field_77994_a;
/*     */                 } 
/*     */               }
/* 145 */               ItemStack item = this.inv.func_70301_a(0).func_77946_l();
/* 146 */               item.field_77994_a = inputCount;
/* 147 */               StackUtil.putInInventory(targetInventory, direction, this.owner, item);
/* 148 */               this.totalTradeCount += tradeCount;
/* 149 */               tradePerformed = true;
/* 150 */               getNetwork().initiateTileEntityEvent((TileEntity)this, 0, true);
/* 151 */               func_70296_d();
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 157 */       if (tradePerformed)
/*     */       {
/* 159 */         updateStock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 166 */     super.onLoaded();
/* 167 */     if (isSimulating())
/*     */     {
/* 169 */       updateStock();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateStock() {
/* 175 */     this.stock = 0;
/* 176 */     for (Direction direction : directions) {
/*     */       
/* 178 */       TileEntity target = direction.applyToTileEntity((TileEntity)this);
/* 179 */       if (target instanceof IInventory && (!(target instanceof TileEntityPersonalChest) || ((TileEntityPersonalChest)target).owner.equals(this.owner))) {
/*     */         
/* 181 */         IInventory targetInventory = (IInventory)target;
/* 182 */         if (target instanceof net.minecraft.tileentity.TileEntityChest)
/*     */         {
/* 184 */           targetInventory = Blocks.field_150486_ae.func_149951_m(target.func_145831_w(), target.field_145851_c, target.field_145848_d, target.field_145849_e);
/*     */         }
/* 186 */         if (targetInventory.func_70302_i_() >= 18)
/*     */         {
/* 188 */           for (int i = 0; i < targetInventory.func_70302_i_(); i++) {
/*     */             
/* 190 */             ItemStack stack = targetInventory.func_70301_a(i);
/* 191 */             if (StackUtil.isStackEqual(this.inv.func_70301_a(1), stack))
/*     */             {
/* 193 */               this.stock += stack.field_77994_a;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 199 */     getNetwork().updateTileGuiField((TileEntity)this, "stock");
/* 200 */     getNetwork().updateTileGuiField((TileEntity)this, "totalTradeCount");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 205 */     return canAccess(entityPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(EntityPlayer player) {
/* 211 */     if (this.owner == null) {
/*     */       
/* 213 */       this.owner = player.func_146103_bH().getId();
/* 214 */       getNetwork().updateTileGuiField((TileEntity)this, "owner");
/* 215 */       return true;
/*     */     } 
/* 217 */     return canAccess(player.func_146103_bH().getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(UUID player) {
/* 223 */     if (this.owner == null)
/*     */     {
/* 225 */       return true;
/*     */     }
/* 227 */     return this.owner.equals(player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 233 */     return "Trade-O-Mat";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 239 */     if (canAccess(entityPlayer))
/*     */     {
/* 241 */       return new ContainerTradeOMatOpen(entityPlayer, this);
/*     */     }
/* 243 */     return new ContainerTradeOMatClosed(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 249 */     if (canAccess(entityPlayer))
/*     */     {
/* 251 */       return "block.personal.GuiTradeOMatOpen";
/*     */     }
/* 253 */     return "block.personal.GuiTradeOMatClosed";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 263 */     return new int[] { (var1 == 1) ? 1 : 0 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 275 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/* 280 */     switch (event) {
/*     */ 
/*     */       
/*     */       case 0:
/* 284 */         IC2.audioManager.playOnce(this, PositionSpec.Center, "Machines/o-mat.ogg", true, IC2.audioManager.defaultVolume);
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 289 */     IC2.platform.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + event + ", tile entity below)\nT: " + this + " (" + this.field_145851_c + "," + this.field_145848_d + "," + this.field_145849_e + ")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(EntityPlayer player) {
/* 298 */     if (!canAccess(player))
/*     */     {
/* 300 */       return this;
/*     */     }
/* 302 */     return getInventory(player.func_146103_bH().getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(UUID player) {
/* 308 */     if (!canAccess(player))
/*     */     {
/* 310 */       return this;
/*     */     }
/* 312 */     return this.inv;
/*     */   }
/*     */ 
/*     */   
/*     */   UUID getOwner() {
/* 317 */     return this.owner;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\TileEntityTradeOMat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */