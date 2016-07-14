package ln.firebaseanalytics.src;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseAnalyticsActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {


    // [START define_variables]
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;
    private Button btnCrash, btnSignIn, btnSendInvite, btnShareDeepLink;
    private String TAG = "FirebaseAnalytics:";

    GoogleApiClient mGoogleApiClient;
    // Firebase instance variables
    FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private static final int REQUEST_INVITE = 1;
    private static final String DEEP_LINK_URL = "https://n789s.app.goo.gl/mxGI";
    // [END define_variables]

    // [START on_create]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_analytics);

        // Call all the methods here
        validateAppCode();
        getDeviceToken();
        initializeView();
        loadAds();
        setListeners();
//        googlePlusLogin();
    }

    /**
     * Method will generate device Token
     */
    private void getDeviceToken() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("firebase", "Refreshed token: " + refreshedToken);
    }

    /**
     * Method will initialize view
     */

    private void initializeView() {

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // Initialize variables
        mAdView = (AdView) findViewById(R.id.adView);
        btnCrash = (Button) findViewById(R.id.btnCrash);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSendInvite = (Button) findViewById(R.id.btnSendInvite);
        btnShareDeepLink = (Button) findViewById(R.id.btnShareDeepLink);
    }

    /**
     * Method will set listeners
     */

    private void setListeners() {
        btnCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                causeCrash();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFirebaseUser == null) {
                    // Not signed in, launch the Sign In activity
                    startActivity(new Intent(FirebaseAnalyticsActivity.this, SignInActivity.class));
                } else {
                    mUsername = mFirebaseUser.getDisplayName();
                    Log.d("USERNAME-", mUsername);
                    if (mFirebaseUser.getPhotoUrl() != null) {
                        mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                        Log.d("USER PHOTO-", mPhotoUrl);
                    }
                }
            }
        });

        btnSendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInvitation();
            }
        });

        btnShareDeepLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDeepLink(DEEP_LINK_URL);
            }
        });
    }

    /**
     * Method will use for google plus login
     */
//    private void googlePlusLogin() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API)
//                .addApi(AppInvite.API)
//                .build();
//    }

    /**
     * Method will use to send invitation to friends
     */
    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }


    /**
     * Method will load Banner Ads
     */
    private void loadAds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8000677498673833/2693498101");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    /**
     * onActivityResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "sent");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE,
                        payload);
                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode,
                        data);
                Log.d(TAG, "Invitations sent: " + ids.length);
            } else {
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "not sent");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE,
                        payload);
                // Sending failed or it was canceled, show failure message to
                // the user
                Log.d(TAG, "Failed to send invitation.");
            }
        }

    }

    /**
     * Method will use to crash application
     */
    private void causeCrash() {
        throw new NullPointerException("Fake null pointer exception!!");
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    /**
     * Method will use to share DeepLink
     */
    private void shareDeepLink(String deepLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Firebase Deep Link");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);

        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    /**
     * Method will use to check app is valid
     */
    private void validateAppCode() {
        String appCode = getString(R.string.app_name);
        if (appCode.contains("YOUR_APP_CODE")) {
            new AlertDialog.Builder(this)
                    .setTitle("Invalid Configuration")
                    .setMessage("Please set your app code in res/values/strings.xml")
                    .setPositiveButton(android.R.string.ok, null)
                    .create().show();
        }
    }
}
