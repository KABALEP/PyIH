/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.IItemTickListener;
/*    */ import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ItemArmorSolarHelmet
/*    */   extends ItemArmorUtility
/*    */   implements IItemTickListener
/*    */ {
/*    */   int prod;
/*    */   
/*    */   public ItemArmorSolarHelmet(int index, int renderIndex, int producing) {
/* 20 */     super(index, renderIndex, 0);
/* 21 */     func_77656_e(0);
/* 22 */     this.prod = producing;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onTick(EntityPlayer player, ItemStack itemStack) {
/* 28 */     if (player.field_71071_by.field_70460_b[2] != null && player.field_71071_by.field_70460_b[2].func_77973_b() instanceof ic2.api.item.IElectricItem && TileEntitySolarGenerator.isSunVisible(player.field_70170_p, (int)player.field_70165_t, (int)player.field_70163_u + 1, (int)player.field_70161_v))
/*    */     {
/* 30 */       return (ElectricItem.manager.charge(player.field_71071_by.field_70460_b[2], this.prod, 2147483647, true, false) > 0.0D);
/*    */     }
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_77619_b() {
/* 38 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTextureName() {
/* 44 */     return "solar";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack par1ItemStack) {
/* 51 */     if (this.prod == 1)
/*    */     {
/* 53 */       return super.func_77613_e(par1ItemStack);
/*    */     }
/* 55 */     return EnumRarity.uncommon;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorSolarHelmet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */