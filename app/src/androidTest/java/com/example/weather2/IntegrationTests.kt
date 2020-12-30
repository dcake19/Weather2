package com.example.weather2

import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.example.api_location.key.GEOCODING_API_KEY
import com.example.data_storage_location.db.DatabaseName
import com.example.data_storage_location.db.LocationDao
import com.example.data_storage_location.db.LocationDatabase
import com.example.open_weather.RetrofitClient
import com.example.open_weather.key.OPEN_WEATHER_API_KEY
import com.example.ui_weather_2.idling_resource.EspressoIdlingResource
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.core.AllOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
@LargeTest
class IntegrationTests {

   // @get:Rule
    //val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java,
        true,     // initialTouchMode
        false)   // launchActivity. False to customize the intent

    private var mockWebServer = MockWebServer()
    private lateinit var db: LocationDatabase
    private lateinit var dao: LocationDao

    @Before
    fun init(){
        EspressoIdlingResource.createIdlingResource()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)

        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        RetrofitClient.retrofit = retrofit

        val retrofitLocation = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        com.example.api_location.RetrofitClient.retrofit = retrofitLocation

        val context = getInstrumentation().targetContext
        db = Room.databaseBuilder(context, LocationDatabase::class.java, DatabaseName.LOCATION)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
        dao = db.locationDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        db.locationDao().delete(db.locationDao().getLocations().map { it.placeId })
        db.close()
        mockWebServer.shutdown()
    }

    @Test
    fun test1(){
        val location1 = TestUtil.getLocation1()
        val location2 = TestUtil.getLocation2()

        dao.insert(location1)
        dao.insert(location2)

        val response1 = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtil.getJsonPath("api/weather_1.json"))

        val response2 = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtil.getJsonPath("api/weather_2.json"))

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/data/2.5/onecall?units=metric&appid=$OPEN_WEATHER_API_KEY&lat=${location1.latitude}&lon=${location1.longitude}") {
                    response1
                } else if(request.method == "GET" && request.path == "/data/2.5/onecall?units=metric&appid=$OPEN_WEATHER_API_KEY&lat=${location2.latitude}&lon=${location2.longitude}"){
                    response2
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher

        activityRule.launchActivity(null)

        // below is a small sample of what should be tested
        checkId(R.id.text_temp,"0°C")
        onView(
            AllOf.allOf(
                withId(R.id.layout_weather_forecast),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.swipeLeft())

        onView(withId(R.id.button_locations)).perform(click())

        onView(withId(R.id.list_locations))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(
            RecyclerViewMatcher.withRecyclerView(R.id.list_locations)
            .atPositionOnView(0, R.id.location_text_place_name))
            .check(ViewAssertions.matches(ViewMatchers.withText("Eaton Ford")))

        onView(withId(R.id.button_back)).perform(click())
    }

    @Test
    fun test2(){
        val deviceLat = "37.4219983"
        val deviceLong = "-122.084"

        val locationLat = "52.2276314"
        val locationLong = "-0.2832154"

        val response1 = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtil.getJsonPath("api/location_1.json"))

        val response2 = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestUtil.getJsonPath("api/weather_1.json"))

        val dispatcher2 = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.method == "GET" && request.path == "/geocode/json?latlng=$deviceLat%2C$deviceLong&key=$GEOCODING_API_KEY") {
                    response1
                }else if (request.method == "GET" && request.path == "/data/2.5/onecall?units=metric&appid=$OPEN_WEATHER_API_KEY&lat=$locationLat&lon=$locationLong") {
                    response2
                }else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher2

        activityRule.launchActivity(null)

        // below is a small sample of what should be tested
        checkId(R.id.text_temp,"0°C")
        onView(
            AllOf.allOf(
                withId(R.id.layout_weather_forecast),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.swipeLeft())

        onView(withId(R.id.button_locations)).perform(click())

        onView(withId(R.id.list_locations))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(
            RecyclerViewMatcher.withRecyclerView(R.id.list_locations)
                .atPositionOnView(0, R.id.location_text_place_name))
            .check(ViewAssertions.matches(ViewMatchers.withText("Eaton Ford")))

        onView(withId(R.id.button_back)).perform(click())
    }

    private fun checkId(id: Int,display: String){
        val viewInteraction = onView(
            AllOf.allOf(
                withId(id),
                ViewMatchers.isDescendantOfA(
                    AllOf.allOf(
                        withId(R.id.layout_weather_forecast),
                        ViewMatchers.isDisplayed()
                    )
                )
            )
        )

        viewInteraction.perform(CustomScrollActions.nestedScrollTo())
        viewInteraction.check(ViewAssertions.matches(ViewMatchers.withText(display)))
    }
}