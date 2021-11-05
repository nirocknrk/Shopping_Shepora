package com.nrk.sephora.assignmentshepora.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.nrk.sephora.assignmentshepora.ui.theme.AssignmentSheporaTheme
import com.nrk.sephora.assignmentshepora.view.ui.ProductListingApp
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AssignmentSheporaTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ProductListingApp()
                }
            }
        }
    }

}
