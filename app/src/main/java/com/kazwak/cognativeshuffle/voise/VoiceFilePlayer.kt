package com.kazwak.cognativeshuffle.voise

import android.content.res.AssetManager
import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateOf
import com.kazwak.cognativeshuffle.Content
import java.time.LocalDateTime
import java.util.TimerTask

/**
 * 音声ファイルの操作クラス
 */
class VoiceFilePlayer {

    // 定数、フラグ
    companion object {
        const val ROOT_PATH = "voices/"     // ルートパス
        var isPlaying = mutableStateOf(false)
        var isCallinfStop = mutableStateOf(false)
        var endTime : LocalDateTime = LocalDateTime.MIN
        val PLAY_MINUTES = 60L // 実行時間（分）
    }

    private val pathArray = arrayListOf("zunda/")         // キャラクタごとのディレクトリ
    // キャラクターを追加する場合、リストの要素を追加する
//  ex)
//     private val pathArray = arrayListOf("zunda/", "metan/")

    var mediaPlayer:MediaPlayer? = null
    var playedId  = ArrayList<Int>()

    /**
     * 指定されたIDに対応するキャラクターの音声を再生する
     */
    fun playShuffleVoice(id: Int, assetManager: AssetManager){

        // 停止ボタンが押されている場合は再生を実行せずに終了する
        if (isCallinfStop.value == true){
            return
        }
        // 初回の場合は終了時刻を設定し、実行中フラグをtrueにする
        if (Companion.isPlaying.value == false){
            // 終了時刻を設定する
            Companion.endTime = LocalDateTime.now().plusMinutes(Companion.PLAY_MINUTES)
            isPlaying.value = true
        }

        // 実行ファイルを設定する
        var fileName = ""
        mediaPlayer = MediaPlayer()
        mediaPlayer?.isLooping = false

        if (LocalDateTime.now().isBefore(Companion.endTime)){
            // 終了時刻前の場合、ランダムに実行ファイルを洗濯する
            val path = pathArray.get(index = id)
            val voicesArray =  assetManager.list( Companion.ROOT_PATH + path )
            var openId = -1
            val size = voicesArray?.size
            if (size != null && size > 0){
                val range = (0..(size - 1))
                // 未実行のファイルの場合のみ実行対象とする
                while (true){
                    openId = range.random()
                    if (!this.playedId.contains(openId)){
                        // まだ実行していない音声ファイルの場合は
                        // 実行済みリストにIDを追加してループを抜ける
                        this.playedId.add(openId)
                        break
                    }
                    if (size == this.playedId.size){
                        this.playedId = ArrayList<Int>()
                    }
                }
            } else {
                return
            }
            // 取得したIDのファイルを実行する
            fileName = Companion.ROOT_PATH + path + voicesArray.get(openId)
        }else{
            // 終了時刻を過ぎている場合、終了音声を設定する
            when (id) {
                0 -> {
                    fileName = Companion.ROOT_PATH + "zunda_goodnight.wav"
                }
                // キャラクターを追加する場合、この下にファイルの設定を追加する
//  ex)
//                1 -> {
//                    fileName = Companion.ROOT_PATH + "metan_goodnight.wav"
//                }
            }
            this.stopShuffleVoice()
        }

        val voice = assetManager.openFd(fileName)
        mediaPlayer?.setDataSource(
            voice.fileDescriptor,
            voice.startOffset,
            voice.length
        )
        voice.close()
        mediaPlayer?.prepare()
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
        }
    }

    /**
     * 音声ファイルの再生を停止する
     */
    fun stopShuffleVoice(){
        // 停止処理呼び出し中フラグと実行中フラグを更新する
        Companion.isCallinfStop.value = true    // 停止処理呼び出し中
        Companion.isPlaying.value = false       // 停止中
        // 終了時刻を初期化する
        Companion.endTime = LocalDateTime.MIN
    }

    /**
     *  キャラクタボイスを実行する
     *  id - 0:ずんだもん
     *  assetManager - アセット管理オブジェクト. 音声ファイルの操作に使用する.
     */
    fun playCharactorVoice(id: Int, assetManager: AssetManager){
        mediaPlayer = MediaPlayer()
        var fileName = ""
        when (id) {
            0 -> {
                fileName = Companion.ROOT_PATH + "zunda.wav"
            }
            // キャラクターを追加する場合、この下に対応したファイルパスを追加する。
//  ex)
//            1->{
//                fileName = Companion.ROOT_PATH + "metan.wav"
//            }
        }
        val voice = assetManager.openFd(fileName)
        mediaPlayer?.setDataSource(
            voice.fileDescriptor,
            voice.startOffset,
            voice.length
        )
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
        }
    }

}