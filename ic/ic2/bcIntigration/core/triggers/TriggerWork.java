/*     */ package ic2.bcIntigration.core.triggers;
/*     */ 
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.block.machine.tileentity.TileEntityAdvancedMachine;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class TriggerWork
/*     */   implements ITriggerExternal
/*     */ {
/*     */   boolean work;
/*     */   IIcon icon;
/*     */   
/*     */   public TriggerWork(boolean work) {
/*  25 */     this.work = work;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int arg0) {
/*  31 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  37 */     return StatCollector.func_74838_a(this.work ? "gate.machineOn.name" : "gate.machineOff.name");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon() {
/*  44 */     return this.icon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/*  50 */     return this.work ? "trigger.icMachineWork" : "trigger.icMachineNotWork";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  56 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  62 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerIcons(IIconRegister register) {
/*  69 */     this.icon = register.func_94245_a("buildcraftcore:triggers/trigger_machine_" + (this.work ? "active" : "inactive"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatement rotateLeft() {
/*  75 */     return (IStatement)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(TileEntity tile, ForgeDirection dir, IStatementContainer arg2, IStatementParameter[] arg3) {
/*  81 */     if (tile == null)
/*     */     {
/*  83 */       return !this.work;
/*     */     }
/*  85 */     if (tile instanceof TileEntityElectricMachine) {
/*     */       
/*  87 */       TileEntityElectricMachine machine = (TileEntityElectricMachine)tile;
/*  88 */       if (machine.getActive())
/*     */       {
/*  90 */         return this.work;
/*     */       }
/*     */     }
/*  93 */     else if (tile instanceof TileEntityAdvancedMachine) {
/*     */       
/*  95 */       TileEntityAdvancedMachine machine = (TileEntityAdvancedMachine)tile;
/*  96 */       if (machine.isProcessing())
/*     */       {
/*  98 */         return this.work;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 103 */     else if (!this.work) {
/*     */       
/* 105 */       return false;
/*     */     } 
/*     */     
/* 108 */     return !this.work;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\triggers\TriggerWork.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */