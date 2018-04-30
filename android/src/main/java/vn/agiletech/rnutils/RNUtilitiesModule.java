
package vn.agiletech.rnutils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
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

  private final ReactApplicationContext reactContext;

  public RNUtilitiesModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNUtilities";
  }

  /**
   * Inject several needed info
   * @return Map constants
   */
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    final PackageManager packageManager = this.getReactApplicationContext().getPackageManager();
    final String packageName = this.getReactApplicationContext().getPackageName();
    try {
      Locale locale = null;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        locale = getReactApplicationContext().getResources().getConfiguration().getLocales().get(0);
      } else {
        //noinspection deprecation
        locale = getReactApplicationContext().getResources().getConfiguration().locale;
      }
      constants.put(APP_VERSION, packageManager.getPackageInfo(packageName, 0).versionName);
      constants.put(APP_BUILD, packageManager.getPackageInfo(packageName, 0).versionCode);
      constants.put(APP_ID, packageName);
      constants.put(APP_LOCALE, locale.getLanguage());
      constants.put(APP_COUNTRY, locale.getDisplayCountry());
      constants.put(APP_COUNTRY_CODE, locale.getCountry());
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    } finally {
      return constants;
    }
  }

  @ReactMethod
  public void share(final ReadableMap options, final String title) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);

    if (options.hasKey("url")) {
        intent.putExtra(Intent.EXTRA_TEXT, options.getString("url"));
    }

    if (options.hasKey("subject")) {
        intent.putExtra(Intent.EXTRA_SUBJECT, options.getString("subject"));
    }

    intent.setType("text/plain");

    Intent chooser = Intent.createChooser(intent, title);
    chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    this.getReactApplicationContext().startActivity(chooser);
  }
}