/*    */ package ic2.bobIntigration;
/*    */ 
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import ic2.bobIntigration.core.BaublesPlugin;
/*    */ import ic2.core.ISubModul;
/*    */ import ic2.core.util.IElectricHelper;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubModul
/*    */   implements ISubModul, IElectricHelper
/*    */ {
/*    */   public boolean canLoad() {
/* 18 */     return Loader.isModLoaded("Baubles");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void beforeItemLoad() {
/* 24 */     BaublesPlugin.preventLoading();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void afterItemLoad() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostLoad() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModulName() {
/* 41 */     return "Baubles Modul";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean supportsSide(Side par1) {
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean chargeFromArmor(ItemStack par1, EntityPlayer par2) {
/* 53 */     return BaublesPlugin.chargeFromArmor(par1, par2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IInventory getBaublesInventory(EntityPlayer par1) {
/* 59 */     return BaublesPlugin.getBaublesInventory(par1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getConfigName() {
/* 65 */     return "Baubles";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bobIntigration\SubModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */