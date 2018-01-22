package origami.origamifblogin.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class OSFacebookLogin {
    private OSFacebookListener mListener;
    private CallbackManager mCallBackManager;
    private Profile profile = null;
    private OSResponse osResponse;
    private String FIELD_EMAIL = "email", FIELD_NAME = "name", FIELD_GENDER = "gender";
    private String FIELDS = "fields";
    private String PARAMETERS = "id,name,email,gender,birthday,picture.type(large),cover,age_range";

    public OSFacebookLogin(@NonNull OSFacebookListener osFacebookListener) {
        mListener = osFacebookListener;
        mCallBackManager = CallbackManager.Factory.create();

        FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                osResponse = new OSResponse();
                AccessToken accessToken = new AccessToken();
                accessToken.setApplicationId(loginResult.getAccessToken().getApplicationId());
                accessToken.setUserId(loginResult.getAccessToken().getUserId());
                accessToken.setToken(loginResult.getAccessToken().getToken());
                accessToken.setLastRefresh(loginResult.getAccessToken().getLastRefresh());
                accessToken.setExpires(loginResult.getAccessToken().getExpires());
                accessToken.setExpired(loginResult.getAccessToken().isExpired());
                osResponse.setAccessToken(accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            osResponse.setEmail(object.getString(FIELD_EMAIL));
                            osResponse.setName(object.getString(FIELD_NAME));
                            osResponse.setGender(object.getString(FIELD_GENDER));

                            profile = Profile.getCurrentProfile();
                            if (profile != null) {
                                osResponse.setId(profile.getId());
                                osResponse.setFirstName(profile.getFirstName());
                                osResponse.setLastName(profile.getLastName());
                                osResponse.setMiddleName(profile.getMiddleName());
                                osResponse.setName(profile.getName());
                                osResponse.setLinkUri(profile.getLinkUri());
                                osResponse.setProfileImage(profile.getProfilePictureUri(250, 250).toString());
                            }

                            mListener.onFacebookSuccess(osResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mListener.onFacebookFailed(e.getMessage());
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString(FIELDS, PARAMETERS);
                request.setParameters(parameters);
                request.executeAsync();

                profile = Profile.getCurrentProfile();
            }

            @Override
            public void onCancel() {
                mListener.onFacebookFailed("User cancelled operation");
            }

            @Override
            public void onError(FacebookException e) {
                mListener.onFacebookFailed(e.getMessage());
            }
        };

        LoginManager.getInstance().registerCallback(mCallBackManager, mCallBack);
    }


    @NonNull
    @CheckResult
    public CallbackManager getCallbackManager() {
        return mCallBackManager;
    }

    public void performSignIn(Activity activity) {
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
    }

    public void performSignIn(Fragment fragment) {
        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
    }

    public void performSignOut() {
        LoginManager.getInstance().logOut();
        mListener.onFacebookSignOut();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }
}
