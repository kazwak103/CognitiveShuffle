package com.kazwak.cognitiveshuffle

import android.app.Activity
import android.content.res.AssetManager
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kazwak.cognitiveshuffle.voice.VoiceFilePlayer
import java.util.Timer
import java.util.TimerTask

@Composable
fun ControlButtons(voiceId: Int, activity: Activity, vfp: VoiceFilePlayer)
{
    // タイマー
    val timer:Timer = Timer()
    class task : TimerTask(){
        override fun run() {
            val asset = activity.assets
            vfp.playShuffleVoice(voiceId, asset)
            if (VoiceFilePlayer.isCallinfStop.value.equals(true)){
                this.cancel()
                VoiceFilePlayer.isCallinfStop.value = false
                VoiceFilePlayer.isPlaying.value = false
            }
        }
    }
    val timerTask = task()
    val delay = 0L
    val period = 10000L

    Button (enabled = VoiceFilePlayer.isCallinfStop.value.not(),
        onClick = {
            if(VoiceFilePlayer.isPlaying.value.equals(true)){
                vfp.stopShuffleVoice()
            }else{
                // 画面の輝度を下げる
                val windowAttribute = activity.window.attributes
                windowAttribute.screenBrightness = 0.1F
                activity.window.attributes = windowAttribute
                // スケジュール実行
                timer.schedule(timerTask,delay,period)
            }
        }
    ) {
        if(VoiceFilePlayer.isPlaying.value.equals(true)) {
            Text("停止")
        }else{
            Text("開始")
        }
    }
}

