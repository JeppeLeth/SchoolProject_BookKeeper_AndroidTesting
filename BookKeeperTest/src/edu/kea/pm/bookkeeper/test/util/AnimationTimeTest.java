package edu.kea.pm.bookkeeper.test.util;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import edu.kea.pm.bookkeeper.activity.MainActivity;

public class AnimationTimeTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	public AnimationTimeTest()
	{
		super(MainActivity.class);
	}

	public AnimationTimeTest(Class<MainActivity> activityClass)
	{
		super(activityClass);
	}

   
    public void testAnimation_frameworkTimes() {
    	Activity activity = getActivity();
    	int longAnim = activity.getResources().getInteger(android.R.integer.config_longAnimTime);
    	int mediumAnim = activity.getResources().getInteger(android.R.integer.config_mediumAnimTime);
    	int shortAnim = activity.getResources().getInteger(android.R.integer.config_shortAnimTime);
        Log.d("AnimationTimeTest", "config_longAnimTime = "+longAnim+"ms");
        Log.d("AnimationTimeTest", "config_mediumAnimTime = "+mediumAnim+"ms");
        Log.d("AnimationTimeTest", "config_shortAnimTime = "+shortAnim+"ms");
        assertTrue("config_longAnimTime was longer than 500ms (actual"+longAnim+")", longAnim <= 500);
        assertTrue("config_mediumAnimTime was longer than 400ms (actual"+mediumAnim+")", mediumAnim <= 400);
        assertTrue("config_shortAnimTime was longer than 200ms (actual"+shortAnim+")", shortAnim <= 200);
    }
    
    
}
