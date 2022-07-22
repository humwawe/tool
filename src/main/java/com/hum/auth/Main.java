package com.hum.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author PF-2CRL0N
 */
public class Main {

  public static void main(String[] args) throws IOException {
    String filename = "src\\main\\java\\com\\hum\\auth\\data\\auth.txt";
    String jsonStr = Files.readString(Path.of(filename), StandardCharsets.UTF_8);
    JSONObject jsonObject = JSON.parseObject(jsonStr);

    JSONArray dataArray = jsonObject.getJSONArray("data");
    for (Object data : dataArray) {
      JSONObject eleObject = objectToJSONObject(data);
      dfs(eleObject, 0);
    }
  }

  private static void dfs(JSONObject eleObject, int dep) {
    System.out.println("\t".repeat(dep) + eleObject.get("name"));
    JSONArray children = eleObject.getJSONArray("children");
    if (children.size() == 0) {
      return;
    }
    for (Object child : children) {
      JSONObject childJsonObject = objectToJSONObject(child);
      dfs(childJsonObject, dep + 1);
    }
  }

  private static JSONObject objectToJSONObject(Object o) {
    return (JSONObject) o;
    //    return JSON.parseObject(o.toString());
  }


}
