# Facebook Login

Origami Studios presents a fully integrated Facebook library for android platform

## Dependency

## Step 1

Add the JitPack repository to your build file, 
### Gradle
Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
## Step 2

Add the dependency

```
dependencies {
    implementation 'com.github.mshehzad1:FacebookLogin:1.0'
}
```

## Step 3

### Facebook App Id
Create your facebook app on facebook developer console and get facebook app id.

### AndroidManifest

Permissions: 
Add internet permissions

```
<uses-permission android:name="android.permission.INTERNET" />
```
Meta Data: 
Add following metadata in app manifest file

```
<meta-data
 android:name="com.facebook.sdk.ApplicationId"
 android:value="@string/facebook_app_id" />
```
Facebook App id: Add your facebook app id in strings file.

```
<resources>
    <string name="facebook_app_id">your facebook app id goes here .... </string>
</resources>

```
## Step 4

### Getting started ( First Method )
Add following code in your Login activity class. Or copy the code and paste in your Login class

```
public class Login extends AppCompatActivity {

    private OSFacebookLogin osFacebookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fbSignIn = (Button) findViewById(R.id.fbLogin);
        Button fbSignOut = (Button) findViewById(R.id.fbLogout);

        osFacebookLogin = new OSFacebookLogin(new OSFacebookListener() {
            @Override
            public void onFacebookSuccess(OSResponse osResponse) {
                Toast.makeText(getApplicationContext(), osResponse.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFacebookFailed(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFacebookSignOut() {
                Toast.makeText(getApplicationContext(), "Sign out successfully", Toast.LENGTH_SHORT).show();
            }
        });


        fbSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                osFacebookLogin.performSignIn((Activity) getApplicationContext());
            }
        });

        fbSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                osFacebookLogin.performSignOut();
            }
        });
        
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        osFacebookLogin.onActivityResult(requestCode, resultCode, data);
    }
}

```

### Getting started ( Second Method )
Create a private OSFacebookLogin variable

```
 private OSFacebookLogin osFacebookLogin;
```
Initialize the osFacebookLogin in activity onCreate method

```
 osFacebookLogin = new OSFacebookLogin(this);
```
Implements OSFacebookListener in your Login activity, it will add following methods in your Login activity class

```
public class Login extends AppCompatActivity implements OSFacebookListener{
@Override
    public void onFacebookSuccess(OSResponse osResponse) {
        Toast.makeText(getApplicationContext(), osResponse.getName(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onFacebookFailed(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onFacebookSignOut() {
        Toast.makeText(getApplicationContext(), "Sign out successfully", Toast.LENGTH_SHORT).show();
    }
}
```

