/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.block.machine.container.ContainerSoundBeacon;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiSoundBeacon
/*    */   extends GuiContainer {
/*    */   ContainerSoundBeacon container;
/*    */   String name;
/*    */   String inv;
/*    */   
/*    */   public GuiSoundBeacon(ContainerSoundBeacon par1) {
/* 17 */     super((Container)par1);
/* 18 */     this.container = par1;
/* 19 */     this.name = StatCollector.func_74838_a("blockSoundBeacon.name");
/* 20 */     this.inv = StatCollector.func_74838_a("container.inventory");
/* 21 */     this.field_147000_g = 184;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 26 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 27 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/*    */     
/* 29 */     int tileEffect = (int)(this.container.sound.tileEffect * 100.0F);
/* 30 */     int itemEffect = (int)(this.container.sound.itemEffect * 100.0F);
/* 31 */     int armorEffect = (int)(this.container.sound.armorEffect * 100.0F);
/*    */     
/* 33 */     GL11.glPushMatrix();
/* 34 */     GL11.glTranslatef(30.0F, 38.0F, 0.0F);
/* 35 */     GL11.glScalef(0.5F, 0.5F, 0.0F);
/* 36 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.soundBeaconTile.name"), 0, -30, 4210752);
/* 37 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.soundBeaconRange.name", new Object[] { Integer.valueOf(this.container.sound.tileRange) }), 0, -20, 4210752);
/* 38 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.soundBeaconEffect.name", new Object[] { tileEffect + "%" }), 0, -10, 4210752);
/*    */     
/* 40 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.soundBeaconItem.name"), 100, -30, 4210752);
/* 41 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.soundBeaconRange.name", new Object[] { Integer.valueOf(this.container.sound.itemRange) }), 100, -20, 4210752);
/* 42 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.soundBeaconEffect.name", new Object[] { itemEffect + "%" }), 100, -10, 4210752);
/*    */     
/* 44 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.soundBeaconArmor.name"), 200, -30, 4210752);
/* 45 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.soundBeaconRange.name", new Object[] { Integer.valueOf(this.container.sound.armorRange) }), 200, -20, 4210752);
/* 46 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.soundBeaconEffect.name", new Object[] { armorEffect + "%" }), 200, -10, 4210752);
/* 47 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 52 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 53 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUISoundBeacon.png"));
/* 54 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 55 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 56 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiSoundBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */