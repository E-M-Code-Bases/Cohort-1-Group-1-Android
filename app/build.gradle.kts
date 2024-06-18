plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.starstream"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.starstream"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // Add local .jar file
    implementation(files("libs/YouTubeAndroidPlayerApi.jar"))

    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    //Lifecycle
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")

    //Room
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    //DataStore
    implementation ("androidx.datastore:datastore-preferences:1.1.1")

    //Retrofit + Gson
    implementation ("com.squareup.retrofit2:retrofit:2.10.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.10.0")
    implementation ("com.google.code.gson:gson:2.10.1")

    //Glide + Glide Transformations
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    kapt ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("jp.wasabeef:glide-transformations:4.3.0")

    //Palette
    implementation ("androidx.palette:palette-ktx:1.0.0")

    //Custom Views
    implementation ("com.github.chrisbanes:PhotoView:2.3.0")
    implementation ("com.github.cachapa:ExpandableLayout:2.9.2")


    /* Koin - dependency injection */
    implementation ("io.insert-koin:koin-core:3.5.3")
    implementation ("io.insert-koin:koin-android:3.5.3")
    implementation ("io.insert-koin:koin-android-compat:3.3.0")

}

