/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.block.machine.container.ContainerUraniumEnricher;
/*    */ import ic2.core.item.reactor.ItemReactorEnrichUranium;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiUraniumEnricher
/*    */   extends GuiContainer
/*    */ {
/*    */   ContainerUraniumEnricher container;
/*    */   public String name;
/*    */   public String inv;
/*    */   
/*    */   public GuiUraniumEnricher(ContainerUraniumEnricher par1) {
/* 22 */     super((Container)par1);
/* 23 */     this.container = par1;
/* 24 */     this.name = StatCollector.func_74838_a("blockUraniumEnricher.name");
/* 25 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 30 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 31 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 36 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 37 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIUraniumEnricher.png"));
/* 38 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 39 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 40 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 41 */     int amount = (int)(this.container.tile.amount / 1000.0F * 33.0F);
/* 42 */     byte type = this.container.tile.type;
/* 43 */     if (amount > 0 && type != -1) {
/*    */       
/* 45 */       Color color = ItemReactorEnrichUranium.UraniumType.values()[type].getColor();
/* 46 */       GL11.glColor3ub((byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue());
/* 47 */       func_73729_b(j + 91, k + 16 + 33 - amount, 176, 108 - amount, 4, amount + 1);
/* 48 */       GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*    */     } 
/* 50 */     int charge = (int)(this.container.tile.energy / this.container.tile.maxEnergy * 14.0F);
/* 51 */     if (charge > 0)
/*    */     {
/* 53 */       func_73729_b(j + 8, k + 58 + 14 - charge, 176, 14 - charge, 14, charge);
/*    */     }
/* 55 */     int itemProgress = (int)(this.container.tile.itemProgress / 100.0F * 24.0F);
/* 56 */     if (itemProgress > 0)
/*    */     {
/* 58 */       func_73729_b(j + 62, k + 27, 176, 14, itemProgress + 1, 16);
/*    */     }
/* 60 */     int uranProgress = (int)(this.container.tile.uranProgress / 1000.0F * 51.0F);
/* 61 */     if (uranProgress > 0)
/*    */     {
/* 63 */       func_73729_b(j + 97, k + 34, 176, 35, uranProgress + 1, 35);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiUraniumEnricher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */