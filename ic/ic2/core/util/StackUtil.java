/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.inventory.IItemTransporter;
/*     */ import ic2.core.block.inventory.TransporterManager;
/*     */ import ic2.core.block.inventory.filter.BackupItemFilter;
/*     */ import ic2.core.block.personal.IPersonalBlock;
/*     */ import ic2.core.block.personal.IPersonalInventory;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackUtil
/*     */ {
/*  34 */   private static Direction[] directions = Direction.values();
/*     */ 
/*     */   
/*     */   public static void distributeDrop(TileEntity source, List<ItemStack> itemStacks) {
/*  38 */     for (Direction direction : directions) {
/*     */       
/*  40 */       if (itemStacks.isEmpty()) {
/*     */         break;
/*     */       }
/*     */       
/*  44 */       TileEntity target = direction.applyToTileEntity(source);
/*  45 */       if (target instanceof IInventory && !(target instanceof ic2.core.block.machine.tileentity.TileEntityMiner) && !(target instanceof ic2.core.block.machine.tileentity.TileEntityPump)) {
/*     */         InventoryLargeChest inventoryLargeChest;
/*  47 */         IInventory inventory = (IInventory)target;
/*  48 */         if (target instanceof net.minecraft.tileentity.TileEntityChest)
/*     */         {
/*  50 */           for (Direction direction2 : directions) {
/*     */             
/*  52 */             if (direction2 != Direction.YN && direction2 != Direction.YP) {
/*     */               
/*  54 */               TileEntity target2 = direction2.applyToTileEntity(target);
/*  55 */               if (target2 instanceof net.minecraft.tileentity.TileEntityChest) {
/*     */                 
/*  57 */                 inventoryLargeChest = new InventoryLargeChest("", inventory, (IInventory)target2);
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*  63 */         Iterator<ItemStack> it = itemStacks.iterator();
/*  64 */         while (it.hasNext()) {
/*     */           
/*  66 */           ItemStack itemStackSource = it.next();
/*  67 */           if (itemStackSource != null) {
/*     */             
/*  69 */             int added = putInInventory(inventoryLargeChest, direction, null, itemStackSource);
/*  70 */             itemStackSource.field_77994_a -= added;
/*  71 */             if (itemStackSource.field_77994_a != 0) {
/*     */               continue;
/*     */             }
/*     */             
/*  75 */             it.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  80 */     for (ItemStack itemStack : itemStacks) {
/*     */       
/*  82 */       if (itemStack.field_77994_a > itemStack.func_77976_d()) {
/*     */         
/*  84 */         for (int i = 0; itemStack.field_77994_a > 0 && i < 1000; i++) {
/*     */           
/*  86 */           ItemStack drop = splitStack(itemStack, itemStack.func_77976_d());
/*  87 */           dropAsEntity(source.func_145831_w(), source.field_145851_c, source.field_145848_d, source.field_145849_e, drop);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*  92 */       dropAsEntity(source.func_145831_w(), source.field_145851_c, source.field_145848_d, source.field_145849_e, itemStack);
/*     */     } 
/*     */ 
/*     */     
/*  96 */     itemStacks.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack splitStack(ItemStack input, int size) {
/* 101 */     if (input == null)
/*     */     {
/* 103 */       return null;
/*     */     }
/* 105 */     ItemStack copy = input.func_77946_l();
/* 106 */     if (input.field_77994_a > size) {
/*     */       
/* 108 */       input.field_77994_a -= size;
/* 109 */       copy.field_77994_a = size;
/* 110 */       return copy;
/*     */     } 
/*     */ 
/*     */     
/* 114 */     input.field_77994_a = 0;
/* 115 */     return copy;
/*     */   }
/*     */   
/*     */   public static ItemStack getFromInventory(Object target, Direction dir, UUID owner, IItemTransporter.IFilter filter, int amount, boolean doRemove) {
/*     */     IPersonalInventory iPersonalInventory;
/*     */     IInventory iInventory1;
/* 121 */     if (!(target instanceof IInventory) && !(target instanceof IPersonalBlock))
/*     */     {
/* 123 */       return null;
/*     */     }
/* 125 */     IInventory inventory = null;
/* 126 */     if (target instanceof IPersonalBlock)
/*     */     {
/* 128 */       iPersonalInventory = ((IPersonalBlock)target).getInventory(owner);
/*     */     }
/* 130 */     if (iPersonalInventory == null && target instanceof IInventory)
/*     */     {
/* 132 */       iInventory1 = (IInventory)target;
/*     */     }
/* 134 */     if (iInventory1 == null)
/*     */     {
/* 136 */       return null;
/*     */     }
/* 138 */     IItemTransporter transporter = TransporterManager.manager.getTransporter(iInventory1);
/* 139 */     if (transporter == null)
/*     */     {
/* 141 */       return null;
/*     */     }
/* 143 */     return transporter.removeItem(filter, dir.toForgeDirection(), amount, doRemove);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getFromInventory(IInventory inventory, Direction dir, ItemStack itemStackDestination) {
/* 148 */     BackupItemFilter backupItemFilter = new BackupItemFilter(itemStackDestination);
/* 149 */     return getFromInventory(inventory, dir, (UUID)null, (IItemTransporter.IFilter)backupItemFilter, 1, true);
/*     */   }
/*     */   public static int putInInventory(Object target, Direction dir, UUID owner, ItemStack itemStackSource) {
/*     */     IPersonalInventory iPersonalInventory;
/*     */     IInventory iInventory1;
/* 154 */     if (!(target instanceof IInventory) && !(target instanceof IPersonalBlock))
/*     */     {
/* 156 */       return 0;
/*     */     }
/* 158 */     IInventory inventory = null;
/* 159 */     if (target instanceof IPersonalBlock)
/*     */     {
/* 161 */       iPersonalInventory = ((IPersonalBlock)target).getInventory(owner);
/*     */     }
/* 163 */     if (iPersonalInventory == null && target instanceof IInventory)
/*     */     {
/* 165 */       iInventory1 = (IInventory)target;
/*     */     }
/* 167 */     if (iInventory1 == null)
/*     */     {
/* 169 */       return 0;
/*     */     }
/* 171 */     IItemTransporter item = TransporterManager.manager.getTransporter(iInventory1);
/* 172 */     if (item == null)
/*     */     {
/* 174 */       return 0;
/*     */     }
/* 176 */     ItemStack added = item.addItem(itemStackSource, dir.toForgeDirection(), true);
/* 177 */     if (added == null)
/*     */     {
/* 179 */       return 0;
/*     */     }
/* 181 */     return added.field_77994_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean putInInventory(IInventory inventory, Direction dir, ItemStack itemStackSource) {
/* 186 */     return (putInInventory(inventory, dir, (UUID)null, itemStackSource) >= itemStackSource.field_77994_a);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void dropAsEntity(World world, int x, int y, int z, ItemStack itemStack) {
/* 192 */     if (itemStack == null) {
/*     */       return;
/*     */     }
/*     */     
/* 196 */     double f = 0.7D;
/* 197 */     double dx = world.field_73012_v.nextFloat() * f + (1.0D - f) * 0.5D;
/* 198 */     double dy = world.field_73012_v.nextFloat() * f + (1.0D - f) * 0.5D;
/* 199 */     double dz = world.field_73012_v.nextFloat() * f + (1.0D - f) * 0.5D;
/* 200 */     EntityItem entityItem = new EntityItem(world, x + dx, y + dy, z + dz, itemStack.func_77946_l());
/* 201 */     entityItem.field_145804_b = 10;
/* 202 */     world.func_72838_d((Entity)entityItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack copyWithSize(ItemStack itemStack, int newSize) {
/* 207 */     ItemStack ret = itemStack.func_77946_l();
/* 208 */     ret.field_77994_a = newSize;
/* 209 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack) {
/* 214 */     NBTTagCompound ret = itemStack.func_77978_p();
/* 215 */     if (ret == null) {
/*     */       
/* 217 */       ret = new NBTTagCompound();
/* 218 */       itemStack.func_77982_d(ret);
/*     */     } 
/* 220 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack, String data) {
/* 225 */     NBTTagCompound ret = itemStack.func_77978_p();
/* 226 */     if (ret == null || !ret.func_74764_b(data))
/*     */     {
/* 228 */       itemStack.func_77983_a(data, (NBTBase)new NBTTagCompound());
/*     */     }
/* 230 */     return itemStack.func_77978_p().func_74775_l(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeNBTDataToItemStack(ItemStack itemStack, NBTTagCompound data, String name) {
/* 235 */     itemStack.func_77983_a(name, (NBTBase)data);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStackEqual(ItemStack stack1, ItemStack stack2) {
/* 240 */     return (stack1 != null && stack2 != null && stack1.func_77973_b() == stack2.func_77973_b() && (stack1.func_77973_b().func_77645_m() || stack1.func_77960_j() == stack2.func_77960_j() || stack1.func_77960_j() == 32767 || stack2.func_77960_j() == 32767));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack copyWithWildCard(ItemStack itemStack) {
/* 245 */     ItemStack ret = itemStack.func_77946_l();
/* 246 */     Items.field_151100_aR.setDamage(ret, 32767);
/* 247 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasOreTag(String oreTag, ItemStack item) {
/* 252 */     for (int id : OreDictionary.getOreIDs(item)) {
/*     */       
/* 254 */       if (oreTag.equals(OreDictionary.getOreName(id)))
/*     */       {
/* 256 */         return true;
/*     */       }
/*     */     } 
/* 259 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasOreID(ItemStack item) {
/* 264 */     int[] ints = OreDictionary.getOreIDs(item);
/* 265 */     if (ints == null || ints.length == 0)
/*     */     {
/* 267 */       return false;
/*     */     }
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFirstOreID(ItemStack item) {
/* 274 */     int[] ints = OreDictionary.getOreIDs(item);
/* 275 */     if (ints == null || ints.length == 0)
/*     */     {
/* 277 */       return null;
/*     */     }
/* 279 */     return OreDictionary.getOreName(ints[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addOreIfMissing(String id, ItemStack item) {
/* 284 */     boolean empty = OreDictionary.getOres(id).isEmpty();
/* 285 */     if (empty || IC2.enableOreDictOverride)
/*     */     {
/* 287 */       OreDictionary.registerOre(id, item.func_77946_l());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addInfo(ItemStack par1, EnumChatFormatting format, List<String> list) {
/* 293 */     NBTTagCompound nbt = getOrCreateNbtData(par1);
/* 294 */     NBTTagCompound display = nbt.func_74775_l("display");
/* 295 */     nbt.func_74782_a("display", (NBTBase)display);
/* 296 */     NBTTagList lore = display.func_150295_c("Lore", 8);
/* 297 */     display.func_74782_a("Lore", (NBTBase)lore);
/* 298 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/* 300 */       lore.func_74742_a((NBTBase)new NBTTagString(list.get(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addToolTip(ItemStack stack, String text) {
/* 306 */     NBTTagCompound nbt = getOrCreateNbtData(stack);
/* 307 */     NBTTagCompound display = nbt.func_74775_l("display");
/* 308 */     nbt.func_74782_a("display", (NBTBase)display);
/* 309 */     NBTTagList lore = display.func_150295_c("Lore", 8);
/* 310 */     display.func_74782_a("Lore", (NBTBase)lore);
/* 311 */     lore.func_74742_a((NBTBase)new NBTTagString(text));
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\StackUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */