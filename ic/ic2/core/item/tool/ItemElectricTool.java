/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ItemElectricTool
/*     */   extends ItemTool
/*     */   implements IElectricItem
/*     */ {
/*     */   public int co;
/*     */   public int operationEnergyCost;
/*     */   public int maxCharge;
/*     */   public int transferLimit;
/*     */   public int tier;
/*  41 */   public Set mineableBlocks = new HashSet();
/*     */   
/*     */   public int iconIndex;
/*     */   
/*     */   public ItemElectricTool(int sprite, Item.ToolMaterial toolmaterial, int operationEnergyCost) {
/*  46 */     super(0.0F, toolmaterial, new HashSet());
/*  47 */     this.iconIndex = sprite;
/*  48 */     this.operationEnergyCost = operationEnergyCost;
/*  49 */     func_77656_e(27);
/*  50 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  56 */     return Ic2Icons.getTexture("i1")[this.iconIndex];
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack tool, Block block) {
/*  61 */     if (!ElectricItem.manager.canUse(tool, this.operationEnergyCost))
/*     */     {
/*  63 */       return 1.0F;
/*     */     }
/*  65 */     if (ForgeHooks.isToolEffective(tool, block, 0))
/*     */     {
/*  67 */       return this.field_77864_a;
/*     */     }
/*     */     
/*  70 */     if (func_150897_b(block))
/*     */     {
/*  72 */       return this.field_77864_a;
/*     */     }
/*  74 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDigSpeed(ItemStack tool, Block block, int md) {
/*  80 */     if (!ElectricItem.manager.canUse(tool, this.operationEnergyCost))
/*     */     {
/*  82 */       return 1.0F;
/*     */     }
/*  84 */     if (ForgeHooks.isToolEffective(tool, block, md))
/*     */     {
/*  86 */       return this.field_77864_a;
/*     */     }
/*  88 */     if (func_150897_b(block))
/*     */     {
/*  90 */       return this.field_77864_a;
/*     */     }
/*  92 */     return super.getDigSpeed(tool, block, md);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_150897_b(Block block) {
/*  98 */     return this.mineableBlocks.contains(block);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77644_a(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap func_111205_h() {
/* 120 */     Multimap multimap = super.func_111205_h();
/* 121 */     multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", this.co, 0));
/* 122 */     return multimap;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextureFile() {
/* 127 */     return "/ic2/sprites/item_0.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack par1) {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getChargedItem(ItemStack itemStack) {
/* 138 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEmptyItem(ItemStack itemStack) {
/* 144 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack itemStack) {
/* 150 */     return this.maxCharge;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack itemStack) {
/* 156 */     return this.transferLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack par1) {
/* 161 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_150894_a(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLiving) {
/* 167 */     Block block = par2World.func_147439_a(par4, par5, par6);
/* 168 */     if (block == null) {
/*     */       
/* 170 */       IC2.getInstance();
/* 171 */       IC2.log.debug("ItemElectricTool.onBlockDestroyed(): received invalid block id " + par3);
/* 172 */       return false;
/*     */     } 
/*     */     
/* 175 */     if (block.func_149712_f(par2World, par4, par5, par6) != 0.0D)
/*     */     {
/* 177 */       if (par7EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */         
/* 179 */         ElectricItem.manager.use(par1ItemStack, this.operationEnergyCost, par7EntityLiving);
/*     */       }
/*     */       else {
/*     */         
/* 183 */         ElectricItem.manager.discharge(par1ItemStack, this.operationEnergyCost, this.tier, true, false, false);
/*     */       } 
/*     */     }
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List<ItemStack> itemList) {
/* 197 */     ItemStack charged = new ItemStack((Item)this, 1);
/* 198 */     ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
/* 199 */     itemList.add(charged);
/* 200 */     itemList.add(new ItemStack((Item)this, 1, func_77612_l()));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister p_94581_1_) {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemElectricTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */