// Copyright 2016 Google Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//      http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.android.basicandroidaccessibility;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

/**
 *
 */
public class ContentLabelingActivity extends AppCompatActivity {

    private boolean mPlaying = false;
    private ImageButton mPlayPauseToggleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_labeling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPlayPauseToggleImageView = (ImageButton) findViewById(R.id.play_pause_toggle_view);
        if (mPlayPauseToggleImageView != null) {
            updateImageButton();
            mPlayPauseToggleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlaying = !mPlaying;
                    updateImageButton();
                }
            });
        }
    }

    private void updateImageButton() {
        if (mPlaying) {
            mPlayPauseToggleImageView.setImageResource(R.drawable.ic_pause);
            // Adding a contentDescription to describe the current state of the view
            mPlayPauseToggleImageView.setContentDescription(getString(R.string.pause));
        } else {
            mPlayPauseToggleImageView.setImageResource(R.drawable.ic_play_arrow);
            // Adding a contentDescription to describe the current state of the view
            mPlayPauseToggleImageView.setContentDescription(getString(R.string.play));

        }
    }
}
