/*    */ package ic2.api.energy.tile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IEnergyConductorColored
/*    */   extends IEnergyConductor
/*    */ {
/*    */   WireColor getConductorColor();
/*    */   
/*    */   public enum WireColor
/*    */   {
/* 17 */     Blank,
/* 18 */     Black,
/* 19 */     Red,
/* 20 */     Green,
/* 21 */     Brown,
/* 22 */     Blue,
/* 23 */     Purple,
/* 24 */     Cyan,
/* 25 */     Silver,
/* 26 */     Gray,
/* 27 */     Pink,
/* 28 */     Lime,
/* 29 */     Yellow,
/* 30 */     LightBlue,
/* 31 */     Magenta,
/* 32 */     Orange,
/* 33 */     White;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\tile\IEnergyConductorColored.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */