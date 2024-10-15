package com.vodafone.core.designSystem.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize

@Composable
fun WAnimationContent(
    modifier: Modifier = Modifier,
    state: Boolean,
    topBar: @Composable () -> Unit = {},
    loadingContent: @Composable () -> Unit = {},
    isError: Boolean = false,
    onClickTryAgain: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable () -> Unit,
) {
    AnimatedContent(
        targetState = state,
        transitionSpec = {
            fadeIn(animationSpec = tween(150, 150)) togetherWith
                    fadeOut(animationSpec = tween(150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                IntSize(targetSize.width, initialSize.height) at 150
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                IntSize(initialSize.width, targetSize.height) at 150
                                durationMillis = 300
                            }
                        }
                    }
        }, label = "Content"
    ) { isLoading ->
        Column(
            modifier.fillMaxSize().background(backgroundColor)
        ) {
            if (isError) {
                WErrorLoading(state = isError, onClickTryAgain = onClickTryAgain)
            } else {
                if (isLoading) {
                    loadingContent()
                } else {
                    topBar()
                    content()
                }
            }
        }
    }
}