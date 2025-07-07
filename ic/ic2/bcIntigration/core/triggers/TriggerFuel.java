/*    */ package ic2.bcIntigration.core.triggers;
/*    */ 
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerExternal;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public class TriggerFuel
/*    */   implements ITriggerExternal
/*    */ {
/*    */   boolean hasFuel;
/*    */   
/*    */   public TriggerFuel(boolean fuel) {
/* 23 */     this.hasFuel = fuel;
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
/* 35 */     return StatCollector.func_74838_a(this.hasFuel ? "gate.hasFuel.name" : "gate.noFuel.name");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon() {
/* 42 */     return this.hasFuel ? Ic2Items.filledFuelCan.func_77954_c() : Ic2Items.fuelCan.func_77954_c();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUniqueTag() {
/* 48 */     return this.hasFuel ? "generator.hasFuel" : "generator.hasNotFuel";
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
/*    */   
/*    */   public IStatement rotateLeft() {
/* 73 */     return (IStatement)this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTriggerActive(TileEntity tile, ForgeDirection arg1, IStatementContainer arg2, IStatementParameter[] arg3) {
/* 79 */     if (tile == null)
/*    */     {
/* 81 */       return !this.hasFuel;
/*    */     }
/* 83 */     if (tile instanceof TileEntityBaseGenerator) {
/*    */       
/* 85 */       TileEntityBaseGenerator fuel = (TileEntityBaseGenerator)tile;
/* 86 */       if (fuel.fuel > 0)
/*    */       {
/* 88 */         return this.hasFuel;
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 93 */     else if (!this.hasFuel) {
/*    */       
/* 95 */       return false;
/*    */     } 
/*    */     
/* 98 */     return !this.hasFuel;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\triggers\TriggerFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */