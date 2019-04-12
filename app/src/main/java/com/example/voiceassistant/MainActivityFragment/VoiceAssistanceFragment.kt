package com.example.voiceassistant.MainActivityFragment

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.voiceassistant.R
import com.example.voiceassistant.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.voice_assistance_fragment.*
import java.util.*

class VoiceAssistanceFragment : Fragment(), RecognitionListener, TextToSpeech.OnInitListener{

    lateinit var textToSpeech: TextToSpeech

    companion object {
        fun newInstance(): VoiceAssistanceFragment = VoiceAssistanceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.voice_assistance_fragment, container, false)

        val voiceButton = view.findViewById<ImageButton>(R.id.voiceButton)

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity)
        speechRecognizer.setRecognitionListener(this)

        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en_US")
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US")
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)

        textToSpeech = TextToSpeech(activity, this)

        voiceButton.setOnClickListener {
            clearChat()
            speechRecognizer.startListening(recognizerIntent)
        }

        return view
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }
        }else{
            Log.d(RetrofitClient.TAG, "TTS init failed")
        }
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Log.d(RetrofitClient.TAG, "onReadyForSpeech")
    }

    override fun onRmsChanged(rmsdB: Float) {
    }

    override fun onBufferReceived(buffer: ByteArray?) {
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val partialText = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        voiceText.text = partialText?.get(0).toString().capitalize()
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.d(RetrofitClient.TAG, "onEvent")
    }

    override fun onBeginningOfSpeech() {
        Log.d(RetrofitClient.TAG, "onBeginningOfSpeech")
    }

    override fun onEndOfSpeech() {
        Log.d(RetrofitClient.TAG, "onEndOfSpeech")
        val text = "Celia stop farting please"
        assistantText.text = text
        speak(text)
    }

    override fun onResults(results: Bundle?) {
        Log.d(RetrofitClient.TAG, "onResults")
    }

    override fun onError(error: Int) {
        Log.d(RetrofitClient.TAG, "onError")
        when(error){
            SpeechRecognizer.ERROR_AUDIO -> Log.d(RetrofitClient.TAG, "ERROR AUDIO")
            SpeechRecognizer.ERROR_CLIENT -> Log.d(RetrofitClient.TAG, "ERROR CLIENT")
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Log.d(RetrofitClient.TAG, "ERROR_INSUFFICIENT_PERMISSIONS")
            SpeechRecognizer.ERROR_NETWORK -> Log.d(RetrofitClient.TAG, "ERROR NETWORK")
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> Log.d(RetrofitClient.TAG, "ERROR NETWORK TIMEOUT")
            SpeechRecognizer.ERROR_NO_MATCH -> Log.d(RetrofitClient.TAG, "ERROR NO MATCH")
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> Log.d(RetrofitClient.TAG, "ERROR RECOGNIZER BUSY")
            SpeechRecognizer.ERROR_SERVER -> Log.d(RetrofitClient.TAG, "ERROR SERVER")
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> Log.d(RetrofitClient.TAG, "ERROR SPEECH TIMEOUT")
        }
    }

    private fun clearChat(){
        voiceText.text = ""
        assistantText.text = ""
    }

    private fun speak(text : String){
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}