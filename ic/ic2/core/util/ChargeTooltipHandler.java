/*     */ package ic2.core.util;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItemManager;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.item.GatewayElectricItemManager;
/*     */ import ic2.core.item.armor.ItemArmorElectric;
/*     */ import ic2.core.item.armor.ItemArmorJetpack;
/*     */ import ic2.core.item.armor.ItemArmorJetpackQuantumSuit;
/*     */ import ic2.core.item.boats.EntityElectricBoat;
/*     */ import java.text.DecimalFormat;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
/*     */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChargeTooltipHandler
/*     */ {
/*     */   @SubscribeEvent
/*     */   public void onToolTip(ItemTooltipEvent evt) {
/*  42 */     if (evt.itemStack == null) {
/*     */       return;
/*     */     }
/*     */     
/*  46 */     IElectricItemManager manager = ((GatewayElectricItemManager)ElectricItem.manager).getManager(evt.itemStack);
/*  47 */     if (manager != null) {
/*     */       
/*  49 */       String tooltip = manager.getToolTip(evt.itemStack);
/*  50 */       if (tooltip != null)
/*     */       {
/*  52 */         evt.toolTip.add(tooltip);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onRender(RenderGameOverlayEvent.Post evt) {
/*  61 */     if (evt.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     Minecraft mc = Minecraft.func_71410_x();
/*  66 */     if (mc == null) {
/*     */       return;
/*     */     }
/*     */     
/*  70 */     EntityClientPlayerMP entityClientPlayerMP = mc.field_71439_g;
/*  71 */     if (entityClientPlayerMP == null) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     if (((EntityPlayer)entityClientPlayerMP).field_70154_o instanceof EntityElectricBoat) {
/*     */       
/*  77 */       handleElectricBoat((EntityElectricBoat)((EntityPlayer)entityClientPlayerMP).field_70154_o, (EntityPlayer)entityClientPlayerMP, mc, evt);
/*     */       return;
/*     */     } 
/*  80 */     handleNuclearJetpack((EntityPlayer)entityClientPlayerMP, mc, evt);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void handleElectricBoat(EntityElectricBoat boat, EntityPlayer player, Minecraft mc, RenderGameOverlayEvent.Post evt) {
/*  86 */     mc.func_110434_K().func_110577_a(Gui.field_110324_m);
/*  87 */     boolean charge = GuiScreen.func_146271_m();
/*  88 */     float scale = charge ? boat.getCharge() : boat.getForce();
/*  89 */     if (!charge) {
/*     */       
/*  91 */       scale /= ((scale < 0.0F) ? 2 : 4);
/*  92 */       if (scale < 0.0F)
/*     */       {
/*  94 */         scale = -scale;
/*     */       }
/*     */     } 
/*  97 */     FontRenderer fontrenderer = mc.field_71466_p;
/*  98 */     ScaledResolution scaledresolution = evt.resolution;
/*  99 */     int i = scaledresolution.func_78326_a();
/* 100 */     short short1 = 182;
/* 101 */     int j = i / 2 - short1 / 2;
/* 102 */     int k = (int)(scale * (short1 + 1));
/* 103 */     int b0 = scaledresolution.func_78328_b() - 29;
/* 104 */     drawTexturedModalRect(j, b0, 0, 74, short1, 5, 0);
/* 105 */     drawTexturedModalRect(j, b0, 0, 74, short1, 5, 0);
/*     */     
/* 107 */     if (k > 0)
/*     */     {
/* 109 */       drawTexturedModalRect(j, b0, 0, 79, k, 5, 0);
/*     */     }
/* 111 */     scale *= 100.0F;
/* 112 */     DecimalFormat format = new DecimalFormat("#.##");
/* 113 */     String s = StatCollector.func_74837_a(charge ? "itemInfo.boatCharge.name" : "itemInfo.boatForce.name", new Object[] { format.format(scale) + "%" });
/* 114 */     fontrenderer.func_78261_a(s, i / 2 - fontrenderer.func_78256_a(s) / 2, b0 - 2, 16777215);
/* 115 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 116 */     mc.func_110434_K().func_110577_a(Gui.field_110324_m);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void handleNuclearJetpack(EntityPlayer player, Minecraft mc, RenderGameOverlayEvent.Post evt) {
/* 122 */     ItemStack item = player.field_71071_by.field_70460_b[2];
/* 123 */     if (item == null) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     boolean jetplate = item.func_77973_b() instanceof ItemArmorJetpackQuantumSuit;
/* 128 */     if (!(item.func_77973_b() instanceof ItemArmorJetpack) && !jetplate) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     ItemArmorJetpack pack = jetplate ? ((ItemArmorJetpackQuantumSuit)item.func_77973_b()).jetpack : (ItemArmorJetpack)item.func_77973_b();
/* 133 */     if (!pack.canDoRocketMode()) {
/*     */       return;
/*     */     }
/*     */     
/* 137 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(item);
/* 138 */     if (!nbt.func_74767_n("RocketMode")) {
/*     */       return;
/*     */     }
/*     */     
/* 142 */     mc.func_110434_K().func_110577_a(Gui.field_110324_m);
/* 143 */     int maxCharge = pack.getMaxRocketCharge();
/* 144 */     int charge = nbt.func_74762_e("UsedEnergy");
/* 145 */     float scale = charge / maxCharge;
/* 146 */     FontRenderer fontrenderer = mc.field_71466_p;
/* 147 */     ScaledResolution scaledresolution = evt.resolution;
/* 148 */     int i = scaledresolution.func_78326_a();
/* 149 */     short short1 = 182;
/* 150 */     int j = i / 2 - short1 / 2;
/* 151 */     int k = (int)(scale * (short1 + 1));
/* 152 */     int b0 = scaledresolution.func_78328_b() - 29;
/* 153 */     drawTexturedModalRect(j, b0, 0, 74, short1, 5, 0);
/* 154 */     drawTexturedModalRect(j, b0, 0, 74, short1, 5, 0);
/*     */     
/* 156 */     if (k > 0)
/*     */     {
/* 158 */       drawTexturedModalRect(j, b0, 0, 79, k, 5, 0);
/*     */     }
/* 160 */     String s = StatCollector.func_74838_a("itemInfo.rocketCharge.name");
/* 161 */     fontrenderer.func_78261_a(s, i / 2 - fontrenderer.func_78256_a(s) / 2, b0 - 2, 16777215);
/* 162 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 163 */     mc.func_110434_K().func_110577_a(Gui.field_110324_m);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_, int zLevel) {
/* 169 */     float f = 0.00390625F;
/* 170 */     float f1 = 0.00390625F;
/* 171 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 172 */     tessellator.func_78382_b();
/* 173 */     tessellator.func_78374_a((p_73729_1_ + 0), (p_73729_2_ + p_73729_6_), zLevel, ((p_73729_3_ + 0) * f), ((p_73729_4_ + p_73729_6_) * f1));
/* 174 */     tessellator.func_78374_a((p_73729_1_ + p_73729_5_), (p_73729_2_ + p_73729_6_), zLevel, ((p_73729_3_ + p_73729_5_) * f), ((p_73729_4_ + p_73729_6_) * f1));
/* 175 */     tessellator.func_78374_a((p_73729_1_ + p_73729_5_), (p_73729_2_ + 0), zLevel, ((p_73729_3_ + p_73729_5_) * f), ((p_73729_4_ + 0) * f1));
/* 176 */     tessellator.func_78374_a((p_73729_1_ + 0), (p_73729_2_ + 0), zLevel, ((p_73729_3_ + 0) * f), ((p_73729_4_ + 0) * f1));
/* 177 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onHitByLightning(EntityStruckByLightningEvent event) {
/* 183 */     if (!IC2.enableSpecialElectricArmor) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 188 */     if (event.entity instanceof EntityPlayer) {
/*     */       
/* 190 */       EntityPlayer player = (EntityPlayer)event.entity;
/* 191 */       int charge = 100000000;
/* 192 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 194 */         ItemStack item = player.field_71071_by.func_70440_f(i);
/* 195 */         if (item != null)
/*     */         {
/* 197 */           charge = (int)(charge - ElectricItem.manager.charge(item, charge, 2147483647, true, false));
/*     */         }
/*     */       } 
/* 200 */       if (charge > 0)
/*     */       {
/* 202 */         if (IC2.modul.containsKey("Baubles Modul")) {
/*     */           
/* 204 */           IInventory inv = ((IElectricHelper)IC2.modul.get("Baubles Modul")).getBaublesInventory(player);
/* 205 */           if (inv != null)
/*     */           {
/* 207 */             for (int j = 0; j < inv.func_70302_i_(); j++) {
/*     */               
/* 209 */               ItemStack item = inv.func_70301_a(j);
/* 210 */               if (item != null)
/*     */               {
/* 212 */                 charge = (int)(charge - ElectricItem.manager.charge(item, charge, 2147483647, true, false));
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/* 218 */       if (charge > 0 && charge < 100000000)
/*     */       {
/* 220 */         if (!ItemArmorElectric.hasElectricBoots((EntityLivingBase)player))
/*     */         {
/* 222 */           player.func_70097_a(DamageSource.field_76377_j, charge);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\ChargeTooltipHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */