package com.chryl.util;
public class DES{
  public static final byte EN0 = 0; /* MODE == encrypt */
  public static final byte DE1 = 1; /* MODE == decrypt */

  private final int[] KnL = new int[32];
  private final int[] KnR = new int[32];
  private final int[] Kn3 = new int[32];
  
//  private static final byte[] Df_Key = new byte[ /*24*/]{
//      (byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67, (byte)0x89,
//      (byte)0xab, (byte)0xcd, (byte)0xef,
//      (byte)0xfe, (byte)0xdc, (byte)0xba, (byte)0x98, (byte)0x76,
//      (byte)0x54, (byte)0x32, (byte)0x10,
//      (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef, (byte)0x01,
//      (byte)0x23, (byte)0x45, (byte)0x67
//  };
//  

  private static final char[] bytebit = new char[ /*8*/]{
      0200, 0100, 040, 020, 010, 04, 02, 01
  };

  private static final int[] bigbyte = new int[ /*24*/]{
      0x00800000, 0x400000, 0x200000, 0x100000,
      0x0080000, 0x40000, 0x20000, 0x10000,
      0x008000, 0x4000, 0x2000, 0x1000,
      0x00800, 0x400, 0x200, 0x100,
      0x0080, 0x40, 0x20, 0x10,
      0x008, 0x4, 0x2, 0x1
  };

  /* Use the key schedule specified in the Standard (ANSI X3.92-1981). */

  private static final byte[] pc1 = new byte[ /*56*/]{
      (byte)56, (byte)48, (byte)40, (byte)32, (byte)24, (byte)16,
      (byte)8,
      (byte)0, (byte)57, (byte)49, (byte)41, (byte)33, (byte)25,
      (byte)17,
      (byte)9, (byte)1, (byte)58, (byte)50, (byte)42, (byte)34, (byte)26,
      (byte)18, (byte)10, (byte)2, (byte)59, (byte)51, (byte)43,
      (byte)35,
      (byte)62, (byte)54, (byte)46, (byte)38, (byte)30, (byte)22,
      (byte)14,
      (byte)6, (byte)61, (byte)53, (byte)45, (byte)37, (byte)29,
      (byte)21,
      (byte)13, (byte)5, (byte)60, (byte)52, (byte)44, (byte)36,
      (byte)28,
      (byte)20, (byte)12, (byte)4, (byte)27, (byte)19, (byte)11, (byte)3
  };

  private static final byte[] totrot = new byte[ /*16*/]{
      (byte)1, (byte)2, (byte)4, (byte)6, (byte)8, (byte)10, (byte)12,
      (byte)14,
      (byte)15, (byte)17, (byte)19, (byte)21, (byte)23, (byte)25,
      (byte)27, (byte)28
  };

  private static final byte[] pc2 = new byte[ /*48*/]{
      (byte)13, (byte)16, (byte)10, (byte)23, (byte)0, (byte)4, (byte)2,
      (byte)27,
      (byte)14, (byte)5, (byte)20, (byte)9, (byte)22, (byte)18, (byte)11,
      (byte)3,
      (byte)25, (byte)7, (byte)15, (byte)6, (byte)26, (byte)19, (byte)12,
      (byte)1,
      (byte)40, (byte)51, (byte)30, (byte)36, (byte)46, (byte)54,
      (byte)29, (byte)39,
      (byte)50, (byte)44, (byte)32, (byte)47, (byte)43, (byte)48,
      (byte)38, (byte)55,
      (byte)33, (byte)52, (byte)45, (byte)41, (byte)49, (byte)35,
      (byte)28, (byte)31};

  private static final int[] SP1 = new int[ /*64*/]{
      0x0001010400, 0x0000000000, 0x0000010000, 0x0001010404,
      0x0001010004, 0x0000010404, 0x0000000004, 0x0000010000,
      0x0000000400, 0x0001010400, 0x0001010404, 0x0000000400,
      0x0001000404, 0x0001010004, 0x0001000000, 0x0000000004,
      0x0000000404, 0x0001000400, 0x0001000400, 0x0000010400,
      0x0000010400, 0x0001010000, 0x0001010000, 0x0001000404,
      0x0000010004, 0x0001000004, 0x0001000004, 0x0000010004,
      0x0000000000, 0x0000000404, 0x0000010404, 0x0001000000,
      0x0000010000, 0x0001010404, 0x0000000004, 0x0001010000,
      0x0001010400, 0x0001000000, 0x0001000000, 0x0000000400,
      0x0001010004, 0x0000010000, 0x0000010400, 0x0001000004,
      0x0000000400, 0x0000000004, 0x0001000404, 0x0000010404,
      0x0001010404, 0x0000010004, 0x0001010000, 0x0001000404,
      0x0001000004, 0x0000000404, 0x0000010404, 0x0001010400,
      0x0000000404, 0x0001000400, 0x0001000400, 0x0000000000,
      0x0000010004, 0x0000010400, 0x0000000000, 0x0001010004};
  private static final int[] SP2 = new int[ /*64*/]{
      0x0080108020, 0x0080008000, 0x0000008000, 0x0000108020,
      0x0000100000, 0x0000000020, 0x0080100020, 0x0080008020,
      0x0080000020, 0x0080108020, 0x0080108000, 0x0080000000,
      0x0080008000, 0x0000100000, 0x0000000020, 0x0080100020,
      0x0000108000, 0x0000100020, 0x0080008020, 0x0000000000,
      0x0080000000, 0x0000008000, 0x0000108020, 0x0080100000,
      0x0000100020, 0x0080000020, 0x0000000000, 0x0000108000,
      0x0000008020, 0x0080108000, 0x0080100000, 0x0000008020,
      0x0000000000, 0x0000108020, 0x0080100020, 0x0000100000,
      0x0080008020, 0x0080100000, 0x0080108000, 0x0000008000,
      0x0080100000, 0x0080008000, 0x0000000020, 0x0080108020,
      0x0000108020, 0x0000000020, 0x0000008000, 0x0080000000,
      0x0000008020, 0x0080108000, 0x0000100000, 0x0080000020,
      0x0000100020, 0x0080008020, 0x0080000020, 0x0000100020,
      0x0000108000, 0x0000000000, 0x0080008000, 0x0000008020,
      0x0080000000, 0x0080100020, 0x0080108020, 0x0000108000};
  private static final int[] SP3 = new int[ /*64*/]{
      0x0000000208, 0x0008020200, 0x0000000000, 0x0008020008,
      0x0008000200, 0x0000000000, 0x0000020208, 0x0008000200,
      0x0000020008, 0x0008000008, 0x0008000008, 0x0000020000,
      0x0008020208, 0x0000020008, 0x0008020000, 0x0000000208,
      0x0008000000, 0x0000000008, 0x0008020200, 0x0000000200,
      0x0000020200, 0x0008020000, 0x0008020008, 0x0000020208,
      0x0008000208, 0x0000020200, 0x0000020000, 0x0008000208,
      0x0000000008, 0x0008020208, 0x0000000200, 0x0008000000,
      0x0008020200, 0x0008000000, 0x0000020008, 0x0000000208,
      0x0000020000, 0x0008020200, 0x0008000200, 0x0000000000,
      0x0000000200, 0x0000020008, 0x0008020208, 0x0008000200,
      0x0008000008, 0x0000000200, 0x0000000000, 0x0008020008,
      0x0008000208, 0x0000020000, 0x0008000000, 0x0008020208,
      0x0000000008, 0x0000020208, 0x0000020200, 0x0008000008,
      0x0008020000, 0x0008000208, 0x0000000208, 0x0008020000,
      0x0000020208, 0x0000000008, 0x0008020008, 0x0000020200};
  private static final int[] SP4 = new int[ /*64*/]{
      0x0000802001, 0x0000002081, 0x0000002081, 0x0000000080,
      0x0000802080, 0x0000800081, 0x0000800001, 0x0000002001,
      0x0000000000, 0x0000802000, 0x0000802000, 0x0000802081,
      0x0000000081, 0x0000000000, 0x0000800080, 0x0000800001,
      0x0000000001, 0x0000002000, 0x0000800000, 0x0000802001,
      0x0000000080, 0x0000800000, 0x0000002001, 0x0000002080,
      0x0000800081, 0x0000000001, 0x0000002080, 0x0000800080,
      0x0000002000, 0x0000802080, 0x0000802081, 0x0000000081,
      0x0000800080, 0x0000800001, 0x0000802000, 0x0000802081,
      0x0000000081, 0x0000000000, 0x0000000000, 0x0000802000,
      0x0000002080, 0x0000800080, 0x0000800081, 0x0000000001,
      0x0000802001, 0x0000002081, 0x0000002081, 0x0000000080,
      0x0000802081, 0x0000000081, 0x0000000001, 0x0000002000,
      0x0000800001, 0x0000002001, 0x0000802080, 0x0000800081,
      0x0000002001, 0x0000002080, 0x0000800000, 0x0000802001,
      0x0000000080, 0x0000800000, 0x0000002000, 0x0000802080};
  private static final int[] SP5 = new int[ /*64*/]{
      0x0000000100, 0x0002080100, 0x0002080000, 0x0042000100,
      0x0000080000, 0x0000000100, 0x0040000000, 0x0002080000,
      0x0040080100, 0x0000080000, 0x0002000100, 0x0040080100,
      0x0042000100, 0x0042080000, 0x0000080100, 0x0040000000,
      0x0002000000, 0x0040080000, 0x0040080000, 0x0000000000,
      0x0040000100, 0x0042080100, 0x0042080100, 0x0002000100,
      0x0042080000, 0x0040000100, 0x0000000000, 0x0042000000,
      0x0002080100, 0x0002000000, 0x0042000000, 0x0000080100,
      0x0000080000, 0x0042000100, 0x0000000100, 0x0002000000,
      0x0040000000, 0x0002080000, 0x0042000100, 0x0040080100,
      0x0002000100, 0x0040000000, 0x0042080000, 0x0002080100,
      0x0040080100, 0x0000000100, 0x0002000000, 0x0042080000,
      0x0042080100, 0x0000080100, 0x0042000000, 0x0042080100,
      0x0002080000, 0x0000000000, 0x0040080000, 0x0042000000,
      0x0000080100, 0x0002000100, 0x0040000100, 0x0000080000,
      0x0000000000, 0x0040080000, 0x0002080100, 0x0040000100};
  private static final int[] SP6 = new int[ /*64*/]{
      0x0020000010, 0x0020400000, 0x0000004000, 0x0020404010,
      //0x0020400000000010, 0x0020404010, 0x0000400000,
      0x0020400000, 0x0000000010, 0x0020404010, 0x0000400000,
      0x0020004000, 0x0000404010, 0x0000400000, 0x0020000010,
      0x0000400010, 0x0020004000, 0x0020000000, 0x0000004010,
      0x0000000000, 0x0000400010, 0x0020004010, 0x0000004000,
      0x0000404000, 0x0020004010, 0x0000000010, 0x0020400010,
      0x0020400010, 0x0000000000, 0x0000404010, 0x0020404000,
      0x0000004010, 0x0000404000, 0x0020404000, 0x0020000000,
      0x0020004000, 0x0000000010, 0x0020400010, 0x0000404000,
      0x0020404010, 0x0000400000, 0x0000004010, 0x0020000010,
      0x0000400000, 0x0020004000, 0x0020000000, 0x0000004010,
      0x0020000010, 0x0020404010, 0x0000404000, 0x0020400000,
      0x0000404010, 0x0020404000, 0x0000000000, 0x0020400010,
      0x0000000010, 0x0000004000, 0x0020400000, 0x0000404010,
      0x0000004000, 0x0000400010, 0x0020004010, 0x0000000000,
      0x0020404000, 0x0020000000, 0x0000400010, 0x0020004010};
  private static final int[] SP7 = new int[ /*64*/]{
      0x0000200000, 0x0004200002, 0x0004000802, 0x0000000000,
      0x0000000800, 0x0004000802, 0x0000200802, 0x0004200800,
      0x0004200802, 0x0000200000, 0x0000000000, 0x0004000002,
      0x0000000002, 0x0004000000, 0x0004200002, 0x0000000802,
      0x0004000800, 0x0000200802, 0x0000200002, 0x0004000800,
      0x0004000002, 0x0004200000, 0x0004200800, 0x0000200002,
      0x0004200000, 0x0000000800, 0x0000000802, 0x0004200802,
      0x0000200800, 0x0000000002, 0x0004000000, 0x0000200800,
      0x0004000000, 0x0000200800, 0x0000200000, 0x0004000802,
      0x0004000802, 0x0004200002, 0x0004200002, 0x0000000002,
      0x0000200002, 0x0004000000, 0x0004000800, 0x0000200000,
      0x0004200800, 0x0000000802, 0x0000200802, 0x0004200800,
      0x0000000802, 0x0004000002, 0x0004200802, 0x0004200000,
      0x0000200800, 0x0000000000, 0x0000000002, 0x0004200802,
      0x0000000000, 0x0000200802, 0x0004200000, 0x0000000800,
      0x0004000002, 0x0004000800, 0x0000000800, 0x0000200002};
  private static final int[] SP8 = new int[ /*64*/]{
      0x0010001040, 0x0000001000, 0x0000040000, 0x0010041040,
      0x0010000000, 0x0010001040, 0x0000000040, 0x0010000000,
      0x0000040040, 0x0010040000, 0x0010041040, 0x0000041000,
      0x0010041000, 0x0000041040, 0x0000001000, 0x0000000040,
      0x0010040000, 0x0010000040, 0x0010001000, 0x0000001040,
      0x0000041000, 0x0000040040, 0x0010040040, 0x0010041000,
      0x0000001040, 0x0000000000, 0x0000000000, 0x0010040040,
      0x0010000040, 0x0010001000, 0x0000041040, 0x0000040000,
      0x0000041040, 0x0000040000, 0x0010041000, 0x0000001000,
      0x0000000040, 0x0010040040, 0x0000001000, 0x0000041040,
      0x0010001000, 0x0000000040, 0x0010000040, 0x0010040000,
      0x0010040040, 0x0010000000, 0x0000040000, 0x0010001040,
      0x0000000000, 0x0010041040, 0x0000040040, 0x0010000040,
      0x0010040000, 0x0010001000, 0x0010001040, 0x0000000000,
      0x0010041040, 0x0000041000, 0x0000041000, 0x0000001040,
      0x0000001040, 0x0000040040, 0x0010000000, 0x0010041000};

  static{
  }

  public DES(){
  }

  public void deskey(byte[] key, byte mode){
    int i, j, l, m, n;
    byte[] pc1m = new byte[56], pcr = new byte[56];
    int[] kn = new int[32];

    for(j = 0; j < 56; j++){
      l = pc1[j];
      m = l & 07;
      pc1m[j] = (byte)(((key[l >> 3] & bytebit[m])) != 0 ? 1 : 0);
    }
    for(i = 0; i < 16; i++){
      if(mode == DE1){
        m = (15 - i) << 1;
      } else{
        m = i << 1;
      }
      n = m + 1;
      kn[m] = kn[n] = 0;
      for(j = 0; j < 28; j++){
        l = j + totrot[i];
        if(l < 28){
          pcr[j] = pc1m[l];
        } else{
          pcr[j] = pc1m[l - 28];
        }
      }
      for(j = 28; j < 56; j++){
        l = j + totrot[i];
        if(l < 56){
          pcr[j] = pc1m[l];
        } else{
          pcr[j] = pc1m[l - 28];
        }
      }
      for(j = 0; j < 24; j++){
        if(pcr[pc2[j]] != 0){
          kn[m] |= bigbyte[j];
        }
        if(pcr[pc2[j + 24]] != 0){
          kn[n] |= bigbyte[j];
        }
      }
    }
    cookey(kn);
    return;
  }

  public void cookey(int[] raw1){
    int cook, raw0;
    int[] dough = new int[32];
    int i;

    cook = 0;
    raw0 = 0;
    for(i = 0; i < 16; i++, raw0 += 2){
      dough[cook] = (raw1[raw0] & 0x00fc0000) << 6;
      dough[cook] |= (raw1[raw0] & 0x00000fc0) << 10;
      dough[cook] |= ((raw1[raw0 + 1] & 0x00fc0000) >> 10) & 0x003fffff;
      dough[cook++] |= ((raw1[raw0 + 1] & 0x00000fc0) >> 6) & 0x03ffffff;
      dough[cook] = (raw1[raw0] & 0x0003f000) << 12;
      dough[cook] |= (raw1[raw0] & 0x0000003f) << 16;
      dough[cook] |= ((raw1[raw0 + 1] & 0x0003f000) >> 4) & 0x0fffffff;
      dough[cook++] |= (raw1[raw0 + 1] & 0x0000003f);
    }
    usekey(dough);
    return;
  }

  public void cpkey(int[] into){
    System.arraycopy(KnL, 0, into, 0, KnL.length);
  }

  public void usekey(int[] from){
    System.arraycopy(from, 0, KnL, 0, KnL.length);
  }

  public void des(byte[] inblock, byte[] outblock){
    int[] work = new int[2];

    scrunch(inblock, work);
    desfunc(work, KnL);
    unscrun(work, outblock);
  }

  public byte[] des(byte[] inblock){
    byte[] outblock = new byte[8];
    des(inblock, outblock);
    return outblock;
  }

  public byte[] des(byte mode, byte[] inblock, byte[] key){
    byte[] outblock = new byte[8];
    deskey(key, mode);
    des(inblock, outblock);
    return outblock;
  }

  public byte[] desAll(byte mode, byte[] from, byte[] key){
    if(from == null || key == null){
      return null;
    }

    int fromLen = from.length;
    int blocks = (from.length+7)/8;
    byte[] ret = new byte[blocks*8];
    deskey(key, mode);
    byte[] to = new byte[8];
    byte[] ft = new byte[8];
    for(int i = 0; i < blocks; i++){
      if(i == blocks-1){
        ft = new byte[8];  //�󲹳�0
        System.arraycopy(from, i * 8, ft, 0, fromLen - (blocks-1)*8);
      }else{
        System.arraycopy(from, i * 8, ft, 0, 8);
      }
      des(ft, to);
      System.arraycopy(to, 0, ret, i*8, 8);
    }
    return ret;
  }  
  
  public byte[] des3(byte mode, byte[] from, byte[] key){
    if(from == null || key == null){
      return null;
    }

    int fromLen = from.length;
    int blocks = (from.length+7)/8;
    byte[] ret = new byte[blocks*8];
    des2key(key, mode);
    byte[] to = new byte[8];
    byte[] ft = new byte[8];
    for(int i = 0; i < blocks; i++){
      if(i == blocks-1){
        ft = new byte[8];  //�󲹳�0
        System.arraycopy(from, i * 8, ft, 0, fromLen - (blocks-1)*8);
      }else{
        System.arraycopy(from, i * 8, ft, 0, 8);
      }
      Ddes(ft, to);
      System.arraycopy(to, 0, ret, i*8, 8);
    }
    return ret;
  }

  void scrunch(byte[] outof, int[] into){
    into[0] = (outof[0] & 0x00ff) << 24;
    into[0] |= (outof[1] & 0x00ff) << 16;
    into[0] |= (outof[2] & 0x00ff) << 8;
    into[0] |= (outof[3] & 0x00ff);
    into[1] = (outof[4] & 0x00ff) << 24;
    into[1] |= (outof[5] & 0x00ff) << 16;
    into[1] |= (outof[6] & 0x00ff) << 8;
    into[1] |= (outof[7] & 0x00ff);
  }

  void unscrun(int[] outof, byte[] into){
    into[0] = (byte)((outof[0] >> 24) & 0x00ff);
    into[1] = (byte)((outof[0] >> 16) & 0x00ff);
    into[2] = (byte)((outof[0] >> 8) & 0x00ff);
    into[3] = (byte)(outof[0] & 0x00ff);
    into[4] = (byte)((outof[1] >> 24) & 0x00ff);
    into[5] = (byte)((outof[1] >> 16) & 0x00ff);
    into[6] = (byte)((outof[1] >> 8) & 0x00ff);
    into[7] = (byte)(outof[1] & 0x00ff);
  }

  void desfunc(int[] block, int[] keys){
    int fval, work, right, leftt;
    int round;

    int keys0 = 0;
    int block0 = 0;

    leftt = block[0];
    right = block[1];
    work = ((leftt >> 4) ^ right) & 0x0f0f0f0f;
    right ^= work;
    leftt ^= (work << 4);
    work = ((leftt >> 16) ^ right) & 0x0000ffff;
    right ^= work;
    leftt ^= (work << 16);
    work = ((right >> 2) ^ leftt) & 0x33333333;
    leftt ^= work;
    right ^= (work << 2);
    work = ((right >> 8) ^ leftt) & 0x00ff00ff;
    leftt ^= work;
    right ^= (work << 8);
    right = ((right << 1) | ((right >> 31) & 1)) & 0xffffffff;
    work = (leftt ^ right) & 0xaaaaaaaa;
    leftt ^= work;
    right ^= work;
    leftt = ((leftt << 1) | ((leftt >> 31) & 1)) & 0x00ffffffff;

    for(round = 0; round < 8; round++){
      work = (right << 28) | ((right >> 4) & 0x0fffffff);
      work ^= keys[keys0++];
      fval = SP7[(int)(work & 0x3f)];
      fval |= SP5[(int)((work >> 8) & 0x3f)];
      fval |= SP3[(int)((work >> 16) & 0x3f)];
      fval |= SP1[(int)((work >> 24) & 0x3f)];
      work = right ^ keys[keys0++];  //fval=S8(n)work & 0x3f)];
      fval |= SP8[(int)(work & 0x3f)];
      fval |= SP6[(int)((work >> 8) & 0x3f)];
      fval |= SP4[(int)((work >> 16) & 0x3f)];
      fval |= SP2[(int)((work >> 24) & 0x3f)];
      leftt ^= fval;
      work = (leftt << 28) | ((leftt >> 4) & 0x0fffffff);
      work ^= keys[keys0++];
      fval = SP7[(int)(work & 0x3f)];
      fval |= SP5[(int)((work >> 8) & 0x3f)];
      fval |= SP3[(int)((work >> 16) & 0x3f)];
      fval |= SP1[(int)((work >> 24) & 0x3f)];
      work = leftt ^ keys[keys0++];
      fval |= SP8[(int)(work & 0x3f)];
      fval |= SP6[(int)((work >> 8) & 0x3f)];
      fval |= SP4[(int)((work >> 16) & 0x3f)];
      fval |= SP2[(int)((work >> 24) & 0x3f)];
      right ^= fval;
    }

    right = (right << 31) | ((right >> 1) & 0x7fffffff);
    work = (leftt ^ right) & 0x00aaaaaaaa;
    leftt ^= work;
    right ^= work;
    leftt = (leftt << 31) | ((leftt >> 1) & 0x7fffffff);
    work = (((leftt >> 8) & 0x00ffffff) ^ right) & 0x00ff00ff;
    right ^= work;
    leftt ^= (work << 8);
    work = (((leftt >> 2) & 0x3fffffff) ^ right) & 0x33333333;
    right ^= work;
    leftt ^= (work << 2);
    work = (((right >> 16) & 0x0000ffff) ^ leftt) & 0x0000ffff;
    leftt ^= work;
    right ^= (work << 16);
    work = (((right >> 4) & 0x0fffffff) ^ leftt) & 0x0f0f0f0f;
    leftt ^= work;
    right ^= (work << 4);
    block[block0++] = right;
    block[block0] = leftt;
    return;
  }

  void des2key(byte[] hexkey, byte mode) /* stomps on Kn3 too */
  /* hexkey : unsigned char[16] */
  {
    byte revmod;
    byte[] key2 = new byte[8];
    System.arraycopy(hexkey, 8, key2, 0, 8);
    revmod = (mode == EN0) ? DE1 : EN0;
    deskey(key2, revmod);
    cpkey(KnR);
    deskey(hexkey, mode);
    cpkey(Kn3); /* Kn3 = KnL */
    return;
  }

  void Ddes(byte[] from, byte[] into)
  /*param :  unsigned char[8] */
  {
    int[] work = new int[2];

    scrunch(from, work);
    desfunc(work, KnL);
    desfunc(work, KnR);
    desfunc(work, Kn3);
    unscrun(work, into);
    return;
  }

  public static byte [] Xor(byte [] vect1, byte [] vect2, int iLen) {
    return XorByOff(vect1, vect2, 0, iLen);
  }

  public static byte [] XorByOff(byte [] vect1, byte [] vect2, int off, int iLen) {
    byte [] ret = new byte[iLen];
    for(int i=0; i<iLen; i++){
      ret[i] = (byte) (vect1[i]^vect2[off+i]);
    }
    return ret;
  }
  
  public static byte [] genPbocMac(byte [] key, byte[] data) {
    byte [] mac = new byte[8];
    byte [] buf = new byte[8];
    
    int iBlock = 0;
    int iLen = data.length;
    DES des = new DES();
    while(iLen > iBlock) {
      if((iLen - iBlock) <= 8) {
        if((iLen - iBlock) == 8){
          mac = XorByOff(mac, data, iBlock, 8);
          mac = des.des(DES.EN0, mac, key);
          buf = new byte[8];
          buf[0] = (byte) 0x80;
          mac = Xor(mac, buf, 8);
          mac = des.des3(DES.EN0, mac, key);
          return mac;
        }else{
          buf = new byte[8];
          System.arraycopy(data, iBlock, buf, 0, iLen - iBlock);
          buf[iLen - iBlock] = (byte) 0x80;
          mac = Xor(mac, buf, 8);
          mac = des.des3(DES.EN0, mac, key);
          return mac;
        }
      }
      mac = XorByOff(mac, data, iBlock, 8);
      mac = des.des(DES.EN0, mac, key);
      iBlock = iBlock + 8;
    }
    return mac;
  }
  
  
  public static byte [] genMac(byte [] key, byte[] data) {
    byte [] mac = new byte[8];
    byte [] buf = new byte[8];
    
    int iBlock = 0;
    int iLen = data.length;
    DES des = new DES();
    while(iLen > iBlock) {
      if((iLen - iBlock) <= 8) {
        if((iLen - iBlock) == 8){
          mac = XorByOff(mac, data, iBlock, 8);
          mac = des.des3(DES.EN0, mac, key);
          return mac;
        }else{
          buf = new byte[8];
          System.arraycopy(data, iBlock, buf, 0, iLen - iBlock);
          mac = Xor(mac, buf, 8);
          mac = des.des3(DES.EN0, mac, key);
          return mac;
        }
      }
      mac = XorByOff(mac, data, iBlock, 8);
      mac = des.des3(DES.EN0, mac, key);
      iBlock = iBlock + 8;
    }
    return mac;
  } 
  
  
  public static byte [] pbocDivKey(byte []mastKey, byte[] divData) {
    if(divData.length < 8){
      return null;
    }
    byte [] div2 = new byte[8];
    for(int i=0; i<8; i++){
      div2[i] = (byte) ((byte) 0xFF - divData[i]);
    }
    DES des = new DES();
    byte[] ret1 = des.des3(DES.EN0, divData, mastKey);
    byte[] ret2 = des.des3(DES.EN0, div2, mastKey);
    byte[] ret = new byte[16];
    System.arraycopy(ret1, 0, ret, 0, 8);
    System.arraycopy(ret2, 0, ret, 8, 8);
    return ret;
  }
}
