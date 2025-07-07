/*    */ package ic2.core.util;
/*    */ 
/*    */ import ic2.api.info.IWindTicker;
/*    */ import ic2.core.IC2;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class WindTicker
/*    */   implements IWindTicker
/*    */ {
/* 12 */   public static HashMap<World, WorldWind> worldWindStrengh = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWindStrenght(World world) {
/* 17 */     if (world == null)
/*    */     {
/* 19 */       return 0;
/*    */     }
/* 21 */     return getWorldWind(world).getWindStrengh();
/*    */   }
/*    */ 
/*    */   
/*    */   private WorldWind getWorldWind(World world) {
/* 26 */     if (!worldWindStrengh.containsKey(world))
/*    */     {
/* 28 */       worldWindStrengh.put(world, new WorldWind());
/*    */     }
/* 30 */     return worldWindStrengh.get(world);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick(World par1) {
/* 35 */     getWorldWind(par1).onTick();
/*    */   }
/*    */ 
/*    */   
/*    */   public static class WorldWind
/*    */   {
/*    */     float windStrenght;
/*    */     float NewWindStrengh;
/* 43 */     WindTicker.WindCalculation nextWind = null;
/*    */ 
/*    */     
/*    */     public int getWindStrengh() {
/* 47 */       return (int)(20.0F * this.windStrenght);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onTick() {
/* 52 */       if (this.nextWind == null)
/*    */       {
/* 54 */         this.nextWind = new WindTicker.WindCalculation();
/*    */       }
/* 56 */       if (this.nextWind.isReady()) {
/*    */         
/* 58 */         this.NewWindStrengh = this.nextWind.getStrengh();
/* 59 */         this.nextWind = null;
/*    */       } 
/* 61 */       if (this.windStrenght < this.NewWindStrengh) {
/*    */         
/* 63 */         this.windStrenght += 0.01F;
/*    */       }
/* 65 */       else if (this.windStrenght > this.NewWindStrengh) {
/*    */         
/* 67 */         this.windStrenght -= 0.01F;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class WindCalculation
/*    */   {
/* 79 */     int time = IC2.random.nextInt(IC2.maxWindChangeDelay);
/* 80 */     float newStrengh = IC2.random.nextInt(150) / 100.0F;
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean isReady() {
/* 85 */       if (this.time > 0) {
/*    */         
/* 87 */         this.time--;
/* 88 */         return false;
/*    */       } 
/* 90 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public float getStrengh() {
/* 95 */       return this.newStrengh;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\WindTicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */