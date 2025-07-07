/*     */ package ic2.core.network.internal;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.network.packets.IC2Packet;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PayloadPacket
/*     */   extends IC2Packet
/*     */ {
/*     */   int dim;
/*     */   int x;
/*     */   int y;
/*     */   int z;
/*     */   public String[] strings;
/*     */   public int[] numbers;
/*     */   public boolean[] flags;
/*     */   
/*     */   public PayloadPacket() {}
/*     */   
/*     */   public PayloadPacket(TileEntity tile, int text, int number, int flag) {
/*  27 */     this.dim = (tile.func_145831_w()).field_73011_w.field_76574_g;
/*  28 */     this.x = tile.field_145851_c;
/*  29 */     this.y = tile.field_145848_d;
/*  30 */     this.z = tile.field_145849_e;
/*  31 */     this.strings = new String[text];
/*  32 */     this.numbers = new int[number];
/*  33 */     this.flags = new boolean[flag];
/*     */   }
/*     */ 
/*     */   
/*     */   public void addString(int slot, String text) {
/*  38 */     this.strings[slot] = text;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFlag(int slot, boolean flag) {
/*  43 */     this.flags[slot] = flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNumber(int slot, int number) {
/*  48 */     this.numbers[slot] = number;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(ByteBuf par1) {
/*  54 */     this.dim = par1.readInt();
/*  55 */     this.x = par1.readInt();
/*  56 */     this.y = par1.readInt();
/*  57 */     this.z = par1.readInt();
/*  58 */     int length = par1.readByte();
/*  59 */     this.strings = new String[length]; int i;
/*  60 */     for (i = 0; i < length; i++)
/*     */     {
/*  62 */       this.strings[i] = readString(par1);
/*     */     }
/*  64 */     length = par1.readByte();
/*  65 */     this.numbers = new int[length];
/*  66 */     for (i = 0; i < length; i++)
/*     */     {
/*  68 */       this.numbers[i] = par1.readInt();
/*     */     }
/*  70 */     length = par1.readByte();
/*  71 */     this.flags = new boolean[length];
/*  72 */     for (i = 0; i < length; i++)
/*     */     {
/*  74 */       this.flags[i] = par1.readBoolean();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ByteBuf par1) {
/*  81 */     par1.writeInt(this.dim);
/*  82 */     par1.writeInt(this.x);
/*  83 */     par1.writeInt(this.y);
/*  84 */     par1.writeInt(this.z);
/*  85 */     par1.writeByte(this.strings.length); int i;
/*  86 */     for (i = 0; i < this.strings.length; i++)
/*     */     {
/*  88 */       writeString(par1, this.strings[i]);
/*     */     }
/*  90 */     par1.writeByte(this.numbers.length);
/*  91 */     for (i = 0; i < this.numbers.length; i++)
/*     */     {
/*  93 */       par1.writeInt(this.numbers[i]);
/*     */     }
/*  95 */     par1.writeByte(this.flags.length);
/*  96 */     for (i = 0; i < this.flags.length; i++)
/*     */     {
/*  98 */       par1.writeBoolean(this.flags[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlePacket(EntityPlayer par1) {
/* 105 */     World world = IC2.platform.getWorld(this.dim);
/* 106 */     if (world == null)
/*     */     {
/* 108 */       throw new RuntimeException("Packet Got Wrong Data");
/*     */     }
/* 110 */     TileEntity tile = world.func_147438_o(this.x, this.y, this.z);
/* 111 */     if (!(tile instanceof IPayloadReceiver))
/*     */     {
/* 113 */       throw new RuntimeException("Packet Got Wrong Data");
/*     */     }
/* 115 */     ((IPayloadReceiver)tile).onPayloadPacket(par1, this);
/*     */   }
/*     */ 
/*     */   
/*     */   private String readString(ByteBuf par1) {
/* 120 */     byte[] array = new byte[par1.readInt()];
/* 121 */     par1.readBytes(array);
/* 122 */     return new String(array);
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeString(ByteBuf par1, String text) {
/* 127 */     byte[] array = text.getBytes();
/* 128 */     par1.writeInt(array.length);
/* 129 */     par1.writeBytes(array);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getFlag(int slot) {
/* 134 */     return this.flags[slot];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumber(int slot) {
/* 139 */     return this.numbers[slot];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(int slot) {
/* 144 */     return this.strings[slot];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize(int type) {
/* 149 */     switch (type) {
/*     */       case 0:
/* 151 */         return this.strings.length;
/* 152 */       case 1: return this.numbers.length;
/* 153 */       case 2: return this.flags.length;
/*     */     } 
/* 155 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\internal\PayloadPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */