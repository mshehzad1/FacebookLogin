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

