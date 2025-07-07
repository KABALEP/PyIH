/*     */ package ic2.core.network;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ 
/*     */ public class DataEncoder {
/*     */   public static Object decode(DataInputStream is) throws IOException {
/*     */     short s1;
/*     */     int id;
/*     */     byte b1;
/*     */     World world;
/*     */     short length;
/*     */     FluidStack stack;
/*     */     boolean empty;
/*     */     byte subType;
/*     */     int amount, idata[];
/*     */     short[] shdata;
/*     */     byte[] bydata;
/*     */     long[] ldata;
/*     */     boolean[] bdata;
/*     */     char[] cdata;
/*     */     String[] stdata;
/*     */     int size, n;
/*     */     float[] fdata;
/*     */     double[] ddata;
/*     */     boolean flag;
/*     */     byte[] array;
/*     */     int i1, j, k, l;
/*     */     byte b;
/*     */     int i2, i3, meta;
/*     */     Block[] blocks;
/*     */     Item[] items;
/*     */     int i4, i5, i, m;
/*     */     ItemStack itemStack;
/*     */     int i6;
/*     */     boolean nbt;
/*  42 */     byte type = is.readByte();
/*  43 */     switch (type) {
/*     */       case -1:
/*  45 */         return null;
/*  46 */       case 0: return Integer.valueOf(is.readInt());
/*     */       
/*     */       case 1:
/*  49 */         s1 = is.readShort();
/*  50 */         idata = new int[s1];
/*  51 */         for (i1 = 0; i1 < s1; i1++)
/*     */         {
/*  53 */           idata[i1] = is.readInt();
/*     */         }
/*  55 */         return idata;
/*     */       case 2:
/*  57 */         return Short.valueOf(is.readShort());
/*     */       
/*     */       case 3:
/*  60 */         s1 = is.readShort();
/*  61 */         shdata = new short[s1];
/*  62 */         for (j = 0; j < s1; j++)
/*     */         {
/*  64 */           shdata[j] = is.readShort();
/*     */         }
/*  66 */         return shdata;
/*     */       case 4:
/*  68 */         return Byte.valueOf(is.readByte());
/*     */       
/*     */       case 5:
/*  71 */         s1 = is.readShort();
/*  72 */         bydata = new byte[s1];
/*  73 */         for (k = 0; k < s1; k++)
/*     */         {
/*  75 */           bydata[k] = is.readByte();
/*     */         }
/*  77 */         return bydata;
/*     */       case 6:
/*  79 */         return Long.valueOf(is.readLong());
/*     */       
/*     */       case 7:
/*  82 */         s1 = is.readShort();
/*  83 */         ldata = new long[s1];
/*  84 */         for (l = 0; l < s1; l++)
/*     */         {
/*  86 */           ldata[l] = is.readLong();
/*     */         }
/*  88 */         return ldata;
/*     */       case 8:
/*  90 */         return Boolean.valueOf(is.readBoolean());
/*     */       
/*     */       case 9:
/*  93 */         s1 = is.readShort();
/*  94 */         bdata = new boolean[s1];
/*  95 */         b = 0;
/*  96 */         for (m = 0; m < s1; m++) {
/*     */           
/*  98 */           if (m % 8 == 0)
/*     */           {
/* 100 */             b = is.readByte();
/*     */           }
/* 102 */           bdata[m] = ((b & 1 << m % 8) != 0);
/*     */         } 
/* 104 */         return bdata;
/*     */ 
/*     */       
/*     */       case 10:
/* 108 */         s1 = is.readShort();
/* 109 */         cdata = new char[s1];
/* 110 */         for (i2 = 0; i2 < s1; i2++)
/*     */         {
/* 112 */           cdata[i2] = is.readChar();
/*     */         }
/* 114 */         return new String(cdata);
/*     */ 
/*     */       
/*     */       case 11:
/* 118 */         s1 = is.readShort();
/* 119 */         stdata = new String[s1];
/* 120 */         for (i3 = 0; i3 < s1; i3++) {
/*     */           
/* 122 */           short slength = is.readShort();
/* 123 */           char[] sdata = new char[slength];
/* 124 */           for (int j2 = 0; j2 < slength; j2++)
/*     */           {
/* 126 */             sdata[j2] = is.readChar();
/*     */           }
/* 128 */           stdata[i3] = new String(sdata);
/*     */         } 
/* 130 */         return stdata;
/*     */ 
/*     */       
/*     */       case 12:
/* 134 */         id = is.readInt();
/* 135 */         if (id <= 0)
/*     */         {
/* 137 */           return null;
/*     */         }
/* 139 */         size = is.readByte();
/* 140 */         meta = is.readShort();
/* 141 */         itemStack = new ItemStack(Item.func_150899_d(id), size, meta);
/* 142 */         nbt = is.readBoolean();
/* 143 */         if (nbt)
/*     */         {
/* 145 */           itemStack.func_77982_d(CompressedStreamTools.func_74794_a(is));
/*     */         }
/* 147 */         return itemStack;
/*     */ 
/*     */       
/*     */       case 13:
/* 151 */         return CompressedStreamTools.func_74794_a(is);
/*     */ 
/*     */       
/*     */       case 14:
/* 155 */         b1 = is.readByte();
/* 156 */         switch (b1) {
/*     */           case 0:
/* 158 */             return Block.func_149684_b(is.readUTF());
/* 159 */           case 1: return Item.field_150901_e.func_82594_a(is.readUTF());
/* 160 */           case 2: return StatList.func_151177_a(is.readUTF());
/* 161 */           case 3: return Potion.field_76425_a[is.readInt()];
/* 162 */           case 4: return Enchantment.field_77331_b[is.readInt()];
/*     */           
/*     */           case 5:
/* 165 */             n = is.readInt();
/* 166 */             blocks = new Block[n];
/* 167 */             for (i6 = 0; i6 < n; i6++) {
/*     */               
/* 169 */               Object obj = decode(is);
/* 170 */               if (obj != null)
/*     */               {
/*     */ 
/*     */                 
/* 174 */                 blocks[i6] = (Block)obj; } 
/*     */             } 
/* 176 */             return blocks;
/*     */ 
/*     */           
/*     */           case 6:
/* 180 */             n = is.readInt();
/* 181 */             items = new Item[n];
/* 182 */             for (i6 = 0; i6 < n; i6++) {
/*     */               
/* 184 */               Object obj = decode(is);
/* 185 */               if (obj != null)
/*     */               {
/*     */ 
/*     */                 
/* 189 */                 items[i6] = (Item)obj; } 
/*     */             } 
/* 191 */             return items;
/*     */         } 
/*     */       
/*     */       
/*     */       
/*     */       case 15:
/* 197 */         b1 = is.readByte();
/* 198 */         switch (b1) {
/*     */           case 0:
/* 200 */             return new ChunkCoordinates(is.readInt(), is.readInt(), is.readInt());
/* 201 */           case 1: return new ChunkCoordIntPair(is.readInt(), is.readInt());
/* 202 */           case 2: return new ChunkPosition(is.readInt(), is.readInt(), is.readInt());
/*     */         } 
/*     */       
/*     */       
/*     */       case 16:
/* 207 */         world = IC2.platform.getWorld(is.readInt());
/* 208 */         if (world == null)
/*     */         {
/* 210 */           return null;
/*     */         }
/* 212 */         return world.func_147438_o(is.readInt(), is.readInt(), is.readInt());
/*     */       case 17:
/* 214 */         return IC2.platform.getWorld(is.readInt());
/* 215 */       case 18: return Float.valueOf(is.readFloat());
/*     */       
/*     */       case 19:
/* 218 */         length = is.readShort();
/* 219 */         fdata = new float[length];
/* 220 */         for (i4 = 0; i4 < length; i4++)
/*     */         {
/* 222 */           fdata[i4] = is.readFloat();
/*     */         }
/* 224 */         return fdata;
/*     */       case 20:
/* 226 */         return Double.valueOf(is.readDouble());
/*     */       
/*     */       case 21:
/* 229 */         length = is.readShort();
/* 230 */         ddata = new double[length];
/* 231 */         for (i5 = 0; i5 < length; i5++)
/*     */         {
/* 233 */           ddata[i5] = is.readDouble();
/*     */         }
/* 235 */         return ddata;
/*     */ 
/*     */       
/*     */       case 22:
/* 239 */         stack = new FluidStack(FluidRegistry.getFluid(is.readInt()), is.readInt());
/* 240 */         flag = is.readBoolean();
/* 241 */         if (flag)
/*     */         {
/* 243 */           stack.tag = CompressedStreamTools.func_74794_a(is);
/*     */         }
/* 245 */         return stack;
/*     */ 
/*     */       
/*     */       case 23:
/* 249 */         empty = is.readBoolean();
/* 250 */         if (empty)
/*     */         {
/* 252 */           return new FluidTank(is.readInt());
/*     */         }
/*     */ 
/*     */         
/* 256 */         return new FluidTank((FluidStack)decode(is), is.readInt());
/*     */ 
/*     */ 
/*     */       
/*     */       case 24:
/* 261 */         subType = is.readByte();
/* 262 */         switch (subType) {
/*     */           case 0:
/* 264 */             return new UUID(is.readLong(), is.readLong());
/* 265 */           case 1: return new GameProfile((UUID)decode(is), is.readUTF());
/*     */         } 
/*     */       
/*     */       
/*     */       case 25:
/* 270 */         return Crops.instance.getCropCard(is.readUTF(), is.readUTF());
/*     */ 
/*     */       
/*     */       case 26:
/* 274 */         return Integer.valueOf(is.readInt());
/*     */ 
/*     */       
/*     */       case 27:
/* 278 */         amount = is.readInt();
/* 279 */         array = new byte[amount];
/* 280 */         for (i = 0; i < amount; i++)
/*     */         {
/* 282 */           array[i] = is.readByte();
/*     */         }
/* 284 */         return array;
/*     */     } 
/*     */ 
/*     */     
/* 288 */     IC2.platform.displayError("An unknown data type was received over multiplayer to be decoded.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: type ID " + type + ")");
/* 289 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void encode(DataOutputStream os, Object o) throws IOException {
/* 296 */     if (o == null) {
/*     */       
/* 298 */       os.writeByte(-1);
/*     */     }
/* 300 */     else if (o instanceof INetworkFieldData) {
/*     */       
/* 302 */       os.writeByte(27);
/* 303 */       ByteArrayOutputStream array = new ByteArrayOutputStream();
/* 304 */       DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(array));
/* 305 */       ((INetworkFieldData)o).write(stream);
/* 306 */       stream.close();
/* 307 */       array.close();
/* 308 */       byte[] byteArray = array.toByteArray();
/* 309 */       os.writeInt(byteArray.length);
/* 310 */       for (int i = 0; i < byteArray.length; i++)
/*     */       {
/* 312 */         os.writeByte(byteArray[i]);
/*     */       }
/*     */     }
/* 315 */     else if (o instanceof Integer) {
/*     */       
/* 317 */       os.writeByte(0);
/* 318 */       os.writeInt(((Integer)o).intValue());
/*     */     }
/* 320 */     else if (o instanceof int[]) {
/*     */       
/* 322 */       os.writeByte(1);
/* 323 */       int[] array = (int[])o;
/* 324 */       os.writeShort(array.length);
/* 325 */       for (int i = 0; i < array.length; i++)
/*     */       {
/* 327 */         os.writeInt(array[i]);
/*     */       }
/*     */     }
/* 330 */     else if (o instanceof Short) {
/*     */       
/* 332 */       os.writeByte(2);
/* 333 */       os.writeShort(((Short)o).shortValue());
/*     */     }
/* 335 */     else if (o instanceof short[]) {
/*     */       
/* 337 */       os.writeByte(3);
/* 338 */       short[] oa2 = (short[])o;
/* 339 */       os.writeShort(oa2.length);
/* 340 */       for (int i = 0; i < oa2.length; i++)
/*     */       {
/* 342 */         os.writeShort(oa2[i]);
/*     */       }
/*     */     }
/* 345 */     else if (o instanceof Byte) {
/*     */       
/* 347 */       os.writeByte(4);
/* 348 */       os.writeByte(((Byte)o).byteValue());
/*     */     }
/* 350 */     else if (o instanceof byte[]) {
/*     */       
/* 352 */       os.writeByte(5);
/* 353 */       byte[] oa3 = (byte[])o;
/* 354 */       os.writeShort(oa3.length);
/* 355 */       for (int i = 0; i < oa3.length; i++)
/*     */       {
/* 357 */         os.writeByte(oa3[i]);
/*     */       }
/*     */     }
/* 360 */     else if (o instanceof Long) {
/*     */       
/* 362 */       os.writeByte(6);
/* 363 */       os.writeLong(((Long)o).longValue());
/*     */     }
/* 365 */     else if (o instanceof long[]) {
/*     */       
/* 367 */       os.writeByte(7);
/* 368 */       long[] oa4 = (long[])o;
/* 369 */       os.writeShort(oa4.length);
/* 370 */       for (int i = 0; i < oa4.length; i++)
/*     */       {
/* 372 */         os.writeLong(oa4[i]);
/*     */       }
/*     */     }
/* 375 */     else if (o instanceof Boolean) {
/*     */       
/* 377 */       os.writeByte(8);
/* 378 */       os.writeBoolean(((Boolean)o).booleanValue());
/*     */     }
/* 380 */     else if (o instanceof boolean[]) {
/*     */       
/* 382 */       os.writeByte(9);
/* 383 */       boolean[] oa5 = (boolean[])o;
/* 384 */       os.writeShort(oa5.length);
/* 385 */       byte b = 0;
/* 386 */       for (int j = 0; j < oa5.length; j++) {
/*     */         
/* 388 */         if (j % 8 == 0 && j > 0) {
/*     */           
/* 390 */           os.writeByte(b);
/* 391 */           b = 0;
/*     */         } 
/* 393 */         b = (byte)(b | (byte)((oa5[j] ? 1 : 0) << j % 8));
/*     */       } 
/* 395 */       os.writeByte(b);
/*     */     }
/* 397 */     else if (o instanceof String) {
/*     */       
/* 399 */       os.writeByte(10);
/* 400 */       String oa6 = (String)o;
/* 401 */       os.writeShort(oa6.length());
/* 402 */       os.writeChars(oa6);
/*     */     }
/* 404 */     else if (o instanceof String[]) {
/*     */       
/* 406 */       os.writeByte(11);
/* 407 */       String[] oa7 = (String[])o;
/* 408 */       os.writeShort(oa7.length);
/* 409 */       for (int i = 0; i < oa7.length; i++)
/*     */       {
/* 411 */         os.writeShort(oa7[i].length());
/* 412 */         os.writeChars(oa7[i]);
/*     */       }
/*     */     
/* 415 */     } else if (o instanceof ItemStack) {
/*     */       
/* 417 */       os.writeByte(12);
/* 418 */       ItemStack item = (ItemStack)o;
/* 419 */       int id = Item.func_150891_b(item.func_77973_b());
/* 420 */       os.writeInt(id);
/* 421 */       if (id <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 425 */       os.writeByte(item.field_77994_a);
/* 426 */       os.writeShort(item.func_77960_j());
/* 427 */       boolean nbtData = item.func_77942_o();
/* 428 */       os.writeBoolean(nbtData);
/* 429 */       if (nbtData)
/*     */       {
/* 431 */         NBTTagCompound nbt = item.func_77978_p();
/* 432 */         if (nbt != null)
/*     */         {
/* 434 */           CompressedStreamTools.func_74800_a(nbt, os);
/*     */         }
/*     */       }
/*     */     
/* 438 */     } else if (o instanceof NBTTagCompound) {
/*     */       
/* 440 */       os.writeByte(13);
/* 441 */       CompressedStreamTools.func_74800_a((NBTTagCompound)o, os);
/*     */     }
/* 443 */     else if (o instanceof Block) {
/*     */       
/* 445 */       os.writeByte(14);
/* 446 */       os.writeByte(0);
/* 447 */       os.writeUTF(Block.field_149771_c.func_148750_c(o));
/*     */     }
/* 449 */     else if (o instanceof Item) {
/*     */       
/* 451 */       os.writeByte(14);
/* 452 */       os.writeByte(1);
/* 453 */       os.writeUTF(Item.field_150901_e.func_148750_c(o));
/*     */     }
/* 455 */     else if (o instanceof net.minecraft.stats.Achievement) {
/*     */       
/* 457 */       os.writeByte(14);
/* 458 */       os.writeByte(2);
/* 459 */       os.writeUTF(((StatBase)o).field_75975_e);
/*     */     }
/* 461 */     else if (o instanceof Potion) {
/*     */       
/* 463 */       os.writeByte(14);
/* 464 */       os.writeByte(3);
/* 465 */       os.writeInt(((Potion)o).field_76415_H);
/*     */     }
/* 467 */     else if (o instanceof Enchantment) {
/*     */       
/* 469 */       os.writeByte(14);
/* 470 */       os.writeByte(4);
/* 471 */       os.writeInt(((Enchantment)o).field_77352_x);
/*     */     }
/* 473 */     else if (o instanceof Block[]) {
/*     */       
/* 475 */       Block[] blocks = (Block[])o;
/* 476 */       os.writeByte(14);
/* 477 */       os.writeByte(5);
/* 478 */       os.writeInt(blocks.length);
/* 479 */       for (int i = 0; i < blocks.length; i++)
/*     */       {
/* 481 */         encode(os, blocks[i]);
/*     */       }
/*     */     }
/* 484 */     else if (o instanceof Item[]) {
/*     */       
/* 486 */       Item[] items = (Item[])o;
/* 487 */       os.writeByte(14);
/* 488 */       os.writeByte(5);
/* 489 */       os.writeInt(items.length);
/* 490 */       for (int i = 0; i < items.length; i++)
/*     */       {
/* 492 */         encode(os, items[i]);
/*     */       }
/*     */     }
/* 495 */     else if (o instanceof ChunkCoordinates) {
/*     */       
/* 497 */       os.writeByte(15);
/* 498 */       os.writeByte(0);
/* 499 */       ChunkCoordinates coords = (ChunkCoordinates)o;
/* 500 */       os.writeInt(coords.field_71574_a);
/* 501 */       os.writeInt(coords.field_71572_b);
/* 502 */       os.writeInt(coords.field_71573_c);
/*     */     }
/* 504 */     else if (o instanceof ChunkCoordIntPair) {
/*     */       
/* 506 */       os.writeByte(15);
/* 507 */       os.writeByte(1);
/* 508 */       ChunkCoordIntPair coords = (ChunkCoordIntPair)o;
/* 509 */       os.writeInt(coords.field_77276_a);
/* 510 */       os.writeInt(coords.field_77275_b);
/*     */     }
/* 512 */     else if (o instanceof ChunkPosition) {
/*     */       
/* 514 */       os.writeByte(15);
/* 515 */       os.writeByte(2);
/* 516 */       ChunkPosition pos = (ChunkPosition)o;
/* 517 */       os.writeInt(pos.field_151329_a);
/* 518 */       os.writeInt(pos.field_151327_b);
/* 519 */       os.writeInt(pos.field_151328_c);
/*     */     }
/* 521 */     else if (o instanceof TileEntity) {
/*     */       
/* 523 */       os.writeByte(16);
/* 524 */       TileEntity oa12 = (TileEntity)o;
/* 525 */       os.writeInt((oa12.func_145831_w()).field_73011_w.field_76574_g);
/* 526 */       os.writeInt(oa12.field_145851_c);
/* 527 */       os.writeInt(oa12.field_145848_d);
/* 528 */       os.writeInt(oa12.field_145849_e);
/*     */     }
/* 530 */     else if (o instanceof World) {
/*     */       
/* 532 */       os.writeInt(17);
/* 533 */       os.writeInt(((World)o).field_73011_w.field_76574_g);
/*     */     }
/* 535 */     else if (o instanceof Float) {
/*     */       
/* 537 */       os.writeByte(18);
/* 538 */       os.writeFloat(((Float)o).floatValue());
/*     */     }
/* 540 */     else if (o instanceof float[]) {
/*     */       
/* 542 */       os.writeByte(19);
/* 543 */       float[] oa13 = (float[])o;
/* 544 */       os.writeShort(oa13.length);
/* 545 */       for (int i = 0; i < oa13.length; i++)
/*     */       {
/* 547 */         os.writeFloat(oa13[i]);
/*     */       }
/*     */     }
/* 550 */     else if (o instanceof Double) {
/*     */       
/* 552 */       os.writeByte(20);
/* 553 */       os.writeDouble(((Double)o).doubleValue());
/*     */     }
/* 555 */     else if (o instanceof double[]) {
/*     */       
/* 557 */       os.writeByte(21);
/* 558 */       double[] oa14 = (double[])o;
/* 559 */       os.writeShort(oa14.length);
/* 560 */       for (int i = 0; i < oa14.length; i++)
/*     */       {
/* 562 */         os.writeDouble(oa14[i]);
/*     */       }
/*     */     }
/* 565 */     else if (o instanceof FluidStack) {
/*     */       
/* 567 */       os.writeByte(22);
/* 568 */       FluidStack stack = (FluidStack)o;
/* 569 */       os.writeInt(stack.getFluidID());
/* 570 */       os.writeInt(stack.amount);
/* 571 */       NBTTagCompound data = stack.tag;
/* 572 */       os.writeBoolean((data != null));
/* 573 */       if (data != null)
/*     */       {
/* 575 */         CompressedStreamTools.func_74800_a(data, os);
/*     */       }
/*     */     }
/* 578 */     else if (o instanceof FluidTank) {
/*     */       
/* 580 */       os.writeByte(23);
/* 581 */       FluidTank tank = (FluidTank)o;
/* 582 */       boolean empty = (tank.getFluid() == null);
/* 583 */       os.writeBoolean(empty);
/* 584 */       if (empty)
/*     */       {
/* 586 */         os.writeInt(tank.getCapacity());
/*     */       }
/*     */       else
/*     */       {
/* 590 */         encode(os, tank.getFluid());
/* 591 */         os.writeInt(tank.getCapacity());
/*     */       }
/*     */     
/* 594 */     } else if (o instanceof UUID) {
/*     */       
/* 596 */       os.writeByte(24);
/* 597 */       os.writeByte(0);
/* 598 */       UUID id = (UUID)o;
/* 599 */       os.writeLong(id.getMostSignificantBits());
/* 600 */       os.writeLong(id.getLeastSignificantBits());
/*     */     }
/* 602 */     else if (o instanceof GameProfile) {
/*     */       
/* 604 */       os.writeByte(24);
/* 605 */       os.writeByte(1);
/* 606 */       GameProfile id = (GameProfile)o;
/* 607 */       encode(os, id.getId());
/* 608 */       os.writeUTF(id.getName());
/*     */     }
/* 610 */     else if (o instanceof CropCard) {
/*     */       
/* 612 */       os.writeByte(25);
/* 613 */       CropCard card = (CropCard)o;
/* 614 */       os.writeUTF(card.owner());
/* 615 */       os.writeUTF(card.name());
/*     */     }
/* 617 */     else if (o instanceof Enum) {
/*     */       
/* 619 */       os.writeByte(26);
/* 620 */       os.writeInt(((Enum)o).ordinal());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\DataEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */