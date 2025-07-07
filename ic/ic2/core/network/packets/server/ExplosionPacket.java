/*    */ package ic2.core.network.packets.server;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.audio.AudioPosition;
/*    */ import ic2.core.audio.PositionSpec;
/*    */ import ic2.core.network.packets.IC2Packet;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExplosionPacket
/*    */   extends IC2Packet
/*    */ {
/*    */   int dimID;
/*    */   double x;
/*    */   double y;
/*    */   double z;
/*    */   boolean nuke;
/*    */   
/*    */   public ExplosionPacket() {}
/*    */   
/*    */   public ExplosionPacket(World par1, double par2, double par3, double par4, boolean par5) {
/* 26 */     this.dimID = par1.field_73011_w.field_76574_g;
/* 27 */     this.x = par2;
/* 28 */     this.y = par3;
/* 29 */     this.z = par4;
/* 30 */     this.nuke = par5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuf par1) {
/* 36 */     this.dimID = par1.readInt();
/* 37 */     this.x = par1.readDouble();
/* 38 */     this.y = par1.readDouble();
/* 39 */     this.z = par1.readDouble();
/* 40 */     this.nuke = par1.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf par1) {
/* 46 */     par1.writeInt(this.dimID);
/* 47 */     par1.writeDouble(this.x);
/* 48 */     par1.writeDouble(this.y);
/* 49 */     par1.writeDouble(this.z);
/* 50 */     par1.writeBoolean(this.nuke);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlePacket(EntityPlayer par1) {
/* 56 */     World world = IC2.platform.getWorld(this.dimID);
/* 57 */     if (world == null)
/*    */     {
/* 59 */       throw new RuntimeException("Packet Got Incorrect Data");
/*    */     }
/* 61 */     if (this.nuke) {
/*    */       
/* 63 */       IC2.audioManager.playOnce(new AudioPosition(world, (float)this.x, (float)this.y, (float)this.z), PositionSpec.Center, "Tools/NukeExplosion.ogg", false, 4.0F);
/*    */     }
/*    */     else {
/*    */       
/* 67 */       world.func_72908_a(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/*    */     } 
/* 69 */     world.func_72869_a("hugeexplosion", this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\packets\server\ExplosionPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */