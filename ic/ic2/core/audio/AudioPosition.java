/*    */ package ic2.core.audio;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class AudioPosition
/*    */ {
/*    */   public World world;
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   
/*    */   public static AudioPosition getFrom(Object obj, PositionSpec positionSpec) {
/* 16 */     if (obj instanceof AudioPosition)
/*    */     {
/* 18 */       return (AudioPosition)obj;
/*    */     }
/* 20 */     if (obj instanceof Entity) {
/*    */       
/* 22 */       Entity e = (Entity)obj;
/* 23 */       return new AudioPosition(e.field_70170_p, (float)e.field_70165_t, (float)e.field_70163_u, (float)e.field_70161_v);
/*    */     } 
/* 25 */     if (obj instanceof TileEntity) {
/*    */       
/* 27 */       TileEntity te = (TileEntity)obj;
/* 28 */       return new AudioPosition(te.func_145831_w(), te.field_145851_c + 0.5F, te.field_145848_d + 0.5F, te.field_145849_e + 0.5F);
/*    */     } 
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public AudioPosition(World world, float x, float y, float z) {
/* 35 */     this.world = world;
/* 36 */     this.x = x;
/* 37 */     this.y = y;
/* 38 */     this.z = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\audio\AudioPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */