# NavitiaSDKUX_android
[ ![Download](https://api.bintray.com/packages/navitiasdkteam/NavitiaSDK/navitia-sdk-ux/images/download.svg) ](https://bintray.com/navitiasdkteam/NavitiaSDK/navitia-sdk-ux/_latestVersion)
An Android module you can use in your app to offer cool transport stuff to your users.

## Installation
Add the following dependency to your `build.gradle` file:
```ruby
dependencies {
    implementation 'org.kisio.sdk:navitia-sdk-ux:1.0.0'
}
```

For the use of cartography, add your Google Maps API Key to your `AndroidManifest.xml` as well
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY"/>
```

## Usage
```java
// Insert your Navitia token
Configuration.token = "TOKEN";

setContentView(R.layout.activity_main);

final Button button = findViewById(R.id.sdk_open);
button.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        final Intent intent = new Intent(v.getContext(), JourneySolutionsActivity.class);

        final JourneySolutionsInParameters parameters = new JourneySolutionsInParameters("2.3665844;48.8465337", "2.2979169;48.8848719");
        parameters.originLabel = "Chez moi";
        parameters.destinationLabel = "Au travail";
        parameters.datetime = DateTime.now().plusDays(1);
        parameters.datetimeRepresents = "departure";
        parameters.forbiddenUris = Arrays.asList("physical_mode:Bus");
        parameters.firstSectionModes = Arrays.asList("bss");
        parameters.lastSectionModes = Arrays.asList("car");
        parameters.count = 5;

        intent.putExtra(JourneySolutionsActivity.IntentParameters.parameters.name(), parameters);

        startActivity(intent);
    }
});
```

## Demo app
To run the example project, clone the repo, and run the `sample` module.
