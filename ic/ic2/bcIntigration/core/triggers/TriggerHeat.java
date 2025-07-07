/*     */ package ic2.bcIntigration.core.triggers;
/*     */ 
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.block.machine.tileentity.TileEntityAdvancedMachine;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TriggerHeat
/*     */   implements ITriggerExternal
/*     */ {
/*     */   public boolean heated;
/*     */   
/*     */   public TriggerHeat(boolean heated) {
/*  23 */     this.heated = heated;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int arg0) {
/*  29 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  35 */     return StatCollector.func_74838_a(this.heated ? "gate.fullSpeed.name" : "gate.notFullSpeed.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconID() {
/*  40 */     return this.heated ? 12 : 13;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon() {
/*  47 */     return Ic2Icons.getTexture("triggers")[getIconID()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/*  53 */     return this.heated ? "advMachine.heatedUp" : "advMachine.heatUp";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  59 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  65 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerIcons(IIconRegister arg0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatement rotateLeft() {
/*  78 */     return (IStatement)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(TileEntity tile, ForgeDirection arg1, IStatementContainer arg2, IStatementParameter[] arg3) {
/*  84 */     if (tile == null)
/*     */     {
/*  86 */       return !this.heated;
/*     */     }
/*  88 */     if (tile instanceof TileEntityAdvancedMachine) {
/*     */       
/*  90 */       TileEntityAdvancedMachine tei = (TileEntityAdvancedMachine)tile;
/*  91 */       if (tei.speed >= tei.MaxSpeed)
/*     */       {
/*  93 */         return this.heated;
/*     */       
/*     */       }
/*     */     
/*     */     }
/*  98 */     else if (!this.heated) {
/*     */       
/* 100 */       return false;
/*     */     } 
/*     */     
/* 103 */     return !this.heated;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\triggers\TriggerHeat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */