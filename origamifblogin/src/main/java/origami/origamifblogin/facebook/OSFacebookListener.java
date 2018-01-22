package origami.origamifblogin.facebook;

public interface OSFacebookListener {

    void onFacebookSuccess(OSResponse osResponse);

    void onFacebookFailed(String errorMessage);

    void onFacebookSignOut();
}
