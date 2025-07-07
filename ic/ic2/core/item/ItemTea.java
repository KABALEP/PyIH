/*     */ package ic2.core.item;
/*     */ 
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemTea
/*     */   extends ItemIC2
/*     */ {
/*     */   int type;
/*  19 */   UUID id = UUIDTypeAdapter.fromString("fcf721ea2e9848eba3c00a7e88e7a9b8");
/*     */ 
/*     */   
/*     */   public ItemTea(int index, int par2) {
/*  23 */     super(index);
/*  24 */     this.type = par2;
/*  25 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack item, World p_77659_2_, EntityPlayer player) {
/*  32 */     if (player.func_71043_e(true))
/*     */     {
/*  34 */       player.func_71008_a(item, func_77626_a(item));
/*     */     }
/*  36 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77654_b(ItemStack item, World p_77654_2_, EntityPlayer player) {
/*  42 */     decreaseEffect(Potion.field_76419_f.field_76415_H, player, 600);
/*  43 */     decreaseEffect(Potion.field_76421_d.field_76415_H, player, 600);
/*  44 */     decreaseEffect(Potion.field_76431_k.field_76415_H, player, 1200);
/*  45 */     decreaseEffect(Potion.field_76438_s.field_76415_H, player, 2400);
/*  46 */     switch (this.type) {
/*     */       
/*     */       case 0:
/*  49 */         applyPotionEffect(Potion.field_76424_c.field_76415_H, 100, 1200, 0, player);
/*  50 */         applyPotionEffect(Potion.field_76422_e.field_76415_H, 100, 1200, 0, player);
/*     */         break;
/*     */       case 1:
/*  53 */         applyPotionEffect(Potion.field_76424_c.field_76415_H, 200, 1200, 0, player);
/*  54 */         applyPotionEffect(Potion.field_76422_e.field_76415_H, 200, 1200, 0, player);
/*  55 */         applyPotionEffect(Potion.field_76428_l.field_76415_H, 150, 300, 0, player);
/*     */         break;
/*     */       case 2:
/*  58 */         if (player.func_146103_bH().getId().equals(this.id)) {
/*     */           
/*  60 */           applyPotionEffect(Potion.field_76424_c.field_76415_H, 1200, 3600, 3, player);
/*  61 */           applyPotionEffect(Potion.field_76422_e.field_76415_H, 1200, 3600, 3, player);
/*     */           
/*     */           break;
/*     */         } 
/*  65 */         applyPotionEffect(Potion.field_76424_c.field_76415_H, 600, 1200, 1, player);
/*  66 */         applyPotionEffect(Potion.field_76422_e.field_76415_H, 600, 1200, 1, player);
/*     */         break;
/*     */     } 
/*     */     
/*  70 */     return Ic2Items.mugEmpty.func_77946_l();
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyPotionEffect(int id, int inc, int max, int amp, EntityPlayer player) {
/*  75 */     PotionEffect ef = player.func_70660_b(Potion.field_76425_a[id]);
/*  76 */     if (ef == null) {
/*     */       
/*  78 */       ef = new PotionEffect(id, inc, amp);
/*  79 */       player.func_70690_d(ef);
/*     */       return;
/*     */     } 
/*  82 */     if (ef.func_76458_c() > amp) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     int maxTime = Math.min(max, ef.func_76459_b() + inc);
/*  87 */     ef = new PotionEffect(id, maxTime, amp);
/*  88 */     player.func_70690_d(ef);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decreaseEffect(int effectID, EntityPlayer par1, int amount) {
/*  93 */     if (par1.func_82165_m(effectID)) {
/*     */       
/*  95 */       PotionEffect ef = par1.func_70660_b(Potion.field_76425_a[effectID]);
/*  96 */       if (ef.func_76459_b() <= amount) {
/*     */         
/*  98 */         par1.func_82170_o(effectID);
/*     */         return;
/*     */       } 
/* 101 */       ef = new PotionEffect(ef.func_76456_a(), ef.func_76459_b() - amount, ef.func_76458_c(), ef.func_82720_e());
/* 102 */       par1.func_82170_o(effectID);
/* 103 */       par1.func_70690_d(ef);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack item) {
/* 110 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack p_77661_1_) {
/* 116 */     return EnumAction.drink;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemTea.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */