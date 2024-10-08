package com.dreamsoftware.fudge.component.profiles

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.fudge.component.FudgeTvFocusRequester
import com.dreamsoftware.fudge.component.FudgeTvScalableAvatar
import com.dreamsoftware.fudge.component.FudgeTvText
import com.dreamsoftware.fudge.component.FudgeTvTextTypeEnum
import com.dreamsoftware.fudge.utils.EMPTY

data class ProfileSelectorVO(
    val uuid: String,
    val alias: String,
    @DrawableRes val avatarIconRes: Int
)

@Composable
fun FudgeTvProfileSelector(
    profiles: List<ProfileSelectorVO>,
    editMode: Boolean = false,
    onProfileSelected: (uuid: String) -> Unit
) {
    var selectedAvatar by remember { mutableStateOf(String.EMPTY) }
    FudgeTvFocusRequester { requester ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(profiles.size) {
                    val profile = profiles[it]
                    FudgeTvScalableAvatar(
                        avatarRes = profile.avatarIconRes,
                        editMode = editMode,
                        modifier = Modifier
                            .then(if (it == 0) Modifier.focusRequester(requester) else Modifier)
                            .onFocusChanged {
                                selectedAvatar = profile.alias
                            },
                        onPressed = {
                            onProfileSelected(profile.uuid)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.size(30.dp))
            if (selectedAvatar.isNotEmpty()) {
                ProfileAvatarName(name = selectedAvatar)
            }
        }
    }
}

@Composable
private fun ProfileAvatarName(name: String) {
    AnimatedContent(
        targetState = name,
        transitionSpec = {
            (slideInVertically { height -> height } + fadeIn() togetherWith
                    slideOutVertically { height -> -height } + fadeOut())
                .using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = false),
                )
        },
        label = String.EMPTY,
    ) { text ->
        FudgeTvText(
            titleText = text,
            type = FudgeTvTextTypeEnum.HEADLINE_LARGE,
            textBold = true,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
        )
    }
}