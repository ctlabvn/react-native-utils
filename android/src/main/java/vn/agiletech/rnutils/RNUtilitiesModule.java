
package vn.agiletech.rnutils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by anhtuank7c on 3/26/18.
 */
public class RNUtilitiesModule extends ReactContextBaseJavaModule {
  private static final String APP_VERSION = "appVersion";
  private static final String APP_BUILD = "buildVersion";
  private static final String APP_ID = "bundleIdentifier";
  private static final String APP_LOCALE = "locale";
  private static final String APP_COUNTRY = "country";
  private static final String APP_COUNTRY_CODE = "countryCode";
  private static final List<String> SHARE_TYPES = Arrays.asList("text/plain", "image/*", "audio/*", "video/*");

  public RNUtilitiesModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNUtilities";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    final PackageManager packageManager = this.getReactApplicationContext().getPackageManager();
    final String packageName = this.getReactApplicationContext().getPackageName();
    try {
      Locale locale = getReactApplicationContext().getResources().getConfiguration().locale;
      constants.put(APP_VERSION, packageManager.getPackageInfo(packageName, 0).versionName);
      constants.put(APP_BUILD, packageManager.getPackageInfo(packageName, 0).versionCode);
      constants.put(APP_ID, packageName);
      constants.put(APP_LOCALE, locale.getLanguage());
      constants.put(APP_COUNTRY, locale.getDisplayCountry());
      constants.put(APP_COUNTRY_CODE, locale.getCountry());
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return constants;
  }

  @ReactMethod
  public void share(final ReadableMap options, Promise promise) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);

    if(!options.hasKey("title")) {
      promise.reject(new Exception("Missing title field"));
    }
    if (options.hasKey("subject")) {
        intent.putExtra(Intent.EXTRA_SUBJECT, options.getString("subject"));
    }
    if (options.hasKey("url")) {
      intent.putExtra(Intent.EXTRA_TEXT, options.getString("url"));
    }
    if(!options.hasKey("type") || (options.hasKey("type") && !SHARE_TYPES.contains(options.getString("type")))) {
      intent.setType("text/plain");
    } else {
      intent.setType(options.getString("type"));
      if(!options.hasKey("url")) {
        promise.reject(new Exception("Missing url field"));
      }
      intent.putExtra(Intent.EXTRA_TEXT, options.getString("url"));
    }
    Intent chooser = Intent.createChooser(intent, options.getString("title"));
    chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    this.getReactApplicationContext().startActivity(chooser);

    WritableMap result = Arguments.createMap();
    result.putBoolean("success", true);
    result.putString("method", "unknown");
    promise.resolve(result);
  }
}