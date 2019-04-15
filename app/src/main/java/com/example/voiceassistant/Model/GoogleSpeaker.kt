package com.example.voiceassistant.Model

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.voiceassistant.Retrofit.RetrofitClient
import java.util.*

class GoogleSpeaker(context: Context) : TextToSpeech.OnInitListener{

    private val textToSpeech: TextToSpeech = TextToSpeech(context, this)

    override fun onInit(status: Int) {

        if(status == TextToSpeech.SUCCESS){
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(RetrofitClient.TAG,"The Language specified is not supported!")
            }
        }else{
            Log.d(RetrofitClient.TAG, "TTS init failed")
        }
    }

    fun speak(text : String){
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    fun destroy(){
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}