/*    */ package ic2.core.item;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.util.IExtraData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ 
/*    */ public class ItemNoUse
/*    */   extends Item
/*    */   implements IExtraData
/*    */ {
/* 22 */   static HashMap<Integer, Boolean> uses = new HashMap<>();
/*    */   public static ItemNoUse instance;
/* 24 */   static int counter = 0;
/*    */ 
/*    */   
/*    */   public ItemNoUse() {
/* 28 */     this; instance = this;
/* 29 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack addItem(boolean hasUseLater) {
/* 34 */     int meta = counter;
/* 35 */     counter++;
/* 36 */     uses.put(Integer.valueOf(meta), Boolean.valueOf(hasUseLater));
/* 37 */     return new ItemStack(this, 1, meta);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack replaceWithOre(String par1, ItemStack original) {
/* 42 */     List<String> oreNames = Arrays.asList(OreDictionary.getOreNames());
/* 43 */     if (oreNames.contains(par1)) {
/*    */       
/* 45 */       ArrayList<ItemStack> ores = OreDictionary.getOres(par1);
/* 46 */       if (!ores.isEmpty()) {
/*    */         
/* 48 */         ItemStack result = ores.get(0);
/* 49 */         original.func_150996_a(result.func_77973_b());
/* 50 */         if (result.func_77960_j() != 32767)
/*    */         {
/* 52 */           original.func_77964_b(result.func_77960_j());
/*    */         }
/* 54 */         return original;
/*    */       } 
/*    */     } 
/* 57 */     return original;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
/* 64 */     super.func_77624_a(par1ItemStack, par2EntityPlayer, par3List, par4);
/* 65 */     int meta = par1ItemStack.func_77960_j();
/* 66 */     if (uses.containsKey(Integer.valueOf(meta))) {
/*    */       
/* 68 */       boolean lean = ((Boolean)uses.get(Integer.valueOf(meta))).booleanValue();
/* 69 */       if (lean) {
/*    */         
/* 71 */         par3List.add("This Item will get Uses Later");
/*    */       }
/*    */       else {
/*    */         
/* 75 */         par3List.add("This Item Is completly removed");
/*    */       } 
/* 77 */       par3List.add("The Result can Change!");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onEntityItemUpdate(EntityItem entityItem) {
/* 85 */     entityItem.func_70106_y();
/* 86 */     return super.onEntityItemUpdate(entityItem);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_77658_a() {
/* 92 */     return "noUsage";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 98 */     Ic2Items.teBlock = addItem(false);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister p_94581_1_) {}
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemNoUse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */