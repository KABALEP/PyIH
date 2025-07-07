/*     */ package ic2.bcIntigration.core.triggers;
/*     */ 
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.block.wiring.TileEntityCableDetector;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TriggerEnergyFlow
/*     */   implements ITriggerExternal
/*     */ {
/*     */   boolean flowing;
/*     */   
/*     */   public TriggerEnergyFlow(boolean flow) {
/*  32 */     this.flowing = flow;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int arg0) {
/*  38 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  44 */     return StatCollector.func_74838_a(this.flowing ? "gate.conductorEnergyFlowing.name" : "gate.conductorEnergyNotFlowing.name");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon() {
/*  51 */     return Ic2Icons.getTexture("triggers")[getIconID()];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconID() {
/*  56 */     return this.flowing ? 10 : 11;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/*  62 */     return this.flowing ? "detector.flowing" : "detector.NotFlowing";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  68 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  74 */     return 0;
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
/*  87 */     return (IStatement)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(TileEntity tile, ForgeDirection arg1, IStatementContainer arg2, IStatementParameter[] arg3) {
/*  93 */     if (tile == null)
/*     */     {
/*  95 */       return !this.flowing;
/*     */     }
/*  97 */     if (tile instanceof TileEntityCableDetector) {
/*     */       
/*  99 */       TileEntityCableDetector detector = (TileEntityCableDetector)tile;
/* 100 */       if (detector.getActive())
/*     */       {
/* 102 */         return this.flowing;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 107 */     else if (!this.flowing) {
/*     */       
/* 109 */       return false;
/*     */     } 
/*     */     
/* 112 */     return !this.flowing;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\triggers\TriggerEnergyFlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */