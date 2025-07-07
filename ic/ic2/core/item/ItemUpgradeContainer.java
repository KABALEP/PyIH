/*     */ package ic2.core.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.IMachineUpgradeItem;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemUpgradeContainer
/*     */   extends ItemIC2
/*     */   implements IMachineUpgradeItem, IHandHeldInventory
/*     */ {
/*     */   public ItemUpgradeContainer() {
/*  30 */     super(143);
/*  31 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack item, EntityPlayer player, List<String> list, boolean adv) {
/*  40 */     if (GuiScreen.func_146272_n()) {
/*     */       
/*  42 */       ItemStack[] array = getItems(item);
/*  43 */       for (int i = 0; i < array.length; i++) {
/*     */         
/*  45 */         ItemStack stack = array[i];
/*  46 */         if (stack != null)
/*     */         {
/*  48 */           list.add("" + stack.field_77994_a + "x " + stack.func_82833_r());
/*     */         }
/*     */       } 
/*     */       return;
/*     */     } 
/*  53 */     list.add(StatCollector.func_74838_a("itemInfo.upgradeContainerStored.name"));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
/*  58 */     if (IC2.platform.isSimulating())
/*     */     {
/*  60 */       IC2.platform.launchGui(entityPlayer, getInventory(entityPlayer, itemStack));
/*     */     }
/*  62 */     return itemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExtraProcessTime(ItemStack upgrade, IMachine machine) {
/*  67 */     int result = 0;
/*  68 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/*  70 */       IMachineUpgradeItem item = getItem(stack);
/*  71 */       if (item != null)
/*     */       {
/*  73 */         result += item.getExtraProcessTime(stack, machine) * stack.field_77994_a;
/*     */       }
/*     */     } 
/*  76 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProcessTimeMultiplier(ItemStack upgrade, IMachine machine) {
/*  82 */     double result = 1.0D;
/*  83 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/*  85 */       IMachineUpgradeItem item = getItem(stack);
/*  86 */       if (item != null)
/*     */       {
/*  88 */         result *= Math.pow(item.getProcessTimeMultiplier(stack, machine), stack.field_77994_a);
/*     */       }
/*     */     } 
/*  91 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyDemand(ItemStack upgrade, IMachine machine) {
/*  97 */     int result = 0;
/*  98 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 100 */       IMachineUpgradeItem item = getItem(stack);
/* 101 */       if (item != null)
/*     */       {
/* 103 */         result += item.getExtraEnergyDemand(stack, machine) * stack.field_77994_a;
/*     */       }
/*     */     } 
/* 106 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyDemandMultiplier(ItemStack upgrade, IMachine machine) {
/* 112 */     double result = 1.0D;
/* 113 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 115 */       IMachineUpgradeItem item = getItem(stack);
/* 116 */       if (item != null)
/*     */       {
/* 118 */         result *= Math.pow(item.getEnergyDemandMultiplier(stack, machine), stack.field_77994_a);
/*     */       }
/*     */     } 
/* 121 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyStorage(ItemStack upgrade, IMachine machine) {
/* 127 */     int result = 0;
/* 128 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 130 */       IMachineUpgradeItem item = getItem(stack);
/* 131 */       if (item != null)
/*     */       {
/* 133 */         result += item.getExtraEnergyStorage(stack, machine) * stack.field_77994_a;
/*     */       }
/*     */     } 
/* 136 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyStorageMultiplier(ItemStack upgrade, IMachine machine) {
/* 142 */     double result = 1.0D;
/* 143 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 145 */       IMachineUpgradeItem item = getItem(stack);
/* 146 */       if (item != null)
/*     */       {
/* 148 */         result *= Math.pow(item.getEnergyStorageMultiplier(stack, machine), stack.field_77994_a);
/*     */       }
/*     */     } 
/* 151 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraTier(ItemStack upgrade, IMachine machine) {
/* 157 */     int result = 0;
/* 158 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 160 */       IMachineUpgradeItem item = getItem(stack);
/* 161 */       if (item != null)
/*     */       {
/* 163 */         result += item.getExtraTier(stack, machine) * stack.field_77994_a;
/*     */       }
/*     */     } 
/* 166 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTick(ItemStack upgrade, IMachine machine) {
/* 172 */     boolean flag = false;
/* 173 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 175 */       IMachineUpgradeItem item = getItem(stack);
/* 176 */       if (item != null)
/*     */       {
/* 178 */         flag = (item.onTick(stack, machine) || flag);
/*     */       }
/*     */     } 
/* 181 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProcessEnd(ItemStack upgrade, IMachine machine, List<ItemStack> results) {
/* 187 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 189 */       IMachineUpgradeItem item = getItem(stack);
/* 190 */       if (item != null)
/*     */       {
/* 192 */         item.onProcessEnd(stack, machine, results);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useRedstoneinverter(ItemStack upgrade, IMachine machine) {
/* 200 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 202 */       IMachineUpgradeItem item = getItem(stack);
/* 203 */       if (item != null)
/*     */       {
/* 205 */         if (item.useRedstoneinverter(stack, machine))
/*     */         {
/* 207 */           return true;
/*     */         }
/*     */       }
/*     */     } 
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInstalling(ItemStack upgrade, IMachine machine) {
/* 217 */     List<Integer> ints = new ArrayList<>();
/* 218 */     ItemStack[] items = getItems(upgrade);
/* 219 */     for (int i = 0; i < items.length; i++) {
/*     */       
/* 221 */       IMachineUpgradeItem item = getItem(items[i]);
/* 222 */       if (item != null) {
/*     */         
/* 224 */         IMachine.UpgradeType type = item.getType(items[i]);
/* 225 */         if (machine.getSupportedTypes().contains(type))
/*     */         {
/* 227 */           ints.add(Integer.valueOf(i));
/*     */         }
/*     */       } 
/*     */     } 
/* 231 */     for (Integer slot : ints) {
/*     */       
/* 233 */       IMachineUpgradeItem item = getItem(items[slot.intValue()]);
/* 234 */       item.onInstalling(items[slot.intValue()], machine);
/*     */     } 
/* 236 */     int[] data = new int[ints.size()];
/* 237 */     for (int j = 0; j < ints.size(); j++)
/*     */     {
/* 239 */       data[j] = ((Integer)ints.get(j)).intValue();
/*     */     }
/* 241 */     setValidSlots(upgrade, data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSoundVolumeMultiplier(ItemStack upgrade, IMachine machine) {
/* 247 */     float result = 1.0F;
/* 248 */     for (ItemStack stack : getValidItems(upgrade)) {
/*     */       
/* 250 */       IMachineUpgradeItem item = getItem(stack);
/* 251 */       if (item != null)
/*     */       {
/* 253 */         result = (float)(result * Math.pow(item.getEnergyStorageMultiplier(stack, machine), stack.field_77994_a));
/*     */       }
/*     */     } 
/* 256 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMachineUpgradeItem getItem(ItemStack par1) {
/* 261 */     if (par1 == null || !(par1.func_77973_b() instanceof IMachineUpgradeItem))
/*     */     {
/* 263 */       return null;
/*     */     }
/* 265 */     return (IMachineUpgradeItem)par1.func_77973_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachine.UpgradeType getType(ItemStack par1) {
/* 271 */     return IMachine.UpgradeType.Custom;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IHasGui getInventory(EntityPlayer p0, ItemStack p1) {
/* 277 */     return (IHasGui)new HandHeldUpgradeContainer(p0, p1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidSlots(ItemStack item, int[] par1) {
/* 283 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(item);
/* 284 */     nbt.func_74783_a("ValidSlots", par1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getValidSlots(ItemStack item) {
/* 289 */     return StackUtil.getOrCreateNbtData(item).func_74759_k("ValidSlots");
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getValidItems(ItemStack par1) {
/* 294 */     int[] validSlots = getValidSlots(par1);
/* 295 */     ItemStack[] items = new ItemStack[validSlots.length];
/* 296 */     ItemStack[] possible = getItems(par1);
/* 297 */     for (int i = 0; i < validSlots.length; i++)
/*     */     {
/* 299 */       items[i] = possible[validSlots[i]];
/*     */     }
/* 301 */     return items;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getItems(ItemStack par1) {
/* 306 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 307 */     NBTTagList list = nbt.func_150295_c("Items", 10);
/* 308 */     ItemStack[] items = new ItemStack[3];
/* 309 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/* 311 */       NBTTagCompound slotNBT = list.func_150305_b(i);
/* 312 */       int slot = slotNBT.func_74771_c("Slot");
/* 313 */       if (slot >= 0 && slot < 3)
/*     */       {
/* 315 */         items[slot] = ItemStack.func_77949_a(slotNBT);
/*     */       }
/*     */     } 
/* 318 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemUpgradeContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */