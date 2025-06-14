package top.yogiczy.mytv.tv.ui.screen.settings.categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.material3.Switch
import androidx.tv.material3.Text
import top.yogiczy.mytv.core.util.utils.headersValid
import top.yogiczy.mytv.core.util.utils.humanizeMs
import top.yogiczy.mytv.core.util.utils.humanizeBufferNum
import top.yogiczy.mytv.tv.ui.screen.settings.SettingsViewModel
import top.yogiczy.mytv.tv.ui.screen.settings.components.SettingsCategoryScreen
import top.yogiczy.mytv.tv.ui.screen.settings.components.SettingsListItem
import top.yogiczy.mytv.tv.ui.screen.settings.settingsVM
import top.yogiczy.mytv.tv.ui.theme.MyTvTheme

@Composable
fun SettingsVideoPlayerScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = settingsVM,
    toVideoPlayerCoreScreen: () -> Unit = {},
    toWebviewCoreScreen: () -> Unit = {},
    toVideoPlayerRenderModeScreen: () -> Unit = {},
    toVideoPlayerDisplayModeScreen: () -> Unit = {},
    toVideoPlayerLoadTimeoutScreen: () -> Unit = {},
    toVideoPlayerBufferTimeScreen: () -> Unit = {},
    onBackPressed: () -> Unit = {},
) {
    SettingsCategoryScreen(
        modifier = modifier,
        header = { Text("设置 / 播放器") },
        onBackPressed = onBackPressed,
    ) { firstItemFocusRequester ->
        item {
            SettingsListItem(
                modifier = Modifier.focusRequester(firstItemFocusRequester),
                headlineContent = "视频播放器内核",
                trailingContent = settingsViewModel.videoPlayerCore.label,
                onSelect = toVideoPlayerCoreScreen,
                link = true,
            )
        }

        item {
            SettingsListItem(
                headlineContent = "渲染方式",
                trailingContent = settingsViewModel.videoPlayerRenderMode.label,
                onSelect = toVideoPlayerRenderModeScreen,
                link = true,
            )
        }

        item {
            SettingsListItem(
                headlineContent = "强制软解",
                supportingContent = "对于Media3，使用设备和扩展软解码器\n对于IJK，将禁用MediaCodec解码（使用ffmpeg）",
                trailingContent = {
                    Switch(settingsViewModel.videoPlayerForceSoftDecode, null)
                },
                onSelect = {
                    settingsViewModel.videoPlayerForceSoftDecode =
                        !settingsViewModel.videoPlayerForceSoftDecode
                },
            )
        }

        item {
            SettingsListItem(
                headlineContent = "停止上一媒体项",
                trailingContent = {
                    Switch(settingsViewModel.videoPlayerStopPreviousMediaItem, null)
                },
                onSelect = {
                    settingsViewModel.videoPlayerStopPreviousMediaItem =
                        !settingsViewModel.videoPlayerStopPreviousMediaItem
                },
            )
        }

        item {
            SettingsListItem(
                headlineContent = "跳过多帧渲染",
                trailingContent = {
                    Switch(settingsViewModel.videoPlayerSkipMultipleFramesOnSameVSync, null)
                },
                onSelect = {
                    settingsViewModel.videoPlayerSkipMultipleFramesOnSameVSync =
                        !settingsViewModel.videoPlayerSkipMultipleFramesOnSameVSync
                },
            )
        }

        item{
            SettingsListItem(
                headlineContent = "支持 Media TS 高复杂度解析",
                supportingContent = "支持在某些设备上使用 Media3 播放缺少 AUD 或 IDR 关键帧的 MPEG-TS 文件，启用该选项可能导致意外错误",
                trailingContent = {
                    Switch(settingsViewModel.videoPlayerSupportTSHighProfile, null)
                },
                onSelect = {
                    settingsViewModel.videoPlayerSupportTSHighProfile =
                        !settingsViewModel.videoPlayerSupportTSHighProfile
                },
            )
        }

        item {
            SettingsListItem(
                headlineContent = "全局显示模式",
                trailingContent = settingsViewModel.videoPlayerDisplayMode.label,
                onSelect = toVideoPlayerDisplayModeScreen,
                link = true,
            )
        }

        item {
            SettingsListItem(
                headlineContent = "WebView内核",
                trailingContent = settingsViewModel.webViewCore.label,
                onSelect = toWebviewCoreScreen,
                link = true,
            )
        }

        item {
            SettingsListItem(
                headlineContent = "加载超时",
                supportingContent = "影响超时换源、断线重连",
                trailingContent = settingsViewModel.videoPlayerLoadTimeout.humanizeMs(),
                onSelect = toVideoPlayerLoadTimeoutScreen,
                link = true,
            )
        }

        item {
            SettingsListItem(
                headlineContent = "播放缓冲",
                supportingContent = "对于Media3，为播放前的最小缓存加载时间（秒）\n对于Ijk，为播放前的最小缓存加载帧（f）",
                trailingContent = settingsViewModel.videoPlayerBufferTime.humanizeBufferNum(),
                onSelect = toVideoPlayerBufferTimeScreen,
                link = true,
            )
        }

        item {
            SettingsListItem(
                headlineContent = "全局UA",
                trailingContent = settingsViewModel.videoPlayerUserAgent,
                remoteConfig = true,
            )
        }

        item {
            val isValid = settingsViewModel.videoPlayerHeaders.headersValid()

            SettingsListItem(
                headlineContent = "自定义headers",
                supportingContent = settingsViewModel.videoPlayerHeaders,
                remoteConfig = true,
                trailingIcon = if (!isValid) Icons.Default.ErrorOutline else null,
            )
        }

        // item {
        //     SettingsListItem(
        //         headlineContent = "音量平衡",
        //         supportingContent = "启用后，将统一均衡输出播放音量，解决订阅源音量大小不一致的情况；仅支持Media3播放器",
        //         trailingContent = {
        //             Switch(settingsViewModel.videoPlayerVolumeNormalization, null)
        //         },
        //         onSelect = {
        //             settingsViewModel.videoPlayerVolumeNormalization =
        //                 !settingsViewModel.videoPlayerVolumeNormalization
        //         },
        //     )
        // }
    }
}

@Preview(device = "id:Android TV (720p)")
@Composable
private fun SettingsVideoPlayerScreenPreview() {
    MyTvTheme {
        SettingsVideoPlayerScreen(
            settingsViewModel = SettingsViewModel().apply {
                videoPlayerUserAgent =
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36"
                videoPlayerHeaders = "Accept: "
            }
        )
    }
}