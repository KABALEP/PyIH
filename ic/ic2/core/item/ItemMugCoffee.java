/*     */ package ic2.core.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemMugCoffee
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemMugCoffee(int index) {
/*  25 */     super(index);
/*  26 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int i) {
/*  32 */     return Ic2Icons.getTexture("i0")[this.iconIndex + i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack p_77667_1_) {
/*  40 */     switch (p_77667_1_.func_77960_j()) {
/*     */       case 0:
/*  42 */         return "item.coffee0";
/*  43 */       case 1: return "item.coffee1";
/*  44 */       case 2: return "item.coffee2";
/*     */     } 
/*  46 */     return super.func_77667_c(p_77667_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer player) {
/*  51 */     int meta = itemstack.func_77960_j();
/*  52 */     int highest = 0;
/*  53 */     int x = amplifyEffect(player, Potion.field_76424_c, meta);
/*  54 */     if (x > highest)
/*     */     {
/*  56 */       highest = x;
/*     */     }
/*  58 */     x = amplifyEffect(player, Potion.field_76422_e, meta);
/*  59 */     if (x > highest)
/*     */     {
/*  61 */       highest = x;
/*     */     }
/*  63 */     if (meta == 2)
/*     */     {
/*  65 */       highest -= 2;
/*     */     }
/*  67 */     if (highest >= 3) {
/*     */       
/*  69 */       player.func_70690_d(new PotionEffect(Potion.field_76431_k.field_76415_H, (highest - 2) * 200, 0));
/*  70 */       if (highest >= 4)
/*     */       {
/*  72 */         player.func_70690_d(new PotionEffect(Potion.field_76433_i.field_76415_H, 1, highest - 3));
/*     */       }
/*     */     } 
/*  75 */     return new ItemStack(Ic2Items.mugEmpty.func_77973_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public int amplifyEffect(EntityPlayer player, Potion potion, int meta) {
/*  80 */     PotionEffect eff = player.func_70660_b(potion);
/*  81 */     if (eff != null) {
/*     */       
/*  83 */       int max = 1;
/*  84 */       if (meta == 1)
/*     */       {
/*  86 */         max = 5;
/*     */       }
/*  88 */       if (meta == 2)
/*     */       {
/*  90 */         max = 6;
/*     */       }
/*  92 */       int newAmp = eff.func_76458_c();
/*  93 */       int newDur = eff.func_76459_b();
/*  94 */       if (newAmp < max)
/*     */       {
/*  96 */         newAmp++;
/*     */       }
/*  98 */       if (meta == 0) {
/*     */         
/* 100 */         newDur += 600;
/*     */       }
/*     */       else {
/*     */         
/* 104 */         newDur += 1200;
/*     */       } 
/* 106 */       eff.func_76452_a(new PotionEffect(eff.func_76456_a(), newDur, newAmp));
/* 107 */       return newAmp;
/*     */     } 
/* 109 */     player.func_70690_d(new PotionEffect(potion.field_76415_H, 300, 0));
/* 110 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack itemstack) {
/* 115 */     return 32;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack itemstack) {
/* 120 */     return EnumAction.drink;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer player) {
/* 125 */     player.func_71008_a(itemstack, func_77626_a(itemstack));
/* 126 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List<ItemStack> itemList) {
/* 132 */     for (int meta = 0; meta < 3; meta++)
/*     */     {
/* 134 */       itemList.add(new ItemStack(this, 1, meta));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemMugCoffee.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */