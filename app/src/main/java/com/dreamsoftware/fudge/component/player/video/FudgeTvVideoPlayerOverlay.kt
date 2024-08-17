package com.dreamsoftware.fudge.component.player.video

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.theme.FudgeTvTheme

@Composable
fun FudgeTvVideoPlayerOverlay(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    state: FudgeTvVideoPlayerState = rememberVideoPlayerState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onBuildControls: @Composable () -> Unit = {},
    onBuildCenterButton: @Composable () -> Unit = {},
) {
    with(state) {
        LaunchedEffect(controlsVisibility) {
            if (controlsVisibility) {
                focusRequester.requestFocus()
            }
        }

        LaunchedEffect(isPlaying) {
            if (isPlaying) {
                showControls()
            } else {
                showControls(seconds = Int.MAX_VALUE)
            }
        }

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = controlsVisibility,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                OverlayBackground(modifier = Modifier.fillMaxSize())
            }

            AnimatedVisibility(
                visible = controlsVisibility,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                onBuildCenterButton()
            }
        }

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AnimatedVisibility(
                visible = controlsVisibility,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .padding(bottom = 32.dp, top = 8.dp, start = 56.dp, end = 56.dp)
                ) {
                    onBuildControls()
                }
            }
        }
    }
}

@Composable
private fun OverlayBackground(modifier: Modifier = Modifier) {
    Box(
        modifier.background(
            Brush.verticalGradient(
                listOf(
                    Color.Black.copy(alpha = 0.1f),
                    Color.Black.copy(alpha = 0.8f),
                )
            )
        )
    )
}


@Preview(device = Devices.TV_1080p)
@Composable
fun PreviewVideoPlayerOverlay() {
    FudgeTvTheme {
        Box(Modifier.fillMaxSize()) {
            FudgeTvVideoPlayerOverlay(
                isPlaying = true,
                modifier = Modifier.align(Alignment.BottomCenter),
                onBuildControls = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.Blue)
                    )
                },
                onBuildCenterButton = {
                    Box(
                        Modifier
                            .size(88.dp)
                            .background(Color.Green)
                    )
                }
            )
        }
    }
}