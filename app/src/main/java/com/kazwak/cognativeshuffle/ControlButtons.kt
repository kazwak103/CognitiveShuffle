package com.kazwak.cognativeshuffle

import android.content.res.AssetManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kazwak.cognativeshuffle.voise.VoiceFilePlayer
import java.util.Timer
import java.util.TimerTask

@Composable
fun ControlButtons(voiceId: Int, asset: AssetManager, vfp: VoiceFilePlayer)
{
    // タイマー
    var timer:Timer = Timer()
    class task : TimerTask(){
        override fun run() {
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
            timer.schedule(timerTask,delay,period)
        }
    }) {
        if(VoiceFilePlayer.isPlaying.value.equals(true)) {
            Text("停止")
        }else{
            Text("開始")
        }
    }
}

