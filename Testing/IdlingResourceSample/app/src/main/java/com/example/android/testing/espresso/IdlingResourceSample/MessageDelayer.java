/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.espresso.IdlingResourceSample;

import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import com.example.android.testing.espresso.IdlingResourceSample.IdlingResource.SimpleIdlingResource;

// This class is not part of the Android framework. It was created for this project.
// It takes a String and returns it after a delay via a callback.

/**
 * <p>
 * This executes a long-running operation on a different thread that results in problems with
 * Espresso if an IdlingResource {@link IdlingResource} is not implemented and registered.
 */
class MessageDelayer {

    private static final int DELAY_MILLIS = 3000;

    interface DelayerCallback {
        void onDone(String text);
    }

    /**
     * Takes a String and returns it after {@link #DELAY_MILLIS} via a {@link DelayerCallback}.
     * @param message the String that will be returned via the callback
     * @param callback used to notify the caller asynchronously
     */

// The processMessage() method takes a String (the one that the user typed into the EditText field),
// and returns it after the delay time we setup in DELAY_MILLIS.
// The String is returned via the callback in the onDone method.

    // processMessage() has 3 parameters - the message, the activity to return back to in the callback, and the IdlingResource.
    static void processMessage(final String message, final DelayerCallback callback,
            @Nullable final SimpleIdlingResource idlingResource) {
        // The IdlingResource is null in production.
        // The if statement checks whether or not IdlingResource is null. If it isn’t we can go ahead and set idle to false.
        // Idle means no UI events in the current message queue, no more tasks in the default AsyncTask thread pool.

        if (idlingResource != null) {
            // If idle is false there are tasks or events that are happening and any testing should be on halt until these processes finish.
            idlingResource.setIdleState(false);
        }

        // With idle set as false we create a handler and run the method postDelayed().
        // Delay the execution, return message via callback.
        Handler handler = new Handler();
        // The first parameter of postDelayed() is the Runnable that will be run once the delay time is up.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // The first action of the Runnable is to check the callback we received in processMessage()
                // (i.e. which activity we should return to after the delay).
                if (callback != null) {
                    // We return to that activity’s onDone() method and return the message variable.
                    callback.onDone(message);
                    // Then we check that idlingResource is not null.
                    if (idlingResource != null) {
                        // If it's not null we set its state to true.
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}
