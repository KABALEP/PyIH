/*    */ package ic2.core;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class CreativeTabIC2
/*    */   extends CreativeTabs {
/*    */   private static ItemStack laser;
/*    */   private static ItemStack a;
/*    */   private static ItemStack b;
/*    */   private static ItemStack z;
/*    */   private int ticker;
/*    */   
/*    */   public CreativeTabIC2() {
/* 19 */     super("IC2");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_151244_d() {
/* 25 */     if (laser == null)
/*    */     {
/* 27 */       laser = Ic2Items.miningLaser.func_77946_l();
/*    */     }
/* 29 */     if (IC2.seasonal) {
/*    */       
/* 31 */       if (a == null)
/*    */       {
/* 33 */         a = new ItemStack(Items.field_151144_bL, 1, 2);
/*    */       }
/* 35 */       if (b == null)
/*    */       {
/* 37 */         b = new ItemStack(Items.field_151144_bL, 1, 0);
/*    */       }
/* 39 */       if (z == null)
/*    */       {
/* 41 */         z = Ic2Items.nanoBodyarmor.func_77946_l();
/*    */       }
/* 43 */       if (++this.ticker >= 5000)
/*    */       {
/* 45 */         this.ticker = 0;
/*    */       }
/* 47 */       return (this.ticker < 4500) ? b : ((this.ticker < 3000) ? a : ((this.ticker < 2500) ? laser : z));
/*    */     } 
/* 49 */     return laser;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public Item func_78016_d() {
/* 56 */     return Ic2Items.rubber.func_77973_b();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\CreativeTabIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */