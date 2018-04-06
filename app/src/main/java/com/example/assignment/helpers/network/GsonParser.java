package com.example.assignment.helpers.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The helper for JSON format
 */
public class GsonParser {
  /**
   * Returns decoded data from a file of assets.
   * @param fileNameFromAssets - the filename
   * @param context - The context
   * @param typeToken - The class for deserialisation
   * @param <DynamicObject> Type of the object
   * @return object
   * @throws IOException If there is an error with a file, throws an exception.
   */
  public static <DynamicObject> DynamicObject getParsedObjectByType(
    String fileNameFromAssets, Context context,
    TypeToken<DynamicObject> typeToken
  ) throws IOException {
    InputStream raw = context.getAssets().open(fileNameFromAssets);
    InputStreamReader reader = new InputStreamReader(raw, "UTF8");
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.enableComplexMapKeySerialization().create();
    return gson.fromJson(reader, typeToken.getType());
  }

  /**
   * Returns decoded data from a string.
   */
  public static <DynamicObject> DynamicObject getParsedObjectByType(
      String content,
      TypeToken<DynamicObject> typeToken
  ) throws IOException {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.enableComplexMapKeySerialization().create();
    return gson.fromJson(content, typeToken.getType());
  }

  /**
   * Returns serialised object
   * @param o - the object for serialisation
   * @return the serialised string
   */
  public static String toJson(Object o)  {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.enableComplexMapKeySerialization().create();
    return gson.toJson(o);
  }
}
