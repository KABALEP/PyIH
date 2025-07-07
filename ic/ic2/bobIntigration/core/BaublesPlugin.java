/*    */ package ic2.bobIntigration.core;
/*    */ 
/*    */ import baubles.api.BaublesApi;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IElectricItem;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.util.ItemObject;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class BaublesPlugin
/*    */ {
/*    */   public static boolean chargeFromArmor(ItemStack par1, EntityPlayer par2) {
/* 17 */     boolean inventoryChanged = false;
/* 18 */     IInventory inv = BaublesApi.getBaubles(par2);
/* 19 */     if (inv != null)
/*    */     {
/* 21 */       for (int i = 0; i < inv.func_70302_i_(); i++) {
/*    */         
/* 23 */         ItemStack armorItemStack = inv.func_70301_a(i);
/*    */         
/* 25 */         if (armorItemStack != null && armorItemStack.func_77973_b() instanceof IElectricItem) {
/*    */           
/* 27 */           IElectricItem armorItem = (IElectricItem)armorItemStack.func_77973_b();
/*    */           
/* 29 */           if (armorItem.canProvideEnergy(armorItemStack) && armorItem.getTier(armorItemStack) >= ((IElectricItem)par1.func_77973_b()).getTier(par1)) {
/*    */             
/* 31 */             double transfer = ElectricItem.manager.charge(par1, 2.147483647E9D, 2147483647, true, true);
/*    */             
/* 33 */             transfer = ElectricItem.manager.discharge(armorItemStack, transfer, 2147483647, true, true, false);
/*    */             
/* 35 */             if (transfer > 0.0D) {
/*    */               
/* 37 */               ElectricItem.manager.charge(par1, transfer, 2147483647, true, false);
/*    */               
/* 39 */               inventoryChanged = true;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     }
/* 45 */     return inventoryChanged;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void preventLoading() {
/* 51 */     IC2 ic2 = IC2.getInstance();
/* 52 */     IC2.blockedItems.put("item.itemBatRE", new ItemObject((new BaublesBatteryDischarged(0, 10000, 100, 1)).func_77655_b("itemBatRE").func_77637_a((CreativeTabs)IC2.tabIC2)));
/* 53 */     IC2.blockedItems.put("item.itemBatLamaCrystal", new ItemObject((new BaublesBattery(10, 1000000, 600, 3)).setRarity(EnumRarity.uncommon).func_77655_b("itemBatLamaCrystal").func_77637_a((CreativeTabs)IC2.tabIC2)));
/* 54 */     IC2.blockedItems.put("item.itemBatCrystal", new ItemObject((new BaublesBattery(5, 100000, 250, 2)).func_77655_b("itemBatCrystal").func_77637_a((CreativeTabs)IC2.tabIC2)));
/* 55 */     IC2.blockedItems.put("item.itemBatRECharged", new ItemObject((new BaublesBattery(0, 10000, 100, 1)).func_77655_b("itemBatRECharged").func_77637_a((CreativeTabs)IC2.tabIC2)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static IInventory getBaublesInventory(EntityPlayer par1) {
/* 61 */     return BaublesApi.getBaubles(par1);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bobIntigration\core\BaublesPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */