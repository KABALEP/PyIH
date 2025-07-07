/*    */ package ic2.bcIntigration.core.triggers;
/*    */ 
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.machine.tileentity.TileEntityMatter;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public class TriggerScrap
/*    */   implements ITriggerExternal
/*    */ {
/*    */   public boolean hasScrap;
/*    */   
/*    */   public TriggerScrap(boolean scrap) {
/* 23 */     this.hasScrap = scrap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IStatementParameter createParameter(int arg0) {
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 35 */     return StatCollector.func_74838_a(this.hasScrap ? "gate.massFabAmplifier.name" : "gate.massFabNoAmplifier.name");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon() {
/* 42 */     return this.hasScrap ? Ic2Items.scrap.func_77954_c() : Ic2Items.smallIronDust.func_77954_c();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 48 */     return this.hasScrap ? "matter.hasAmplifier" : "matter.hasNotAmplifier";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int maxParameters() {
/* 54 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int minParameters() {
/* 60 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister arg0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public IStatement rotateLeft() {
/* 72 */     return (IStatement)this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(TileEntity tile, ForgeDirection arg1, IStatementContainer arg2, IStatementParameter[] arg3) {
/* 78 */     if (tile == null)
/*    */     {
/* 80 */       return !this.hasScrap;
/*    */     }
/* 82 */     if (tile instanceof TileEntityMatter) {
/*    */       
/* 84 */       TileEntityMatter tem = (TileEntityMatter)tile;
/* 85 */       if (tem.scrap > 0)
/*    */       {
/* 87 */         return this.hasScrap;
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 92 */     else if (!this.hasScrap) {
/*    */       
/* 94 */       return false;
/*    */     } 
/*    */     
/* 97 */     return !this.hasScrap;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\triggers\TriggerScrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */