package com.hum.user;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author hum
 */
public class Main {

  static int N = 4200000 + 5;
  static int M = N;
  static int[] h = new int[N];
  static int[] e = new int[M];
  static int[] ne = new int[M];
  static int idx = 0;
  static int[] size = new int[N];
  static int[] dep = new int[N];

  public static void main(String[] args) throws IOException {
    String[] filenames = new String[]{"src\\main\\java\\com\\hum\\user\\data\\1-1203787.txt", "src\\main\\java\\com\\hum\\user\\data\\1203788-1928694.txt", "src\\main\\java\\com\\hum\\user\\data\\1928695-2656622.txt", "src\\main\\java\\com\\hum\\user\\data\\2656623-3409051.txt", "src\\main\\java\\com\\hum\\user\\data\\3409052-4112470.txt"};
    Map<Integer, String> id2phone = new HashMap<>();
    Map<String, Integer> phone2id = new HashMap<>();

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();

    int lineCnt = 0;
    for (String filename : filenames) {
      List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
      lines.remove(0);
      lineCnt += lines.size();
      for (String line : lines) {
        String[] strs = line.split(",");
        int id = Integer.parseInt(strs[0]);
        String pa = strs[1];
        String pb = strs[2];
        id2phone.put(id, pa);
        phone2id.put(pa, id);
        list1.add(pa);
        list2.add(pb);
      }
    }

    System.out.println("id2phone.size(): " + id2phone.size());
    System.out.println("phone2id.size(): " + phone2id.size());

    Arrays.fill(h, -1);

    BufferedWriter out2 = new BufferedWriter(new FileWriter("src\\main\\java\\com\\hum\\user\\data\\result2.txt"));
    out2.write("没有邀请人id\r\n");
    for (int i = 0; i < list1.size(); i++) {
      int a = phone2id.getOrDefault(list1.get(i), -1);
      int b = phone2id.getOrDefault(list2.get(i), -1);
      if (a == -1 || b == -1) {
        out2.write(a + "\r\n");
        continue;
      }
      add(b, a);
    }
    out2.flush();

    System.out.println("start");

    for (int i = 1; i <= N; i++) {
      if (id2phone.containsKey(i)) {
        dfs(i);
      }
    }
    System.out.println("write file");

    BufferedWriter out = new BufferedWriter(new FileWriter("src\\main\\java\\com\\hum\\user\\data\\result.txt"));
    out.write("id\tsize\tdep\r\n");
    int cnt = 0;
    for (int i = 1; i <= N; i++) {
      if (id2phone.containsKey(i)) {
        cnt++;
        out.write(i + "\t" + size[i] + "\t" + dep[i]);
        out.write("\r\n");
      }
    }
    System.out.println("write cnt: " + cnt);
    out.flush();
  }

  private static void dfs(int u) {
    if (size[u] != 0) {
      return;
    }
    size[u]++;
    int d = 0;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      dfs(j);
      size[u] += size[j];
      d = Math.max(d, dep[j]);
    }
    dep[u] = d + 1;
  }

  private static void add(int a, int b) {
    e[idx] = b;
    ne[idx] = h[a];
    h[a] = idx++;
  }
}
