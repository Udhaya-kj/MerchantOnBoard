package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;

import life.corals.onboarding.Dialogs.AlertDialogYesNo;
import life.corals.onboarding.R;

public class Welcome_Activity extends AppCompatActivity implements OnLikeListener,
        OnAnimationEndListener {

    private LinearLayout button_next;
    public static final String MyPREFERENCES_OPEN_SCREEN = "MyPrefs_Open_Screen";
    public static final String OPEN_SCREEN = "OPEN_SCREEN";
    private SharedPreferences sharedpreferences_open_screen;
    LikeButton likeButton;
    private AlertDialogYesNo alertDialogYesNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);

        sharedpreferences_open_screen = getSharedPreferences(MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        likeButton = (LikeButton) findViewById(R.id.star_button);
        likeButton.setVisibility(View.GONE);

        SharedPreferences preferences_buss_det = getSharedPreferences(Business_Details_Activity.MyPREFERENCES_BUSSINESS_DETAILS, Context.MODE_PRIVATE);
        String get_sp = preferences_buss_det.getString(Business_Details_Activity.BUSSINESS_TYPE_INDEX, "");
        Log.d("Open_Screen---->", "" + preferences_buss_det.getAll().toString() + "," + get_sp);
        if (!TextUtils.isEmpty(get_sp)) {
            alertDialogYesNo = new AlertDialogYesNo(Welcome_Activity.this, "", "We notice you have an active application. Do you want to continue?", "YES", "NO", false, true, true, false, false) {
                @Override
                public void onOKButtonClick() {

                    final String os = sharedpreferences_open_screen.getString(OPEN_SCREEN, "");
                    //don't clear spref data
                    if (os.equals("1")) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                likeButton.setVisibility(View.VISIBLE);
                                likeButton.performClick();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        likeButton.setVisibility(View.GONE);
                                    }
                                }, 1000);
                            }
                        }, 500);

                    } else if (os.equals("2")) {
                        Intent in = new Intent(getApplicationContext(), Terms_Conditions_Activity.class);
                        startActivity(in);
                        finish();
                    } else if (os.equals("3")) {
                        Intent in = new Intent(getApplicationContext(), Setup_Steps_Activity.class);
                        startActivity(in);
                        finish();
                    } else if (os.equals("4")) {
                        Intent in = new Intent(getApplicationContext(), Business_Details_Activity.class);
                        startActivity(in);
                        finish();
                    } else if (os.equals("5")) {
                        Intent in = new Intent(getApplicationContext(), Intro_Business_Activity.class);
                        startActivity(in);
                        finish();

                    } else if (os.equals("6")) {
                        Intent in = new Intent(getApplicationContext(), Operating_Hours_Activity.class);
                        startActivity(in);
                        finish();

                    } else if (os.equals("7")) {
                        Intent in = new Intent(getApplicationContext(), Upload_Photo_Activity.class);
                        startActivity(in);
                        finish();

                    } else if (os.equals("8")) {
                        Intent in = new Intent(getApplicationContext(), Setup_QR_Code_Activity.class);
                        startActivity(in);
                        finish();


                    } else if (os.equals("9")) {
                        Intent in = new Intent(getApplicationContext(), Loyalty_Points_Activity.class);
                        startActivity(in);
                        finish();

                    } else if (os.equals("10")) {
                        Intent in = new Intent(getApplicationContext(), Add_Redemption_Activity.class);
                        startActivity(in);
                        finish();

                    } else if (os.equals("11")) {
                        Intent in = new Intent(getApplicationContext(), Review_Activity.class);
                        startActivity(in);
                        finish();

                    }

                }

                @Override
                public void onCancelButtonClick() {

                    alertDialogYesNo = new AlertDialogYesNo(Welcome_Activity.this, "", "Your current application will be deleted?", "OK", "CANCEL", false, true, true, false, true) {
                        @Override
                        public void onOKButtonClick() {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    likeButton.setVisibility(View.VISIBLE);
                                    likeButton.performClick();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            likeButton.setVisibility(View.GONE);
                                        }
                                    }, 1000);
                                }
                            }, 500);

                            //clear all shared preferences data
                            SharedPreferences preferences_buss_det = getSharedPreferences(Business_Details_Activity.MyPREFERENCES_BUSSINESS_DETAILS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_bt = preferences_buss_det.edit();
                            editor_bt.clear();
                            editor_bt.commit();

                            SharedPreferences preferences_operating_hours = getSharedPreferences(Operating_Hours_Activity.MyPREFERENCES_OPERATING_HRS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_oh = preferences_operating_hours.edit();
                            editor_oh.clear();
                            editor_oh.commit();

                            SharedPreferences preferences_intro_buss = getSharedPreferences(Intro_Business_Activity.MyPREFERENCES_INTRO_BUSSINESS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_intro = preferences_intro_buss.edit();
                            editor_intro.clear();
                            editor_intro.commit();


                            SharedPreferences preferences_loyalty = getSharedPreferences(Loyalty_Points_Activity.MyPREFERENCES_LOYALTY_POINTS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_loyalty = preferences_loyalty.edit();
                            editor_loyalty.clear();
                            editor_loyalty.commit();

                            SharedPreferences preferences_preview = getSharedPreferences(Upload_Photo_Activity.MyPREFERENCES_UPLOAD_PHOTO, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_preview = preferences_preview.edit();
                            editor_preview.clear();
                            editor_preview.commit();

                    /*SharedPreferences preferences_otp = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_VALIDATE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_otp = preferences_otp.edit();
                    editor_otp.clear();
                    editor_otp.commit();

                    SharedPreferences preferences_otp_count = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_COUNT, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_otp_count = preferences_otp_count.edit();
                    editor_otp_count.clear();
                    editor_otp_count.commit();*/

                            SharedPreferences preferences_QR = getSharedPreferences(Setup_QR_Code_Activity.MyPREFERENCES_QR_STATUS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_qr = preferences_QR.edit();
                            editor_qr.clear();
                            editor_qr.commit();

                            SharedPreferences preferences_redeem = getSharedPreferences(Add_Redemption_Activity.MyPREFERENCES_REDEEM, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor_redeem = preferences_redeem.edit();
                            editor_redeem.clear();
                            editor_redeem.commit();

                            SharedPreferences preferences_add_redeem = getSharedPreferences(Redeem_Points_Activity.MyPREFERENCES_ADD_REDEEM, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_add_redeem = preferences_add_redeem.edit();
                            editor_add_redeem.clear();
                            editor_add_redeem.commit();

                        }

                        @Override
                        public void onCancelButtonClick() {


                            final String os = sharedpreferences_open_screen.getString(OPEN_SCREEN, "");
                            //don't clear spref data
                            if (os.equals("1")) {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        likeButton.setVisibility(View.VISIBLE);
                                        likeButton.performClick();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                likeButton.setVisibility(View.GONE);
                                            }
                                        }, 1000);
                                    }
                                }, 500);

                            } else if (os.equals("2")) {
                                Intent in = new Intent(getApplicationContext(), Terms_Conditions_Activity.class);
                                startActivity(in);
                                finish();
                            } else if (os.equals("3")) {
                                Intent in = new Intent(getApplicationContext(), Setup_Steps_Activity.class);
                                startActivity(in);
                                finish();
                            } else if (os.equals("4")) {
                                Intent in = new Intent(getApplicationContext(), Business_Details_Activity.class);
                                startActivity(in);
                                finish();
                            } else if (os.equals("5")) {
                                Intent in = new Intent(getApplicationContext(), Intro_Business_Activity.class);
                                startActivity(in);
                                finish();

                            } else if (os.equals("6")) {
                                Intent in = new Intent(getApplicationContext(), Operating_Hours_Activity.class);
                                startActivity(in);
                                finish();

                            } else if (os.equals("7")) {
                                Intent in = new Intent(getApplicationContext(), Upload_Photo_Activity.class);
                                startActivity(in);
                                finish();

                            } else if (os.equals("8")) {
                                Intent in = new Intent(getApplicationContext(), Setup_QR_Code_Activity.class);
                                startActivity(in);
                                finish();


                            } else if (os.equals("9")) {
                                Intent in = new Intent(getApplicationContext(), Loyalty_Points_Activity.class);
                                startActivity(in);
                                finish();

                            } else if (os.equals("10")) {
                                Intent in = new Intent(getApplicationContext(), Add_Redemption_Activity.class);
                                startActivity(in);
                                finish();

                            } else if (os.equals("11")) {
                                Intent in = new Intent(getApplicationContext(), Review_Activity.class);
                                startActivity(in);
                                finish();

                            }

                        }
                    };


                }
            };

        }


        button_next = (LinearLayout) findViewById(R.id.button_next_welcome);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(), Terms_Conditions_Activity.class);
                startActivity(in);
                finish();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(OPEN_SCREEN, "1");
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(OPEN_SCREEN, "1");
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(OPEN_SCREEN, "1");
        editor.commit();
    }

    @Override
    public void onAnimationEnd(LikeButton likeButton) {

        likeButton.setVisibility(View.GONE);
    }

    @Override
    public void liked(LikeButton likeButton) {
        //Toast.makeText(this, "Liked...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        //Toast.makeText(this, "Unliked...", Toast.LENGTH_SHORT).show();
    }
}
