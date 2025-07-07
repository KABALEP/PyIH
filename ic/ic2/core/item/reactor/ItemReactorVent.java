/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.api.reactor.IReactorPlannerComponent;
/*    */ import ic2.api.reactor.ISteamReactor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagInt;
/*    */ 
/*    */ public class ItemReactorVent
/*    */   extends ItemReactorHeatStorage {
/*    */   public int selfVent;
/*    */   public int reactorVent;
/*    */   
/*    */   public ItemReactorVent(int index, int heatStorage, int selfvent, int reactorvent) {
/* 16 */     super(index, heatStorage);
/* 17 */     this.selfVent = selfvent;
/* 18 */     this.reactorVent = reactorvent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
/* 24 */     if (heatrun) {
/*    */       
/* 26 */       if (this.reactorVent > 0) {
/*    */ 
/*    */         
/* 29 */         int reactorDrain = reactor.getHeat(), rheat = reactorDrain;
/* 30 */         if (reactorDrain > this.reactorVent)
/*    */         {
/* 32 */           reactorDrain = this.reactorVent;
/*    */         }
/* 34 */         rheat -= reactorDrain;
/* 35 */         if ((reactorDrain = alterHeat(reactor, yourStack, x, y, reactorDrain)) > 0) {
/*    */           return;
/*    */         }
/*    */         
/* 39 */         reactor.setHeat(rheat);
/*    */       } 
/* 41 */       alterHeat(reactor, yourStack, x, y, -this.selfVent);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processTick(ISteamReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun, boolean damageTick) {
/* 48 */     processChamber((IReactor)reactor, yourStack, x, y, heatrun);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 54 */     return IReactorPlannerComponent.ReactorComponentType.Vent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 60 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.SelfCooling)
/*    */     {
/* 62 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.selfVent);
/*    */     }
/* 64 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorCooling)
/*    */     {
/* 66 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.reactorVent);
/*    */     }
/* 68 */     return nulltag;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorVent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */