/*    */ package ic2.neiIntegration.core;
/*    */ 
/*    */ import codechicken.nei.guihook.IContainerTooltipHandler;
/*    */ import ic2.api.crops.CropCard;
/*    */ import ic2.api.crops.Crops;
/*    */ import ic2.core.item.ItemCropSeed;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropHelper
/*    */   implements IContainerTooltipHandler
/*    */ {
/*    */   public List<String> handleItemDisplayName(GuiContainer arg0, ItemStack arg1, List<String> arg2) {
/* 22 */     return arg2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> handleItemTooltip(GuiContainer arg0, ItemStack arg1, int arg2, int arg3, List<String> arg4) {
/* 28 */     if (arg0 instanceof ic2.core.item.tool.GuiCropnalyzer)
/*    */     {
/* 30 */       handleCrops(arg1, arg4);
/*    */     }
/* 32 */     return arg4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> handleTooltip(GuiContainer arg0, int arg1, int arg2, List<String> arg3) {
/* 38 */     return arg3;
/*    */   }
/*    */ 
/*    */   
/*    */   private void handleCrops(ItemStack par1, List<String> arguments) {
/* 43 */     if (par1 != null && par1.func_77973_b() instanceof ItemCropSeed) {
/*    */       
/* 45 */       ItemCropSeed seed = (ItemCropSeed)par1.func_77973_b();
/* 46 */       if (ItemCropSeed.getScannedFromStack(par1) == 4) {
/*    */         
/* 48 */         CropCard card = Crops.instance.getCropCard(par1);
/* 49 */         arguments.add(EnumChatFormatting.YELLOW + "Attributes: ");
/* 50 */         String[] list = card.attributes();
/* 51 */         if (list != null && list.length > 0) {
/*    */           
/* 53 */           for (String key : list)
/*    */           {
/* 55 */             arguments.add(EnumChatFormatting.BLUE + key);
/*    */           }
/*    */           return;
/*    */         } 
/* 59 */         arguments.add("None");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\CropHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */