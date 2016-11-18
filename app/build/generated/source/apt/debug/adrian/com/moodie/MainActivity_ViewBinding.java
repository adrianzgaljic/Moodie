// Generated code from Butter Knife. Do not modify!
package adrian.com.moodie;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.facebook.login.widget.LoginButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  @UiThread
  public MainActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.facebookLayout = Utils.findRequiredViewAsType(source, R.id.facebook_layout, "field 'facebookLayout'", LinearLayout.class);
    target.loginButton = Utils.findRequiredViewAsType(source, R.id.login_button, "field 'loginButton'", LoginButton.class);
    target.movieRecyclerView = Utils.findRequiredViewAsType(source, R.id.movies_recycler_view, "field 'movieRecyclerView'", RecyclerView.class);
    target.ivMood = Utils.findRequiredViewAsType(source, R.id.iv_mood, "field 'ivMood'", ImageView.class);
    target.tvMood = Utils.findRequiredViewAsType(source, R.id.tv_mood, "field 'tvMood'", TextView.class);
    target.tvHappy = Utils.findRequiredViewAsType(source, R.id.tv_happy_count, "field 'tvHappy'", TextView.class);
    target.tvHaha = Utils.findRequiredViewAsType(source, R.id.tv_haha_count, "field 'tvHaha'", TextView.class);
    target.tvInLove = Utils.findRequiredViewAsType(source, R.id.tv_inlove_count, "field 'tvInLove'", TextView.class);
    target.tvWow = Utils.findRequiredViewAsType(source, R.id.tv_wow_count, "field 'tvWow'", TextView.class);
    target.tvSad = Utils.findRequiredViewAsType(source, R.id.tv_sad_count, "field 'tvSad'", TextView.class);
    target.tvAngry = Utils.findRequiredViewAsType(source, R.id.tv_angry_count, "field 'tvAngry'", TextView.class);
    target.tvWeather = Utils.findRequiredViewAsType(source, R.id.tv_weather, "field 'tvWeather'", TextView.class);
    target.ivWeatherIcon = Utils.findRequiredViewAsType(source, R.id.iv_weather_icon, "field 'ivWeatherIcon'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.facebookLayout = null;
    target.loginButton = null;
    target.movieRecyclerView = null;
    target.ivMood = null;
    target.tvMood = null;
    target.tvHappy = null;
    target.tvHaha = null;
    target.tvInLove = null;
    target.tvWow = null;
    target.tvSad = null;
    target.tvAngry = null;
    target.tvWeather = null;
    target.ivWeatherIcon = null;

    this.target = null;
  }
}
