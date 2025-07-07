/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.item.IHandHeldInventory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemArmorJetpackNuclear
/*     */   extends ItemArmorJetpack
/*     */   implements IElectricItem, IHandHeldInventory, INuclearJetpack
/*     */ {
/*     */   public ItemArmorJetpackNuclear(int spriteIndex, int armorrendering) {
/*  33 */     super(spriteIndex, armorrendering);
/*  34 */     func_77656_e(27);
/*  35 */     func_77625_d(1);
/*  36 */     setNoRepair();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack itemStack) {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack itemStack) {
/*  49 */     return 30000.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack itemStack) {
/*  55 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack itemStack) {
/*  61 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void use(ItemStack itemStack, int amount) {
/*  67 */     ElectricItem.manager.discharge(itemStack, amount, 2147483647, true, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCharge(ItemStack itemStack) {
/*  73 */     return (int)ElectricItem.manager.discharge(itemStack, 2.147483647E9D, 2147483647, true, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDropPercentage() {
/*  79 */     return 0.05F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPower() {
/*  85 */     return 0.95F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isElectricJetpack() {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IHasGui getInventory(EntityPlayer p0, ItemStack p1) {
/*  97 */     return new NuclearJetpackInventory(p0, p1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
/* 109 */     super.onArmorTick(world, player, itemStack);
/* 110 */     if (world.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     NuclearJetpackLogic reactor = new NuclearJetpackLogic(itemStack, player);
/* 115 */     reactor.onTick(player, itemStack);
/* 116 */     reactor.save(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/* 123 */     ItemStack stack = new ItemStack((Item)this, 1);
/* 124 */     if (getChargedItem(stack) == this) {
/*     */       
/* 126 */       ItemStack charged = new ItemStack((Item)this, 1);
/* 127 */       ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
/* 128 */       itemList.add(charged);
/*     */     } 
/* 130 */     if (getEmptyItem(stack) == this)
/*     */     {
/* 132 */       itemList.add(new ItemStack((Item)this, 1, func_77612_l()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack par1, EntityPlayer par2, List<String> par3, boolean par4) {
/* 140 */     super.func_77624_a(par1, par2, par3, par4);
/*     */     
/* 142 */     if (GuiContainer.func_146272_n()) {
/*     */       
/* 144 */       par3.addAll(Arrays.asList(StatCollector.func_74838_a("itemInfo.nuclearJetpackUsage.name").split("%n ")));
/*     */     }
/*     */     else {
/*     */       
/* 148 */       par3.add(StatCollector.func_74837_a("itemInfo.pressShiftInfo.name", new Object[] { EnumChatFormatting.AQUA, EnumChatFormatting.GRAY }));
/* 149 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 150 */       NBTTagCompound logic = nbt.func_74775_l("Logic");
/* 151 */       par3.add(StatCollector.func_74837_a("itemInfo.nuclearJetpackReactorHeat.name", new Object[] { Integer.valueOf(logic.func_74762_e("Heat")), Integer.valueOf(logic.func_74762_e("MaxHeat")) }));
/* 152 */       par3.add(StatCollector.func_74837_a("itemInfo.nuclearJetpackCompDamage.name", new Object[] { (100 - logic.func_74762_e("PartHeat")) + "%" }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
/* 161 */     if (par3EntityPlayer.func_70093_af()) {
/*     */       
/* 163 */       if (IC2.platform.isSimulating())
/*     */       {
/* 165 */         IC2.platform.launchGui(par3EntityPlayer, getInventory(par3EntityPlayer, par1ItemStack));
/*     */       }
/* 167 */       return par1ItemStack;
/*     */     } 
/* 169 */     return super.func_77659_a(par1ItemStack, par2World, par3EntityPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFuelCost(ItemArmorJetpack.HoverMode hoverMode) {
/* 175 */     switch (hoverMode) {
/*     */       case None:
/* 177 */         return 12;
/* 178 */       case Basic: return 9;
/* 179 */       case Adv: return 12;
/*     */     } 
/* 181 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeight(int worldHight) {
/* 187 */     return (int)(worldHight / 1.1F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getThruster(ItemArmorJetpack.HoverMode hoverMode) {
/* 193 */     switch (hoverMode) {
/*     */       case Adv:
/* 195 */         return 1.5F;
/* 196 */       case Basic: return 1.0F;
/* 197 */       case None: return 0.5F;
/*     */     } 
/* 199 */     return 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDoAdvHover() {
/* 205 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getChargedItem(ItemStack itemStack) {
/* 211 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEmptyItem(ItemStack itemStack) {
/* 217 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/* 224 */     return new ISpecialArmor.ArmorProperties(2, 0.5D, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 230 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDoRocketMode() {
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRocketCharge() {
/* 247 */     return 25000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureName() {
/* 253 */     return "jetpack_Nuclear";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getID(ItemStack item) {
/* 259 */     return StackUtil.getOrCreateNbtData(item).func_74762_e("Uuid");
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorJetpackNuclear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */