/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.inventory.IItemTransporter;
/*     */ import ic2.core.block.inventory.TransporterManager;
/*     */ import ic2.core.block.machine.container.ContainerCropHarvester;
/*     */ import ic2.core.util.AabbUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagIntArray;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.ChunkPosition;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class TileEntityCropHarvester
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui
/*     */ {
/*  34 */   public static AabbUtil.IBlockFilter filter = new CropFilter();
/*  35 */   public CustomInv inv = new CustomInv(this, 5);
/*  36 */   LinkedList<ItemStack> items = new LinkedList<>();
/*  37 */   LinkedList<ChunkPosition> todoList = new LinkedList<>();
/*  38 */   public int nextDelay = 10;
/*  39 */   public int delay = 0;
/*  40 */   public int radius = 1;
/*  41 */   IItemTransporter transporter = null;
/*     */ 
/*     */   
/*     */   public TileEntityCropHarvester() {
/*  45 */     super(9, -1, 50000, 128);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/*  51 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int p_94128_1_) {
/*  57 */     return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  63 */     return "CropHarvester";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/*  69 */     return (ContainerIC2)new ContainerCropHarvester(p0.field_71071_by, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/*  75 */     return "block.machine.gui.GuiCropHarvester";
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
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  87 */     super.func_145839_a(nbt);
/*  88 */     this.todoList.clear();
/*  89 */     this.items.clear();
/*  90 */     NBTTagList list = nbt.func_150295_c("WorkList", 10); int i;
/*  91 */     for (i = 0; i < list.func_74745_c(); i++) {
/*     */       
/*  93 */       int[] pos = list.func_150306_c(i);
/*  94 */       this.todoList.add(new ChunkPosition(pos[0], pos[1], pos[2]));
/*     */     } 
/*  96 */     list = nbt.func_150295_c("ItemList", 10);
/*  97 */     for (i = 0; i < list.func_74745_c(); i++) {
/*     */       
/*  99 */       ItemStack stack = ItemStack.func_77949_a(list.func_150305_b(i));
/* 100 */       if (stack != null)
/*     */       {
/*     */ 
/*     */         
/* 104 */         this.items.add(stack); } 
/*     */     } 
/* 106 */     this.inv.readFromNBT(nbt.func_74775_l("Upgrades"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 112 */     super.func_145841_b(nbt);
/* 113 */     NBTTagList list = new NBTTagList();
/* 114 */     for (ChunkPosition pos : this.todoList) {
/*     */       
/* 116 */       list.func_74742_a((NBTBase)new NBTTagIntArray(new int[] { pos.field_151329_a, pos.field_151327_b, pos.field_151328_c }));
/*     */     } 
/* 118 */     nbt.func_74782_a("WorkList", (NBTBase)list);
/* 119 */     list = new NBTTagList();
/* 120 */     for (ItemStack item : this.items)
/*     */     {
/* 122 */       list.func_74742_a((NBTBase)item.func_77955_b(new NBTTagCompound()));
/*     */     }
/* 124 */     nbt.func_74782_a("ItemList", (NBTBase)list);
/* 125 */     nbt.func_74782_a("Upgrades", (NBTBase)this.inv.writeToNBT());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 131 */     addItemsToInventory();
/* 132 */     if (this.delay > 0) {
/*     */       
/* 134 */       this.delay--;
/*     */       return;
/*     */     } 
/* 137 */     if (!hasEnergy(100)) {
/*     */       
/* 139 */       this.delay += 20;
/*     */       return;
/*     */     } 
/* 142 */     if (this.items.size() > 0) {
/*     */       
/* 144 */       this.delay += 20;
/*     */       return;
/*     */     } 
/* 147 */     this.delay += this.nextDelay;
/* 148 */     if (this.todoList.isEmpty()) {
/*     */       
/* 150 */       this.todoList.addAll(AabbUtil.getTargets(func_145831_w(), this.field_145851_c, this.field_145848_d, this.field_145849_e, this.radius, filter, ForgeDirection.VALID_DIRECTIONS));
/* 151 */       useEnergy(10);
/*     */     } 
/* 153 */     if (this.todoList.isEmpty()) {
/*     */       
/* 155 */       this.delay += 120;
/*     */       return;
/*     */     } 
/* 158 */     ChunkPosition target = this.todoList.remove();
/* 159 */     if (this.todoList.isEmpty())
/*     */     {
/* 161 */       this.delay += 120;
/*     */     }
/* 163 */     TileEntity tile = func_145831_w().func_147438_o(target.field_151329_a, target.field_151327_b, target.field_151328_c);
/* 164 */     useEnergy(1);
/* 165 */     if (tile instanceof ICropTile) {
/*     */       
/* 167 */       this.delay += this.nextDelay;
/* 168 */       ICropTile crop = (ICropTile)tile;
/* 169 */       CropCard card = crop.getCrop();
/* 170 */       if (card == null) {
/*     */         return;
/*     */       }
/*     */       
/* 174 */       if (card.canBeHarvested(crop) && crop.getSize() >= card.getOptimalHavestSize(crop)) {
/*     */         
/* 176 */         ItemStack[] array = crop.harvest_automated(true);
/* 177 */         for (int i = 0; i < array.length; i++) {
/*     */           
/* 179 */           ItemStack stack = array[i];
/* 180 */           if (stack != null)
/*     */           {
/* 182 */             this.items.add(stack);
/*     */           }
/*     */         } 
/* 185 */         addItemsToInventory();
/* 186 */         useEnergy(20);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItemsToInventory() {
/* 193 */     if (this.items.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 197 */     if (this.transporter == null) {
/*     */       
/* 199 */       this.transporter = TransporterManager.manager.getTransporter(this);
/*     */       return;
/*     */     } 
/* 202 */     List<ItemStack> left = new ArrayList<>();
/* 203 */     while (this.items.size() > 0) {
/*     */       
/* 205 */       ItemStack stack = this.items.remove();
/* 206 */       if (stack == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 210 */       ItemStack added = this.transporter.addItem(stack.func_77946_l(), ForgeDirection.UNKNOWN, true);
/* 211 */       if (added != null) {
/*     */         
/* 213 */         stack.field_77994_a -= added.field_77994_a;
/* 214 */         if (stack.field_77994_a > 0)
/*     */         {
/* 216 */           left.add(stack);
/*     */         }
/*     */       } 
/*     */     } 
/* 220 */     this.items.addAll(left);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverclockerUpgrade() {
/* 225 */     int overclocker = 0;
/* 226 */     int newRange = 1;
/* 227 */     for (int i = 0; i < this.inv.func_70302_i_(); i++) {
/*     */       
/* 229 */       ItemStack stack = this.inv.func_70301_a(i);
/* 230 */       if (stack != null)
/*     */       {
/* 232 */         if (StackUtil.isStackEqual(stack, Ic2Items.overclockerUpgrade)) {
/*     */           
/* 234 */           overclocker++;
/*     */         }
/* 236 */         else if (StackUtil.isStackEqual(stack, Ic2Items.padUpgradeBasicFieldUpgrade)) {
/*     */           
/* 238 */           newRange++;
/*     */         }
/* 240 */         else if (StackUtil.isStackEqual(stack, Ic2Items.padUpgradeFieldUpgrade)) {
/*     */           
/* 242 */           newRange += 2;
/*     */         }
/* 244 */         else if (StackUtil.isStackEqual(stack, Ic2Items.padUpgradeAdvFieldUpgrade)) {
/*     */           
/* 246 */           newRange += 3;
/*     */         } 
/*     */       }
/*     */     } 
/* 250 */     if (newRange > 5)
/*     */     {
/* 252 */       newRange = 5;
/*     */     }
/* 254 */     if (newRange < this.radius)
/*     */     {
/* 256 */       this.todoList.clear();
/*     */     }
/* 258 */     this.radius = newRange;
/* 259 */     if (overclocker < 1) { this.nextDelay = 20; }
/* 260 */     else if (overclocker < 2) { this.nextDelay = 10; }
/* 261 */     else if (overclocker < 3) { this.nextDelay = 5; }
/* 262 */     else if (overclocker < 4) { this.nextDelay = 2; }
/* 263 */     else if (overclocker >= 4) { this.nextDelay = 0; }
/*     */   
/*     */   }
/*     */   
/*     */   public static class CustomInv
/*     */     implements IInventory {
/*     */     TileEntityCropHarvester harvest;
/*     */     ItemStack[] array;
/*     */     int size;
/*     */     
/*     */     public CustomInv(TileEntityCropHarvester tile, int slots) {
/* 274 */       this.size = slots;
/* 275 */       this.harvest = tile;
/* 276 */       this.array = new ItemStack[slots];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int func_70302_i_() {
/* 282 */       return this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70301_a(int slot) {
/* 287 */       return this.array[slot];
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70298_a(int slot, int amount) {
/* 292 */       if (this.array[slot] != null) {
/*     */         
/* 294 */         if ((this.array[slot]).field_77994_a > amount)
/*     */         {
/* 296 */           return this.array[slot].func_77979_a(amount);
/*     */         }
/* 298 */         return func_70304_b(slot);
/*     */       } 
/* 300 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70304_b(int slot) {
/* 305 */       ItemStack result = this.array[slot];
/* 306 */       this.array[slot] = null;
/* 307 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70299_a(int slot, ItemStack item) {
/* 312 */       this.array[slot] = item;
/*     */     }
/*     */ 
/*     */     
/*     */     public String func_145825_b() {
/* 317 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_145818_k_() {
/* 322 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_70297_j_() {
/* 327 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70296_d() {
/* 332 */       this.harvest.setOverclockerUpgrade();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_70300_a(EntityPlayer p_70300_1_) {
/* 337 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70305_f() {}
/*     */ 
/*     */     
/*     */     public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
/* 350 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound nbttagcompound) {
/* 355 */       NBTTagList nbttaglist = nbttagcompound.func_150295_c("Items", 10);
/* 356 */       this.array = new ItemStack[func_70302_i_()];
/* 357 */       for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/*     */         
/* 359 */         NBTTagCompound nbttagcompound2 = nbttaglist.func_150305_b(i);
/* 360 */         byte byte0 = nbttagcompound2.func_74771_c("Slot");
/* 361 */         if (byte0 >= 0 && byte0 < this.array.length)
/*     */         {
/* 363 */           this.array[byte0] = ItemStack.func_77949_a(nbttagcompound2);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public NBTTagCompound writeToNBT() {
/* 370 */       NBTTagList nbttaglist = new NBTTagList();
/* 371 */       for (int i = 0; i < this.array.length; i++) {
/*     */         
/* 373 */         if (this.array[i] != null) {
/*     */           
/* 375 */           NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 376 */           nbttagcompound2.func_74774_a("Slot", (byte)i);
/* 377 */           this.array[i].func_77955_b(nbttagcompound2);
/* 378 */           nbttaglist.func_74742_a((NBTBase)nbttagcompound2);
/*     */         } 
/*     */       } 
/* 381 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 382 */       nbttagcompound.func_74782_a("Items", (NBTBase)nbttaglist);
/* 383 */       return nbttagcompound;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CropFilter
/*     */     implements AabbUtil.IBlockFilter
/*     */   {
/*     */     public boolean isBlockValid(Block block, int meta) {
/* 393 */       return block instanceof ic2.core.block.BlockCrop;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isBlockValid(World world, int x, int y, int z) {
/* 399 */       return world.func_147439_a(x, y, z) instanceof ic2.core.block.BlockCrop;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityCropHarvester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */