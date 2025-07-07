/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemBattery
/*     */   extends BasicElectricItem
/*     */ {
/*     */   public AudioSource audio;
/*     */   
/*     */   public ItemBattery(int sprite, int maxCharge, int transferLimit, int tier) {
/*  23 */     super(sprite);
/*  24 */     setNoRepair();
/*  25 */     this.maxCharge = maxCharge;
/*  26 */     this.transferLimit = transferLimit;
/*  27 */     this.tier = tier;
/*  28 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack par1) {
/*  34 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEmptyItem(ItemStack itemStack) {
/*  42 */     if (this == Ic2Items.chargedReBattery.func_77973_b())
/*     */     {
/*  44 */       return Ic2Items.reBattery.func_77973_b();
/*     */     }
/*  46 */     return super.getEmptyItem(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getChargedItem(ItemStack itemStack) {
/*  54 */     if (this == Ic2Items.reBattery.func_77973_b())
/*     */     {
/*  56 */       return Ic2Items.chargedReBattery.func_77973_b();
/*     */     }
/*  58 */     return super.getChargedItem(itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int i) {
/*  64 */     if (i <= 1)
/*     */     {
/*  66 */       return Ic2Icons.getTexture("i1")[this.iconIndex + 4];
/*     */     }
/*  68 */     if (i <= 8)
/*     */     {
/*  70 */       return Ic2Icons.getTexture("i1")[this.iconIndex + 3];
/*     */     }
/*  72 */     if (i <= 14)
/*     */     {
/*  74 */       return Ic2Icons.getTexture("i1")[this.iconIndex + 2];
/*     */     }
/*  76 */     if (i <= 20)
/*     */     {
/*  78 */       return Ic2Icons.getTexture("i1")[this.iconIndex + 1];
/*     */     }
/*  80 */     return Ic2Icons.getTexture("i1")[this.iconIndex];
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon getIconFromChargeLevel(float chargeLevel) {
/*  85 */     return func_77617_a(1 + (int)Math.round((1.0D - chargeLevel) * (func_77612_l() - 2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemStack, World world, EntityPlayer entityplayer) {
/*  90 */     if (IC2.platform.isSimulating() && itemStack.func_77973_b() == Ic2Items.chargedReBattery.func_77973_b()) {
/*     */       
/*  92 */       boolean transferred = false;
/*  93 */       for (int i = 0; i < 9; i++) {
/*     */         
/*  95 */         ItemStack stack = entityplayer.field_71071_by.field_70462_a[i];
/*  96 */         if (stack != null && stack.func_77973_b() instanceof IElectricItem && stack != itemStack) {
/*     */           
/*  98 */           IElectricItem item = (IElectricItem)stack.func_77973_b();
/*  99 */           double transfer = ElectricItem.manager.discharge(itemStack, (2 * this.transferLimit), item.getTier(itemStack), true, false, true);
/* 100 */           transfer = ElectricItem.manager.charge(stack, transfer, this.tier, true, false);
/* 101 */           ElectricItem.manager.discharge(itemStack, transfer, item.getTier(itemStack), true, false, false);
/* 102 */           if (transfer == 0.0D) {
/*     */             break;
/*     */           }
/*     */           
/* 106 */           transferred = true;
/*     */         } 
/*     */       } 
/* 109 */       if (transferred) {
/*     */         
/* 111 */         entityplayer.field_71070_bA.func_75142_b();
/* 112 */         if (this.audio == null || this.audio.isRemoved()) {
/*     */           
/* 114 */           this.audio = IC2.audioManager.createSource(entityplayer, PositionSpec.Hand, "Tools/BatteryUse.ogg", false, true, 2.0F);
/* 115 */           this.audio.play();
/*     */         } 
/* 117 */         if (this.audio != null && !this.audio.isPlaying()) {
/*     */           
/* 119 */           this.audio.stop();
/* 120 */           this.audio.play();
/*     */         } 
/*     */       } 
/*     */     } 
/* 124 */     return itemStack;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemBattery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */