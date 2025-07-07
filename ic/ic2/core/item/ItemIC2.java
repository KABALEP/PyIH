/*    */ package ic2.core.item;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Icons;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemIC2
/*    */   extends Item
/*    */ {
/*    */   public EnumRarity rarity;
/*    */   public int iconIndex;
/*    */   public String spriteID;
/*    */   boolean hasEffect;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*    */   
/*    */   public ItemIC2(int index) {
/* 30 */     this.rarity = EnumRarity.common;
/* 31 */     this.iconIndex = index;
/* 32 */     this.spriteID = "i0";
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemIC2 setHasEffect() {
/* 37 */     this.hasEffect = true;
/* 38 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemIC2 setSpriteID(String par1) {
/* 43 */     this.spriteID = par1;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTextureFile() {
/* 49 */     return "/ic2/sprites/item_0.png";
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int par1) {
/* 55 */     return Ic2Icons.getTexture(this.spriteID)[this.iconIndex];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public boolean hasEffect(ItemStack par1ItemStack, int pass) {
/* 62 */     if (this.hasEffect)
/*    */     {
/* 64 */       return true;
/*    */     }
/* 66 */     return super.hasEffect(par1ItemStack, pass);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemIC2 setRarity(EnumRarity rarity) {
/* 72 */     this.rarity = rarity;
/* 73 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 78 */     return this.rarity;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */