package origami.origamilogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import origami.origamifblogin.facebook.OSFacebookLogin;
import origami.origamifblogin.facebook.OSFacebookListener;
import origami.origamifblogin.facebook.OSResponse;

public class MainActivity extends AppCompatActivity implements OSFacebookListener {
    private Button fbSignIn, fbSignOut;
    private TextView tvText;
    private OSFacebookLogin osFacebookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbSignIn = (Button) findViewById(R.id.fbLogin);
        fbSignOut = (Button) findViewById(R.id.fbLogout);
        tvText = (TextView) findViewById(R.id.tvText);

        fbSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        fbSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogout();
            }
        });

        osFacebookLogin = new OSFacebookLogin(this);
    }

    private void performLogin() {
        tvText.setText("");
        osFacebookLogin.performSignIn(this);
    }

    private void performLogout() {
        tvText.setText("");
        osFacebookLogin.performSignOut();
    }

    @Override
    public void onFacebookFailed(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        tvText.setText("");
    }

    @Override
    public void onFacebookSuccess(OSResponse osResponse) {
        Toast.makeText(getApplicationContext(), osResponse.getName(), Toast.LENGTH_SHORT).show();
        tvText.setText(osResponse.getAccessToken().getToken());
    }

    @Override
    public void onFacebookSignOut() {
        Toast.makeText(getApplicationContext(), "Sign out successfully", Toast.LENGTH_SHORT).show();
        tvText.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        osFacebookLogin.onActivityResult(requestCode, resultCode, data);
    }
}
