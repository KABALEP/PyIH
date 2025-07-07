/*    */ package ic2.core.util;
/*    */ 
/*    */ import ic2.api.info.IEnergyValueProvider;
/*    */ import ic2.api.info.IFuelValueProvider;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntityFurnace;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemInfo
/*    */   implements IEnergyValueProvider, IFuelValueProvider
/*    */ {
/*    */   public double getEnergyValue(ItemStack itemStack) {
/* 25 */     Item item = itemStack.func_77973_b();
/* 26 */     if (item == null)
/*    */     {
/* 28 */       return 0.0D;
/*    */     }
/* 30 */     if (item instanceof ic2.api.item.IElectricItem)
/*    */     {
/* 32 */       return ElectricItem.manager.getCharge(itemStack);
/*    */     }
/* 34 */     if (item == Ic2Items.suBattery.func_77973_b())
/*    */     {
/* 36 */       return 1000.0D;
/*    */     }
/* 38 */     if (item == Items.field_151137_ax)
/*    */     {
/* 40 */       return 500.0D;
/*    */     }
/* 42 */     if (item == Item.func_150898_a(Blocks.field_150451_bX))
/*    */     {
/* 44 */       return 4500.0D;
/*    */     }
/* 46 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFuelValue(ItemStack itemStack, boolean allowLava) {
/* 53 */     Item itemId = itemStack.func_77973_b();
/*    */     
/* 55 */     FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(itemStack);
/*    */     
/* 57 */     if (liquid != null && liquid.getFluid() == FluidRegistry.LAVA) {
/*    */       
/* 59 */       if (allowLava)
/*    */       {
/* 61 */         return 2000;
/*    */       }
/* 63 */       return 0;
/*    */     } 
/* 65 */     if (itemId == Ic2Items.filledFuelCan.func_77973_b()) {
/*    */       
/* 67 */       NBTTagCompound data = StackUtil.getOrCreateNbtData(itemStack);
/* 68 */       if (!data.func_74764_b("value") && itemStack.func_77960_j() > 0)
/*    */       {
/* 70 */         data.func_74768_a("value", itemStack.func_77960_j());
/*    */       }
/* 72 */       int fv = data.func_74762_e("value") * 2;
/* 73 */       return (fv > 500) ? 500 : fv;
/*    */     } 
/* 75 */     if (itemId == Ic2Items.scrap.func_77973_b() && !IC2.enableBurningScrap)
/*    */     {
/* 77 */       return 0;
/*    */     }
/* 79 */     return TileEntityFurnace.func_145952_a(itemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\ItemInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */